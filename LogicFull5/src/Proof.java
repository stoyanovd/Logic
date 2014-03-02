import java.util.ArrayList;

public class Proof {

    Vertex problem;

    ArrayList<Vertex> assumptions;
    ArrayList<Vertex> lines;

    ArrayList<String> proposes;                                                //TODO  maybe delete this????

    int firstWrongString = -1;
    String possibleError;

    Proof() {
        problem = new Vertex();

        assumptions = new ArrayList<Vertex>();
        lines = new ArrayList<Vertex>();

        proposes = new ArrayList<String>();
    }

    static void copyFromSecond(Proof a, Proof p) {
        a.problem = p.problem;

        a.assumptions = new ArrayList<Vertex>(p.assumptions);
        a.lines = new ArrayList<Vertex>(p.lines);

        a.proposes = new ArrayList<String>(p.proposes);

        a.firstWrongString = p.firstWrongString;
        a.possibleError = p.possibleError;
    }

    Proof(Proof p) {
        this();
        if (p == null)
            return;
        copyFromSecond(this, p);                          //TODO   is it at all working?
    }

    boolean isCorrect() {
        Checker checker = new Checker(this);
        firstWrongString = -1;
        possibleError = "";
        for (int i = 0; i < lines.size(); i++) {
            if(i == 93)
                i = 93;
            if (checker.singleCheck(lines.get(i)) == null) {
                firstWrongString = i;
                possibleError = checker.possibleError;
                return false;
            }
        }
        return true;
    }

    public void singleDeduct()                                    // it must be correct proof
    {
        if (assumptions.size() == 0)
            throw new NullPointerException();

        Proof modProof = new Proof();

        Checker checker = new Checker(this, true);
        for (int i = 0; i < lines.size(); i++)
            modProof.addProof(checker.singleCheck(lines.get(i)));

        lines = new ArrayList<Vertex>(modProof.lines);
    }

    public void fullDeduct() {
        while (assumptions.size() > 0)
            singleDeduct();
    }

    public boolean fullDeductCarefully() {
        while (assumptions.size() > 0) {
            isCorrect();
            if (firstWrongString != -1)
                return false;
            singleDeduct();
        }
        isCorrect();
        return (firstWrongString == -1);
    }

    void addLine(Vertex v) {
        lines.add(new Vertex(v));                                   //TODO  it can be trash    !!!! NO IT ISN'T TRASH !!!!
    }

    void addLine(String s) {
        lines.add(new Vertex(s));
    }


    void addAssumption(Vertex v) {
        assumptions.add(new Vertex(v));
    }

    void addAssumption(String s) {
        assumptions.add(new Vertex(s));
    }

    void setProblem(String s) {
        problem = new Vertex(s);
    }

    void setProblem(Vertex v) {
        problem = new Vertex(v);
    }


    void addProof(Proof p)                                                  // I don't add assumptions!!!
    {
        if (p == null)
            p = null;
        if (p.lines == null)
            p = null;
        for (int i = 0; i < p.lines.size(); i++)
            lines.add(new Vertex(p.lines.get(i)));                                 //TODO  it can be trash       !!!! NO IT ISN'T TRASH !!!!
    }

}
