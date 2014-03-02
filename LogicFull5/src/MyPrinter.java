import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MyPrinter {

    String outputFile;
    PrintWriter out;

    public MyPrinter(String _outputFile) {
        outputFile = _outputFile;
    }


    public void printProof(Proof proof, boolean onlyCorrectness) {

        try {

            out = new PrintWriter(new File(outputFile));

            if (proof == null) {
                out.println("Файл не найден.");
                System.out.println("Файл не найден.");
                return;
            }

            if (proof.firstWrongString != -1) {

                out.println("Доказательство некорректно начиная с высказывания номер " + (proof.firstWrongString + 1));

                System.out.println("Доказательство некорректно начиная с высказывания номер " + (proof.firstWrongString + 1));

                if (proof.possibleError != null && !"".equals(proof.possibleError)) {
                    out.println("Возможная ошибка:  " + proof.possibleError);
                    System.out.println("Возможная ошибка:  " + proof.possibleError);
                }

                return;
            }

            if (onlyCorrectness) {
                out.println("Доказательство корректно.");
                System.out.println("Доказательство корректно.");
                return;
            }

            for (int i = 0; i < proof.assumptions.size(); i++)
                out.print(proof.assumptions.get(i).getStringVersion() + (i + 1 < proof.assumptions.size() ? "," : ""));

            out.println("|-" + proof.problem.getStringVersion());

            for (int i = 0; i < proof.lines.size(); i++)
                out.println(proof.lines.get(i).getStringVersion());

            System.out.println("Доказательство выведено.");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (out != null)
                out.close();

        }
    }

    public void printFreshProof(ProofMaker proofMaker) {

        try {

            out = new PrintWriter(new File(outputFile));
            if (proofMaker == null) {
                out.println("Файл не найден.");
                System.out.println("Файл не найден.");
                return;
            }

            if (proofMaker.problemProof == null) {
                out.println("Формула не является тавтологией. Один из ошибочных наборов:");
                System.out.println("Формула не является тавтологией. Один из ошибочных наборов:");
                for (int i = 0; i < proofMaker.problem.proposes.size(); i++) {
                    out.println(proofMaker.problem.proposes.get(i) + " = " + proofMaker.currentValues.get(i));
                    System.out.println(proofMaker.problem.proposes.get(i) + " = " + proofMaker.currentValues.get(i));
                }
                return;
            }

            printProof(proofMaker.problemProof, false);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }

    public static void printTime(long time) {
        System.out.println("Time used: " + (System.currentTimeMillis() - time));
    }

}
