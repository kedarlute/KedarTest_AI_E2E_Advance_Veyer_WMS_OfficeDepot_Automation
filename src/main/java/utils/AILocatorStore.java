import org.openqa.selenium.By;
import java.util.*;

public class AILocatorStore {

    private static final Map<String, By> learnedLocators = new HashMap<>();

    public static void save(String key, By locator) {
        learnedLocators.put(key, locator);
    }

    public static Optional<By> get(String key) {
        return Optional.ofNullable(learnedLocators.get(key));
    }
}
