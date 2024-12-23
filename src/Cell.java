public class Cell
{
    int row,coll;
    String info;

    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

    public static boolean isNumber(String text)
    {
        int ch= text.charAt(0);
        int dotCount = 0; // how many dots are in this string
        if (ch != '-' &&  (ch <'0' || ch > '9') && ch != '+') // the first char is not a digit or '-' or '+'
            return false;
        for (int i = 1; i < text.length(); i++)
        {
            ch = text.charAt(i);
            if (ch == '.') dotCount++;
           else if (ch < '0' || ch > '9') return false;
        }
        return (dotCount < 2); // true if count is 0 or 1 else false
    }



}
