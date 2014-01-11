import java.util.ArrayList;
import java.util.HashMap;

public class DeductionChecker {


    HashMap<Long, ArrayList<Long>> startsMap;
    HashMap<Long, Boolean> checkedMap;
    HashMap<Long, String> stringsMap;
    ArrayList<Long> assumptions = new ArrayList<Long>();
    String alpha;
    long alphaHash;

    DeductionChecker() {
        startsMap = new HashMap<Long, ArrayList<Long>>();
        checkedMap = new HashMap<Long, Boolean>();
        stringsMap = new HashMap<Long, String>();
        assumptions = new ArrayList<Long>();
        alpha = "";
        alphaHash = 0;
    }

    DeductionChecker(ArrayList<Long> _assumptions, String _alpha, long _alphaHash) {
        startsMap = new HashMap<Long, ArrayList<Long>>();
        checkedMap = new HashMap<Long, Boolean>();
        stringsMap = new HashMap<Long, String>();
        assumptions = _assumptions;
        alpha = _alpha;
        alphaHash = _alphaHash;
    }

    void successfulCheck(Vertex vertex, String raw) {
        stringsMap.put(vertex.hash, raw);
        checkedMap.put(vertex.hash, true);
        if (Vertex.OPERATIONS.get(0).equals(vertex.operation)) {
            if (startsMap.containsKey(vertex.right.hash))
                startsMap.get(vertex.right.hash).add(vertex.left.hash);
            else {
                startsMap.put(vertex.right.hash, new ArrayList<Long>());
                startsMap.get(vertex.right.hash).add(vertex.left.hash);
            }
        }
    }

    String deductionCheck(String raw) {
        if (raw == null)
            return null;
        Vertex vertex = new Vertex(raw);
        if (vertex == null) {
            if (Configuration.DEBUG_MODE)
                System.out.println("null vertex.");
            return null;
        }
        if (Configuration.DEBUG_MODE)
            System.out.println("checker. raw:" + raw + ".  Hash - " + vertex.hash);

        for (int i = 1; i <= 10; i++)
            if (vertex.isAxiom(i)) {
                successfulCheck(vertex, raw);
                if (Configuration.DEBUG_MODE)
                    System.out.println("Correct - axiom - " + i);
                return (raw + "\r\n" + "(" + raw + ")->(" + alpha + "->(" + raw + "))" + "\r\n" + alpha + "->(" + raw + ")" + "\r\n");
            }
        for (int i = 0; i < assumptions.size(); i++)
            if (assumptions.get(i) == vertex.hash) {
                successfulCheck(vertex, raw);
                if (Configuration.DEBUG_MODE)
                    System.out.println("Correct - assumption");
                return (raw + "\r\n" + "(" + raw + ")->(" + alpha + "->(" + raw + "))" + "\r\n" + alpha + "->(" + raw + ")" + "\r\n");
            }
        if (vertex.hash == alphaHash) {
            successfulCheck(vertex, raw);
            if (Configuration.DEBUG_MODE)
                System.out.println("Correct - alpha");
            return aToaProof(alpha);
        }

        if (!startsMap.containsKey(vertex.hash))
            return null;
        ArrayList<Long> ourChecked = new ArrayList<Long>(startsMap.get(vertex.hash));
        for (int i = 0; i < ourChecked.size(); i++)
            if (checkedMap.containsKey(ourChecked.get(i)) && checkedMap.get(ourChecked.get(i))) {

                successfulCheck(vertex, raw);
                String start = stringsMap.get(ourChecked.get(i));
                if (Configuration.DEBUG_MODE)
                    System.out.println("Correct - modus ponsens - " + ourChecked.get(i));
                return "(" + alpha + "->(" + start + "))->" + "((" + alpha + "->" + "((" + start +
                        ")->(" + raw + ")))->(" + alpha + "->(" + raw + ")))" +
                        "\r\n" +
                        "((" + alpha + "->((" + start + ")->(" + raw + ")))->(" + alpha +
                        "->(" + raw + ")))" +
                        "\r\n" + alpha + "->(" + raw + ")" + "\r\n";
            }
        return null;
    }

    String aToaProof(String a) {
        return a + "->(" + a + "->" + a + ")" + "\r\n" +
                "(" + a + "->(" + a + "->" + a + "))->(" + a + "->((" + a + "->" + a + ")->" + a + "))->("
                + a + "->" + a + ")" + "\r\n" +
                "(" + a + "->((" + a + "->" + a + ")->" + a + "))->(" + a + "->" + a + ")" + "\r\n" +
                "(" + a + "->((" + a + "->" + a + ")->" + a + "))" + "\r\n" +
                "(" + a + "->" + a + ")" + "\r\n";
    }

}
