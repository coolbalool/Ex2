import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i = i + 1) {
            for (int j = 0; j < y; j = j + 1) {
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

        else if (cell.getType() == Ex2Utils.FORM) {
            try {
                return String.valueOf(computeFormula(cell));
            } // compute the formula
            catch (Exception e) {
                return Ex2Utils.ERR_FORM;
            } // invalid formula
        }

        return Ex2Utils.ERR_FORM; // default
    }

    public double computeFormula(String cord)
    {

        SCell cell = (SCell) get(cord);
        String str = cell.getData();

        int minPlace = 0; // min brackets depth
        boolean flag = true;

        for (int i = 0; i < str.length(); i++)
            if(str.charAt(i) == '(' && flag) continue;
            else if(str.charAt(i) == '(') minPlace++;
            else if (str.charAt(i) == ')')
            {minPlace--; flag = false;}
            else flag = false;

            int depth = Math.abs(minPlace);
        if (depth != 0) str = str.substring(depth, str.length() - depth); // removes the brackets surrounding the form/number

        try
        {Double.parseDouble(str);
        return Double.parseDouble(str);}  // if it is a number

        catch (NumberFormatException e) // not a number
        {
            if(cell.getData().length()  <= 4 && cell.getDepended().length == 1)
                return computeFormula(cell.getDepended()[0]);

            int opIndex = indexOfMainOp(str);
            String RHS = str.substring(0, opIndex);
            String LHS = str.substring(opIndex + 1);
            char op = str.charAt(opIndex);

            return switch (op) {
                case '+' -> computeFormula(RHS) + computeFormula(LHS);
                case '-' -> computeFormula(RHS) - computeFormula(LHS);
                case '*' -> computeFormula(RHS) * computeFormula(LHS);
                case '/' -> computeFormula(RHS) / computeFormula(LHS);
                default -> 0;
            };
        }
        return -1;
    }

    @Override
    public Cell get(int x, int y) {
        if (!isIn(x, y)) return null;
        return table[x][y];
    }

    public boolean isLoop(String currCord, ArrayList<String> seen) {
        if (seen.contains(currCord)) return true;
        seen.add(currCord);
        Cell cell = get(currCord);
        if (cell == null || cell.getType() != Ex2Utils.FORM) return false;

        for (String dep : ((SCell) cell).getDepended()) {
            if (isLoop(dep, new ArrayList<>(seen))) return true;
        }
        return false;
    }

    // this function gets a string representing a cord (example: B14, E34) and return its corresponding cell if cord invalid null as default
    @Override
    public Cell get(String cords) {
        int[] cord = Ex2Utils.cordStrToInt(cords);
        if (!isIn(cord[0], cord[1]) || cord[0] == -1 || cord[1] == -1) return null; // invalid cord
        return get(cord[0], cord[1]);
    }

    private Set<String> visited = new HashSet<>();

    public int calculateOrder(int x, int y) {
        String cellCord = Ex2Utils.cordToStr(x, y);

        // Detect circular reference
        if (visited.contains(cellCord)) return -1;
        visited.add(cellCord);

        SCell cell = (SCell) get(x, y);
        if (cell == null) {
            visited.remove(cellCord);
            return -1;
        }

        if (cell.getType() == Ex2Utils.TEXT || cell.getType() == Ex2Utils.NUMBER) {
            cell.setOrder(0);
            visited.remove(cellCord);
            return 0;
        }

        if (cell.getType() == Ex2Utils.FORM) {
            int maxOrder = -1;
            for (String dep : cell.getDepended()) {
                int[] depCoords = Ex2Utils.cordStrToInt(dep);
                int depOrder = calculateOrder(depCoords[0], depCoords[1]);
                if (depOrder == -1) {
                    cell.setOrder(-1); // Circular dependency or unresolved reference
                    visited.remove(cellCord);
                    return -1;
                }
                if (get(dep).getType() == Ex2Utils.TEXT) return -1;
                maxOrder = Math.max(maxOrder, depOrder);
            }
            cell.setOrder(maxOrder + 1);
            visited.remove(cellCord);
            return maxOrder + 1;
        }

        visited.remove(cellCord);
        return -1; // Default case
    }
    public int[][] depth() {
        visited.clear(); // Reset visited set
        int[][] ans = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (isLoop(Ex2Utils.cordToStr(i,j),new ArrayList<>())) ans [i][j] = -1;
                else ans[i][j] = calculateOrder(i, j);
            }
        }
        return ans;
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
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && yy >= 0 && xx < width() && yy < height();
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