import java.util.ArrayList;

public class SimpleOperation {

    public static final ArrayList<String> OPERATIONS = new ArrayList<String>();

    static {
        OPERATIONS.add("->");  //  0
        OPERATIONS.add("|");   //  1
        OPERATIONS.add("&");   //  2

        OPERATIONS.add("@");   //  3
        OPERATIONS.add("?");   //  4
        OPERATIONS.add("!");   //  5

        OPERATIONS.add("=");   //  6
        OPERATIONS.add("+");   //  7
        OPERATIONS.add("*");   //  8
        OPERATIONS.add("'");   //  9
    }

    public static final int[][][] values = new int[OPERATIONS.size()][2][2];

    static {
        values[0][0][0] = 1;
        values[0][0][1] = 1;
        values[0][1][0] = 0;
        values[0][1][1] = 1;

        values[1][0][0] = 0;
        values[1][0][1] = 1;
        values[1][1][0] = 1;
        values[1][1][1] = 1;

        values[2][0][0] = 0;
        values[2][0][1] = 0;
        values[2][1][0] = 0;
        values[2][1][1] = 1;

        values[5][0][0] = 1;
        values[5][0][1] = 0;
        values[5][1][0] = 1;
        values[5][1][1] = 0;
    }

    static boolean getValue(boolean left, String operation, boolean right) {
        int l = 0;
        if (left)
            l++;
        int r = 0;
        if (right)
            r++;

        int k = OPERATIONS.indexOf(operation);
        if (k < 0) {
            k = -1;
        }

        if (values[k][l][r] == 1)
            return true;
        else
            return false;
    }

    static boolean getValue(Vertex left, String operation, Vertex right) {
        boolean a = false;
        if (left != null)
            a = left.value;

        boolean b = false;
        if (right != null)
            b = right.value;

        return getValue(a, operation, b);
    }
}
