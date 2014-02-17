import java.util.ArrayList;

public class AxiomsList {

    public static String RN = "\r\n";

    public static boolean axiom_1(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.right.operation))
            return false;
        return (v.left.equals(v.right.right));
    }

    public static boolean axiom_2(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.left.left == null || v.left.right == null || v.right.left.left == null || v.right.left.right == null ||
                v.right.right.left == null || v.right.right.right == null || v.right.left.right.left == null ||
                v.right.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.left.operation) ||
                !"->".equals(v.right.operation) ||
                !"->".equals(v.right.left.operation) ||
                !"->".equals(v.right.right.operation) ||
                !"->".equals(v.right.left.right.operation))
            return false;
        return (v.left.left.equals(v.right.left.left) && v.left.left.equals(v.right.right.left) &&
                v.left.right.equals(v.right.left.right.left) && v.right.right.right.equals(v.right.left.right.right));
    }

    public static boolean axiom_3(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.right.right.left == null || v.right.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.right.operation) ||
                !"&".equals(v.right.right.operation))
            return false;
        return (v.right.right.left.equals(v.left) && v.right.right.right.equals(v.right.left));
    }

    public static boolean axiom_4(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"&".equals(v.left.operation))
            return false;
        return (v.left.left.equals(v.right));
    }

    public static boolean axiom_5(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"&".equals(v.left.operation))
            return false;
        return (v.left.right.equals(v.right));
    }

    public static boolean axiom_6(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"|".equals(v.right.operation))
            return false;
        return (v.right.left.equals(v.left));
    }

    public static boolean axiom_7(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"|".equals(v.right.operation))
            return false;
        return (v.right.right.equals(v.left));
    }

    public static boolean axiom_8(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.left.left == null || v.left.right == null || v.right.left.left == null ||
                v.right.left.right == null || v.right.right.left == null || v.right.right.right == null ||
                v.right.right.left.left == null || v.right.right.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.left.operation) ||
                !"->".equals(v.right.operation) || !"->".equals(v.right.right.operation) || !"->".equals(v.right.left.operation)
                || !"|".equals(v.right.right.left.operation))
            return false;
        return (v.left.left.equals(v.right.right.left.left) &&
                v.left.right.equals(v.right.left.right) && v.left.right.equals(v.right.right.right) &&
                v.right.left.left.equals(v.right.right.left.right));
    }

    public static boolean axiom_9(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.left.left == null || v.left.right == null || v.right.left.left == null || v.right.left.right == null ||
                v.right.right.right == null || v.right.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.left.operation) ||
                !"->".equals(v.right.operation) || !"->".equals(v.right.left.operation) ||
                !"!".equals(v.right.right.operation) || !"!".equals(v.right.left.right.operation))
            return false;
        return (v.left.left.equals(v.right.right.right) &&
                v.left.left.equals(v.right.left.left) && v.right.left.right.right.equals(v.left.right));
    }

    public static boolean axiom_10(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.right == null ||
                v.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"!".equals(v.left.operation) || !"!".equals(v.left.right.operation))
            return false;
        return (v.left.right.right.equals(v.right));
    }


    //////////////////////////////////////////////////


    public static boolean freeToSubstitute(Vertex v, String x, String y, boolean qy) {
        if (v == null)
            return true;
        if ("@".equals(v.operation) || "?".equals(v.operation)) {
            if (x.equals(v.left.operation))
                return true;
            if (y.equals(v.left.operation))
                return freeToSubstitute(v.right, x, y, true);
            return freeToSubstitute(v.right, x, y, qy);
        }
        if (x.equals(v.operation))
            return (!qy);
        if (v.terms != null) {
            if (v.hasEntries(x))
                return (!qy);
            return true;
        }
        return (freeToSubstitute(v.left, x, y, qy) && freeToSubstitute(v.right, x, y, qy));
    }

    public static boolean freeToSubstitute(Vertex v, String x, Vertex y, boolean qy) {

        VertexMaker vertexMaker = new VertexMaker(y);
        for (int i = 0; i < vertexMaker.proposes.size(); i++)
            if (!freeToSubstitute(v, x, vertexMaker.proposes.get(i), qy))
                return false;
        return true;
    }

    public static void substitute(Vertex v, String x, String y) {                   //   Not term but only propositional can be substituted
        if (v == null)
            return;
        if ("@".equals(v.operation) || "?".equals(v.operation)) {
            if (!x.equals(v.left.operation) && !y.equals(v.left.operation))
                substitute(v.right, x, y);
            return;
        }
        if (x.equals(v.operation))
            v.operation = y;
        else {
            substitute(v.left, x, y);
            substitute(v.right, x, y);
        }
    }

    public static boolean axiom_11(Vertex v, String y) {
        if (v == null || v.left == null || v.right == null || v.left.right == null ||
                v.left.left == null)
            return false;
        if (!"->".equals(v.operation) || !"@".equals(v.left.operation))
            return false;
        Vertex temp = new Vertex(v.left.right);
        String x = v.left.left.operation;
        if (!freeToSubstitute(temp, x, y, false))
            return false;
        substitute(temp, x, y);
        return (v.right.equals(temp));
    }

    public static boolean axiom_12(Vertex v, String y) {
        if (v == null || v.left == null || v.right == null || v.right.right == null ||
                v.right.left == null)
            return false;
        if (!"->".equals(v.operation) || !"?".equals(v.right.operation))
            return false;
        Vertex temp = new Vertex(v.right.right);
        String x = v.right.left.operation;
        if (!freeToSubstitute(temp, x, y, false))
            return false;
        substitute(temp, x, y);
        return (v.left.equals(temp));
    }


    ////////////////////////////////////////////////

    public static ArrayList<String> aToaProof(String _a) {

        ArrayList<String> answer = new ArrayList<String>();

        String a = "(" + Vertex.deleteOuterBrackets(_a) + ")";

        answer.add(a + "->(" + a + "->" + a + ")");
        answer.add("(" + a + "->(" + a + "->" + a + "))->(" + a + "->((" + a + "->" + a + ")->" + a + "))->("
                + a + "->" + a + ")");
        answer.add("(" + a + "->((" + a + "->" + a + ")->" + a + "))->(" + a + "->" + a + ")");
        answer.add("(" + a + "->((" + a + "->" + a + ")->" + a + "))");
        answer.add("(" + a + "->" + a + ")");
        answer = Proof.packProof(answer);
        return answer;
    }


    /////////////////////////////////////////////////

    public static Proof Lemma_Contraposition(String _a, String _b) {
        Proof proof = new Proof();

        String a = "(" + Vertex.deleteOuterBrackets(_a) + ")";
        String b = "(" + Vertex.deleteOuterBrackets(_b) + ")";

        proof.addAssumption("(" + a + "->" + b + ")");
        proof.addAssumption("(!" + b + ")");
        proof.beta = "(!" + a + ")";

        proof.proofStrings.add("(" + a + "->" + b + ")->(" + a + "->!" + b + ")->!" + a + "");
        proof.proofStrings.add("" + a + "->" + b + "");
        proof.proofStrings.add("(" + a + "->!" + b + ")->!" + a + "");
        proof.proofStrings.add("!" + b + "->(" + a + "->!" + b + ")");
        proof.proofStrings.add("!" + b + "");
        proof.proofStrings.add("" + a + "->!" + b + "");
        proof.proofStrings.add("!" + a + "");

        proof.pack();
        if (!proof.fullDeduct())
            Helper.println("Lemma_Contraposition  and full_deduct error");
        proof.pack();
        return proof;
    }

    public static Proof Lemma_ExcessThird(String _a) {
        Proof proof = new Proof();

        String a = "(" + Vertex.deleteOuterBrackets(_a) + ")";

        proof.addProof("(" + a + "->" + a + "|!" + a + ")");
        proof.addProof(Lemma_Contraposition(a, "(" + a + "|!" + a + ")"));

        proof.addProof("!(" + a + "|!" + a + ")->!" + a + "");

        proof.addProof("!" + a + "->" + a + "|!" + a + "");
        proof.addProof(Lemma_Contraposition("(!" + a + ")", "(" + a + "|!" + a + ")"));

        proof.addProof("!(" + a + "|!" + a + ")->!!" + a + "");

        proof.addProof("(!(" + a + "|!" + a + ")->!" + a + ")->(!(" + a + "|!" + a + ")->!!" + a + ")->(!!(" + a + "|!" + a + "))");
        proof.addProof("(!(" + a + "|!" + a + ")->!!" + a + ")->(!!(" + a + "|!" + a + "))");
        proof.addProof("!!(" + a + "|!" + a + ")");
        proof.addProof("!!(" + a + "|!" + a + ")->(" + a + "|!" + a + ")");
        proof.addProof("" + a + "|!" + a + "");

        proof.beta = "(" + a + "|!" + a + ")";
        proof.pack();
        return proof;
    }

    public static Proof Lemma_SummarizeExcessPropose(Proof _proof, String _p) {           //TODO  we need to have correct beta!!!!

        //we expect correct proof to both x and !x

        Proof proof = new Proof(_proof);

        proof.addProof(Lemma_ExcessThird(_p));

        String p = "(" + Vertex.deleteOuterBrackets(_p) + ")";
        String a = "(" + Vertex.deleteOuterBrackets(proof.beta) + ")";

        proof.addProof("(" + p + "->" + a + ")->(!" + p + "->" + a + ")->((" + p + "|!" + p + ")->" + a + ")");
        proof.addProof("(!" + p + "->" + a + ")->((" + p + "|!" + p + ")->" + a + ")");
        proof.addProof("(" + p + "|!" + p + ")->" + a);
        proof.addProof(a);

        proof.pack();
        return proof;
    }

    public static Proof Lemma_KillLastAssumption(Proof proofTrue, Proof proofFalse) {        //TODO we need to have correct beta

        Proof t = new Proof(proofTrue);
        Proof f = new Proof(proofFalse);

        String p = proofTrue.assumptionsStrings.get(proofTrue.assumptionsStrings.size() - 1);
        String betaTemp = t.beta;
        t.singleDeduct();
        f.singleDeduct();

        t.addProof(f);
        t.beta = betaTemp;

        Proof proof = new Proof(Lemma_SummarizeExcessPropose(t, p));

        proof.beta = betaTemp;
        proof.pack();
        return proof;
    }
}
