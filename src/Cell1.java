    public class Cell1 {
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

        public static boolean isForm(String text)
        {

            if (text.charAt(0) != '=') return false;

            String validNum = "0123456789-."; // all the valid num characters
            String validOp = "*+/-"; // all the valid operation character
            String validChars = validNum + validOp + "()";

            int parCount = 0; // for checking if the ( ) are valid
            boolean dotSeen = false;
            boolean lastWasOp = false;
            boolean lastWasNum = false;

            for (int i = 1; i < text.length(); i++)
            {
                char ch = text.charAt(i);
                if (validChars.indexOf(ch) == -1) return false; // checks if all the chars are valid

                // parenthesis check
                if (ch == '(')
                {
                    if (lastWasNum) return false;
                    parCount++;
                    dotSeen = false;
                    lastWasNum = false;
                    lastWasOp = false;
                }
                else if (ch == ')')
                {
                    parCount--;
                    dotSeen = false;
                    if (parCount < 0 || lastWasOp) return false;
                    lastWasNum = false;
                    lastWasOp = false;
                }

                else if (validOp.indexOf(ch) != -1)
                {
                    if ((i == 1 && ch != '-') || lastWasOp) return false;
                    dotSeen = false;
                    lastWasOp = true;
                    lastWasNum = false;

                }

                else if (ch == '.')
                {
                    if (dotSeen || !lastWasNum) return false;
                    dotSeen = true;
                }

                else
                {
                    if ( i > 1 && text.charAt(i -1) == ')') return false;
                    lastWasOp = false;
                    lastWasNum = true;
                }
            }

            return parCount == 0 && !lastWasOp;

        }



    }







