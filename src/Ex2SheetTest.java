import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class Ex2SheetTest {

    @Test
    public void testDepthSimpleDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1+C1"); // A1 depends on B1 and C1 depth 2 (max (1,1) + 1)
        sheet.set(1, 0, "5");       // B1 is a number - depth 1
        sheet.set(2, 0, "10");      // C1 is a number - depth 1

        int[][] depths = sheet.depth();
        assertArrayEquals(new int[][] {
                {2, 0, 0},
                {-1, -1, -1},
                {-1, -1, -1}
        }, depths);
    }

    @Test
    public void testDepthCyclicDependency() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1"); // A1 depends on B1
        sheet.set(1, 0, "=A1"); // B1 depends on A1

        int[][] depths = sheet.depth();
        assertArrayEquals(new int[][] {
                {-1, -1, -1},
                {-1, -1, -1},
                {-1, -1, -1}
        }, depths);
    }

    @Test
    public void testDepthUnusedCells() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1+C1"); // A1 depends on B1 and C1
        sheet.set(1, 0, "5");       // B1 is a number

        int[][] depths = sheet.depth();
        assertArrayEquals(new int[][] {
                {2, 0, -1},
                {-1, -1, -1},
                {-1, -1, -1}
        }, depths);
    }

    @Test
    public void testDepthInvalidReferences() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=X1"); // A1 references an out-of-bound cell

        int[][] depths = sheet.depth();
        assertArrayEquals(new int[][] {
                {-1, -1, -1},
                {-1, -1, -1},
                {-1, -1, -1}
        }, depths);
    }

    @Test
    public void testValueNumber() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "123");
        assertEquals("123.0", sheet.value(0, 0));
    }

    @Test
    public void testValueText() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "Hello");
        assertEquals("Hello", sheet.value(0, 0));
    }

    @Test
    public void testValueFormula() { // not finished
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1+C1"); // A1 depends on B1 and C1
        sheet.set(1, 0, "5");       // B1 is a number
        sheet.set(2, 0, "10");      // C1 is a number
        assertEquals("15.0", sheet.value(0, 0));
    }

    @Test
    public void testValueInvalidFormula() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1++C1"); // Invalid formula
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(0, 0));
    }

    @Test
    public void testValueCycle() { // not finished
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1"); // A1 depends on B1
        sheet.set(1, 0, "=A1"); // B1 depends on A1
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(0, 0));
    }

    @Test
    public void testSetAndGet() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "123");
        assertEquals("123.0", sheet.value(0, 0));

        sheet.set(1, 1, "Hello");
        assertEquals("Hello", sheet.value(1, 1));

        sheet.set(2, 2, "=A1+B1");
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "10");
        assertEquals("15.0", sheet.value(2, 2));
    }

    @Test
    public void testCordStrToInt() { // 
        Ex2Sheet sheet = new Ex2Sheet();

        int[] result = sheet.cordStrToInt("A1");
        assertArrayEquals(new int[] {0, 1}, result);

        result = sheet.cordStrToInt("B10");
        assertArrayEquals(new int[] {1, 10}, result);

        result = sheet.cordStrToInt("Z100");
        assertArrayEquals(new int[] {25, 100}, result);

        result = sheet.cordStrToInt("AA1");
        assertArrayEquals(new int[] {-1, -1}, result); // Invalid

        result = sheet.cordStrToInt("1A");
        assertArrayEquals(new int[] {-1, -1}, result); // Invalid
    }

    @Test
    public void testIsIn() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);

        assertTrue(sheet.isIn(0, 0));
        assertTrue(sheet.isIn(2, 2));
        assertFalse(sheet.isIn(-1, 0));
        assertFalse(sheet.isIn(3, 3));
    }

    @Test
    public void testEval() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1+C1");
        sheet.set(1, 0, "5");
        sheet.set(2, 0, "10");

        sheet.eval();

        assertEquals("15.0", sheet.value(0, 0));
    }
}
