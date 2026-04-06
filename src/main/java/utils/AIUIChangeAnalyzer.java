import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

/**
 * @author: Kedarnath Lute
    UI changes - Build ML models which detect subtle changes in the DOM, CSS, or workflows that would normally break locators.
**/

public class AIUIChangeAnalyzer {

    // Store baseline DOM snapshots for workflows
    private static final Map<String, Document> baselineSnapshots = new HashMap<>();

    // Record baseline workflow snapshot
    public static void recordBaseline(String workflowName, String htmlSnapshot) {
        baselineSnapshots.put(workflowName, Jsoup.parse(htmlSnapshot));
    }

    // Compare current DOM with baseline
    public static List<String> detectDomChanges(String workflowName, String currentHtml) {
        Document baseline = baselineSnapshots.get(workflowName);
        Document current = Jsoup.parse(currentHtml);

        List<String> changes = new ArrayList<>();
        if (baseline == null) {
            changes.add("No baseline available for workflow: " + workflowName);
            return changes;
        }

        // Compare element existence
        for (Element baselineEl : baseline.getAllElements()) {
            String cssSelector = baselineEl.cssSelector();
            Element currentEl = current.selectFirst(cssSelector);

            if (currentEl == null) {
                changes.add("Missing element: " + cssSelector);
            } else {
                // Compare attributes
                if (!baselineEl.attributes().equals(currentEl.attributes())) {
                    changes.add("Attribute change in: " + cssSelector);
                }
            }
        }
        return changes;
    }

    // ML-based anomaly detection placeholder
    public static String classifyChange(String workflowName, List<String> changes) {
        if (changes.stream().anyMatch(c -> c.contains("Missing element"))) {
            return "LOCATOR_BREAK";
        }
        if (changes.stream().anyMatch(c -> c.contains("Attribute change"))) {
            return "CSS_DRIFT";
        }
        return "UNKNOWN";
    }

    // Suggest self-healing strategies
    public static String suggestFix(String classification) {
        switch (classification) {
            case "LOCATOR_BREAK": return "Use resilient locators (XPath axes, relative CSS).";
            case "CSS_DRIFT": return "Adapt to style changes (wait for visibility, dynamic z-index).";
            default: return "Manual workflow validation required.";
        }
    }
}
