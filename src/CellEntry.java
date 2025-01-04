public class CellEntry implements Index2D
{

    private String str; // the string  - "B3" for example

    public CellEntry(String cellIndex)
    {
        this.str = cellIndex.toUpperCase(); // all uppercase
    }

    @Override
    public boolean isValid() {

        if (str == null || str.length() < 2  || str.length() > 3) return false; // length = 2,3 and str has a value

        char column = str.charAt(0);
        String rowPart = str.substring(1);

        if (!Character.isLetter(column)) return false; // col [A,Z]

        try {
            int row = Integer.parseInt(rowPart);
            return row >= 0 && row < 100; // row must be between [0,99]
        }
        catch (NumberFormatException e) {
            return false; // isn't a number
        }
    }

    @Override
    public int getX() {

        if (!isValid()) return Ex2Utils.ERR; // error code if invalid

        char column = str.charAt(0);
        return column - 'A'; // [A,Z] to [0,25]
    }

    @Override
    public int getY() {

        if (!isValid()) return Ex2Utils.ERR; // error code if invalid

        String rowPart = str.substring(1);
        return Integer.parseInt(rowPart); // string to int (we know it is valid)
    }

    @Override
    public String toString() {return str;}
}
