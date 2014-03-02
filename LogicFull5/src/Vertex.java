import java.util.ArrayList;

public class Vertex {
    //TODO   check parser   please!!!!!
    String text;
    boolean value;

    long hash;
    long size;          //i think it will be useless
    String operation;

    Vertex left;
    Vertex right;

    ArrayList<Vertex> terms;

    ArrayList<String> proposes;
    ArrayList<String> freeProposes;

    Vertex() {
        text = "";
        value = true;

        hash = 0;
        size = 1;
        operation = "";

        left = null;
        right = null;
        terms = null;

        proposes = new ArrayList<String>();
        freeProposes = new ArrayList<String>();
    }

    public static void makeVertexFromSecond(Vertex a, Vertex v) {
        a.text = v.text;
        a.value = v.value;

        a.hash = v.hash;
        a.size = v.size;
        a.operation = v.operation;

        a.terms = null;
        a.left = null;
        a.right = null;

        if (v.terms != null)
            a.terms = new ArrayList<Vertex>(v.terms);
        if (v.left != null)
            a.left = new Vertex(v.left);
        if (v.right != null)
            a.right = new Vertex(v.right);

        a.proposes = new ArrayList<String>(v.proposes);
        a.freeProposes = new ArrayList<String>(v.freeProposes);
    }

    Vertex(Vertex v) {
        this();
        if (v == null)
            return;
        makeVertexFromSecond(this, v);
    }

    Vertex(String raw) {
        this();
        Vertex v = makeVertexFromString(raw);
        if (v == null)
            return;
        makeVertexFromSecond(this, v);
    }

    @Override
    public int hashCode() {
        return (int) hash;
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
        if (mink >= s.length() - mink)
            return "";
        return s.substring(mink, s.length() - mink);
    }

    static Vertex makeVertexNoOperations(String _operation) {

        Vertex v = new Vertex();
        String operation = deleteOuterBrackets(_operation);

        if (operation.contains("(")) {

            v.operation = getStartingName(operation);
            String raw = deleteOuterBrackets(operation.substring(v.operation.length()));
            Brackets brackets = new Brackets(raw);
            v.terms = new ArrayList<Vertex>();
            int k = 0;
            for (int i = 0; i < raw.length(); i++) {
                if (raw.charAt(i) == '(')
                    i = brackets.close(i);
                else if (raw.charAt(i) == ',') {
                    v.terms.add(new Vertex(raw.substring(k, i)));
                    k = i + 1;
                }
            }
            int newSize = 0;
            if (k < raw.length()) {
                v.terms.add(new Vertex(raw.substring(k)));
                newSize += v.terms.get(v.terms.size() - 1).size;
            }
            for (int i = 0; i < v.terms.size(); i++)
                v.proposes.addAll(v.terms.get(i).proposes);

            v.proposes = MyArrays.makeUnic(v.proposes);

            v.text = v.getStringVersion();
            v.hash = MyHash.hash(v.text);
            v.size = newSize + v.operation.length() + 1;

            return v;
        }

        v.operation = operation;
        v.proposes.add(operation);
        v.text = v.getStringVersion();

        v.hash = MyHash.hash(v.text);
        v.value = false;            //fake values
        v.size = v.operation.length() + 1;
        return v;
    }

    static Vertex makeVertexWithOperation(String _left, String _operation, String _right) {
        if (_left == null && _right == null)
            return makeVertexNoOperations(_operation);
        Vertex v = new Vertex();
        if (_left != null && !"".equals(_left)) {
            v.left = new Vertex(_left);
            v.proposes.addAll(v.left.proposes);
        }
        if (_right != null && !"".equals(_right)) {
            v.right = new Vertex(_right);
            v.proposes.addAll(v.right.proposes);
        }

        v.proposes = MyArrays.makeUnic(v.proposes);

        v.operation = _operation;
        v.text = v.getStringVersion();

        v.hash = MyHash.hash(v.text);
        v.value = SimpleOperation.getValue(v.left, v.operation, v.right);           //fake values
        //v.size = v.left.size + v.right.size + v.operation.length() + 1;

        return v;
    }

    static Vertex makeVertexFromString(String _raw) {
        if (_raw == null)
            return null;

        String raw = deleteOuterBrackets(_raw);

        if (raw.length() == 0)
            return null;

        Brackets brackets = new Brackets(raw);

        for (int i = 0; i < raw.length(); i++) {
            if (raw.charAt(i) == '(')
                i = brackets.close(i);
            else if (raw.charAt(i) == '-')
                return makeVertexWithOperation(raw.substring(0, i), "->", raw.substring(i + 2));
        }

        for (int i = raw.length() - 1; i >= 0; i--) {
            if (raw.charAt(i) == ')')
                i = brackets.open(i);
            else if (raw.charAt(i) == '|')
                return makeVertexWithOperation(raw.substring(0, i), "|", raw.substring(i + 1));
        }

        for (int i = raw.length() - 1; i >= 0; i--) {
            if (raw.charAt(i) == ')')
                i = brackets.open(i);
            else if (raw.charAt(i) == '&')
                return makeVertexWithOperation(raw.substring(0, i), "&", raw.substring(i + 1));
        }

        if (raw.charAt(0) == '@' || raw.charAt(0) == '?') {
            String name = getStartingName(raw.substring(1));
            return makeVertexWithOperation(name, "" + raw.charAt(0), raw.substring(name.length() + 1));
        }

        if (raw.charAt(0) == '!')
            return makeVertexWithOperation(null, "!", raw.substring(1));

        for (int i = raw.length() - 1; i >= 0; i--) {
            if (raw.charAt(i) == ')')
                i = brackets.open(i);
            else if (raw.charAt(i) == '=')
                return makeVertexWithOperation(raw.substring(0, i), "=", raw.substring(i + 1));
        }

        for (int i = raw.length() - 1; i >= 0; i--) {
            if (raw.charAt(i) == ')')
                i = brackets.open(i);
            else if (raw.charAt(i) == '+')
                return makeVertexWithOperation(raw.substring(0, i), "+", raw.substring(i + 1));
        }

        for (int i = raw.length() - 1; i >= 0; i--) {
            if (raw.charAt(i) == ')')
                i = brackets.open(i);
            else if (raw.charAt(i) == '*')
                return makeVertexWithOperation(raw.substring(0, i), "*", raw.substring(i + 1));
        }
        if (raw.charAt(raw.length() - 1) == '\'')
            return makeVertexWithOperation(null, "\'", raw.substring(0, raw.length() - 1));

        return makeVertexWithOperation(null, raw, null);
    }

