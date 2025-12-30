import org.openqa.selenium.By;
import java.util.*;

public class AILocator {

    private final By primary;
    private final List<By> alternatives;

    public AILocator(By primary, List<By> alternatives) {
        this.primary = primary;
        this.alternatives = alternatives;
    }

    public By getPrimary() {
        return primary;
    }

    public List<By> getAlternatives() {
        return alternatives;
    }
}
