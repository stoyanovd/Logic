public class ProofCheckerMain {

    public static void check() {
        check(Configuration.FILE_IN, Configuration.FILE_OUT);
    }

    public static void check(String in, String out) {
        MyReader myReader = new MyReader(in);
        MyPrinter myPrinter = new MyPrinter(out);
        Proof proof = myReader.readProofWithoutHeader();
        proof.isCorrectProof();
        myPrinter.printProof(proof, true);
    }

    public static void checkWithHeader(String in, String out) {
        MyReader myReader = new MyReader(in);
        MyPrinter myPrinter = new MyPrinter(out);
        Proof proof = myReader.readProof();
        proof.isCorrectProof();
        myPrinter.printProof(proof, true);
    }

    public static void main(String[] arg) {
        long time = System.currentTimeMillis();

        check();

        Helper.println("time = " + (System.currentTimeMillis() - time));
    }
}