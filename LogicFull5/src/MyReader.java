import java.io.File;
import java.util.ArrayList;

public class MyReader {

    String inputFile;

    public MyReader(String _inputFile) {
        inputFile = _inputFile;
    }

    public ArrayList<String> readArray() {
        File file = new File(inputFile);
        if (!file.exists()) {
            System.out.println("Input file not exists");
            System.out.println(inputFile);
            return null;
        }

        ArrayList<String> answer = new ArrayList<String>();
        FastScanner in = new FastScanner(file);
        String raw = in.next();
        while (raw != null) {
            if (!"".equals(raw)) {
                if ((int) raw.charAt(0) == 65279)
                    raw = raw.substring(1);
                raw = raw.replace(" ", "");
                if (raw.length() > 0 && raw.charAt(0) != '/')
                    answer.add(raw);
            }
            raw = in.next();
        }
        return answer;
    }

    public String readString() {                                        //  not first strings will go to trash
        ArrayList<String> arrayList = readArray();
        if (arrayList != null && arrayList.size() > 0)
            return arrayList.get(0);
        return null;
    }

    public Proof readProof() {
        ArrayList<String> answer = readArray();
        if (answer == null || answer.size() < 1)
            return null;

        Proof proof = new Proof();

        if (!answer.get(0).contains("|-")) {
            for (int i = 0; i < answer.size(); i++) {
                proof.addLine(answer.get(i));
            }                                                 //TODO is it right, that we don't need proposes there
        } else {
            String header = answer.get(0);
            proof.setProblem(header.substring(header.indexOf("|-") + 2));

            String[] temp = header.substring(0, header.indexOf("|-")).split(",");
            for (int i = 0; i < temp.length; i++)
                proof.addAssumption(temp[i]);

            for (int i = 1; i < answer.size(); i++)
                proof.addLine(answer.get(i));
        }
        return proof;
    }
}
