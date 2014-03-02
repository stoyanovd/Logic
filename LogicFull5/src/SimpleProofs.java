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
                proofs[a][b][0] = myReader.readProof();
                if (!proofs[a][b][0].isCorrect())
                    throw new NullPointerException();

                myReader = new MyReader("proofs/" + (a == 1 ? "" : "not") + "A" + (b == 1 ? "" : "not") + "B" + "or" + ".txt");
                proofs[a][b][1] = myReader.readProof();
                if (!proofs[a][b][1].isCorrect())
                    throw new NullPointerException();

                myReader = new MyReader("proofs/" + (a == 1 ? "" : "not") + "A" + (b == 1 ? "" : "not") + "B" + "and" + ".txt");
                proofs[a][b][2] = myReader.readProof();
                if (!proofs[a][b][2].isCorrect())
                    throw new NullPointerException();
            }

        myReader = new MyReader("proofs/" + "AnotnotA" + ".txt");
        proofNot = myReader.readProof();
        if (!proofNot.isCorrect())
            throw new NullPointerException();

        myReader = new MyReader("proofs/" + "makeAnd" + ".txt");
        proofMakeAnd = myReader.readProof();
        proofMakeAnd.fullDeduct();

        myReader = new MyReader("proofs/" + "makeFollow" + ".txt");
        proofMakeFollow = myReader.readProof();
        proofMakeFollow.fullDeduct();

        myReader = new MyReader("proofs/" + "makeSwapFollow" + ".txt");
        proofMakeSwapFollow = myReader.readProof();
        proofMakeSwapFollow.fullDeduct();
    }

    static public Proof getSimpleProof(Vertex left, Vertex right, String operation) {

        Proof proof = new Proof();

        if ("!".equals(operation)) {
            for (int i = 0; i < proofNot.lines.size(); i++)
                proof.addLine(changeOneString(proofNot.lines.get(i).getStringVersion(),
                        (right.value ? right.getStringVersion() : "!" + right.getStringVersion())));

            return proof;
        }

        int operationCode;
        if ("->".equals(operation))
            operationCode = 0;
        else if ("|".equals(operation))
            operationCode = 1;
        else if ("&".equals(operation))
            operationCode = 2;
        else
            operationCode = 3;                                                                            //TODO  it will be nice to store string lines too

        Proof proofDef = new Proof(proofs[(left.value ? 1 : 0)][(right.value ? 1 : 0)][operationCode]);

        String leftString = left.getStringVersion();
        String rightString = right.getStringVersion();

        for (int i = 0; i < proofDef.lines.size(); i++)
            proof.addLine(changeTwoStrings(proofDef.lines.get(i).getStringVersion(), leftString, rightString));

        for (int i = 0; i < proofDef.assumptions.size(); i++) {                                     //TODO  is it important here  - than add it higher
            proof.addAssumption(changeTwoStrings(proofDef.assumptions.get(i).getStringVersion(), leftString, rightString));
        }

        if (!Configuration.ACCELERATION)
            if (!proofDef.isCorrect())
                throw new NullPointerException();

        return proof;
    }

    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////

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


    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////


    public static Proof getAnd(String _x, String _y, String _z) {
        String x = Vertex.deleteOuterBrackets(_x);
        String y = Vertex.deleteOuterBrackets(_y);
        String z = Vertex.deleteOuterBrackets(_z);

        Proof proof = new Proof();

        for (int i = 0; i < proofMakeAnd.lines.size(); i++)
            proof.addLine(changeThreeStrings(proofMakeAnd.lines.get(i).getStringVersion(), x, y, z));


        if (!Configuration.ACCELERATION)
            if (!proof.isCorrect())
                throw new NullPointerException();

        proof.addLine("(" + x + ")&(" + y + ")->(" + z + ")");

        return proof;
    }

    public static Proof getFollow(String _x, String _y, String _z) {
        String x = Vertex.deleteOuterBrackets(_x);
        String y = Vertex.deleteOuterBrackets(_y);
        String z = Vertex.deleteOuterBrackets(_z);

        Proof proof = new Proof();

        for (int i = 0; i < proofMakeFollow.lines.size(); i++)
            proof.addLine(changeThreeStrings(proofMakeFollow.lines.get(i).getStringVersion(), x, y, z));


        if (!Configuration.ACCELERATION)
            if (!proof.isCorrect())
                throw new NullPointerException();

        proof.addLine("(" + x + ")->(" + y + ")->(" + z + ")");

        return proof;
    }

    public static Proof getSwapFollow(String _x, String _y, String _z) {
        String x = Vertex.deleteOuterBrackets(_x);
        String y = Vertex.deleteOuterBrackets(_y);
        String z = Vertex.deleteOuterBrackets(_z);

        Proof proof = new Proof(proofMakeSwapFollow);

        for (int i = 0; i < proofMakeSwapFollow.lines.size(); i++)
            proof.addLine(changeThreeStrings(proofMakeSwapFollow.lines.get(i).getStringVersion(), x, y, z));


        if (!Configuration.ACCELERATION)
            if (!proof.isCorrect())
                throw new NullPointerException();

        proof.addLine("(" + y + ")->(" + x + ")->(" + z + ")");

        return proof;
    }
}
