# SpreadSheet-Ex2

## Description
This is an implementation of a cell based sheet spread. This project is like the commonly used Exel and has most of its core features. 
![image](https://github.com/user-attachments/assets/90597016-400a-4eb9-853b-7995b186fb48)
## Key Features
1. **Multiple Cell Types Support**
    - Number Support(e.g., "1", "-1.1" , "3e2" (Scientific notation))
    - Text (e.g., "2a", "hello")
    - Formulas (e.g "=1", "=1+2*2", "=(1+2)*3",  "=A1+B2" )

2. **Formula Capabilities**
    - Basic operations (+, -, *, /)
    - Scientific notation (implemented by the number too)
    - Cell references (e.g., A1, B2)
    - Error detection and two types of errors(cycle and form error)

3. **File Operations**
    - Save spreadsheet data to file
    - Load spreadsheet data from file

## Main Classes and Methods

### Ex2Sheet Class

- **Ex2Sheet(int x, int y)**
    - Constructor with the width and height as a parameter
    - Example: `Ex2Sheet sheet = new Ex2Sheet(10, 10);`


- **Ex2Sheet()**
    - Default constructor with default size
    - Example: `Ex2Sheet sheet = new Ex2Sheet();`


- **String value(int x, int y)**
    - Returns the final value; returns `Ex2Utils.EMPTY_CELL` for invalid cases.
    - Example: `String val = sheet.value(0, 0);`


- **String computeFormula(String formula)**
    - Computes the result of a formula.
    - Example: `String result = sheet.computeFormula("=A1+5");`


- **int indexOfMainOp(String str)**
    - Return the index of the main operator (the operator that splits the equation)
    - Example: `int index = sheet.indexOfMainOp("1+2*3");`


- **Cell get(int x, int y)**
    - Returns the cell in (x,y) returns `null` if coordinates are invalid.
    - Example: `Cell cell = sheet.get(0, 0);`


- **boolean isLoop(String currCord, ArrayList<String> seen)**
    - returns if there is a loop (when calling call with new ArrayList)
    - Example: `boolean hasLoop = sheet.isLoop("A1", new ArrayList<>());`


- **Cell get(String cords)**
    - Return the cell in the cord parameter returns `null` if coordinates are invalid.
    - Example: `Cell cell = sheet.get("A1");`


- **int calculateOrder(int x, int y, Set<String> visited)**
    - Calculates the order of the cell 
    - Example: `int order = sheet.calculateOrder(0, 0, new HashSet<>());`


- **int[][] depth()**
    - Returns a 2D array representing the computational depth of each cell.
    - Example: `int[][] depths = sheet.depth();`


- **int width()**
    - Returns the width of the spreadsheet.
    - Example: `int w = sheet.width();`


- **int height()**
    - Returns the height of the spreadsheet.
    - Example: `int h = sheet.height();`


- **void set(int x, int y, String s)**
    - Sets the (x,y) cord with the string s
    - Example: `sheet.set(0, 0, "5");`


- **void eval()**
    - evaluates all the cells in the spreadsheet
    - Example: `sheet.eval();`


- **String eval(int x, int y)**
    - Evaluates a single cell with the cords (x,y)
    - Example: `String val = sheet.eval(0, 0);`


- **boolean isIn(int xx, int yy)**
    - Returns if a cord (xx,yy) is inside the table
    - Example: `boolean inBounds = sheet.isIn(0, 0);`


- **void load(String fileName) throws IOException**
    - Loads the file to the spreadsheet
    - Example: `sheet.load("mysheet.txt");`


- **void save(String fileName) throws IOException**
    - Save the current spreadsheet
    - Example: `sheet.save("mysheet.txt");`

### SCell Class

- **SCell(String s)**
    - constructor to init the data to s
    - Example: `SCell cell = new SCell("5");`

- **boolean isNumber()**
    - Returns `true` if the cell is a number
    - Example: `boolean isNum = cell.isNumber();`

- **String[] getDepended()**
    - returns an array of the cells cord that the cell is depended on
    - Example: `String[] deps = cell.getDepended();`

- **int getOrder()**
    - returns the order of the cell
    - Example: `int order = cell.getOrder();`

- **String toString()**
    - Returns the string representation of the cell.
    - Example: `String str = cell.toString();`

- **void setData(String s)**
    - Set the data to s
    - Example: `cell.setData("=A1+5");`

- **String getData()**
    - Returns the data of the cell.
    - Example: `String data = cell.getData();`

- **int getType()**
    - Returns the type of the cell (number, text, formula).
    - Example: `int type = cell.getType();`

- **void setType(int t)**
    - Sets the type of the cell.
    - Example: `cell.setType(Ex2Utils.FORM);`

- **void setOrder(int t)**
    - Sets the order of the cell.
    - Example: `cell.setOrder(1);`

- **boolean isForm()**
    - Returns `true` if the cell is a valid formula.
    - Example: `boolean validForm = cell.isForm();`

### CellEntry Class

- **CellEntry(String cellIndex)**
    - Constructor to initialize a cell entry with the given string index.
    - Example: `CellEntry entry = new CellEntry("A1");`

- **boolean isValid()**
    - Returns `true` if the cell index is valid.
    - Example: `boolean valid = entry.isValid();`

- **int getX()**
    - Returns the X coordinate of the cell index.
    - Example: `int x = entry.getX();`

- **int getY()**
    - Returns the Y coordinate of the cell index.
    - Example: `int y = entry.getY();`

- **String toString()**
    - Returns the string representation of the cell index.
    - Example: `String str = entry.toString();`
