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

            if (proof == null)
                out.println("Ошибка. Попытка вывести несуществующее доказательство.");
            else if (proof.firstWrongString != -1) {
                out.println("Доказательство некорректно начиная с высказывания номер " + (proof.firstWrongString + 1));
                if (proof.possibleError != null)
                    out.println(proof.possibleError);
                Helper.println("uncorrect proof  -  it was written");
            } else if (onlyCorrectness)
                out.println("Доказательство корректно.");
            else {
                if (proof.beta != null && !"".equals(proof.beta)) {
                    if (proof.assumptions != null)
                        for (int i = 0; i < proof.assumptionsStrings.size(); i++)
                            out.print(proof.assumptionsStrings.get(i) + (i + 1 < proof.assumptionsStrings.size() ? "," : ""));
                    out.println("|-" + proof.beta);
                }
                if (proof.proofStrings != null)
                    for (int i = 0; i < proof.proofStrings.size(); i++)
                        out.println(proof.proofStrings.get(i));
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printValues(ProofMaker proofMaker) {

        try {

            out = new PrintWriter(new File(outputFile));

            if (proofMaker == null)
                out.println("Ошибка. Попытка вывести несуществующее доказательство.");
            else {
                out.println("Формула не является тавтологией. Один из ошибочных наборов:");
                for (int i = 0; i < proofMaker.proposesNames.size(); i++)
                    out.println(proofMaker.proposesNames.get(i) + " = " + proofMaker.values.get(i));
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
