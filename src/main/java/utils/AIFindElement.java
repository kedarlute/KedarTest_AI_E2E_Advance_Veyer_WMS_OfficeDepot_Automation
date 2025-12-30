import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class AIFindElement {

    private WebDriver driver;
    private WebDriverWait wait;

    public AIFindElement(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement find(String key, AILocator aiLocator) {

        // First Step is --- Try learned locator first
        if (AILocatorStore.get(key).isPresent()) {
            try {
                return wait.until(ExpectedConditions
                        .presenceOfElementLocated(AILocatorStore.get(key).get()));
            } catch (Exception ignored) {}
        }

        // Second Step --- Try primary locator
        try {
            WebElement element = wait.until(
                    ExpectedConditions.presenceOfElementLocated(aiLocator.getPrimary())
            );
            AILocatorStore.save(key, aiLocator.getPrimary());
            return element;
        } catch (Exception ignored) {}

        // Third Step --- Try alternative locators
        for (By alt : aiLocator.getAlternatives()) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.presenceOfElementLocated(alt)
                );
                AILocatorStore.save(key, alt);
                return element;
            } catch (Exception ignored) {}
        }

        throw new NoSuchElementException(
                "AI failed to locate element: " + key
        );
    }
}
