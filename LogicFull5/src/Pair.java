public class Pair<K, V> {

    K first;
    V second;

    Pair() {
    }

    Pair(K k, V v) {
        first = k;
        second = v;
    }

    public boolean equals(Pair p) {
        return (p.first.equals(first) && p.second.equals(second));
    }
}
