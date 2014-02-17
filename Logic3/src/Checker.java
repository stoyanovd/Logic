import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Checker {


    HashMap<Long, ArrayList<Long>> startsMap;
    HashMap<Long, Boolean> checkedMap;
    ArrayList<Long> assumptions;
    HashMap<Pair<Long, Long>, Boolean> impls;
    ArrayList<String> proposes;

    String possibleError;

    Checker() {
        startsMap = new HashMap<Long, ArrayList<Long>>();
        checkedMap = new HashMap<Long, Boolean>();
        assumptions = new ArrayList<Long>();
        impls = new HashMap<Pair<Long, Long>, Boolean>();
        proposes = new ArrayList<String>();
    }

    Checker(Proof proof) {
        this();
        assumptions = new ArrayList<Long>(proof.assumptions);
        proposes = new ArrayList<String>(proof.proposes);
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
        if ("->".equals(vertex.operation))
            impls.put(new Pair(vertex.left.hash, vertex.right.hash), true);
    }

    boolean singleCheck(String raw) {
        return singleCheck(new Vertex(raw));
    }

    boolean singleCheck(Vertex vertex) {

        Helper.parsePrintln("Hash - " + vertex.hash);
        if (vertex == null)
            return true;
        if (checkedMap.containsKey(vertex.hash) && checkedMap.get(vertex.hash)) {
            successfulCheck(vertex);
            return true;
        }
        for (int i = 1; i <= 10; i++)
            if (vertex.isAxiom(i)) {
                successfulCheck(vertex);
                Helper.checkPrintln("Correct - axiom - " + i);
                return true;
            }

        if (Configuration.QUANTUM_NUMERATION)
            for (int i = 1; i <= 2; i++)
                for (int j = 0; j < proposes.size(); j++)
                    if (vertex.isQuantumAxiom(i, proposes.get(j))) {
                        successfulCheck(vertex);                                                         //TODO        possible errors
                        Helper.println("Correct - quantum axiom - " + i + "   and proposes = " + j);
                        return true;
                    }

        for (int i = 0; i < assumptions.size(); i++)
            if (assumptions.get(i) == vertex.hash) {
                successfulCheck(vertex);
                Helper.checkPrintln("Correct - assumption - " + i);
                return true;
            }


        if (Configuration.QUANTUM_NUMERATION)

            if (vertex.left != null && vertex.right != null && vertex.right.left != null && vertex.right.right != null)
                if ("->".equals(vertex.operation) && "@".equals(vertex.right.operation) &&
                        impls.containsKey(new Pair<Long, Long>(vertex.left.hash, vertex.right.right.hash)) &&
                        impls.get(new Pair<Long, Long>(vertex.left.hash, vertex.right.right.hash))) {
                    if (vertex.left.hasNotFreeEntries(vertex.right.left.operation)) {
                        successfulCheck(vertex);
                        Helper.println("Correct - modus ponens quantum @ - " + vertex.left.hash + "   " + vertex.right.right.hash);
                        return true;
                    } else {
                        possibleError = " ( " + vertex.right.left.operation + " ) has free entries in ( " + vertex.left + " ) ";
                        Helper.println("possible error   -  " + possibleError);
                    }
                }
        if (Configuration.QUANTUM_NUMERATION)

            if (vertex.left != null && vertex.right != null && vertex.left.left != null && vertex.left.right != null)
                if ("->".equals(vertex.operation) && "?".equals(vertex.left.operation) &&
                        impls.containsKey(new Pair<Long, Long>(vertex.left.right.hash, vertex.right.hash)) &&
                        impls.get(new Pair<Long, Long>(vertex.left.right.hash, vertex.right.hash))) {
                    if (vertex.right.hasNotFreeEntries(vertex.left.left.operation)) {
                        successfulCheck(vertex);
                        Helper.println("Correct - modus ponens quantum ? - " + vertex.left.right.hash + "   " + vertex.right.hash);
                        return true;
                    } else {
                        possibleError = " ( " + vertex.left.left.operation + " ) has free entries in ( " + vertex.right + " ) ";
                        Helper.println("possible error   -  " + possibleError);
                    }
                }


        if (!startsMap.containsKey(vertex.hash))
            return false;
        ArrayList<Long> ourChecked = new ArrayList<Long>(startsMap.get(vertex.hash));
        for (int i = 0; i < ourChecked.size(); i++)
            if (checkedMap.containsKey(ourChecked.get(i)) && checkedMap.get(ourChecked.get(i))) {
                successfulCheck(vertex);
                Helper.checkPrintln("Correct - modus ponens - " + ourChecked.get(i));
                return true;
            }

        return false;
    }

}
