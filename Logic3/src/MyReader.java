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
            Helper.println("Input file not exists");
            Helper.println(inputFile);
            return null;
        }

        ArrayList<String> answer = new ArrayList<String>();
        FastScanner in = new FastScanner(file);
        String raw = in.next();
        while (raw != null) {
            if ((int) raw.charAt(0) == 65279)
                raw = raw.substring(1);
            answer.add(raw);
            raw = in.next();
        }
        return answer;
    }

    public String readString() {
        ArrayList<String> arrayList = readArray();
        if (arrayList != null && arrayList.size() > 0)
            return arrayList.get(0);
        return null;
    }

    public Proof readProof() {
        ArrayList<String> answer = readArray();
        if (answer.size() < 1)
            return null;
        Proof proof = new Proof(answer.get(0), new ArrayList<String>(answer.subList(1, answer.size())));
        return proof;
    }

    public Proof readProofWithoutHeader() {
        ArrayList<String> answer = readArray();
        if (answer == null || answer.size() < 1)
            return null;
        Proof proof = new Proof();
        proof.proofStrings = new ArrayList<String>(answer);
        return proof;
    }
}
