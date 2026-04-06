package utils;

/**
 * @author: Kedarnath Lute
  -- Failure pattern learning (timeouts, stale elements, intercepts)
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AIFlakyFailureAnalyzer {

    // Historical run data (could be replaced with DB or external storage)
    private static final Map<String, List<TestRun>> history = new ConcurrentHashMap<>();

    public static class TestRun {
        public long durationMs;
        public String environment;
        public Exception exception;
        public String locator;
        public boolean passed;

        public TestRun(long durationMs, String environment, Exception exception, String locator, boolean passed) {
            this.durationMs = durationMs;
            this.environment = environment;
            this.exception = exception;
            this.locator = locator;
            this.passed = passed;
        }
    }

    // Add run data
    public static void recordRun(String testName, TestRun run) {
        history.computeIfAbsent(testName, k -> new ArrayList<>()).add(run);
    }

    // Detect flakiness based on historical variance
    public static boolean isFlaky(String testName) {
        List<TestRun> runs = history.getOrDefault(testName, Collections.emptyList());
        if (runs.size() < 3) return false;

        long avg = runs.stream().mapToLong(r -> r.durationMs).sum() / runs.size();
        long variance = runs.stream().mapToLong(r -> Math.abs(r.durationMs - avg)).sum() / runs.size();

        long failCount = runs.stream().filter(r -> !r.passed).count();

        return variance > 1000 || failCount > 0; // heuristic threshold
    }

    // Classify root cause
    public static String classify(Exception e) {
        String msg = e.getMessage().toLowerCase();

        if (msg.contains("timeout")) return "SYNC_ISSUE";
        if (msg.contains("stale")) return "DOM_REFRESH";
        if (msg.contains("intercepted")) return "UI_OVERLAY";
        if (msg.contains("connection") || msg.contains("refused")) return "DEPENDENCY_FAILURE";

        return "UNKNOWN";
    }

    // Suggest self-healing strategy
    public static String suggestFix(Exception e) {
        String type = classify(e);
        switch (type) {
            case "SYNC_ISSUE": return "Increase wait or use explicit waits.";
            case "DOM_REFRESH": return "Re-locate element or refresh DOM.";
            case "UI_OVERLAY": return "Wait for overlay to disappear.";
            case "DEPENDENCY_FAILURE": return "Retry with fallback/mocks.";
            default: return "Manual investigation required.";
        }
    }

    // ML placeholder (could integrate with TensorFlow/ONNX)
    public static double predictFlakinessProbability(String testName) {
        List<TestRun> runs = history.getOrDefault(testName, Collections.emptyList());
        if (runs.isEmpty()) return 0.0;

        long failCount = runs.stream().filter(r -> !r.passed).count();
        return (double) failCount / runs.size();
    }
}
