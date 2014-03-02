public class MainProver {

    public static void prove() {
        prove(Configuration.FILE_IN, Configuration.FILE_OUT);
    }

    public static void prove(String in, String out) {
        MyReader myReader = new MyReader(in);
        MyPrinter myPrinter = new MyPrinter(out);
        String problem = myReader.readString();

        if (problem == null) {
            myPrinter.printProof(null, true);
            return;
        }
        Vertex v = new Vertex(problem);
        ProofMaker proofMaker = new ProofMaker(v);

        myPrinter.printFreshProof(proofMaker);

        if (proofMaker.problemProof != null)
            System.out.println("Корректность выведенного доказательства = " + proofMaker.problemProof.isCorrect());

    }

    public static void main(String[] arg) {

        long time = System.currentTimeMillis();

        prove();

        MyPrinter.printTime(time);
    }

}
