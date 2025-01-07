import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

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

        assertTrue(new SCell("123").isNumber());
        assertTrue(new SCell("45.67").isNumber());
        assertTrue(new SCell("-89.01").isNumber());
        assertTrue(new SCell("2e2").isNumber()); // Scientific notation
        assertTrue(new SCell("-1.23e-4").isNumber());
        assertFalse(new SCell("NotANumber").isNumber());
        assertFalse(new SCell("").isNumber());
        assertFalse(new SCell("1.23e").isNumber()); // Invalid scientific notation

    }

    @Test
    public void testIsForm() {
        // Valid formulas
        assertTrue(new SCell("=A1+B2").isForm());
        assertTrue(new SCell("=B1").isForm());
        assertTrue(new SCell("=1+2").isForm());
        assertTrue(new SCell("=(3*4)-5").isForm());
        assertTrue(new SCell("=(A1+B2)*(C3-D24)").isForm());
        assertTrue(new SCell("=(1+2)*(3-4)/5").isForm());
        assertTrue(new SCell("=(A12+B22)/3.14+E45").isForm());
        assertTrue(new SCell("=((1+2)*3)-4").isForm());
        assertTrue(new SCell("=(A1+B2)*(C3-(D24/E56))").isForm());
        assertTrue(new SCell("=(2+(5*(5-1)))*2+(3*(1/2))*12").isForm());

        // Invalid formulas
        assertFalse(new SCell("A1+B2").isForm());  // Missing '='
        assertFalse(new SCell("=A1+").isForm());  // Ends with operator
        assertFalse(new SCell("=(A1+B2").isForm());  // Unbalanced parentheses
        assertFalse(new SCell("=123A").isForm());  // Invalid number-letter sequence
        assertFalse(new SCell("=A1**)B2").isForm());  // Invalid operator usage
        assertFalse(new SCell("=(1+2)*-").isForm());  // Operator at the end
        assertFalse(new SCell("=+A1").isForm());  // Leading operator without operand
        assertFalse(new SCell("=1..2+3").isForm());  // Double dot in number
        assertFalse(new SCell("=A1+(B24))").isForm());  // Extra closing parenthesis

        // Valid formulas
        assertTrue(new SCell("=A1+B2").isForm());
        assertTrue(new SCell("=1+A1*C3").isForm());
        assertTrue(new SCell("=(A1+(B2*2))").isForm());
        assertTrue(new SCell("=A1+(B2*2)").isForm());
        assertTrue(new SCell("=232+A3").isForm());
        assertTrue(new SCell("=2e2+A3").isForm());
        assertTrue(new SCell("=22+A3").isForm());
        assertTrue(new SCell("=A1+B2+(C3+D4)").isForm());

        // Invalid formulas
        assertFalse(new SCell("").isForm());  // Empty input
        assertFalse(new SCell("Hello").isForm());  // Non-formula text
        assertFalse(new SCell("=+1A").isForm());  // Malformed formula
        assertFalse(new SCell("=A1+B2+").isForm());  // Ends with operator
        assertFalse(new SCell("=(A1+B2").isForm());  // Unbalanced parentheses
        assertFalse(new SCell("=123A").isForm());  // Invalid number-letter sequence
        assertFalse(new SCell("=A1**)B2").isForm());  // Invalid operator usage
        assertFalse(new SCell("=(1+2)*-").isForm());  // Operator at the end
        assertFalse(new SCell("=1..2+3").isForm());  // Double dot in number
        assertFalse(new SCell("=A1+(B24))").isForm());  // Extra closing p
        assertTrue(new SCell("=A1+B2").isForm());
        assertTrue(new SCell("=(1+2)*(3-4)/5").isForm());
        assertTrue(new SCell("=((1+2)*3)-4").isForm());
        assertTrue(new SCell("=(A1+B2)*(C3-(D24/E56))").isForm());
        assertFalse(new SCell("").isForm());  // Empty input
        assertFalse(new SCell("Hello").isForm());  // Non-formula text
        assertFalse(new SCell("=A1+").isForm());  // Ends with operator
        assertFalse(new SCell("=(A1+B2").isForm());  // Unbalanced parentheses
        assertFalse(new SCell("=1..2+3").isForm());  // Double dot in number
    }

    @Test
    public void testGetDepended() {
        SCell cell1 = new SCell("=A1+B2");
        SCell cell2 = new SCell("=1+A1*C3");
        SCell cell3 = new SCell("123"); // Not a formula
        SCell cell4 = new SCell("=A1+(B2*2)");
        SCell cell5 = new SCell("=232+A3");
        SCell cell6 = new SCell("=2e2+A3"); // Contains scientific notation
        SCell cell7 = new SCell("=22+A3");

        SCell emptyCell = new SCell(""); // Empty input
        SCell textCell = new SCell("Hello"); // Non-formula text
        SCell invalidFormulaCell = new SCell("=+1A"); // Malformed formula
        SCell nestedFormulaCell = new SCell("=A1+B2+(C3+D4)");

        assertArrayEquals(new String[]{"A1", "B2"}, new SCell("=A1+B2").getDepended());
        assertArrayEquals(new String[]{"A1", "C3"}, new SCell("=1+A1*C3").getDepended());
        assertArrayEquals(new String[]{}, new SCell("123").getDepended());  // Not a formula
        assertArrayEquals(new String[]{}, new SCell("").getDepended());  // Empty input
        assertArrayEquals(new String[]{}, new SCell("Hello").getDepended());  // Non-formula text
        assertArrayEquals(new String[]{"A1", "B2", "C3", "D4"}, new SCell("=A1+B2+(C3+D4)").getDepended());

        assertArrayEquals(new String[]{}, emptyCell.getDepended());
        assertArrayEquals(new String[]{}, textCell.getDepended());
        assertArrayEquals(new String[]{}, invalidFormulaCell.getDepended());
        assertArrayEquals(new String[]{"A1", "B2", "C3", "D4"}, nestedFormulaCell.getDepended());

        assertArrayEquals(new String[]{"A1", "B2"}, cell1.getDepended());
        assertArrayEquals(new String[]{"A1", "C3"}, cell2.getDepended());
        assertArrayEquals(new String[]{}, cell3.getDepended());
        assertArrayEquals(new String[]{"A1", "B2"}, cell4.getDepended());
        assertArrayEquals(new String[]{"A3"}, cell5.getDepended());
        assertArrayEquals(new String[]{"A3"}, cell7.getDepended());
        assertArrayEquals(new String[]{"A3"}, cell6.getDepended());
    }

}