    void makeCurrentValues(ArrayList<String> names, ArrayList<Boolean> newValues) {
        if (left == null && right == null) {
            if (names.indexOf(operation) == -1)
                left = null;
            value = newValues.get(names.indexOf(operation));
        } else {
            if (left != null)
                left.makeCurrentValues(names, newValues);
            if (right != null)
                right.makeCurrentValues(names, newValues);
            value = SimpleOperation.getValue(left, operation, right);
        }
    }

    void makeCurrentValues(ArrayList<Boolean> newValues) {
        makeCurrentValues(proposes, newValues);
    }

    public static String getStartingName(String s) {
        if (s == null || "".equals(s))
            throw new NullPointerException();
        String ans = "" + s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                break;
            ans += s.charAt(i);
        }
        return ans;
    }

    @Override
    public boolean equals(Object vv) {
        if (vv == null)
            return false;

        if (!(vv instanceof Vertex))
            return false;
        Vertex v = (Vertex) vv;

        if (hash != v.hash)
            return false;

        if (!MyArrays.equalsNull(operation, v.operation))
            return false;
        if (operation != null && !operation.equals(v.operation))
            return false;


        if (!MyArrays.equalsNull(terms, v.terms))
            return false;
        if (terms != null && !terms.equals(v.terms))
            return false;

        if (!MyArrays.equalsNull(left, v.left))
            return false;
        if (left != null && !left.equals(v.left))
            return false;

        if (!MyArrays.equalsNull(right, v.right))
            return false;
        if (right != null && !right.equals(v.right))
            return false;

        return true;
    }

    public static boolean equalVertex(Vertex l, Vertex r) {
        if (l == null && r != null)
            return false;
        if (l != null && r == null)
            return false;
        if (l == null)
            return true;
        return l.equals(r);
    }

    public static boolean equalVertexOnluNullness(Vertex l, Vertex r) {
        if (l == null && r != null)
            return false;
        if (l != null && r == null)
            return false;
        return true;
    }


    public String getStringVersion() {                                 // with surface brackets
        if (text != null && !"".equals(deleteOuterBrackets(text)))
            return text;
        if (left == null && right == null) {
            if (terms == null)
                return ("(" + operation + ")");
            String answer = "(" + operation + "(";
            for (int i = 0; i < terms.size(); i++)
                answer += Vertex.deleteOuterBrackets(terms.get(i).getStringVersion()) + (i + 1 < terms.size() ? "," : "");
            answer += "))";
            return answer;
        }
        if ("!".equals(operation))
            return ("(!(" + Vertex.deleteOuterBrackets(right.getStringVersion()) + "))");

        if ("@".equals(operation) || "?".equals(operation))
            return ("(" + operation + left.operation + "(" + Vertex.deleteOuterBrackets(right.getStringVersion()) + "))");

        if ("'".equals(operation))
            return ("((" + Vertex.deleteOuterBrackets(right.getStringVersion()) + ")')");

        return "((" + Vertex.deleteOuterBrackets(left.getStringVersion()) + ")" + operation +
                "(" + Vertex.deleteOuterBrackets(right.getStringVersion()) + "))";
    }

    public void getFreeProposes() {

        getFreeProposesInner(new ArrayList<String>());
    }

    private void getFreeProposesInner(ArrayList<String> alreadyInQuantors) {

        freeProposes = new ArrayList<String>();

        if ("@".equals(operation) || "?".equals(operation)) {
            ArrayList<String> tempList = new ArrayList<String>(alreadyInQuantors);
            tempList.add(left.operation);
            right.getFreeProposesInner(tempList);
            freeProposes.addAll(right.freeProposes);

            return;
        }

        if (left != null) {
            left.getFreeProposesInner(alreadyInQuantors);
            freeProposes.addAll(left.freeProposes);
        }

        if (right != null) {
            right.getFreeProposesInner(alreadyInQuantors);
            freeProposes.addAll(right.freeProposes);
        }

        if (terms != null)
            for (int i = 0; i < terms.size(); i++) {
                terms.get(i).getFreeProposesInner(alreadyInQuantors);
                freeProposes.addAll(terms.get(i).freeProposes);
            }

        if (left == null && right == null && terms == null)
            if (!alreadyInQuantors.contains(operation))
                freeProposes.add(operation);

        freeProposes = MyArrays.makeUnic(freeProposes);
    }
}
