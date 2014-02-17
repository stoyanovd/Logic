public class MyHash {

    public static final long[] PRIME_POWERS = new long[1000];
    public static final long HASH_PRIME = 31;

    static {
        PRIME_POWERS[0] = 1;
        for (int i = 1; i < 300; i++)
            PRIME_POWERS[i] = PRIME_POWERS[i - 1] * HASH_PRIME;
    }

    static long hash(Vertex left, Vertex right, String operation) {

        if (left == null && right == null && operation == null)
            return 0;
        if (left == null && right == null)
            return hashString(operation);

        long leftHash = 0;
        if (left != null) {
            leftHash = left.hash;
        }
        long rightHash = 0;
        int rightSize = 0;
        if (right != null) {
            rightHash = right.hash;
            rightSize = right.size;
        }
        return (rightHash + hashString(operation) * PRIME_POWERS[rightSize] + leftHash * PRIME_POWERS[rightSize + 1]);
    }

    static long hash(Vertex v) {
        if (v == null)
            return 0;
        if (v.terms == null || v.terms.size() == 0)
            return hash(v.left, v.right, v.operation);
        long h = hashString(v.operation);
        for (int i = 0; i < v.terms.size(); i++)
            h += PRIME_POWERS[i + 1] * v.terms.get(i).hash;
        return h;
    }

    static long hashString(String raw) {
        long h = 0;
        for (int i = 0; i < raw.length(); i++)
            h = h * HASH_PRIME + raw.charAt(i);
        return h;
    }

    static long hashVertex(String raw) {
        return hash(new Vertex(raw));
    }

}
