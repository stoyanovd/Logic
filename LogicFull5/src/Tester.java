import java.util.ArrayList;

public class Tester {

    static int mode = 4;

    static boolean single = false;

    static int test = 0;


    public static void main(String[] arg) {
        long time = System.currentTimeMillis();

        if (single)
            make(mode, test);
        else
            for (int i = 0; i < f.get(mode).size(); i++) {
                make(mode, i);
                MyPrinter.printTime(time);
                time = System.currentTimeMillis();
            }
    }

    static void make(int n, int m) {
        switch (n) {
            case 0: {
                Configuration.QUANTUM_NUMERATION = false;
                Configuration.ARITHMETIC_NUMERATION = false;
                MainChecker.check(f.get(n).get(m), "a.out");
                break;
            }
            case 1: {
                Configuration.QUANTUM_NUMERATION = false;
                Configuration.ARITHMETIC_NUMERATION = false;
                MainDeducer.deduce(f.get(n).get(m), "a.out");
                break;

            }
            case 2: {
                Configuration.QUANTUM_NUMERATION = false;
                Configuration.ARITHMETIC_NUMERATION = false;
                MainProver.prove(f.get(n).get(m), "a.out");
                break;

            }
            case 3: {
                Configuration.QUANTUM_NUMERATION = true;
                Configuration.ARITHMETIC_NUMERATION = false;
                MainDeducer.deduce(f.get(n).get(m), "a.out");
                break;

            }
            case 4: {
                Configuration.QUANTUM_NUMERATION = true;
                Configuration.ARITHMETIC_NUMERATION = true;
                MainChecker.check(f.get(n).get(m), "a.out");
                break;

            }

        }
    }

    static ArrayList<ArrayList<String>> f = new ArrayList<ArrayList<String>>();

    static {
        f.add(new ArrayList<String>());
        f.get(0).add("tests1/axiom1.in");
        f.get(0).add("tests1/axiom2.in");
        f.get(0).add("tests1/axiom3.in");
        f.get(0).add("tests1/maxtest1.in");

        f.add(new ArrayList<String>());
        f.get(1).add("tests2/proof0.in");

        f.add(new ArrayList<String>());
        f.get(2).add("tests3/aandnota.in");
        f.get(2).add("tests3/aornota.in");
        f.get(2).add("tests3/or.in");
        f.get(2).add("tests3/axiom1.in");
        f.get(2).add("tests3/swap_follow.in");
        f.get(2).add("tests3/big_one_propose.in");
        f.get(2).add("tests3/axiom1_4proposes.in");

        f.add(new ArrayList<String>());
        f.get(3).add("tests4/axiom.in");
        f.get(3).add("tests4/intro.in");
        f.get(3).add("tests4/intro2.in");
        f.get(3).add("tests4/must-fail.in");
        f.get(3).add("tests4/must-fail2.in");
        f.get(3).add("tests4/must-fail3.in");

        f.add(new ArrayList<String>());
        f.get(4).add("tests5/induction.in");
        f.get(4).add("tests5/must-fail.in");
        //f.get(4).add("tests5/proof5.in");
        f.get(4).add("tests5/reflexive.in");
        f.get(4).add("tests5/ax1.in");
    }

}
