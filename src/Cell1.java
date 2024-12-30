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
            String validChars = validNum + validOp + "()"; // all the valid characters

            int parCount = 0; // for checking if the ( ) are valid (the amount of open parenthesis)
            boolean dotSeen = false; // for ensuring no double dots in a number
            boolean lastWasOp = false;
            boolean lastWasNum = false;

            for (int i = 1; i < text.length(); i++)
            {
                char ch = text.charAt(i);
                if (validChars.indexOf(ch) == -1) return false; // checks if all the chars are valid

                if (ch == '(')
                {
                    if (lastWasNum) return false; // cant be a number before '('
                    parCount++;  // for validation of the parenthesis
                    dotSeen = false; // resets the dot flag
                    lastWasNum = false; // '(' isn't a num
                    lastWasOp = false; // '(' isn't an operator
                }
                else if (ch == ')')
                {
                    parCount--; // reduces the count of the open parenthesis
                    dotSeen = false;
                    if (parCount < 0 || lastWasOp) return false; // if there are more ')' than '(' it isn't a form or operator before ')' is invalid
                    lastWasNum = false; // ')' isn't an num
                    lastWasOp = false;// ')' isn't an operator
                }

                else if (validOp.indexOf(ch) != -1) // ch is an operator
                {
                    if ((i == 1 && ch != '-') || lastWasOp) return false; // if last char is op or is it the first number (except '-')
                    dotSeen = false; // resets dot flag
                    lastWasOp = true;
                    lastWasNum = false; // resets num flag

                }

                else if (ch == '.')
                {
                    if (dotSeen || !lastWasNum) return false; // if there wasn't a num before or there have been more than one dot in the number return false
                    dotSeen = true;
                }

                else // all number [0,9]
                {
                    if ( i > 1 && text.charAt(i -1) == ')') return false; // if last char is ) then a num after is invalid
                    lastWasOp = false;
                    lastWasNum = true;
                }
            }

            return parCount == 0 && !lastWasOp; // if the string does not end with an op and the parenthesis are valid

        }



    }







