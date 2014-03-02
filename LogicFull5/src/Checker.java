import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Checker {

    String possibleError;

    Proof owner;

    Vertex alpha;
    String alphaString;

    ArrayList<String> freeProposes;
    ArrayList<Integer> freeProposesOwners;

    HashSet<Vertex> checked;
    HashMap<Vertex, ArrayList<Vertex>> possibleBeginnings;
    HashMap<Vertex, HashSet<Vertex>> follows;

    boolean itIsDeduction = false;


    Checker(Proof p) {
        possibleError = "";

        owner = p;

        alpha = new Vertex();
        alphaString = "";

        checked = new HashSet<Vertex>();
        possibleBeginnings = new HashMap<Vertex, ArrayList<Vertex>>();
        follows = new HashMap<Vertex, HashSet<Vertex>>();

        freeProposes = new ArrayList<String>();
        freeProposesOwners = new ArrayList<Integer>();
        for (int i = 0; i < p.assumptions.size(); i++) {
            p.assumptions.get(i).getFreeProposes();
            freeProposes.addAll(p.assumptions.get(i).freeProposes);
            for (int j = 0; j < p.assumptions.get(i).freeProposes.size(); j++)
                freeProposesOwners.add(i);

        }

        itIsDeduction = false;
    }

    Checker(Proof p, Vertex _alpha) {
        this(p);

        alpha = _alpha;
        alphaString = alpha.getStringVersion();
        if (alphaString == null)
            throw new NullPointerException();

        alpha.getFreeProposes();                                // I think it is a trash

        itIsDeduction = true;
    }

    Checker(Proof p, boolean thisIsDeduction) {
        this(p);
        if (!thisIsDeduction)
            return;

        alpha = p.assumptions.get(p.assumptions.size() - 1);
        alphaString = alpha.getStringVersion();
        alpha.getFreeProposes();

        p.assumptions.remove(p.assumptions.size() - 1);
        p.problem = new Vertex(alphaString + "->" + p.problem.getStringVersion());

        itIsDeduction = true;
    }

    private void successfulCheck(Vertex v) {
        checked.add(v);
        if (v != null && v.left != null && v.right != null && "->".equals(v.operation)) {
            if (!possibleBeginnings.containsKey(v.right))
                possibleBeginnings.put(v.right, new ArrayList<Vertex>());

            possibleBeginnings.get(v.right).add(v.left);

            if (!follows.containsKey(v.left))
                follows.put(v.left, new HashSet<Vertex>());

            follows.get(v.left).add(v.right);
        }
    }

    public Proof singleCheck(Vertex v) {
        if (v == null)
            return new Proof();

        if (checked.contains(v))
            return new Proof();                                //  a  little  dangerous

        for (int i = 1; i <= 10; i++)
            if (AxiomsSupporter.isAxiom(v, i)) {
                successfulCheck(v);
                return deductAxiom(v);
            }

        if (Configuration.QUANTUM_NUMERATION || Configuration.ARITHMETIC_NUMERATION)
            for (int i = 1; i <= 2; i++) {
                String temp = AxiomsSupporter.isQuantumAxiom(v, i);
                if (temp == null) {
                    successfulCheck(v);
                    return deductAxiom(v);
                }
                if (!"".equals(temp))
                    possibleError = temp;
            }

        if (Configuration.ARITHMETIC_NUMERATION)
            for (int i = 1; i <= 9; i++)
                if (AxiomsSupporter.isArithmeticAxiom(v, i)) {
                    successfulCheck(v);
                    return deductAxiom(v);
                }

        for (int i = 0; i < owner.assumptions.size(); i++)
            if (owner.assumptions.get(i).equals(v)) {
                successfulCheck(v);
                return deductAxiom(v);
            }

        if (alpha.equals(v)) {
            successfulCheck(v);
            return deductAlpha();
        }

        if (Configuration.QUANTUM_NUMERATION || Configuration.ARITHMETIC_NUMERATION)
            if (AxiomsSupporter.isModusPonensAHeader(v))
                if (follows.containsKey(v.left) && follows.get(v.left).contains(v.right.right))
                    if (true)                                                                         //TODO   maybe  unsubstitute is required??
                    {
                        if (!freeProposes.contains(v.right.left.operation)) {
                            v.left.getFreeProposes();
                            if (!v.left.freeProposes.contains(v.right.left.operation)) {
                                successfulCheck(v);
                                return deductModusPonensA(v);
                            } else
                                possibleError = "Переменная " + v.right.left.operation +
                                        " входит свободно в " + v.left.getStringVersion();
                        } else {
                            possibleError = "Используется Modus Ponens с квантором @ по " + v.right.left.operation +
                                    " входящей свободно в допущение " +
                                    owner.assumptions.get(freeProposesOwners.get(freeProposes.indexOf(v.right.left.operation))).getStringVersion() + ".";
                        }
                    }

        if (Configuration.QUANTUM_NUMERATION || Configuration.ARITHMETIC_NUMERATION)
            if (AxiomsSupporter.isModusPonensEHeader(v))
                if (follows.containsKey(v.left.right) && follows.get(v.left.right).contains(v.right))
                    if (true)                                                                         //TODO   maybe  unsubstitute is required??
                    {
                        if (!freeProposes.contains(v.left.left.operation)) {
                            v.right.getFreeProposes();
                            if (!v.right.freeProposes.contains(v.left.left.operation)) {
                                successfulCheck(v);
                                return deductModusPonensE(v);
                            } else
                                possibleError = "Переменная " + v.left.left.operation +
                                        " входит свободно в " + v.right.getStringVersion();
                        } else {
                            possibleError = "Используется Modus Ponens с квантором ? по " + v.left.left.operation +
                                    " входящей свободно в допущение " +
                                    owner.assumptions.get(freeProposesOwners.get(freeProposes.indexOf(v.left.left.operation))).getStringVersion() + ".";
                        }
                    }

        if (!possibleBeginnings.containsKey(v))
            return null;

        ArrayList<Vertex> beginnings = new ArrayList<Vertex>(possibleBeginnings.get(v));
        for (int i = 0; i < beginnings.size(); i++)
            if (checked.contains(beginnings.get(i))) {
                successfulCheck(v);
                return deductModusPonens(beginnings.get(i), v);
            }

        return null;
    }


    /////////////////////////////////////////////
    /////////////////////////////////////////////

    private Proof deductAxiom(Vertex v) {
        if (!itIsDeduction)
            return new Proof();

        Proof proof = new Proof();
        String vString = v.getStringVersion();

        proof.addLine(v);
        proof.addLine("(" + vString + "->(" + alphaString + "->" + vString + "))");
        proof.addLine("(" + alphaString + "->" + vString + ")");

        return proof;
    }

    private Proof deductAlpha() {
        if (!itIsDeduction)
            return new Proof();
        return new Proof(AxiomsList.aToaProof(alpha));
    }

    private Proof deductModusPonens(Vertex l, Vertex r) {
        if (!itIsDeduction)
            return new Proof();

        String lString = l.getStringVersion();
        String rString = r.getStringVersion();
        Proof proof = new Proof();

        proof.addLine("(" + alphaString + "->" + lString + ")->((" + alphaString + "->(" + lString +
                "->" + rString + "))->(" + alphaString + "->" + rString + "))");
        proof.addLine("(" + alphaString + "->(" + lString + "->" + rString + "))->(" + alphaString +
                "->" + rString + ")");
        proof.addLine("(" + alphaString + "->" + rString + ")");

        return proof;
    }

    private Proof deductModusPonensA(Vertex v) {
        if (!itIsDeduction)
            return new Proof();

        String leftString = v.left.getStringVersion();
        String rightString = v.right.right.getStringVersion();

        if (v.right.left == null)
            throw new NullPointerException();
        Proof proof = new Proof();
        proof.addProof(SimpleProofs.getAnd(alphaString, leftString, rightString));
        proof.addLine(alphaString + "&" + leftString + "->@" + v.right.left.operation + rightString);
        proof.addProof(SimpleProofs.getFollow(alphaString, leftString, "@" + v.right.left.operation + rightString));

        return proof;
    }

    private Proof deductModusPonensE(Vertex v) {
        if (!itIsDeduction)
            return new Proof();

        String leftString = v.left.right.getStringVersion();
        String rightString = v.right.getStringVersion();

        Proof proof = new Proof();
        proof.addProof(SimpleProofs.getSwapFollow(alphaString, leftString, rightString));
        proof.addLine("?" + v.left.left.operation + leftString + "->" + alphaString + "->" + rightString);
        proof.addProof(SimpleProofs.getSwapFollow("?" + v.left.left.operation + leftString, alphaString, rightString));

        return proof;
    }
}
