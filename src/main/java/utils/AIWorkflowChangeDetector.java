import java.util.*;

/**
 * @author: Kedarnath Lute
    NLP and ML detect changes in application UI or workflows/Journey; with changed navigation paths
 **/   

public class AIWorkflowChangeDetector {

    public static class UserAction {
        public String type;   // CLICK, INPUT, NAVIGATE
        public String target; // locator or page
        public String value;  // input value or URL

        public UserAction(String type, String target, String value) {
            this.type = type;
            this.target = target;
            this.value = value;
        }

        @Override
        public String toString() {
            return type + " -> " + target + " : " + value;
        }
    }

    public static class UserJourney {
        public String name;
        public List<UserAction> actions;

        public UserJourney(String name) {
            this.name = name;
            this.actions = new ArrayList<>();
        }

        public void addAction(UserAction action) {
            actions.add(action);
        }
    }

    // Baseline journeys (expected workflows)
    private static final Map<String, UserJourney> baselineJourneys = new HashMap<>();

    public static void recordBaseline(String name, UserJourney journey) {
        baselineJourneys.put(name, journey);
    }

    // Compare current journey with baseline
    public static List<String> detectChanges(String name, UserJourney current) {
        List<String> changes = new ArrayList<>();
        UserJourney baseline = baselineJourneys.get(name);

        if (baseline == null) {
            changes.add("No baseline available for " + name);
            return changes;
        }

        int minSize = Math.min(baseline.actions.size(), current.actions.size());
        for (int i = 0; i < minSize; i++) {
            UserAction base = baseline.actions.get(i);
            UserAction curr = current.actions.get(i);

            if (!base.type.equals(curr.type) || !base.target.equals(curr.target)) {
                changes.add("Step " + (i+1) + " changed: expected " + base + " but got " + curr);
            }
        }

        if (current.actions.size() > baseline.actions.size()) {
            changes.add("Extra steps detected: " + (current.actions.size() - baseline.actions.size()));
        } else if (current.actions.size() < baseline.actions.size()) {
            changes.add("Missing steps detected: " + (baseline.actions.size() - current.actions.size()));
        }

        return changes;
    }

    // Classify type of change
    public static String classifyChange(List<String> changes) {
        if (changes.stream().anyMatch(c -> c.contains("Extra steps"))) return "NEW_NAVIGATION_PATH";
        if (changes.stream().anyMatch(c -> c.contains("Missing steps"))) return "WORKFLOW_DEVIATION";
        if (changes.stream().anyMatch(c -> c.contains("changed"))) return "UI_LOCATOR_DRIFT";
        return "UNKNOWN";
    }

    // Generate updated test script
    public static String generateUpdatedTest(UserJourney journey) {
        StringBuilder sb = new StringBuilder();
        sb.append("public void test_" + journey.name + "() {\n");
        sb.append("    WebDriver driver = new ChromeDriver();\n");
        sb.append("    driver.get(\"http://app-under-test\");\n");

        for (UserAction action : journey.actions) {
            switch (action.type) {
                case "CLICK":
                    sb.append("    driver.findElement(By.cssSelector(\"" + action.target + "\")).click();\n");
                    break;
                case "INPUT":
                    sb.append("    driver.findElement(By.cssSelector(\"" + action.target + "\")).sendKeys(\"" + action.value + "\");\n");
                    break;
                case "NAVIGATE":
                    sb.append("    driver.get(\"" + action.value + "\");\n");
                    break;
            }
        }

        sb.append("    driver.quit();\n");
        sb.append("}\n");
        return sb.toString();
    }
}
