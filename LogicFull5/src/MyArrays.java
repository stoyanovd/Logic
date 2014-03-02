import java.util.ArrayList;

public class MyArrays {

    public static ArrayList<String> makeUnic(ArrayList<String> a) {
        ArrayList<String> ans = new ArrayList<String>();
        for (int i = 0; i < a.size(); i++)
            if (!ans.contains(a.get(i)))
                ans.add(a.get(i));
        return ans;
    }

    public static boolean isIntersected(ArrayList<String> a, ArrayList<String> b) {
        for (int i = 0; i < a.size(); i++)
            if (b.contains(a.get(i)))
                return false;
        return true;
    }

    public static <K, V> boolean equalsNull(K k, V v) {
        if (k == null && v != null)
            return false;
        if (k != null && v == null)
            return false;
        return true;
    }
}
