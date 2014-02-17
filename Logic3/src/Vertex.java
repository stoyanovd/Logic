import java.util.ArrayList;

public class Vertex {

    public static final ArrayList<String> OPERATIONS = new ArrayList<String>();

    static {
        OPERATIONS.add("->");  //  0
        OPERATIONS.add("|");   //  1
        OPERATIONS.add("&");   //  2
        OPERATIONS.add("!");   //  3

        OPERATIONS.add("@");   //  4
        OPERATIONS.add("?");   //  5

        OPERATIONS.add("_p");  //  6
        OPERATIONS.add("_t");  //  7
    }

    Vertex left;
    Vertex right;
    String operation;
    ArrayList<Vertex> terms;
    long hash;
    int size;

    Vertex() {
        left = null;
        right = null;
        operation = "";
        hash = 0;
        size = 0;
    }

    Vertex(Vertex v) {
        if (v == null)
            new Vertex();
        left = new Vertex(v.left);
        right = new Vertex(v.right);
        operation = v.operation;
        hash = v.hash;
        size = v.size;
        terms = new ArrayList<Vertex>(v.terms);
    }

    Vertex(String raw) {
        this(raw, null);
    }

    static String deleteOuterBrackets(String s) {

        if (s == null || s.length() == 0 || s.charAt(0) != '(')
            return s;
        int k = 0;
        int mink = s.length();
        int lb = -1;
        int rb = s.length();

        while (lb < s.length() - 1 && s.charAt(lb + 1) == '(')
            lb++;
        while (rb > 0 && s.charAt(rb - 1) == ')')
            rb--;

        for (int i = 0; i < rb; i++) {
            if (s.charAt(i) == '(')
                k++;
            else if (s.charAt(i) == ')')
                k--;
            if (i > lb && i < rb && k < mink)
                mink = k;
        }
        return s.substring(mink, s.length() - mink);
    }

    Vertex(String _raw, VertexMaker vertexMaker) {

        String raw = deleteOuterBrackets(_raw);

        Brackets brackets = new Brackets(raw);

        int k = raw.length();
        int j = 0;
        for (int i = 0; i < raw.length(); i++) {
            if (raw.charAt(i) == '(') {
                i = brackets.close(i);
            } else if (raw.charAt(i) == OPERATIONS.get(j).charAt(0)) {
                k = i;
                break;
            }
        }

        while (k == raw.length() && j < 6) {
            if (j == 0)
                j++;
            for (int i = raw.length() - 1; i >= 0; i--) {
                if (raw.charAt(i) == ')') {
                    i = brackets.open(i);
                } else if (raw.charAt(i) == OPERATIONS.get(j).charAt(0)) {
                    k = i;
                    break;
                }
            }
            if (k != raw.length())
                break;
            j++;
        }

        if (j >= 6) {
            if (!raw.contains("(")) {
                left = null;
                right = null;
                operation = raw;
                if (vertexMaker != null && operation != null && !"".equals(operation)) {
                    if (!vertexMaker.proposes.contains(operation))
                        vertexMaker.proposes.add(operation);
                }
                size = 1;
                hash = MyHash.hashString(operation);
                return;
            }
            left = null;
            right = null;
            operation = raw.substring(0, raw.indexOf('('));
            terms = new ArrayList<Vertex>();
            size = 0;
            String parse = raw.substring(raw.indexOf('(') + 1, raw.length() - 1);

            Brackets b = new Brackets(parse);
            while (parse.length() > 0) {
                int i;
                for (i = 0; i < parse.length(); i++) {
                    if (parse.charAt(i) == ',')
                        break;
                    if (parse.charAt(i) == '(')
                        i = b.close(i);
                }
                terms.add(new Vertex(parse.substring(0, i), vertexMaker));
                size = Math.max(terms.get(i).size, size);
                parse = parse.substring(0, i);
            }
            size++;
            hash = MyHash.hash(this);
            return;
        }

        if (j == 3) {
            left = null;
            right = new Vertex(raw.substring(1, raw.length()), vertexMaker);
            operation = OPERATIONS.get(j);
            hash = MyHash.hash(left, right, operation);
            size = right.size + 1;
            return;
        }

        if (j > 3) {
            String parse = "" + raw.charAt(1);
            for (int i = 2; i < raw.length(); i++) {
                if (Character.isDigit(raw.charAt(i)))
                    parse += raw.charAt(i);
                else
                    break;
            }
            left = new Vertex(parse, vertexMaker);
            right = new Vertex(raw.substring(1 + parse.length(), raw.length()), vertexMaker);
            operation = OPERATIONS.get(j);
            hash = MyHash.hash(left, right, operation);
            size = Math.max(right.size, left.size) + 1;
            return;
        }

        left = new Vertex(raw.substring(0, k), vertexMaker);
        right = new Vertex(raw.substring(k + 2 - (OPERATIONS.get(j).charAt(0) != '-' ? 1 : 0), raw.length()), vertexMaker);
        operation = OPERATIONS.get(j);
        hash = MyHash.hash(left, right, operation);
        size = Math.max(right.size, left.size) + 1;
    }

    boolean equals(Vertex v) {
        return (hash == v.hash && size == v.size);
    }

    boolean isPropose() {
        if (this == null)
            return false;
        if (left != null || right != null || (terms != null && terms.size() > 0))
            return false;
        return (operation != null && !("".equals(operation)));
    }

    boolean isAxiom(int i) {
        switch (i) {
            case 1:
                return AxiomsList.axiom_1(this);
            case 2:
                return AxiomsList.axiom_2(this);
            case 3:
                return AxiomsList.axiom_3(this);
            case 4:
                return AxiomsList.axiom_4(this);
            case 5:
                return AxiomsList.axiom_5(this);
            case 6:
                return AxiomsList.axiom_6(this);
            case 7:
                return AxiomsList.axiom_7(this);
            case 8:
                return AxiomsList.axiom_8(this);
            case 9:
                return AxiomsList.axiom_9(this);
            case 10:
                return AxiomsList.axiom_10(this);
        }
        return false;
    }

    boolean isQuantumAxiom(int i, String y) {
        if (i == 1)
            return AxiomsList.axiom_11(this, y);
        else if (i == 2)
            return AxiomsList.axiom_12(this, y);
        return false;
    }

    public boolean hasEntries(String y) {
        if (this == null)
            return false;
        if (operation.equals(y))
            return true;
        if (terms != null) {
            for (int i = 0; i < terms.size(); i++)
                if (terms.get(i).hasEntries(y))
                    return true;
            return false;
        }
        return (left.hasEntries(y) || right.hasEntries(y));

    }

    public boolean hasNotFreeEntries(String y) {                       //TODO   needs fresh brains
        if (this == null)
            return true;
        if (operation.equals(y))
            return false;
        if ((operation.equals(OPERATIONS.get(4)) || operation.equals(OPERATIONS.get(5))) && left != null && left.operation.equals(y))
            return true;
        if (terms != null) {
            for (int i = 0; i < terms.size(); i++)
                if (!terms.get(i).hasNotFreeEntries(y))
                    return false;
            return true;
        }
        return (left.hasNotFreeEntries(y) && right.hasNotFreeEntries(y));
    }

    public String getStringVersion() {
        if (left == null && right == null)
            return ("(" + operation + ")");
        if ("!".equals(operation))
            return ("(!(" + Vertex.deleteOuterBrackets(right.getStringVersion()) + "))");

        return "((" + Vertex.deleteOuterBrackets(left.getStringVersion()) + ")" + operation +
                "(" + Vertex.deleteOuterBrackets(right.getStringVersion()) + "))";
    }
}
