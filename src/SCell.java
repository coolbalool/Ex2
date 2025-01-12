// Add your documentation below:

import java.util.ArrayList;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;

    // Add your code here

    // constructor set the type
    public SCell(String s)
    {
        setData(s);
    }

    // the function returns true if the cell is representing a number
    public boolean isNumber()
    {
        try {
            Double.parseDouble(getData()); // if the string cant convert to double it is not a number
        }
        catch (Exception e) { return false; }
        return true;
    }

    // returns an array of the cells that the cell is depended on
    public String[] getDepended() {
        // Return empty array if not a formula or invalid input
        if (getType() != Ex2Utils.FORM || getData() == null || getData().length() < 2) {
            return new String[0];
        }

        ArrayList<String> dependencies = new ArrayList<>();
        StringBuilder currentRef = new StringBuilder();

        // Start from index 1 to skip the '=' sign
        for (int i = 1; i < getData().length(); i++) {
            char ch = getData().charAt(i);

            // Handle scientific notation (e.g., 1.5E-2)
            if ((ch == 'e' || ch == 'E') && i > 1 && Character.isDigit(getData().charAt(i - 1))) {
                continue;
            }

            // Start of cell reference (must be uppercase letter)
            if (Character.isUpperCase(ch)) {
                currentRef.setLength(0);  // Reset StringBuilder
                currentRef.append(ch);

                // Look ahead to collect the full reference (letter + numbers)
                i++;
                while (i < getData().length() && Character.isDigit(getData().charAt(i))) {
                    currentRef.append(getData().charAt(i));
                    i++;
                }
                i--; // Adjust index since for loop will increment

                // Validate and add the reference
                String ref = currentRef.toString();
                int[] coords = Ex2Utils.cordStrToInt(ref);
                if (coords[0] != -1 && coords[1] != -1) {  // Valid coordinates
                    dependencies.add(ref);
                }
            }
        }

        return dependencies.toArray(new String[0]);
    }


   @Override
   public int getOrder()
   {return order;}

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s)
    {
        if (s == null) line = "";
         else line = s;

        if (isNumber())
            type = Ex2Utils.NUMBER;
        else if (line.isEmpty() || line.charAt(0) != '=') type = Ex2Utils.TEXT;
        else if (isForm()) type = Ex2Utils.FORM;
        else type = Ex2Utils.ERR_FORM_FORMAT;
    }

    @Override
    public String getData() {
        if (getType() == Ex2Utils.TEXT)
        return line;
        else return line.replaceAll("\\s", "");
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t)
    {
        this.order = t;
    }

    public boolean isForm()
    {

        if (getData().length() < 2 || getData().charAt(0) != '=' ) return false;

        String validNum = "0123456789-."; // all the valid num characters
        String validOp = "*+/-"; // all the valid operation character
        String validChars = validNum + validOp + "()"; // all the valid characters

        int parCount = 0; // for checking if the ( ) are valid (the amount of open parenthesis)
        boolean dotSeen = false; // for ensuring no double dots in a number
        boolean lastWasOp = false;
        boolean lastWasNum = false;

        for (int i = 1; i < getData().length(); i++)
        {
            char ch = getData().charAt(i);
            if (validChars.indexOf(ch) == -1 && !Character.isAlphabetic(ch)) return false; // checks if all the chars are valid

            if (Character.isAlphabetic(ch)) {
                if (ch == 'e' || ch == 'E') // for scientific notation
                {
                    if (i > 1 && Character.isDigit(getData().charAt(i - 1))) {
                       lastWasNum = true;
                        continue;
                    }
                }
                if (lastWasNum)
                {
                    setType(Ex2Utils.ERR_FORM_FORMAT);
                    return false; // A number cannot directly precede a letter
                }
                int j = i;
                while (j < getData().length() && Character.isAlphabetic(getData().charAt(j))) j++; // Skip letters
                while (j < getData().length() && Character.isDigit(getData().charAt(j))) j++; // Skip digits
                i = j - 1; // Update loop index to skip the full cell reference
                lastWasOp = false;
                lastWasNum = true;
            }

            else if (ch == '(')
            {
                if (lastWasNum)
                {
                    setType(Ex2Utils.ERR_FORM_FORMAT);
                    return false;  // cant be a number before '('
                }
                parCount++;  // for validation of the parenthesis
                dotSeen = false; // resets the dot flag
                lastWasOp = false; // '(' isn't an operator
            }
            else if (ch == ')')
            {
                parCount--; // reduces the count of the open parenthesis
                dotSeen = false;
                if (parCount < 0 || lastWasOp)
                {
                    setType(Ex2Utils.ERR_FORM_FORMAT);
                    return false; // if there are more ')' than '(' it isn't a form or operator before ')' is invalid
                }
                lastWasNum = false; // ')' isn't an num
            }

            else if (validOp.indexOf(ch) != -1) // ch is an operator
            {



                if ((i == 1 && ch != '-') || lastWasOp)
                {
                    setType(Ex2Utils.ERR_FORM_FORMAT);
                    return false; // if last char is op or is it the first number (except '-')
                }
                dotSeen = false; // resets dot flag
                lastWasOp = true;
                lastWasNum = false; // resets num flag

            }

            else if (ch == '.')
            {
                if (dotSeen || !lastWasNum)
                {
                    setType(Ex2Utils.ERR_FORM_FORMAT);
                    return false;  // if there wasn't a num before or there have been more than one dot in the number return false
                }
                dotSeen = true;
            }

            else // all number [0,9]
            {
                if ( i > 1 && getData().charAt(i -1) == ')')
                {
                    setType(Ex2Utils.ERR_FORM_FORMAT);
                    return false;   // if last char is ) then a num after is invalid
                }
                lastWasOp = false;
                lastWasNum = true;
            }
        }
        return parCount == 0 && !lastWasOp; // if the string does not end with an op and the parenthesis are valid

    }

}
