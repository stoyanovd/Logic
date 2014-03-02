public class AxiomsSupporter {


    static boolean isAxiom(Vertex v, int i) {
        switch (i) {
            case 1:
                return AxiomsList.axiom_1(v);
            case 2:
                return AxiomsList.axiom_2(v);
            case 3:
                return AxiomsList.axiom_3(v);
            case 4:
                return AxiomsList.axiom_4(v);
            case 5:
                return AxiomsList.axiom_5(v);
            case 6:
                return AxiomsList.axiom_6(v);
            case 7:
                return AxiomsList.axiom_7(v);
            case 8:
                return AxiomsList.axiom_8(v);
            case 9:
                return AxiomsList.axiom_9(v);
            case 10:
                return AxiomsList.axiom_10(v);
        }
        return false;
    }

    static String isQuantumAxiom(Vertex v, int i) {
        if (i == 1)
            return AxiomsList.axiom_11(v);
        else if (i == 2)
            return AxiomsList.axiom_12(v);
        return "";
    }

    static boolean isArithmeticAxiom(Vertex v, int i) {
        switch (i) {
            case 1:
                return AxiomsList.axiom_Arithmetic_1(v);
            case 2:
                return AxiomsList.axiom_Arithmetic_2(v);
            case 3:
                return AxiomsList.axiom_Arithmetic_3(v);
            case 4:
                return AxiomsList.axiom_Arithmetic_4(v);
            case 5:
                return AxiomsList.axiom_Arithmetic_5(v);
            case 6:
                return AxiomsList.axiom_Arithmetic_6(v);
            case 7:
                return AxiomsList.axiom_Arithmetic_7(v);
            case 8:
                return AxiomsList.axiom_Arithmetic_8(v);
            case 9:
                return AxiomsList.axiom_Arithmetic_9(v);
        }
        return false;
    }

    static boolean isModusPonensAHeader(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.right.left == null || v.right.right == null)
            return false;
        if (!"->".equals(v.operation) || !"@".equals(v.right.operation))
            return false;
        return true;
    }

    static boolean isModusPonensEHeader(Vertex v) {
        if (v == null || v.left == null || v.right == null || v.left.left == null || v.left.right == null)
            return false;
        if (!"->".equals(v.operation) || !"?".equals(v.left.operation))
            return false;
        return true;
    }
}
