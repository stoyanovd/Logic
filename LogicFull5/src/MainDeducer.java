public class MainDeducer {

    public static void deduce() {
        deduce(Configuration.FILE_IN, Configuration.FILE_OUT);
    }

    public static void deduce(String in, String out) {
        MyReader myReader = new MyReader(in);
        MyPrinter myPrinter = new MyPrinter(out);
        Proof proof = myReader.readProof();
        if (proof == null) {
            myPrinter.printProof(proof, true);
            return;
        }
        proof.isCorrect();
        if (proof.firstWrongString != -1) {
            myPrinter.printProof(proof, false);
            return;
        }

        if (!proof.fullDeductCarefully()) {
            System.out.println("Дедукция багает. Осталось " + proof.assumptions.size() + " допущений.");
            System.out.println("Текущее доказательство некорректно начиная со строки " + proof.firstWrongString);
            myPrinter.printProof(proof, false);
            return;
        }
        myPrinter.printProof(proof, false);
    }

    public static void main(String[] arg) {
        long time = System.currentTimeMillis();

        deduce();

        MyPrinter.printTime(time);
    }
}
