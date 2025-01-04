import org.junit.Test;
import static org.junit.Assert.*;

public class TestSCell {

    @Test
    public void testIsNumber() {
        SCell cell1 = new SCell("123");
        SCell cell2 = new SCell("45.67");
        SCell cell3 = new SCell("-89.01");
        SCell cell4 = new SCell("NotANumber");
        SCell cell5 = new SCell("2e2"); // Scientific notation

        assertTrue(cell1.isNumber());
        assertTrue(cell2.isNumber());
        assertTrue(cell3.isNumber());
        assertFalse(cell4.isNumber());
        assertTrue(cell5.isNumber());
    }


    @Test
    public void testIsNumberEdgeCases() {
        SCell emptyCell = new SCell("");
        SCell zeroCell = new SCell("0");
        SCell scientificNotationCell = new SCell("1.23e10");
        SCell invalidScientificNotationCell = new SCell("1.23e");
        SCell negativeExponentCell = new SCell("-1.23e-4");

        assertFalse(emptyCell.isNumber());
        assertTrue(zeroCell.isNumber());
        assertTrue(scientificNotationCell.isNumber());
        assertFalse(invalidScientificNotationCell.isNumber());
        assertTrue(negativeExponentCell.isNumber());
    }

    @Test
    public void testGetDepended() {
        SCell cell1 = new SCell("=A1+B2");
        SCell cell2 = new SCell("=1+A1*C3");
        SCell cell3 = new SCell("123"); // Not a formula
        SCell cell4 = new SCell("=A1+(B2*2)");
        SCell cell5 = new SCell("=2e2+A3"); // Contains scientific notation

        assertArrayEquals(new String[]{"A1", "B2"}, cell1.getDepended());
        assertArrayEquals(new String[]{"A1", "C3"}, cell2.getDepended());
        assertArrayEquals(new String[]{}, cell3.getDepended());
        assertArrayEquals(new String[]{"A1", "B2"}, cell4.getDepended());
        assertArrayEquals(new String[]{"A3"}, cell5.getDepended());
    }

    @Test
    public void testGetDependedEdgeCases() {
        SCell emptyCell = new SCell(""); // Empty input
        SCell textCell = new SCell("Hello"); // Non-formula text
        SCell invalidFormulaCell = new SCell("=+1A"); // Malformed formula
        SCell nestedFormulaCell = new SCell("=A1+B2+(C3+D4)");

        assertArrayEquals(new String[]{}, emptyCell.getDepended());
        assertArrayEquals(new String[]{}, textCell.getDepended());
        assertArrayEquals(new String[]{}, invalidFormulaCell.getDepended());
        assertArrayEquals(new String[]{"A1", "B2", "C3", "D4"}, nestedFormulaCell.getDepended());
    }
}