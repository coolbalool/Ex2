// Add your documentation below:

import java.util.ArrayList;

public class SCell implements Cell {
    private String line;
    private int type;
    // Add your code here

    public SCell(String s) {
        // Add your code here
        // determin the type
        setData(s);
        if (isNumber()) type = Ex2Utils.NUMBER;
        else if (s.isEmpty() || getData().charAt(0) != '=') type = Ex2Utils.TEXT;
    }

    // the function returns true if the cell is representing a number
    public boolean isNumber()
    {
        try { Double.parseDouble(getData()); } // if the string cant convert to double it is not a number
        catch (Exception e) { return false; }
        return true;
    }

    public String[] getDepended()
        {

            if (getType() != Ex2Utils.FORM) return new String[0];
            ArrayList<String> arr = new ArrayList<String>(); // dynamic array because we dont know how much cell it depends on

            StringBuilder curr = new StringBuilder();
            boolean flag = false;
            for (int i = 1; i < getData().length(); i++)// start from 1 to remove '='
            {
                char ch = getData().charAt(i);
                if (Character.isAlphabetic(ch))
                {
                    if (i > 1 && !Character.isDigit(getData().charAt(i - 1))) continue; // digit before character is scientific notation  (2e2 = 200)
                    flag = true;
                    curr = new StringBuilder(String.valueOf(ch));
                }
                else if (Character.isDigit(ch) && flag) curr.append(ch); // the digit

                else if (flag)
                {
                    flag = false; // reset flag
                    arr.add(curr.toString()); // add str to the result array
                }
            }
            return arr.toArray(new String[arr.size()]);
        }


    @Override
    public int getOrder() {
        // Add your code here

        return 0;
        // ///////////////////
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s)
    {
        // Add your code here
        line = s;
        // calc type
    }
    @Override
    public String getData() {
        return line;
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
        // Add your code here

    }
}
