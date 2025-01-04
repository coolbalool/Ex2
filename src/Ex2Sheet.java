import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    // this function returns the value that will be presented in the program return Ex2Utils.EMPTY_CELL for invalid cases
    @Override
    public String value(int x, int y) {

        if (!isIn(x, y)) return Ex2Utils.EMPTY_CELL; // not in bound

        Cell cell = get(x, y);
        if (cell == null) return Ex2Utils.EMPTY_CELL; // cell does not exist

        int[][] depths = depth();
        if (depths[x][y] == -1) return Ex2Utils.ERR_CYCLE; // invalid depth

        if (cell.getType() == Ex2Utils.NUMBER)
            return String.valueOf(Double.parseDouble(cell.getData()));// transform to double to calculate the power (4e2 = 400) to string

        else if (cell.getType() == Ex2Utils.TEXT) return cell.getData(); // return the text

         else if (cell.getType() == Ex2Utils.FORM)
         {
            try {return String.valueOf(computeFormula(cell));} // compute the formula
                 catch (Exception e) {return Ex2Utils.ERR_FORM;} // invalid formula
        }

        return Ex2Utils.ERR_FORM; // default
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    public double computeFormula(Cell cell) {
        return 0;
    }

    // this function gets a string representing a cord (example: B14, E34) and return its corresponding cell if cord invalid null as default
    @Override
    public Cell get(String cords)
    {
        int [] cord = cordStrToInt(cords);
        if (!isIn(cord[0],cord[1]) || cord[0] == -1 || cord[1] == -1) return null; // invalid cord
        return get(cord[0],cord[1]);
    }

    // from cords "B14" to [2,14] array invalid cord result in [-1,-1]
    public int[] cordStrToInt(String cords)
    {
        int[] result = {-1,-1};
        if (cords.length()<=1) return result;
        char ch = Character.toUpperCase(cords.charAt(0));
        if (!Character.isAlphabetic(ch)) return result; // if the char is between [A-Z] else default
        result[0] =  ch - 'A';//x is the value of the char in alphabetic order
        try
        {
            String temp = cords.substring(1);
            result[1] = Integer.parseUnsignedInt(temp); // x cord is a natural number (unsigned int)
            if (temp.charAt(0) == '+')  return new int[] {-1,-1};// parseUnsignedInt returns true is the string starts with '+'
        }
        catch(Exception e) { return new int[] {-1,-1}; }

        return result;
    }

    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }

    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here

        // ///////////////////
    }

    // this function returns whether a set of cord are in the spreadsheet or not
    @Override
    public boolean isIn(int xx, int yy)
    {
        return xx >= 0 && yy >= 0 && xx < width() && yy < height();
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];

        // init array to -1
        for (int i = 0; i < ans.length; i++)
            for (int j = 0; j < ans[i].length; j++)
                ans[i][j] = -1;

        int depth = 0, count = 0, max = width() * height();
        boolean flagC = true;

        while(count <max && flagC) {
            flagC = false;
            for(int x = 0;x< width();x++) {
                for(int y = 0;y<height();y++) {
                    if(canBeCalc(x,y)) { // DIY
                        ans[x][y] = depth;
                        count+=1;
                        flagC=true; }}} // end for,for,if
            depth+=1;
        } //while
        return ans;
    }

    public boolean canBeCalc(int x,int y)
    {
        if (!isIn(x, y)) return false;
        SCell c = (SCell)get(x,y);
        String[] needed = c.getDepended();
        int [][] d = depth();
        for (int i = 0; i < needed.length; i++)
        {
             c = (SCell) get(needed[i]);
            int[] cord = cordStrToInt(needed[i]);
            if (c == null || d[cord[0]][cord[1]] != -1) return false; // if cell invalid or the cell cant be calculated
        }

        return true;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {ans = get(x,y).toString();}
        // Add your code here

        /////////////////////
        return ans;
        }
}
