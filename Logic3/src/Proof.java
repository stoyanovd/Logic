import java.util.ArrayList;
import java.util.HashMap;

public class Proof {

    String beta;
    ArrayList<Long> assumptions;
    ArrayList<String> assumptionsStrings;
    ArrayList<String> proofStrings;
    ArrayList<String> proposes;                                        //  i don't fill it in a lot of places

    int firstWrongString = -1;
    String possibleError;

    Proof() {
        beta = "";
        assumptions = new ArrayList<Long>();
        proofStrings = new ArrayList<String>();
        assumptionsStrings = new ArrayList<String>();
        proposes = new ArrayList<String>();
    }

    Proof(Proof p) {
        beta = p.beta;
        assumptions = new ArrayList<Long>(p.assumptions);
        proofStrings = new ArrayList<String>(p.proofStrings);
        assumptionsStrings = new ArrayList<String>(p.assumptionsStrings);
        proposes = new ArrayList<String>(p.proposes);
        possibleError = p.possibleError;
        firstWrongString = p.firstWrongString;
    }

    Proof(String header, ArrayList<String> proofs) {
        this();
        getAssumptions(header);
        proofStrings = new ArrayList<String>(proofs);                       //If we really need strings?
        VertexMaker vertexMaker = new VertexMaker();
        for (int i = 0; i < proofStrings.size(); i++)
            new Vertex(proofStrings.get(i), vertexMaker);
        proposes = new ArrayList<String>(vertexMaker.proposes);
    }

    public boolean getAssumptions(String _raw) {

        if (_raw == null)
            return false;

        String raw = _raw;
        if ((int) _raw.charAt(0) == 65279)
            raw = _raw.substring(1);
        String[] tempStrings = raw.split("\\|-");
        for (int i = 0; i < tempStrings.length; i++)
            Helper.parsePrintln("split |-   :" + tempStrings[i]);
        if (tempStrings.length < 2 || tempStrings[1] == null || "".equals(tempStrings[1])) {
            Helper.parsePrintln("split  -  there is not beta");
            return false;
        }
        beta = tempStrings[1];
        String temp = tempStrings[0];
        tempStrings = temp.split(",");                                                        //  here NEW array is created???
        for (int i = 0; i < tempStrings.length; i++)
            Helper.parsePrintln("split ,   :" + tempStrings[i]);
        for (int i = 0; i < tempStrings.length; i++) {
            Vertex vertex = new Vertex(tempStrings[i]);
            assumptions.add(vertex.hash);
            assumptionsStrings.add(tempStrings[i]);
            Helper.parsePrintln("Assumption:" + tempStrings[i] + "; hash = " + vertex.hash + " first char=" + (tempStrings[i].length() == 0 ? "_" : (int) (tempStrings[i].charAt(0))));
        }
        return true;
    }

    public boolean isCorrectProofWithHeader() {
        if (beta == null || proofStrings == null) {

            Helper.println("null beta or sth similar");
            return false;
        }
        Vertex vertex1 = new Vertex(beta);
        Vertex vertex2 = new Vertex(proofStrings.get(proofStrings.size() - 1));
        if (!vertex1.equals(vertex2)) {

            Helper.println("beta not equals to last");
            return false;
        }

        return isCorrectProof();
    }

    public boolean isCorrectProof() {
        if (proofStrings == null)
            return true;                                                    //    I think it is right step  -  empty proof is correct
        Checker checker = new Checker(this);
        for (int i = 0; i < proofStrings.size(); i++)
            if (!checker.singleCheck(proofStrings.get(i))) {
                firstWrongString = i;
                possibleError = checker.possibleError;
                Helper.println("Wrong proof - line: " + i);
                return false;
            }
        return true;
    }

    public boolean singleDeduct() {
        if (assumptions == null || assumptions.size() == 0)
            return false;
        DeductionChecker deductionChecker = new DeductionChecker(this);
        Proof newProof = new Proof();
        for (int i = 0; i < proofStrings.size(); i++)
            if (proofStrings.get(i) != null || !"".equals(proofStrings.get(i)))
                newProof.proofStrings.addAll(deductionChecker.deductionCheck(proofStrings.get(i)));

        beta = "((" + Vertex.deleteOuterBrackets(assumptionsStrings.get(0)) + ")->(" + Vertex.deleteOuterBrackets(beta) + "))";                                           //  excess  brackets
        proposes = new ArrayList<String>(proposes);
        assumptions = new ArrayList<Long>(assumptions.subList(0, assumptions.size() - 1));
        assumptionsStrings = new ArrayList<String>(assumptionsStrings.subList(0, assumptionsStrings.size() - 1));
        proofStrings = new ArrayList<String>(newProof.proofStrings);

        pack();

        return true;
    }

    public boolean fullDeduct() {

        pack();                                     //TODO    isn't it too slowly?????????

        if (assumptions == null)
            return true;
        while (assumptions.size() > 0) {
            if (!singleDeduct())
                return false;
        }
        pack();
        return true;
    }

    public void addAssumption(String a) {
        assumptionsStrings.add(a);
        assumptions.add(MyHash.hashVertex(a));
    }

    public void addProof(Proof p) {
        if (p != null)
            proofStrings.addAll(p.proofStrings);
    }

    public void addProof(String s) {
        if (s != null)
            proofStrings.add(s);
    }

    public void pack() {
        if (proofStrings != null)
            for (int i = 0; i < proofStrings.size(); i++) {
                proofStrings.set(i, "(" + Vertex.deleteOuterBrackets(proofStrings.get(i)) + ")");
            }
        if (assumptionsStrings != null)                                                                //TODO    safe  hashes??????
            for (int i = 0; i < assumptionsStrings.size(); i++) {
                assumptionsStrings.set(i, "(" + Vertex.deleteOuterBrackets(assumptionsStrings.get(i)) + ")");
            }
        if (beta != null && !"".equals(beta))
            beta = "(" + Vertex.deleteOuterBrackets(beta) + ")";
    }

    public static ArrayList<String> packProof(ArrayList<String> strings) {
        ArrayList<String> answer = new ArrayList<String>();
        for (int i = 0; i < strings.size(); i++) {
            answer.add("(" + Vertex.deleteOuterBrackets(strings.get(i)) + ")");
        }
        return answer;
    }

    public void simplify() {
        HashMap<Long, Boolean> exists = new HashMap<Long, Boolean>();
        ArrayList<String> newProofs = new ArrayList<String>();

        for (int i = 0; i < proofStrings.size(); i++) {
            long thisHash = MyHash.hashVertex(proofStrings.get(i));
            if (!(exists.containsKey(thisHash) && exists.get(thisHash))) {
                newProofs.add(proofStrings.get(i));
                exists.put(thisHash, true);
            }
        }
        proofStrings.clear();                                                    //TODO      Is this copying of temp to permanent correct and optimal?
        proofStrings.addAll(newProofs);
        //proofStrings = new ArrayList<String>(newProofs);
    }

}
