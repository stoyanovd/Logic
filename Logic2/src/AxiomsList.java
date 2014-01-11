public class AxiomsList {

    public static boolean axiom_1(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.right.operation))
            return false;
        return (v.left.equals(v.right.right));
    }

    public static boolean axiom_2(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.left.left == null || v.left.right == null || v.right.left.left == null || v.right.left.right == null ||
                v.right.right.left == null || v.right.right.right == null || v.right.left.right.left == null ||
                v.right.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.left.operation) ||
                !"->".equals(v.right.operation) ||
                !"->".equals(v.right.left.operation) ||
                !"->".equals(v.right.right.operation) ||
                !"->".equals(v.right.left.right.operation))
            return false;
        return (v.left.left.equals(v.right.left.left) && v.left.left.equals(v.right.right.left) &&
                v.left.right.equals(v.right.left.right.left) && v.right.right.right.equals(v.right.left.right.right));
    }

    public static boolean axiom_3(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.right.right.left == null || v.right.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.right.operation) ||
                !"&".equals(v.right.right.operation))
            return false;
        return (v.right.right.left.equals(v.left) && v.right.right.right.equals(v.right.left));
    }

    public static boolean axiom_4(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"&".equals(v.left.operation))
            return false;
        return (v.left.left.equals(v.right));
    }

    public static boolean axiom_5(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"&".equals(v.left.operation))
            return false;
        return (v.left.right.equals(v.right));
    }

    public static boolean axiom_6(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"|".equals(v.right.operation))
            return false;
        return (v.right.left.equals(v.left));
    }

    public static boolean axiom_7(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"|".equals(v.right.operation))
            return false;
        return (v.right.right.equals(v.left));
    }

    public static boolean axiom_8(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.left.left == null || v.left.right == null || v.right.left.left == null ||
                v.right.left.right == null || v.right.right.left == null || v.right.right.right == null ||
                v.right.right.left.left == null || v.right.right.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.left.operation) ||
                !"->".equals(v.right.operation) || !"->".equals(v.right.right.operation) || !"->".equals(v.right.left.operation)
                || !"|".equals(v.right.right.left.operation))
            return false;
        return (v.left.left.equals(v.right.right.left.left) &&
                v.left.right.equals(v.right.left.right) && v.left.right.equals(v.right.right.right) &&
                v.right.left.left.equals(v.right.right.left.right));
    }

    public static boolean axiom_9(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null ||
                v.left.left == null || v.left.right == null || v.right.left.left == null || v.right.left.right == null ||
                v.right.right.right == null || v.right.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"->".equals(v.left.operation) ||
                !"->".equals(v.right.operation) || !"->".equals(v.right.left.operation) ||
                !"!".equals(v.right.right.operation) || !"!".equals(v.right.left.right.operation))
            return false;
        return (v.left.left.equals(v.right.right.right) &&
                v.left.left.equals(v.right.left.left) && v.right.left.right.right.equals(v.left.right));
    }

    public static boolean axiom_10(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.right == null ||
                v.left.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"!".equals(v.left.operation) || !"!".equals(v.left.right.operation))
            return false;
        return (v.left.right.right.equals(v.right));
    }


}
