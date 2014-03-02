import java.util.ArrayList;

public class Brackets {

    int getClose[];
    int getOpen[];

    int close(int i) {
        return getClose[i];
    }

    int open(int i) {
        return getOpen[i];
    }

    Brackets(String s) {
        getClose = new int[s.length()];
        getOpen = new int[s.length()];

        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                k++;
            else if (s.charAt(i) == ')')
                k--;
            if (k < 0) {
                System.out.println("Wrong Braces:");
                System.out.println(s);
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        if (k != 0) {
            System.out.println("Wrong Braces:");
            System.out.println(s);
            throw new ArrayIndexOutOfBoundsException();
        }

        ArrayList<Integer> openBraces = new ArrayList<Integer>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                openBraces.add(i);
            } else if (s.charAt(i) == ')') {
                getOpen[i] = openBraces.get(openBraces.size() - 1);
                getClose[openBraces.get(openBraces.size() - 1)] = i;
                openBraces.remove(openBraces.size() - 1);
            }
        }
    }
}
