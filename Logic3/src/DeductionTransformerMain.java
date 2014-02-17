public class DeductionTransformerMain {

    public static void main(String[] arg) {
        long time = System.currentTimeMillis();


        MyReader myReader = new MyReader(Configuration.FILE_IN);
        Proof proof = myReader.readProof();

        MyPrinter myPrinter = new MyPrinter(Configuration.FILE_OUT);
        if (!proof.isCorrectProof())                                        //   header now not is been checked
            myPrinter.printProof(proof, false);

        proof.fullDeduct();

        myPrinter.printProof(proof, false);

        Helper.println("time = " + (System.currentTimeMillis() - time));
    }
}