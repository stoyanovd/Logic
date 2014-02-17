import java.util.ArrayList;

public class VertexMaker {
    ArrayList<String> proposes;

    VertexMaker() {
        proposes = new ArrayList<String>();
    }

    void addProposes(Vertex v) {
        if (v == null)
            return;
        if (v.terms != null) {
            for (int i = 0; i < v.terms.size(); i++)
                addProposes(v.terms.get(i));
            return;
        }
        if (v.left == null && v.right == null) {
            if (v.operation != null && !"".equals(v.operation)) {
                if (!proposes.contains(v.operation))
                    proposes.add(v.operation);
            }
        } else {
            addProposes(v.left);
            addProposes(v.right);
        }
    }

    VertexMaker(Vertex v) {
        this();
        addProposes(v);
    }

}
