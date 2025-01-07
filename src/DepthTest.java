import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class DepthTest {

    private void assertArrayEquals(String message, int[][] expected, int[][] actual) {
        if (!Arrays.deepEquals(expected, actual)) {
            throw new AssertionError(message + "\nExpected: " + Arrays.deepToString(expected) + "\nActual: " + Arrays.deepToString(actual));
        }
    }

    @Test
    public void testDepth(){
        Ex2Sheet cells = new Ex2Sheet(3, 3);
        Ex2GUI.setTable(cells);
        cells.set(0, 0, "10");        // A0 (0,0) is a constant
        cells.set(1, 0, "=A0*2");     // B0 (1,0) depends on A0
        cells.set(1, 1, "=B0+6");// B1 (1,1) is reassigned to depend on B0
        cells.set(2,2,"=C2+2");

        int[][] result = cells.depth();

        assertEquals(0, result[0][0]); // A0 has depth 0 (no dependencies)
        assertEquals(1, result[1][0]); // B0 has depth 1 (depends on A0)
        assertEquals(2, result[1][1]);// B1 has depth 2 (depends on B0 -> A0)
        assertEquals(-1, result[2][2]);
}

@Test
public void testComplexChainOfDependencies() {
    Ex2Sheet cells = new Ex2Sheet(3, 3);
    Ex2GUI.setTable(cells);

    cells.set(0, 0, "5");        // A0 (row 0, col 0) is a constant
    cells.set(1, 0, "=A0+1");    // B0 (row 1, col 0) depends on A0
    cells.set(2, 0, "=B0*2");    // C0 (row 2, col 0) depends on B0
    cells.set(2, 1, "=C0-3");    // D0 (row 2, col 1) depends on C0

    int[][] result = cells.depth();

    assertEquals(0, result[0][0]); // A0 has depth 0
    assertEquals(1, result[1][0]); // B0 has depth 1
    assertEquals(2, result[2][0]); // C0 has depth 2
    assertEquals(3, result[2][1]); // D0 has depth 3
}

@Test
public void testCircularDependency() {
    Ex2Sheet cells = new Ex2Sheet(3, 3);
    Ex2GUI.setTable(cells);

    cells.set(0, 0, "=B0+1");    // A0 depends on B0
    cells.set(0, 1, "=A0+1");    // B0 depends on A0

    int[][] result = cells.depth();

    assertEquals(-1, result[0][0]); // A0 is part of a circular dependency
    assertEquals(-1, result[0][1]); // B0 is part of a circular dependency
}

@Test
public void testDependencyOnEmptyCell() {
    Ex2Sheet cells = new Ex2Sheet(3, 3);
    Ex2GUI.setTable(cells);

    cells.set(0, 0, "=B0+1");    // A0 depends on B0 (empty)

    int[][] result = cells.depth();

    assertEquals(-1, result[0][0]); // A0 has invalid depth due to dependency on an empty cell
    assertEquals(0, result[0][1]); // B0 is empty, so depth is 0
}

@Test
public void testDeepNestedDependencies() {
    Ex2Sheet cells = new Ex2Sheet(5, 5);
    Ex2GUI.setTable(cells);

    cells.set(0, 0, "1");        // A0 is a constant
    cells.set(1, 0, "=A0+1");    // B0 depends on A0
    cells.set(2, 0, "=B0+2");    // C0 depends on B0
    cells.set(3, 0, "=C0+3");    // D0 depends on C0
    cells.set(4, 0, "=D0+4");    // E0 depends on D0

    int[][] result = cells.depth();

    assertEquals(0, result[0][0]); // A0 has depth 0
    assertEquals(1, result[1][0]); // B0 has depth 1
    assertEquals(2, result[2][0]); // C0 has depth 2
    assertEquals(3, result[3][0]); // D0 has depth 3
    assertEquals(4, result[4][0]); // E0 has depth 4

}



    @Test
    public void testAllEmptyCells() {
        Ex2Sheet cells = new Ex2Sheet(3, 3);
        Ex2GUI.setTable(cells);

        int[][] result = cells.depth();

        assertEquals(0, result[0][0]); // All cells should have depth 0 (no dependencies)
        assertEquals(0, result[1][1]);
        assertEquals(0, result[2][2]);
    }

    @Test
    public void testMultipleIndependentFormulas() {
        Ex2Sheet cells = new Ex2Sheet(3, 3);
        Ex2GUI.setTable(cells);

        cells.set(0, 0, "10");        // A0 is a constant
        cells.set(0, 1, "=A0+5");    // B0 depends on A0
        cells.set(1, 0, "=10+20");   // C0 is a formula with no dependencies
        cells.set(1, 1, "15");       // D0 is a constant

        int[][] result = cells.depth();

        assertEquals(0, result[0][0]); // A0 has depth 0
        assertEquals(1, result[0][1]); // B0 has depth 1
        assertEquals(0, result[1][0]); // C0 has depth 0
        assertEquals(0, result[1][1]); // D0 has depth 0
    }


    @Test
    public void testSimpleFormulaWithoutDependencies() {
        Ex2Sheet cells = new Ex2Sheet(3, 3);
        Ex2GUI.setTable(cells);

        cells.set(0, 0, "5");        // A0 is a constant
        cells.set(0, 1, "=5*2");     // B0 is a formula with no cell dependencies

        int[][] result = cells.depth();

        assertEquals(0, result[0][0]); // A0 has depth 0 (constant)
        assertEquals(0, result[0][1]); // B0 has depth 0 (no dependencies)
    }


    @Test
    public void testEmptyCells() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        int[][] expected = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        assertArrayEquals("Failed on empty cells.", expected, sheet.depth());
    }

    @Test
    public void testCellWithSimpleValue() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "10");
        int[][] expected = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        assertArrayEquals("Failed on cell with a simple value.", expected, sheet.depth());
    }


}
