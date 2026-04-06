import java.util.*;

/**
 * @author: Kedarnath Lute
    Smart Priority -- Build ML which ranks which tests to run first based on risk, code changes, 
 **/   

public class AISmartPriorityScheduler {

    public static class TestMeta {
        public String testName;
        public double pastFailureRate;   // 0.0 - 1.0
        public double businessCriticality; // 0.0 - 1.0
        public double codeChangeRisk;   // 0.0 - 1.0
        public long avgDurationMs;

        public TestMeta(String testName, double pastFailureRate,
                        double businessCriticality, double codeChangeRisk,
                        long avgDurationMs) {
            this.testName = testName;
            this.pastFailureRate = pastFailureRate;
            this.businessCriticality = businessCriticality;
            this.codeChangeRisk = codeChangeRisk;
            this.avgDurationMs = avgDurationMs;
        }
    }

    // ML-inspired scoring function
    public static double computePriorityScore(TestMeta meta) {
        // Weighted scoring model (could be replaced with ML inference)
        double score = (0.4 * meta.codeChangeRisk) +
                       (0.3 * meta.pastFailureRate) +
                       (0.2 * meta.businessCriticality) +
                       (0.1 * (1.0 / (1 + meta.avgDurationMs / 1000.0))); // prefer shorter tests
        return score;
    }

    // Rank tests by priority
    public static List<TestMeta> rankTests(List<TestMeta> tests) {
        tests.sort((a, b) -> Double.compare(computePriorityScore(b), computePriorityScore(a)));
        return tests;
    }

    // Suggest which tests to run first
    public static void suggestExecutionOrder(List<TestMeta> tests) {
        List<TestMeta> ranked = rankTests(tests);
        System.out.println("Suggested Execution Order:");
        for (TestMeta t : ranked) {
            System.out.printf("Test: %s | Score: %.3f%n", t.testName, computePriorityScore(t));
        }
    }
}
