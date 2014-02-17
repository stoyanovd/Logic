public class ProofMakerMain {

    public static void make() {
        MyReader myReader = new MyReader(Configuration.FILE_IN);
        ProofMaker proofMaker = new ProofMaker(myReader.readString());
        MyPrinter myPrinter = new MyPrinter(Configuration.FILE_OUT);
        if (proofMaker.answerProof == null)
            myPrinter.printValues(proofMaker);
        else
            myPrinter.printProof(proofMaker.answerProof, false);
    }

    public static void main(String[] arg) {
        long time = System.currentTimeMillis();

        make();

        ProofCheckerMain.checkWithHeader(Configuration.FILE_OUT, Configuration.FILE_SERVICE);

        Helper.println("time = " + (System.currentTimeMillis() - time));
    }

}
