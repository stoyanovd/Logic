import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ProofChecker {
    FastScanner in;
    PrintWriter out;
    public static final boolean DEBUG_MODE = false;

    public int solve() throws IOException {

        String raw = "";
        Checker checker = new Checker();
        for (int i = 1; ; i++) {
            if (DEBUG_MODE)
                System.out.println("Begin - " + i);
            raw = in.next();
            if (raw == null)
                break;
            if (!checker.singleCheck(raw))
                return i;
        }
        return 0;
    }

    public void run() {
        try {

            out = new PrintWriter(new File("a.out"));       //     output file

            File file = new File("a4.in");                  //     input file

            if (!file.exists())
                out.println("Файл не найден.");
            else {
                in = new FastScanner(file);
                int answer = solve();
                if (answer == 0)
                    out.println("Доказательство корректно.");
                else
                    out.println("Доказательство некорректно начиная с высказывания номер " + answer);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] arg) {
        long time = System.currentTimeMillis();
        new ProofChecker().run();
        if (DEBUG_MODE)
            System.out.println("time = " + (System.currentTimeMillis() - time));
    }
}