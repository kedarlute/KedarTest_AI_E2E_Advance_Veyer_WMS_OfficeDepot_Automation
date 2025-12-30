package utils;

/**
 * @author: Kedarnath Lute
 Run only impacted tests based on:
    1> Changed files
    2> Historical mapping (can be replaced by ML model)
 */
import java.util.*;

public class AITestSelector {

    private static final Map<String, List<String>> testMap = new HashMap<>();

    static {
        testMap.put("login", Arrays.asList("LoginTest", "AuthTest"));
        testMap.put("payment", Arrays.asList("PaymentTest"));
        testMap.put("profile", Arrays.asList("ProfileTest"));
    }

    public static void main(String[] args) {
        String changedFiles = args[0].toLowerCase();
        Set<String> selectedTests = new HashSet<>();

        for (String key : testMap.keySet()) {
            if (changedFiles.contains(key)) {
                selectedTests.addAll(testMap.get(key));
            }
        }

        if (selectedTests.isEmpty()) {
            selectedTests.add("SmokeTest");
        }

        selectedTests.forEach(System.out::println);
    }
}
