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
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////

    /*
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
        if (v.left != null && v.right != null)
            if ("@".equals(v.operation) || "?".equals(v.operation)) {
                if (!x.equals(v.left.operation) && !y.equals(v.left.operation))
                    if (v.right != null)
                        substitute(v.right, x, y);
                v.hash = MyHash.hash(v);
                return;
            }

        if (v.terms != null && v.terms.size() > 0) {
            for (int i = 0; i < v.terms.size(); i++)
                substitute(v.terms.get(i), x, y);
        } else if (x.equals(v.operation))
            v.operation = y;
        else {
            if (v.left != null)
                substitute(v.left, x, y);
            if (v.right != null)
                substitute(v.right, x, y);
        }
        v.hash = MyHash.hash(v);
    }

     */
    public static boolean equalSubstitute(Vertex quant, Vertex mod, String y)             //TODO  I will think that we can't bound propose twice
    {                                                                                      // I don't check it, only think so
        if (!Vertex.equalVertexOnluNullness(quant, mod))
            return false;
        if (quant == null)
            return true;                                                                     //  in quant we have y
        //  and in mod we have on it's place some term
        if (quant.operation.equals(y))
            return (mod != null);

        if (!quant.operation.equals(mod.operation))
            return false;


        if (!Vertex.equalVertexOnluNullness(quant.left, mod.left))
            return false;

        if (!Vertex.equalVertexOnluNullness(quant.right, mod.right))
            return false;

        if ((quant.terms != null && mod.terms == null) || (quant.terms == null && mod.terms != null))
            return false;

        if (quant.left != null)
            if (!equalSubstitute(quant.left, mod.left, y))
                return false;

        if (quant.right != null)
            if (!equalSubstitute(quant.right, mod.right, y))
                return false;

        if (quant.terms != null)
            for (int i = 0; i < quant.terms.size(); i++)
                if (!equalSubstitute(quant.terms.get(i), mod.terms.get(i), y))
                    return false;

        return true;
    }


    public static Vertex getNewForm(Vertex quant, Vertex mod, String y)                          //   NOT  from Root
    {
        if (quant.operation.equals(y))
            return new Vertex(mod);

        Vertex l = null;
        if (quant.left != null)
            l = getNewForm(quant.left, mod.left, y);
        if (l != null)
            return l;

        Vertex r = null;
        if (quant.right != null)
            r = getNewForm(quant.right, mod.right, y);
        if (r != null)
            return r;

        Vertex x = null;
        if (quant.terms != null)
            for (int i = 0; i < quant.terms.size(); i++) {
                x = getNewForm(quant.terms.get(i), mod.terms.get(i), y);
                if (x != null)
                    return x;
            }
        return null;
    }


    private static boolean isUniformEntriesInner(Vertex quant, Vertex mod, String y, Vertex newY) {

        if (quant == null)
            return true;

        if (quant.operation.equals(y))
            return mod.equals(newY);

        if (quant.left != null)
            if (!isUniformEntriesInner(quant.left, mod.left, y, newY))
                return false;

        if (quant.right != null)
            if (!isUniformEntriesInner(quant.right, mod.right, y, newY))
                return false;

        if (quant.terms != null)
            for (int i = 0; i < quant.terms.size(); i++)
                if (!isUniformEntriesInner(quant.terms.get(i), mod.terms.get(i), y, newY))
                    return false;

        return true;
    }

    private static boolean isUniformEntries(Vertex quant, Vertex mod, String y) {

        if (!equalSubstitute(quant, mod, y))
            return false;

        Vertex newY = getNewForm(quant, mod, y);
        if (newY.equals(null))
            return false;

        return isUniformEntriesInner(quant, mod, y, newY);
    }

    public static boolean isFreeToSubstitute(Vertex quant, String y, Vertex term, ArrayList<String> alreadyInQuantors) {
        if (quant == null)
            return true;

        if (quant.operation.equals(y))                                                                  //TODO  there  can be quantor with y in this path ?!?!?
            return MyArrays.isIntersected(term.proposes, alreadyInQuantors);

        if ("@".equals(quant.operation) || "?".equals(quant.operation)) {
            ArrayList<String> tempList = new ArrayList<String>(alreadyInQuantors);
            tempList.add(quant.left.operation);
            return isFreeToSubstitute(quant.right, y, term, tempList);
        }

        if (quant.left != null)
            if (!isFreeToSubstitute(quant.left, y, term, alreadyInQuantors))
                return false;

        if (quant.right != null)
            if (!isFreeToSubstitute(quant.right, y, term, alreadyInQuantors))
                return false;

        if (quant.terms != null)
            for (int i = 0; i < quant.terms.size(); i++)
                if (!isFreeToSubstitute(quant.terms.get(i), y, term, alreadyInQuantors))
                    return false;

        return true;

    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////


    public static String axiom_11(Vertex v) {                                                  // null will be good;   ""  will be undefined error
        if (v == null || v.left == null || v.right == null || v.left.right == null ||
                v.left.left == null)
            return "";
        if (!"->".equals(v.operation) || !"@".equals(v.left.operation))
            return "";

        if (!isUniformEntries(v.left.right, v.right, v.left.left.operation))
            return "";

        Vertex term = new Vertex(getNewForm(v.left.right, v.right, v.left.left.operation));

        if (!isFreeToSubstitute(v.left.right, v.left.left.operation, term, new ArrayList<String>()))
            return ("Терм " + term.getStringVersion() + " не свободен для подстановки в формулу " + v.left.right.getStringVersion() +
                    "вместо перемнной " + v.left.left.operation);

        return null;
    }


    public static String axiom_12(Vertex v) {                                                  // null will be good;   ""  will be undefined error
        if (v == null || v.left == null || v.right == null || v.right.right == null ||
                v.right.left == null)
            return "";
        if (!"->".equals(v.operation) || !"?".equals(v.right.operation))
            return "";

        if (!isUniformEntries(v.right.right, v.left, v.right.left.operation))
            return "";

        Vertex term = new Vertex(getNewForm(v.right.right, v.left, v.right.left.operation));

        if (!isFreeToSubstitute(v.right.right, v.right.left.operation, term, new ArrayList<String>()))
            return ("Терм " + term.getStringVersion() + " не свободен для подстановки в формулу " + v.right.right.getStringVersion() +
                    "вместо перемнной " + v.right.left.operation);

        return null;
    }


    //////////////////////////////////////////////////

    /*
    public static String axiom_11_notFreeToSubstitute(Vertex v, String y) {
        if (v == null || v.left == null || v.right == null || v.left.right == null ||
                v.left.left == null)
            return null;
        if (!"->".equals(v.operation) || !"@".equals(v.left.operation))
            return null;
        Vertex temp = new Vertex(v.left.right);
        String x = v.left.left.operation;

        boolean answer = (!freeToSubstitute(temp, x, y, false));

        substitute(temp, x, y);
        if (answer && v.right.equals(temp))
            return (x + " is not free to substitute to  " + temp.getStringVersion() + "   to the free places of " + y);
        else
            return null;
    }

    public static String axiom_12_notFreeToSubstitute(Vertex v, String y) {
        if (v == null || v.left == null || v.right == null || v.right.right == null ||
                v.right.left == null)
            return null;
        if (!"->".equals(v.operation) || !"?".equals(v.right.operation))
            return null;
        Vertex temp = new Vertex(v.right.right);
        String x = v.right.left.operation;

        boolean answer = (!freeToSubstitute(temp, x, y, false));

        substitute(temp, x, y);
        if (answer && v.left.equals(temp))
            return (x + " is not free to substitute to  " + temp.getStringVersion() + "   to the free places of " + y);
        else
            return null;
    }

    */
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////


    public static Proof aToaProof(Vertex aVertex) {

        Proof proof = new Proof();

        String a = aVertex.getStringVersion();

        proof.addLine(a + "->" + a + "->" + a + "");
        proof.addLine("(" + a + "->(" + a + "->" + a + "))->(" + a + "->(" + a + "->" + a + ")->" + a + ")->("
                + a + "->" + a + ")");
        proof.addLine("(" + a + "->(" + a + "->" + a + ")->" + a + ")->(" + a + "->" + a + ")");
        proof.addLine("(" + a + "->((" + a + "->" + a + ")->" + a + "))");
        proof.addLine("(" + a + "->" + a + ")");

        return proof;
    }


    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    /////////////////////////////////////////////////


    public static Proof Lemma_Contraposition(Vertex _a, Vertex _b) {

        Proof proof = new Proof();

        String a = _a.getStringVersion();
        String b = _b.getStringVersion();

        proof.addAssumption("(" + a + "->" + b + ")");
        proof.addAssumption("(!" + b + ")");
        proof.setProblem("(!" + a + ")");

        proof.addLine("(" + a + "->" + b + ")->(" + a + "->!" + b + ")->!" + a);
        proof.addLine(a + "->" + b);
        proof.addLine("(" + a + "->!" + b + ")->!" + a);
        proof.addLine("!" + b + "->(" + a + "->!" + b + ")");
        proof.addLine("!" + b);
        proof.addLine(a + "->!" + b);
        proof.addLine("!" + a);

        proof.fullDeduct();

        if (!Configuration.ACCELERATION)
            if (!proof.isCorrect())
                throw new NullPointerException();

        return proof;
    }

    public static Proof Lemma_ExcessThird(Vertex _a) {

        Proof proof = new Proof();

        String a = _a.getStringVersion();

        proof.addLine(a + "->" + a + "|!" + a);
        proof.addProof(Lemma_Contraposition(_a, new Vertex(a + "|!" + a)));

        proof.addLine("!(" + a + "|!" + a + ")->!" + a);

        proof.addLine("!" + a + "->" + a + "|!" + a);
        proof.addProof(Lemma_Contraposition(new Vertex("(!" + a + ")"), new Vertex(a + "|!" + a)));

        proof.addLine("!(" + a + "|!" + a + ")->!!" + a);

        proof.addLine("(!(" + a + "|!" + a + ")->!" + a + ")->(!(" + a + "|!" + a + ")->!!" + a + ")->(!!(" + a + "|!" + a + "))");
        proof.addLine("(!(" + a + "|!" + a + ")->!!" + a + ")->(!!(" + a + "|!" + a + "))");
        proof.addLine("!!(" + a + "|!" + a + ")");
        proof.addLine("!!(" + a + "|!" + a + ")->(" + a + "|!" + a + ")");                                     //TODO   I didn't check it this time
        proof.addLine(a + "|!" + a);

        proof.setProblem(a + "|!" + a);

        if (!Configuration.ACCELERATION)
            if (!proof.isCorrect())
                throw new NullPointerException();

        return proof;
    }

    public static Proof Lemma_UniteProofsWithExcessPropose(Proof _trueProof, Proof _falseProof) {

        Proof proof = new Proof();

        Vertex propose = _trueProof.assumptions.get(_trueProof.assumptions.size() - 1);
        proof.setProblem(_trueProof.problem);                                                  //TODO optimize copying vertex please!!!

        Proof trueProof = _trueProof;                                       //here was a new operator
        Proof falseProof = _falseProof;                                     //here was a new operator

        trueProof.singleDeduct();
        falseProof.singleDeduct();
        proof.addProof(trueProof);
        proof.addProof(falseProof);

        proof.addProof(Lemma_ExcessThird(propose));

        proof.assumptions = new ArrayList<Vertex>(trueProof.assumptions);

        String p = propose.getStringVersion();
        String a = proof.problem.getStringVersion();

        proof.addLine("(" + p + "->" + a + ")->(!" + p + "->" + a + ")->((" + p + "|!" + p + ")->" + a + ")");
        proof.addLine("(!" + p + "->" + a + ")->((" + p + "|!" + p + ")->" + a + ")");
        proof.addLine("(" + p + "|!" + p + ")->" + a);
        proof.addLine(a);

        if (!Configuration.ACCELERATION)
            if (!proof.isCorrect())
                throw new NullPointerException();

        return proof;
    }


    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////


    public static boolean axiom_Arithmetic_1(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null || v.right.left == null ||
                v.right.right == null || v.right.left.right == null || v.right.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"=".equals(v.left.operation) || !"=".equals(v.right.operation) ||
                !"'".equals(v.right.left.operation) || !"'".equals(v.right.right.operation))
            return false;
        return (v.left.left.equals(v.right.left.right) && v.left.right.equals(v.right.right.right));
    }


    public static boolean axiom_Arithmetic_2(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null || v.right.left == null ||
                v.right.right == null || v.right.left.left == null || v.right.left.right == null ||
                v.right.right.left == null || v.right.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"=".equals(v.left.operation) || !"->".equals(v.right.operation) ||
                !"=".equals(v.right.left.operation) || !"=".equals(v.right.right.operation))
            return false;
        return (v.left.left.equals(v.right.left.left) && v.left.right.equals(v.right.right.left) && v.right.left.right.equals(v.right.right.right));
    }


    public static boolean axiom_Arithmetic_3(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null || v.right.left == null ||
                v.right.right == null || v.left.left.right == null || v.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"=".equals(v.left.operation) || !"=".equals(v.right.operation) ||
                !"'".equals(v.left.left.operation) || !"'".equals(v.left.right.operation))
            return false;
        return (v.left.left.right.equals(v.right.left) && v.left.right.right.equals(v.right.right));
    }


    public static boolean axiom_Arithmetic_4(Vertex v) {
        if (v == null || v.right == null || v.right.left == null ||
                v.right.right == null || v.right.left.right == null)
            return false;
        if (!"!".equals(v.operation) || !"=".equals(v.right.operation) ||
                !"'".equals(v.right.left.operation) || !"0".equals(v.right.right.operation))
            return false;
        return true;
    }


    public static boolean axiom_Arithmetic_5(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null || v.left.right.right == null ||
                v.right.right == null || v.right.right.left == null || v.right.right.right == null)
            return false;
        if (!"=".equals(v.operation) || !"+".equals(v.left.operation) || !"'".equals(v.right.operation) ||
                !"'".equals(v.left.right.operation) || !"+".equals(v.right.right.operation))
            return false;
        return (v.left.left.equals(v.right.right.left) && v.left.right.right.equals(v.right.right.right));
    }


    public static boolean axiom_Arithmetic_6(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"=".equals(v.operation) || !"+".equals(v.left.operation) || !"0".equals(v.left.right.operation))
            return false;
        return (v.left.left.equals(v.right));
    }


    public static boolean axiom_Arithmetic_7(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"=".equals(v.operation) || !"*".equals(v.left.operation) || !"0".equals(v.right.operation) || !"0".equals(v.left.right.operation))
            return false;
        return true;
    }


    public static boolean axiom_Arithmetic_8(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null || v.right.left == null ||
                v.right.right == null || v.left.right.right == null || v.right.left.left == null || v.right.left.right == null)
            return false;
        if (!"=".equals(v.operation) || !"*".equals(v.left.operation) || !"+".equals(v.right.operation) ||
                !"'".equals(v.left.right.operation) || !"*".equals(v.right.left.operation))
            return false;
        return (v.left.left.equals(v.right.left.left) && v.left.right.right.equals(v.right.left.right) && v.left.left.equals(v.right.right));
    }


    public static boolean axiom_Arithmetic_9(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null ||
                v.left.right.left == null || v.left.right.right == null || v.left.right.right.left == null || v.left.right.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"&".equals(v.left.operation) || !"@".equals(v.left.right.operation) ||
                !"->".equals(v.left.right.right.operation))
            return false;

        Vertex term = new Vertex(v.left.right.left.operation);

        if (!equalSubstitute(v.right, v.left.left, v.left.right.left.operation))
            return false;

        if (!equalSubstitute(v.right, v.left.right.right.right, v.left.right.left.operation))
            return false;

        Vertex zero = new Vertex("0");
        Vertex next = new Vertex(term.getStringVersion() + "'");

        if (!zero.equals(getNewForm(v.right, v.left.left, v.left.right.left.operation)))
            return false;

        if (!next.equals(getNewForm(v.right, v.left.right.right.right, v.left.right.left.operation)))
            return false;


        if (!isUniformEntries(v.right, v.left.left, v.left.right.left.operation))
            return false;

        if (!isUniformEntries(v.right, v.left.right.right.right, v.left.right.left.operation))
            return false;

        return true;
    }
}
