package utils;

/**
 * @author: Kedarnath Lute
--- Semantic XPath (label, text, neighbors- following)
 */
import org.openqa.selenium.By;

public class SemanticXPath {

    public static By inputByLabel(String labelText) {
        return By.xpath("//label[normalize-space()='" + labelText +
                "']/following-sibling::input");
    }

    public static By buttonByText(String text) {
        return By.xpath("//button[normalize-space()='" + text + "']");
    }
}
