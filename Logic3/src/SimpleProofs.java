public class SimpleProofs {

    static Proof proofs[][][] = new Proof[2][2][3];
    static Proof proofNot;

    static Proof proofMakeAnd;
    static Proof proofMakeFollow;
    static Proof proofMakeSwapFollow;

    static {
        MyReader myReader;

        for (int a = 0; a < 2; a++)
            for (int b = 0; b < 2; b++) {
                myReader = new MyReader("proofs/" + (a == 1 ? "" : "not") + "A" + (b == 1 ? "" : "not") + "B" + "follow" + ".txt");
                proofs[a][b][0] = myReader.readProofWithoutHeader();

                myReader = new MyReader("proofs/" + (a == 1 ? "" : "not") + "A" + (b == 1 ? "" : "not") + "B" + "or" + ".txt");
                proofs[a][b][1] = myReader.readProofWithoutHeader();

                myReader = new MyReader("proofs/" + (a == 1 ? "" : "not") + "A" + (b == 1 ? "" : "not") + "B" + "and" + ".txt");
                proofs[a][b][2] = myReader.readProofWithoutHeader();
            }

        myReader = new MyReader("proofs/" + "AnotnotA" + ".txt");
        proofNot = myReader.readProofWithoutHeader();

        myReader = new MyReader("proofs/" + "makeAnd" + ".txt");                 //TODO     make nice proofs of Quantum
        proofMakeAnd = myReader.readProofWithoutHeader();

        myReader = new MyReader("proofs/" + "makeFollow" + ".txt");
        proofMakeFollow = myReader.readProofWithoutHeader();

        myReader = new MyReader("proofs/" + "makeSwapFollow" + ".txt");
        proofMakeSwapFollow = myReader.readProofWithoutHeader();
    }

    static public Proof getSimpleProof(String left, boolean l, String right, boolean r, String operation) {
        if ("!".equals(operation)) {

            Proof proof = new Proof(proofNot);

            for (int i = 0; i < proof.proofStrings.size(); i++)
                proof.proofStrings.set(i, changeOneString(proof.proofStrings.get(i), (r ? Vertex.deleteOuterBrackets(right) : "!(" + Vertex.deleteOuterBrackets(right) + ")")));

            proof.pack();
            return proof;
        }

        int operationCode;
        if ("->".equals(operation))
            operationCode = 0;
        else if ("|".equals(operation))
            operationCode = 1;
        else if ("&".equals(operation))
            operationCode = 2;
        else {
            Helper.println("SimpleProofs   getProof   unknown operation");
            return null;
        }

        Proof proof = new Proof(proofs[(l ? 1 : 0)][(r ? 1 : 0)][operationCode]);

        for (int i = 0; i < proof.proofStrings.size(); i++)
            proof.proofStrings.set(i, changeTwoStrings(proof.proofStrings.get(i), Vertex.deleteOuterBrackets(left), Vertex.deleteOuterBrackets(right)));

        proof.pack();
        return proof;
    }

    static public String changeTwoStrings(String s, String a, String b) {
        String answer = "";
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == 'A')
                answer += "(" + a + ")";
            else if (s.charAt(i) == 'B')
                answer += "(" + b + ")";
            else answer += s.charAt(i);
        return answer;
    }

    static public String changeOneString(String s, String a) {
        String answer = "";
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == 'A')
                answer += "(" + a + ")";
            else answer += s.charAt(i);
        return answer;
    }

    static public String changeThreeStrings(String s, String a, String b, String c) {
        String answer = "";
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == 'A')
                answer += "(" + a + ")";
            else if (s.charAt(i) == 'B')
                answer += "(" + b + ")";
            else if (s.charAt(i) == 'C')
                answer += "(" + c + ")";
            else answer += s.charAt(i);
        return answer;
    }

    public static Proof getAnd(String _x, String _y, String _z) {
        String x = Vertex.deleteOuterBrackets(_x);
        String y = Vertex.deleteOuterBrackets(_y);
        String z = Vertex.deleteOuterBrackets(_z);

        Proof proof = new Proof(proofMakeAnd);

        for (int i = 0; i < proof.proofStrings.size(); i++)
            proof.proofStrings.set(i, changeThreeStrings(proof.proofStrings.get(i), x, y, z));

        proof.addProof("(" + x + ")&(" + y + ")->(" + z + ")");
        proof.pack();
        return proof;
    }

    public static Proof getFollow(String _x, String _y, String _z) {
        String x = Vertex.deleteOuterBrackets(_x);
        String y = Vertex.deleteOuterBrackets(_y);
        String z = Vertex.deleteOuterBrackets(_z);

        Proof proof = new Proof(proofMakeFollow);

        for (int i = 0; i < proof.proofStrings.size(); i++)
            proof.proofStrings.set(i, changeThreeStrings(proof.proofStrings.get(i), x, y, z));

        proof.addProof("(" + x + ")->(" + y + ")->(" + z + ")");
        proof.pack();
        return proof;
    }

    public static Proof getSwapFollow(String _x, String _y, String _z) {
        String x = Vertex.deleteOuterBrackets(_x);
        String y = Vertex.deleteOuterBrackets(_y);
        String z = Vertex.deleteOuterBrackets(_z);

        Proof proof = new Proof(proofMakeSwapFollow);

        for (int i = 0; i < proof.proofStrings.size(); i++)
            proof.proofStrings.set(i, changeThreeStrings(proof.proofStrings.get(i), x, y, z));

        proof.addProof("(" + y + ")->(" + x + ")->(" + z + ")");
        proof.pack();
        return proof;
    }
}
