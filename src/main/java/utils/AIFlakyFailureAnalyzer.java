package utils;

/**
 * @author: Kedarnath Lute
  -- Failure pattern learning (timeouts, stale elements, intercepts)
 */
public class AIFlakyFailureAnalyzer {

    public static boolean isFlaky(Exception e) {
        String msg = e.getMessage().toLowerCase();

        return msg.contains("timeout") ||
               msg.contains("stale element") ||
               msg.contains("element click intercepted") ||
               msg.contains("not clickable");
    }

    public static String classify(Exception e) {
        String msg = e.getMessage().toLowerCase();

        if (msg.contains("timeout")) return "SYNC_ISSUE";
        if (msg.contains("stale")) return "DOM_REFRESH";
        if (msg.contains("intercepted")) return "UI_OVERLAY";

        return "UNKNOWN";
    }
}
