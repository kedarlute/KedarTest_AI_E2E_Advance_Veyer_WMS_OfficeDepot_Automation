package utils;

/**
 * @author: Kedarnath Lute
 # Analyze logs → predict failure → suggest fix
 */
import java.nio.file.*;
import java.io.IOException;

public class FailurePredictor {

    public static void main(String[] args) throws IOException {
        String logs = Files.readString(Path.of(args[0])).toLowerCase();

        if (logs.contains("timeout")) {
            System.out.println("PREDICTION: INFRA_TIMEOUT");
            System.out.println("ACTION: Increase wait or scale agents");
        }
        else if (logs.contains("nullpointerexception")) {
            System.out.println("PREDICTION: CODE_DEFECT");
            System.out.println("ACTION: Review recent commits");
        }
        else if (logs.contains("connection refused")) {
            System.out.println("PREDICTION: SERVICE_DOWN");
            System.out.println("ACTION: Restart service");
        }
        else {
            System.out.println("PREDICTION: UNKNOWN");
            System.out.println("ACTION: Manual investigation");
        }
    }
}
