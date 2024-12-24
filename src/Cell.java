    public class Cell {
        int row, coll;
        String info;

        public void setInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        public static boolean isNumber(String text) {
            int ch = text.charAt(0);
            int dotCount = 0; // how many dots are in this string
            if (ch != '-' && (ch < '0' || ch > '9') && ch != '+') // the first char is not a digit or '-' or '+'
                return false;
            for (int i = 1; i < text.length(); i++) {
                ch = text.charAt(i);
                if (ch == '.') dotCount++;
                else if (ch < '0' || ch > '9') return false;
            }
            return (dotCount < 2); // true if count is 0 or 1 else false
        }

        public static boolean isText(String text) {
            return (!isNumber(text) && (text.indexOf('=') == -1)); // if isn't a number and doesn't have '='
        }

        public static

        public static boolean isForm(String text) {

            if (text.charAt(0) != '=') return false;

            String temp = new String(text);
            temp = text.substring(1);

            String validNum = "0123456789-."; // all the valid num characters
            String validOp = "*+/-"; // all the valid operation character
            String validChars = validNum + validOp + "()";

            for (int i = 0; i < temp.length(); i++)
            {

            }

            return true;

        }
    }






    }
