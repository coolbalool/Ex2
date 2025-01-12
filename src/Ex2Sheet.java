import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
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
        if (!isIn(x, y)) return Ex2Utils.EMPTY_CELL; // Not in bounds

        Cell cell = get(x, y);
        if (cell == null) return Ex2Utils.EMPTY_CELL; // Cell does not exist

        int[][] depths = depth(); // Use depth to detect cycles
        if (depths[x][y] == -1) return Ex2Utils.ERR_CYCLE; // Circular dependency detected

        if (cell.getType() == Ex2Utils.NUMBER) {
            return String.valueOf(Double.parseDouble(cell.getData())); // Return numeric value
        } else if (cell.getType() == Ex2Utils.TEXT) {
            return cell.getData(); // Return text value
        } else if (cell.getType() == Ex2Utils.FORM) {
            try {
                return String.valueOf(computeFormula(cell.getData())); // Compute the formula
            } catch (Exception e) {
                return Ex2Utils.ERR_FORM; // Return formula error
            }
        }

        return Ex2Utils.ERR_FORM; // Default case for invalid types
    }

    public String computeFormula(String formula) {
        String str = formula;

        // Remove '=' if it's a formula
        if (str.startsWith("=")) {
            str = str.substring(1);
        }

        // Handle empty or malformed strings
        if (str.isEmpty()) throw new IllegalArgumentException(Ex2Utils.ERR_FORM);

        // Remove outer parentheses if valid
        while (str.length() > 1 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')'
                && Ex2Utils.checkPar(str.substring(1, str.length() - 1))) {
            str = str.substring(1, str.length() - 1);
        }

        // First, check if this is a cell reference
        if (str.matches("[A-Z]+[0-9]+")) {
            Cell referencedCell = get(str);
            if (referencedCell == null) {
                throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
            }
            // Get the value from the referenced cell
            String cellValue = value(Ex2Utils.cordStrToInt(str)[0], Ex2Utils.cordStrToInt(str)[1]);
            if (cellValue.equals(Ex2Utils.ERR_CYCLE) || cellValue.equals(Ex2Utils.ERR_FORM)) {
                throw new IllegalArgumentException(cellValue);
            }
            return cellValue;
        }

        // Try to parse as a number
        try {
            return String.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            // Handle formulas
            int opIndex = indexOfMainOp(str);
            if (opIndex == -1) throw new IllegalArgumentException(Ex2Utils.ERR_FORM);

            String lhs = str.substring(0, opIndex).trim();
            String rhs = str.substring(opIndex + 1).trim();
            char op = str.charAt(opIndex);

            try {
                // Evaluate the formula
                double left = Double.parseDouble(computeFormula(lhs));
                double right = Double.parseDouble(computeFormula(rhs));

                return switch (op) {
                    case '+' -> String.valueOf(left + right);
                    case '-' -> String.valueOf(left - right);
                    case '*' -> String.valueOf(left * right);
                    case '/' -> String.valueOf(left / right);
                    default -> throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
                };
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
            }
        }
    }

    public int indexOfMainOp(String str) {
        int index = -1;
        int minPar = Integer.MAX_VALUE;
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);


            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            }
            else if ("+-*/".indexOf(c) != -1) { // Check if the character is an operator
                // Prioritize operators based on precedence and parentheses depth
                if ((i == 0) && c == '-')continue;
                if (count < minPar || (count == minPar && "+-".indexOf(c) != -1)) {
                    minPar = count;
                    index = i;
                }
            }
        }

        return index;
    }

    @Override
    public Cell get(int x, int y) {
        if (!isIn(x, y)) return null;
        return table[x][y];
    }

    public boolean isLoop(String currCord, ArrayList<String> seen) {
        // If we've seen this coordinate before, we have a loop
        if (seen.contains(currCord)) {
            return true;
        }

        // Get the cell at current coordinates
        Cell cell = get(currCord);
        if (!(cell instanceof SCell)) {
            return false;
        }

        SCell scell = (SCell) cell;
        if (scell.getType() != Ex2Utils.FORM) {
            return false;
        }

        // Add current coordinate to seen list
        seen.add(currCord);

        // Get dependencies and check each one
        String[] dependencies = scell.getDepended();
        for (String dependency : dependencies) {
            // Convert to proper coordinate format if needed
            int[] coords = Ex2Utils.cordStrToInt(dependency);
            if (coords[0] != -1 && coords[1] != -1) {
                // Important: note that cordToStr takes (row, col) while cordStrToInt returns [col, row]
                String formattedDep = Ex2Utils.cordToStr(coords[1] - 1, coords[0]);
                if (formattedDep != null && isLoop(formattedDep, seen)) {
                    return true;
                }
            }
        }

        // Remove current coordinate from seen list (backtracking)
        seen.remove(currCord);
        return false;
    }

    // this function gets a string representing a cord (example: B14, E34) and return its corresponding cell if cord invalid null as default
    @Override
    public Cell get(String cords) {
        int[] coords = Ex2Utils.cordStrToInt(cords);
        if (coords[0] == -1 || coords[1] == -1) {
            return null;
        }
        // Note: coords[0] is column, coords[1] is row
        return get(coords[0], coords[1]);
    }

    public int calculateOrder(int x, int y, Set<String> visited) {
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
                int depOrder = calculateOrder(depCoords[0], depCoords[1], new HashSet<>(visited));
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
        int[][] ans = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (isLoop(Ex2Utils.cordToStr(i, j), new ArrayList<>())) {
                    ans[i][j] = -1;
                } else {
                    ans[i][j] = calculateOrder(i, j, new HashSet<>());
                }
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

        System.out.println("Set cell at (" + x + ", " + y + ") to: " + s);
    }

    @Override
    public void eval() {
        int[][] dd = depth(); // Get dependency depth for all cells

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                SCell cell = (SCell) get(x, y);
                if (cell == null) continue; // Skip empty cells

                // Use the depth matrix to check for cycles
                if (dd[x][y] == -1) {
                    cell.setType(Ex2Utils.ERR_CYCLE_FORM); // Mark the cell as part of a cycle
                }
            }
        }
    }

    @Override
    public String eval(int x, int y) {
        SCell cell = (SCell) get(x, y);
        // If the cell does not exist, return the empty cell representation
        if (cell == null) {
            return Ex2Utils.EMPTY_CELL;
        }
        // Check for circular dependencies
        if (isLoop(Ex2Utils.cordToStr(x, y), new ArrayList<>())) {
            cell.setType(Ex2Utils.ERR_CYCLE_FORM); // Mark the cell as having a cycle
            return Ex2Utils.ERR_CYCLE;
        }
        // Return the computed value of the cell
        return value(x, y);
    }

    // this function returns whether a set of cord are in the spreadsheet or not
    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && yy >= 0 && xx < width() && yy < height();
    }

    @Override
    public void load(String fileName) throws IOException {
        // Create a new empty table
        table = new SCell[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                table[i][j] = new SCell("");
            }
        }

        Scanner sc = new Scanner(new File(fileName));
        sc.nextLine(); // Skip the header line

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(",", 3); // Split into max 3 parts: x, y, and content

            // Check if line has at least 3 parts (x, y, content)
            if (parts.length >= 3) {
                try {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());

                    // Check if coordinates are valid
                    if (isIn(x, y)) {
                        set(x, y, parts[2].trim());
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid lines
                    continue;
                }
            }
        }
        sc.close();
        eval(); // Evaluate the loaded sheet
    }

    @Override
    public void save(String fileName) throws IOException
    {

        try (FileWriter writer = new FileWriter(fileName))
        {
            String firstLine = "* I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method";
            writer.write(firstLine+"\n");

            for (int i = 0; i < width(); i++)
                for (int j = 0; j < height(); j++)
                    if (!get(i, j).getData().isEmpty())
                        writer.write(i + "," + j + "," + get(i, j).getData()+"\n");
        }
        // Exception Thrown
        catch (IOException e) {
            System.out.println("An error occurred while writing" + " to the file: " + e.getMessage());}

    }


}