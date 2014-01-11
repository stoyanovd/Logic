import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ProofChecker {
    FastScanner in;
    PrintWriter out;
    String beta = "";
    Checker checker;
    String inputFile = "";
    String outputFile = "";

    ProofChecker(String in, String out) {
        inputFile = in;
        outputFile = out;
    }

    public boolean getAssumptions() throws IOException {

        String raw = "";
        raw = in.next();
        if (raw == null)
            return false;

        if ((int) raw.charAt(0) == 65279)
            raw = raw.substring(1);
        String[] tempStrings = raw.split("\\|-");
        for (int i = 0; i < tempStrings.length; i++)
            System.out.println("split |-   :" + tempStrings[i]);
        beta = tempStrings[1];                                                      //had you proved   beta???
        String temp = tempStrings[0];
        tempStrings = temp.split(",");
        for (int i = 0; i < tempStrings.length; i++)
            System.out.println("split ,   :" + tempStrings[i]);
        for (int i = 0; i < tempStrings.length; i++) {
            Vertex vertex = new Vertex(tempStrings[i]);
            checker.assumptions.add(vertex.hash);
            if (Configuration.DEBUG_MODE)
                System.out.println("Assumption:" + tempStrings[i] + "; hash = " + vertex.hash + " first char=" + (tempStrings[i].length() == 0 ? "_" : (int) (tempStrings[i].charAt(0))));
        }
        return true;
    }

    public int solve() throws IOException {

        String raw = "";
        checker = new Checker();
        getAssumptions();
        for (int i = 1; ; i++) {
            if (Configuration.DEBUG_MODE)
                System.out.println("Begin - " + i);
            raw = in.next();
            if (raw == null)
                break;
            if (!checker.singleCheck(raw))
                return i;
        }
        return 0;
    }

    public boolean run() {
        int answer = 0;
        try {

            out = new PrintWriter(new File(outputFile));

            File file = new File(inputFile);

            if (!file.exists())
                out.println("Файл не найден.");
            else {
                in = new FastScanner(file);
                answer = solve();
                if (answer == 0)
                    out.println("Доказательство корректно.");
                else
                    out.println("Доказательство некорректно начиная с высказывания номер " + answer);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (answer == 0);
    }


    public static void main(String[] arg) {
        long time = System.currentTimeMillis();
        new ProofChecker(Configuration.FILE_FOR_DEDUCTION, Configuration.FILE_FOR_DEDUCTION_RESULT).run();
        if (Configuration.DEBUG_MODE)
            System.out.println("time = " + (System.currentTimeMillis() - time));
    }
}