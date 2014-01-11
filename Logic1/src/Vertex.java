import java.util.ArrayList;

public class Vertex {

    public static final ArrayList<String> OPERATIONS = new ArrayList<String>();

    static {
        OPERATIONS.add("->");
        OPERATIONS.add("|");
        OPERATIONS.add("&");
        OPERATIONS.add("!");
    }

    Vertex left;
    Vertex right;
    String operation;
    long hash;
    int size;

    Vertex() {
        left = null;
        right = null;
        operation = "";
        hash = 0;
        size = 0;
    }

    Vertex(Vertex _left, Vertex _right, String _operation, long _hash, int _size) {
        left = _left;
        right = _right;
        operation = _operation;
        hash = _hash;
        size = _size;
    }

    Vertex(String raw) {

        int leftBrace = -1;
        int rightBrace = raw.length();
        int braces[] = new int[raw.length()];
        int braces2[] = new int[raw.length()];
        ArrayList<Integer> openBraces = new ArrayList<Integer>();
        int k = 0;
        int mink = raw.length() / 2;
        while (raw.charAt(leftBrace + 1) == '(')
            leftBrace++;
        while (raw.charAt(rightBrace - 1) == ')')
            rightBrace--;
        for (int i = 0; i < raw.length(); i++) {
            if (raw.charAt(i) == '(') {
                k++;
                openBraces.add(i);
            } else if (raw.charAt(i) == ')') {
                k--;
                braces[i] = openBraces.get(openBraces.size() - 1);
                braces2[openBraces.get(openBraces.size() - 1)] = i;
                openBraces.remove(openBraces.size() - 1);
            }

            if (i > leftBrace && i < rightBrace && k < mink)
                mink = k;
        }

        k = raw.length();
        int j = 0;
        for (int i = mink; i < raw.length() - mink; i++) {
            if (raw.charAt(i) == '(') {
                i = braces2[i];
            } else if (raw.charAt(i) == OPERATIONS.get(j).charAt(0)) {
                k = i;
                break;
            }
        }

        while (k == raw.length() && j < OPERATIONS.size()) {
            if (j == 0)
                j++;
            for (int i = raw.length() - mink - 1; i >= mink; i--) {
                if (raw.charAt(i) == ')') {
                    i = braces[i];
                } else if (raw.charAt(i) == OPERATIONS.get(j).charAt(0)) {
                    k = i;
                    break;
                }
            }
            if (k != raw.length())
                break;
            j++;
        }

        if (j == OPERATIONS.size()) {
            left = null;
            right = null;
            operation = raw.substring(mink, raw.length() - mink);
            size = 1;
            hash = MyHash.hash(operation);
            return;
        }

        if (j == 3) {
            left = null;
            right = new Vertex(raw.substring(mink + 1, raw.length() - mink));
            operation = OPERATIONS.get(j);
            size = right.size + 1;
            hash = MyHash.hash(left, right, operation);
            return;
        }

        left = new Vertex(raw.substring(mink, k));
        right = new Vertex(raw.substring(k + 2 - (OPERATIONS.get(j).charAt(0) != '-' ? 1 : 0), raw.length() - mink));
        operation = OPERATIONS.get(j);
        size = Math.max(left.size, right.size) + 1;
        hash = MyHash.hash(left, right, operation);
    }

    boolean equals(Vertex v) {
        return (hash == v.hash);
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

}
