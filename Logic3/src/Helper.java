public class Helper {

    public static void println(String s) {
        if (Configuration.DEBUG_MODE)
            System.out.println(s);
    }

    public static void parsePrintln(String s) {
        if (Configuration.DEBUG_MODE_PARSE)
            System.out.println(s);
    }

    public static void checkPrintln(String s) {
        if (Configuration.DEBUG_MODE_CHECK)
            System.out.println(s);
    }

    public static void deductPrintln(String s) {
        if (Configuration.DEBUG_MODE_DEDUCT)
            System.out.println(s);
    }


    public static void falseProposesPrintln(String s) {
        if (Configuration.DEBUG_MODE_FALSE_PROPOSES)
            System.out.println(s);
    }
}
