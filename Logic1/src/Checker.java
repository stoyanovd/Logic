import java.util.ArrayList;
import java.util.HashMap;

public class Checker {


    HashMap<Long, ArrayList<Long>> startsMap;
    HashMap<Long, Boolean> checkedMap;

    Checker() {
        startsMap = new HashMap<Long, ArrayList<Long>>();
        checkedMap = new HashMap<Long, Boolean>();
    }

    void successfulCheck(Vertex vertex) {
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

    boolean singleCheck(String raw) {
        Vertex vertex = new Vertex(raw);
        if (ProofChecker.DEBUG_MODE)
            System.out.println("Hash - " + vertex.hash);
        if (vertex == null)
            return true;
        if (checkedMap.containsKey(vertex.hash) && checkedMap.get(vertex.hash)) {
            successfulCheck(vertex);
            return true;
        }
        for (int i = 1; i <= 10; i++)
            if (vertex.isAxiom(i)) {
                successfulCheck(vertex);
                if (ProofChecker.DEBUG_MODE)
                    System.out.println("Correct - axiom - " + i);
                return true;
            }
        if (!startsMap.containsKey(vertex.hash))
            return false;
        ArrayList<Long> ourChecked = new ArrayList<Long>(startsMap.get(vertex.hash));
        for (int i = 0; i < ourChecked.size(); i++)
            if (checkedMap.containsKey(ourChecked.get(i)) && checkedMap.get(ourChecked.get(i))) {
                successfulCheck(vertex);
                if (ProofChecker.DEBUG_MODE)
                    System.out.println("Correct - modus ponsens - " + ourChecked.get(i));
                return true;
            }
        return false;
    }

}
