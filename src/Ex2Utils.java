/**
 * This class contains a set of constants for Ex2 (I2CS, ArielU 2025A),
 * As defined in: https://docs.google.com/document/d/1-18T-dj00apE4k1qmpXGOaqttxLn-Kwi/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * Do NOT change this class!
 *
 */
public class Ex2Utils {
    public static final int TEXT=1, NUMBER=2, FORM=3, ERR_FORM_FORMAT=-2, ERR_CYCLE_FORM=-1, ERR=-1;
    public static final String ERR_CYCLE = "ERR_CYCLE!", ERR_FORM = "ERR_FORM!";
    public static final int WIDTH = 9, HEIGHT=17, MAX_CHARS=8, WINDOW_WIDTH=1200, WINDOW_HEIGHT=600;
    public static final int WAIT_TIME_MS = 10, MAX_X=20;
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2, PEN_RADIUS = 0.001;
    public static final double GUI_X_SPACE = 2, GUI_X_START = 3, GUI_Y_TEXT_START = 0.4;
    public static final boolean Debug = false;
    public static final String[] M_OPS = {"+", "-", "*", "/"};
    public static final String EMPTY_CELL = "";
    public static  final String[] ABC= {"A","B","C","D","E","F","G","H","I","J","K","L","O","M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};

    // from cords "B14" to [2,14] array invalid cord result in [-1,-1]
    public static int[] cordStrToInt(String cords) {
        int[] result = {-1, -1};
        if (cords.length() <= 1) return result;
        char ch = Character.toUpperCase(cords.charAt(0));
        if (!Character.isAlphabetic(ch)) return result; // if the char is between [A-Z] else default
        result[0] = ch - 'A';//x is the value of the char in alphabetic order
        try {
            String temp = cords.substring(1);
            result[1] = Integer.parseUnsignedInt(temp); // x cord is a natural number (unsigned int)
            if (temp.charAt(0) == '+')
                return new int[]{-1, -1};// parseUnsignedInt returns true is the string starts with '+'
        } catch (Exception e) {
            return new int[]{-1, -1};
        }

        return result;
    }

    public static String cordToStr(int row, int col) {
        if (row < 0 || col < 0 || col >= 26) return null; // Invalid input

        // Convert column index to a single character (e.g., 0 -> 'A', 1 -> 'B', ..., 25 -> 'Z')
        char columnChar = (char) ('A' + col);

        // Convert row index to 1-based row number
        int rowNumber = row + 1;

        // Combine column character and row number
        return columnChar + String.valueOf(rowNumber);
    }


    // ****** not implemented in Ex2!! ******
    // public static final String[] FUNCTIONS = {"if", "sin", "cos", "pow", "min", "max"};
    // public static String[] B_OPS = {"<", ">", "==","!=", "<=", ">="};
}
