import java.util.ArrayList;

public class ProofMaker {

    Vertex problem;
    Proof problemProof;

    ArrayList<Boolean> currentValues;

    ProofMaker(Vertex _problem) {
        problem = new Vertex(_problem);

        problemProof = makeProof(new Proof(), new ArrayList<Boolean>());            //there  will be null if it is not a tautology
    }

    Proof makeProof(Proof proof, ArrayList<Boolean> values) {
        if (proof.assumptions.size() == problem.proposes.size())
            return makeSimpleProof(proof, values);

        Proof proofTrue = new Proof(proof);
        Proof proofFalse = new Proof(proof);

        ArrayList<Boolean> valuesTrue = new ArrayList<Boolean>(values);
        ArrayList<Boolean> valuesFalse = new ArrayList<Boolean>(values);

        valuesTrue.add(true);
        valuesFalse.add(false);

        proofTrue.addAssumption(problem.proposes.get(proof.assumptions.size()));
        proofFalse.addAssumption("!" + problem.proposes.get(proof.assumptions.size()));

        proofTrue = makeProof(proofTrue, valuesTrue);
        if (proofTrue == null)
            return null;
        proofFalse = makeProof(proofFalse, valuesFalse);
        if (proofFalse == null)
            return null;

        return AxiomsList.Lemma_UniteProofsWithExcessPropose(proofTrue, proofFalse);     //here was a new operator       //TODO  check:  I think all fields now is filled
    }

    Proof makeSimpleProof(Proof proofHeader, ArrayList<Boolean> values) {
        problem.makeCurrentValues(values);
        currentValues = new ArrayList<Boolean>(values);

        if (!problem.value)
            return null;

        Proof proof = makeProofForVertex(problem);                                                      //here was a new operator
        proof.setProblem(problem);
        proof.assumptions = new ArrayList<Vertex>(proofHeader.assumptions);

        return proof;
    }

    Proof makeProofForVertex(Vertex v) {
        Proof proof = new Proof();

        if (v == null)
            return proof;

        if (v.left == null && v.right == null) {
            if (v.value)
                proof.addLine(v.operation);
            else
                proof.addLine("!" + v.operation);
            return proof;
        }

        if ("!".equals(v.operation)) {
            proof.addProof(makeProofForVertex(v.right));
            proof.addProof(SimpleProofs.getSimpleProof(null, v.right, "!"));
        } else {
            proof.addProof(makeProofForVertex(v.left));
            proof.addProof(makeProofForVertex(v.right));
            proof.addProof(SimpleProofs.getSimpleProof(v.left, v.right, v.operation));
        }

        return proof;
    }
}
