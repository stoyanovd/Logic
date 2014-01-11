import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DeductionTransformer {
    FastScanner in;
    PrintWriter out;
    String beta = "";
    String alpha = "";
    DeductionChecker checker;
    ArrayList<Long> assumptions = new ArrayList<Long>();
    int badEntry;
    long alphaHash;
    boolean goodStart = false;
    ArrayList<String> inputStrings;
    ArrayList<String> resultStrings;

    public boolean getAssumptions() {

        String raw = inputStrings.get(0);
        if (raw == null)
            return false;

        if ((int) raw.charAt(0) == 65279)
            raw = raw.substring(1);
        String[] tempStrings = raw.split("\\|-");
        for (int i = 0; i < tempStrings.length; i++)
            System.out.println("split |-   :" + tempStrings[i]);
        if (tempStrings.length < 2 || null == tempStrings[0] || "".equals(tempStrings[0]) || null == tempStrings[1] || "".equals(tempStrings[1]))
            return false;
        beta = tempStrings[1];
        String temp = tempStrings[0];
        tempStrings = temp.split(",");
        for (int i = 0; i < tempStrings.length; i++)
            System.out.println("split ,   :" + tempStrings[i]);
        if (tempStrings.length < 1)
            return false;
        alpha = "(" + tempStrings[tempStrings.length - 1] + ")";
        Vertex vertex = new Vertex(alpha);
        alphaHash = vertex.hash;
        if (Configuration.DEBUG_MODE)
            System.out.println("Alpha:" + alpha + "; hash = " + alphaHash);
        String newAssumption = "";
        assumptions = new ArrayList<Long>();
        for (int i = 0; i < tempStrings.length - 1; i++) {
            vertex = new Vertex(tempStrings[i]);
            assumptions.add(vertex.hash);
            if (Configuration.DEBUG_MODE)
                System.out.println("Assumption:" + tempStrings[i] + "; hash = " + vertex.hash + " first char=" + (int) (tempStrings[i].charAt(0)));
            newAssumption += tempStrings[i];
            if (i != tempStrings.length - 2)
                newAssumption += ",";
        }
        newAssumption += "|-" + alpha + "->(" + beta + ")";
        resultStrings.add(newAssumption);
        return true;
    }

    public boolean getProof() {

        checker = new DeductionChecker(assumptions, alpha, alphaHash);
        String raw = "";
        if (inputStrings.size() > 1)
            raw = inputStrings.get(1);
        String result;
        int i = 1;
        while (raw != null) {
            result = checker.deductionCheck(raw);
            if (result == null) {
                badEntry = i;
                return false;
            } else
                resultStrings.add(result);
            if (inputStrings.size() > i + 1)
                raw = inputStrings.get(i + 1);
            else
                raw = null;
            i++;
        }
        return true;
    }

    DeductionTransformer() {
        try {
            boolean badStart = false;
            if (!(new ProofChecker(Configuration.FILE_FOR_DEDUCTION, Configuration.FILE_FOR_DEDUCTION_RESULT)).run())
                badStart = true;
            out = new PrintWriter(new File(Configuration.FILE_FOR_DEDUCTION_RESULT));

            File file = new File(Configuration.FILE_FOR_DEDUCTION);
            goodStart = false;
            inputStrings = new ArrayList<String>();
            if (!file.exists())
                out.println("Файл не найден.");
            else if (badStart) {
                out.println("Исходный вывод некоррректен.");
            } else {
                in = new FastScanner(file);
                goodStart = true;
                String raw = "";
                raw = in.next();
                while (raw != null && !"".equals(raw)) {
                    inputStrings.add(raw);
                    raw = in.next();
                }
            }
            resultStrings = new ArrayList<String>(inputStrings);
            while (goodStart) {
                System.out.println("BIG_ITERATION");
                inputStrings = new ArrayList<String>();
                String[] temp;
                for(int i = 0; i < resultStrings.size(); i++)
                {
                    temp = resultStrings.get(i).split("\\s+");
                    for(int j = 0; j < temp.length; j++)
                    {
                        System.out.println("new temp - " + temp[j]);
                        inputStrings.add(temp[j]);
                    }
                }
                resultStrings = new ArrayList<String>();
                if (!run())
                    break;
            }
            if (goodStart) {
                resultStrings = new ArrayList<String>(inputStrings);
                for (int i = 0; i < resultStrings.size(); i++)
                    out.println(resultStrings.get(i));
            }
            out.close();
            if (Configuration.DEBUG_MODE)
                (new ProofChecker(Configuration.FILE_FOR_DEDUCTION_RESULT, Configuration.FILE_FOR_CHECK_OF_RESULT)).run();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean run() {

        if (!getAssumptions() || !getProof()) {
            if (Configuration.DEBUG_MODE)
                System.out.println("Bad entry = " + badEntry);
            return false;
        }
        return true;
    }

    public static void main(String[] arg) {
        long time = System.currentTimeMillis();
        new DeductionTransformer();
        if (Configuration.DEBUG_MODE)
            System.out.println("time = " + (System.currentTimeMillis() - time));
    }
}