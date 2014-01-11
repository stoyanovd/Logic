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
            return hash(operation);

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
        return (rightHash + hash(operation) * PRIME_POWERS[rightSize] + leftHash * PRIME_POWERS[rightSize + 1]);
    }

    static long hash(String raw) {
        long h = 0;
        for (int i = 0; i < raw.length(); i++)
            h = h * HASH_PRIME + raw.charAt(i);
        return h;
    }


}
