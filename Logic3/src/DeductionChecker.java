import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class DeductionChecker {

    HashMap<Long, ArrayList<Long>> startsMap;
    HashMap<Long, Boolean> checkedMap;
    ArrayList<Long> assumptions;
    HashMap<Pair<Long, Long>, Boolean> impls;
    ArrayList<String> proposes;

    String possibleError;

    HashMap<Long, String> stringsMap;
    String alpha;
    long alphaHash;
    Vertex alphaVertex;

    DeductionChecker() {
        startsMap = new HashMap<Long, ArrayList<Long>>();
        checkedMap = new HashMap<Long, Boolean>();
        assumptions = new ArrayList<Long>();
        impls = new HashMap<Pair<Long, Long>, Boolean>();
        proposes = new ArrayList<String>();

        stringsMap = new HashMap<Long, String>();
        alpha = "";
        alphaHash = 0;
        alphaVertex = new Vertex();
    }

    DeductionChecker(Proof proof) {
        this();
        assumptions = new ArrayList<Long>(proof.assumptions.subList(0, proof.assumptions.size() - 1));
        proposes = new ArrayList<String>(proof.proposes);
        alpha = "(" + Vertex.deleteOuterBrackets(proof.assumptionsStrings.get(proof.assumptionsStrings.size() - 1)) + ")";
        alphaVertex = new Vertex(alpha);
        alphaHash = alphaVertex.hash;
    }

    void successfulCheck(Vertex vertex, String proofString) {
        stringsMap.put(vertex.hash, proofString);
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

    public ArrayList<String> deductionCheck(String raw) {

        if (raw == null)
            throw new NullPointerException();

        ArrayList<String> answer = new ArrayList<String>();
        Vertex vertex = new Vertex(raw);
        Helper.deductPrintln("Hash - " + vertex.hash);

        if (checkedMap.containsKey(vertex.hash) && checkedMap.get(vertex.hash)) {
            successfulCheck(vertex, raw);
            return new ArrayList<String>();
        }
        for (int i = 1; i <= 10; i++)
            if (vertex.isAxiom(i)) {
                successfulCheck(vertex, raw);
                Helper.deductPrintln("Correct - axiom - " + i);
                answer.add("(" + raw + ")");
                answer.add("((" + raw + ")->(" + alpha + "->(" + raw + ")))");
                answer.add("(" + alpha + "->(" + raw + "))");

                answer = Proof.packProof(answer);
                return answer;
            }

        if (Configuration.QUANTUM_NUMERATION)

            for (int i = 1; i <= 2; i++)
                for (int j = 0; j < proposes.size(); j++)
                    if (vertex.isQuantumAxiom(i, proposes.get(j))) {
                        successfulCheck(vertex, raw);                                                         //TODO        possible errors
                        Helper.deductPrintln("Correct - quantum axiom - " + i + "   and proposes = " + j);
                        answer.add("(" + raw +")");
                        answer.add("(" + raw + ")->(" + alpha + "->(" + raw + "))");
                        answer.add(alpha + "->(" + raw + ")");
                        return answer;
                    }


        for (int i = 0; i < assumptions.size(); i++)
            if (assumptions.get(i) == vertex.hash) {
                successfulCheck(vertex, raw);
                Helper.deductPrintln("Correct - assumption - " + i);
                answer.add("(" + raw + ")");
                answer.add("((" + raw + ")->(" + alpha + "->(" + raw + ")))");
                answer.add("(" + alpha + "->(" + raw + "))");

                answer = Proof.packProof(answer);
                return answer;
            }

        if (vertex.equals(alphaVertex)) {
            successfulCheck(vertex, raw);
            Helper.deductPrintln("Correct - alpha");

            answer = new ArrayList<String>(AxiomsList.aToaProof(alpha));
            answer = Proof.packProof(answer);
            return answer;
        }

        if (Configuration.QUANTUM_NUMERATION)                                                               //TODO     make nice proofs of Quantum

            if (vertex.left != null && vertex.right != null && vertex.right.left != null && vertex.right.right != null)
                if ("->".equals(vertex.operation) && "@".equals(vertex.right.operation) &&
                        impls.containsKey(new Pair<Long, Long>(vertex.left.hash, vertex.right.right.hash)) &&
                        impls.get(new Pair<Long, Long>(vertex.left.hash, vertex.right.right.hash))) {
                    if (vertex.left.hasNotFreeEntries(vertex.right.left.operation)) {
                        successfulCheck(vertex, raw);
                        Helper.deductPrintln("Correct - modus ponens quantum @ - " + vertex.left.hash + "   " + vertex.right.right.hash);

                        String leftString = vertex.left.getStringVersion();
                        String rightString = vertex.right.right.getStringVersion();

                        Proof proof = new Proof(SimpleProofs.getAnd(alpha, leftString, rightString));
                        proof.addProof(alpha + "&" + leftString +"->@" + vertex.right.left.operation + "(" + rightString +")");
                        proof.addProof(SimpleProofs.getFollow(alpha, leftString, "@" + vertex.right.left.operation + "(" + rightString +")"));
                        return proof.proofStrings;
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
                        successfulCheck(vertex, raw);
                        Helper.deductPrintln("Correct - modus ponens quantum ? - " + vertex.left.right.hash + "   " + vertex.right.hash);

                        String leftString = vertex.left.right.getStringVersion();
                        String rightString = vertex.right.getStringVersion();

                        Proof proof = new Proof(SimpleProofs.getSwapFollow(alpha, leftString, rightString));
                        proof.addProof("?" + vertex.left.left.operation + "(" + leftString +")->" + alpha + "->" + rightString);
                        proof.addProof(SimpleProofs.getSwapFollow("?" + vertex.left.left.operation + "(" + leftString +")", alpha, rightString));
                        return proof.proofStrings;
                    } else {
                        possibleError = " ( " + vertex.left.left.operation + " ) has free entries in ( " + vertex.right + " ) ";
                        Helper.println("possible error   -  " + possibleError);
                    }
                }


        if (!startsMap.containsKey(vertex.hash)) {
            Helper.println("Strange situation - we haven't got key, but we think that this is a correct proof.");
            //throw new NullPointerException();
            return null;
        }
        ArrayList<Long> ourChecked = new ArrayList<Long>(startsMap.get(vertex.hash));
        for (int i = 0; i < ourChecked.size(); i++)
            if (checkedMap.containsKey(ourChecked.get(i)) && checkedMap.get(ourChecked.get(i))) {
                successfulCheck(vertex, raw);
                Helper.deductPrintln("Correct - modus ponens - " + ourChecked.get(i));
                String start = stringsMap.get(ourChecked.get(i));

                answer.add("((" + alpha + "->(" + start + "))->" + "((" + alpha + "->" + "((" + start +
                        ")->(" + raw + ")))->(" + alpha + "->(" + raw + "))))");
                answer.add("(((" + alpha + "->((" + start + ")->(" + raw + ")))->(" + alpha +
                        "->(" + raw + "))))");
                answer.add("(" + alpha + "->(" + raw + "))");

                answer = Proof.packProof(answer);
                return answer;
            }

        Helper.println("Strange situation - we do nothing, but we think that this is a correct proof.");
        return null;
    }
}
