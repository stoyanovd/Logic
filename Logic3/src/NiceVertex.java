import java.util.ArrayList;

public class NiceVertex extends Vertex {

    String text;

    NiceVertex left;
    NiceVertex right;

    boolean value;

    NiceVertex(Vertex v, ArrayList<Boolean> values, ArrayList<String> proposesNames) {              //TODO    Does it really works???????

        super();
        if (v == null)
            return;
        hash = v.hash;
        size = v.size;
        operation = v.operation;

        if (v.left == null && v.right == null) {
            text = "(" + v.operation + ")";
            if (!proposesNames.contains(operation))
                Helper.println("NiceVertex  constructor   unknown propose");
            else
                value = values.get(proposesNames.indexOf(operation));
            return;
        }

        left = new NiceVertex(v.left, values, proposesNames);
        right = new NiceVertex(v.right, values, proposesNames);

        if ("!".equals(v.operation)) {
            text = ("(!(" + Vertex.deleteOuterBrackets(right.text) + "))");
            value = !(right.value);
        } else {
            text = "((" + Vertex.deleteOuterBrackets(left.text) + ")" + v.operation + "(" + Vertex.deleteOuterBrackets(right.text) + "))";
            if ("->".equals(operation))
                value = !(left.value) || right.value;
            else if ("|".equals(operation))
                value = right.value || left.value;
            else if ("&".equals(operation))
                value = right.value && left.value;
        }

    }

    public static NiceVertex helpfulNiceVertex(Vertex vertex, ArrayList<Boolean> vangaValues) {
        VertexMaker vertexMaker = new VertexMaker(vertex);

        return new NiceVertex(vertex, vangaValues, vertexMaker.proposes);
    }
}
