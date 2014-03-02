public class MainChecker {

    public static void check() {
        check(Configuration.FILE_IN, Configuration.FILE_OUT);
    }

    public static void check(String in, String out) {
        MyReader myReader = new MyReader(in);
        MyPrinter myPrinter = new MyPrinter(out);
        Proof proof = myReader.readProof();
        if (proof != null)
            proof.isCorrect();                           //fills  some fields
        myPrinter.printProof(proof, true);
    }

    public static void main(String[] arg) {
        long time = System.currentTimeMillis();


        check();

        MyPrinter.printTime(time);
    }
}