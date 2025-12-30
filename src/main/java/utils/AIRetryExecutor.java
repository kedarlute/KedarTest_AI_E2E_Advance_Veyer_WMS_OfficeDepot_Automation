public class AIRetryExecutor {

    private static final int MAX_RETRY = 2;

    public static void execute(Runnable testStep) {
        int attempt = 0;

        while (attempt <= MAX_RETRY) {
            try {
                testStep.run();
                return;
            } catch (Exception e) {
                attempt++;

                if (!FlakyFailureAnalyzer.isFlaky(e) || attempt > MAX_RETRY) {
                    throw e; // real failure
                }

                String reason = FlakyFailureAnalyzer.classify(e);
                System.out.println("AI detected flaky failure: " + reason);
                selfHeal(reason);
            }
        }
    }

    private static void selfHeal(String reason) {
        try {
            if (reason.equals("SYNC_ISSUE")) {
                Thread.sleep(2000); // wait stabilization
            }
            if (reason.equals("DOM_REFRESH")) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ignored) {}
    }
}
