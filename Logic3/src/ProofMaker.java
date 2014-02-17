import java.util.ArrayList;

public class ProofMaker {

    ArrayList<String> proposesNames;
    String beta;
    Vertex betaVertex;
    Proof answerProof;

    ArrayList<Boolean> values;

    ProofMaker(String _beta) {

        if (_beta == null || "".equals(_beta))
            return;
        beta = _beta;
        VertexMaker vertexMaker = new VertexMaker();

        betaVertex = new Vertex(beta, vertexMaker);
        proposesNames = new ArrayList<String>(vertexMaker.proposes);
        values = new ArrayList<Boolean>();

        answerProof = recursiveStep();
    }

    Proof recursiveStep() {
        if (values.size() == proposesNames.size()) {
            Proof proof = getBooleanProof();
            if (proof != null)
                proof.beta = beta;
            return proof;
        }
        values.add(true);
        Proof t = recursiveStep();
        if (t == null)
            return null;
        values.set(values.size() - 1, false);
        Proof f = recursiveStep();
        if (f == null)
            return null;
        values.remove(values.size() - 1);

        Proof proof = new Proof(AxiomsList.Lemma_KillLastAssumption(t, f));
        return proof;
    }

    Proof getBooleanProof() {
        Proof proof = new Proof();
        for (int i = 0; i < values.size(); i++)
            if (values.get(i))
                proof.addAssumption("(" + proposesNames.get(i) + ")");                   //TODO   I think this is excess brackets
            else
                proof.addAssumption("(!(" + proposesNames.get(i) + "))");

        NiceVertex niceVertex = new NiceVertex(betaVertex, values, proposesNames);
        if (!niceVertex.value) {
            Helper.falseProposesPrintln("false  proposes  set");
            for (int i = 0; i < proposesNames.size(); i++)
                Helper.falseProposesPrintln(proposesNames.get(i) + " = " + values.get(i));
            return null;
        }

        proof.addProof(vertexProof(niceVertex));
        proof.simplify();

        return proof;
    }

    Proof vertexProof(NiceVertex v) {
        if (v == null)
            return null;

        Proof proof = new Proof();

        if (v.left == null && v.right == null) {
            if (!proposesNames.contains(v.operation)) {
                Helper.println("vertexProof   unknown propose");
                return null;
            }
            if (values.get(proposesNames.indexOf(v.operation)))
                proof.addProof("(" + v.operation + ")");                                               //TODO  safe brackets
            else
                proof.addProof("(!(" + v.operation + "))");
            return proof;
        }
        if ("!".equals(v.operation)) {
            proof = new Proof(vertexProof(v.right));
            proof.addProof(SimpleProofs.getSimpleProof(null, true, v.right.text, v.right.value, "!"));
        } else {
            proof = new Proof(vertexProof(v.left));
            proof.addProof(vertexProof(v.right));
            proof.addProof(SimpleProofs.getSimpleProof(v.left.text, v.left.value, v.right.text, v.right.value, v.operation));
        }
        return proof;
    }
}
