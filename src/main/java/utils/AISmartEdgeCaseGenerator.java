import java.util.*;

/**
 * @author: Kedarnath Lute
AI script which suggests additional edge cases, Empty inputs, special characters, extreme values.
**/

public class AISmartEdgeCaseGenerator {

    public static class EdgeCase {
        public String input;
        public String category;
        public String description;

        public EdgeCase(String input, String category, String description) {
            this.input = input;
            this.category = category;
            this.description = description;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s -> %s", category, input, description);
        }
    }

    // Generate edge cases based on field type
    public static List<EdgeCase> generateForField(String fieldType) {
        List<EdgeCase> cases = new ArrayList<>();

        switch (fieldType.toLowerCase()) {
            case "text":
                cases.add(new EdgeCase("", "EMPTY", "Empty string input"));
                cases.add(new EdgeCase("   ", "EMPTY", "Whitespace-only input"));
                cases.add(new EdgeCase("<script>alert(1)</script>", "SPECIAL", "HTML/JS injection"));
                cases.add(new EdgeCase("!@#$%^&*()", "SPECIAL", "Special characters"));
                cases.add(new EdgeCase("😊🚀🔥", "SPECIAL", "Emoji input"));
                cases.add(new EdgeCase("A".repeat(5000), "EXTREME", "Very long string"));
                break;

            case "number":
                cases.add(new EdgeCase("0", "BOUNDARY", "Zero value"));
                cases.add(new EdgeCase("-1", "BOUNDARY", "Negative number"));
                cases.add(new EdgeCase("999999999", "EXTREME", "Very large number"));
                cases.add(new EdgeCase("NaN", "SPECIAL", "Not-a-Number"));
                break;

            case "date":
                cases.add(new EdgeCase("", "EMPTY", "Empty date"));
                cases.add(new EdgeCase("0000-00-00", "BOUNDARY", "Invalid date"));
                cases.add(new EdgeCase("9999-12-31", "EXTREME", "Far future date"));
                cases.add(new EdgeCase("1970-01-01", "BOUNDARY", "Epoch boundary"));
                break;

            default:
                cases.add(new EdgeCase("", "EMPTY", "Generic empty input"));
                cases.add(new EdgeCase("###", "SPECIAL", "Generic special characters"));
        }

        return cases;
    }

    // ML-inspired prioritization (placeholder)
    public static List<EdgeCase> prioritize(List<EdgeCase> cases) {
        // Simple heuristic: prioritize SPECIAL > EXTREME > EMPTY
        cases.sort((a, b) -> {
            List<String> order = Arrays.asList("SPECIAL", "EXTREME", "EMPTY", "BOUNDARY");
            return Integer.compare(order.indexOf(a.category), order.indexOf(b.category));
        });
        return cases;
    }

    // Suggest edge cases for a workflow
    public static void suggestForWorkflow(Map<String, String> fields) {
        System.out.println("Suggested Edge Cases:");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            List<EdgeCase> cases = prioritize(generateForField(entry.getValue()));
            System.out.println("Field: " + entry.getKey());
            cases.forEach(c -> System.out.println("  " + c));
        }
    }
}
