import org.junit.Test;
import static org.junit.Assert.*;

public class IndexOfMainOpTest {

    @Test
    public void testIndexOfMainOp() {
        Ex2Sheet sheet = new Ex2Sheet();

        // Test basic formula
        String formula1 = "-1/4";
        assertEquals(2, sheet.indexOfMainOp(formula1)); // '+' is at index 1

        // Test formula with parentheses
        String formula2 = "(3+5)*2";
        assertEquals(5, sheet.indexOfMainOp(formula2)); // '*' is the main operator at index 5

        // Test nested parentheses
        String formula3 = "((3+5)*2)-7";
        assertEquals(9, sheet.indexOfMainOp(formula3)); // '-' is the main operator at index 9

        // Test formula with multiple operators at the same level
        String formula4 = "3+5-2";
        assertEquals(3, sheet.indexOfMainOp(formula4)); // '-' is the main operator at index 3

        // Test formula with different operator precedence
        String formula5 = "3+5*2";
        assertEquals(1, sheet.indexOfMainOp(formula5)); // '+' is the main operator at index 1

        // Test formula with leading and trailing spaces
        String formula6 = "3+5";
        assertEquals(1, sheet.indexOfMainOp(formula6.trim())); // '+' is the main operator at index 3

        // Test formula with only one number (no operator)
        String formula7 = "42";
        assertEquals(-1, sheet.indexOfMainOp(formula7)); // No operator present

        // Test formula with empty string
        String formula8 = "";
        assertEquals(-1, sheet.indexOfMainOp(formula8)); // No operator present

        // Test malformed formula with unbalanced parentheses
        String formula9 = "(3+5)*2)";
        assertEquals(5, sheet.indexOfMainOp(formula9)); // Still identifies '*' as main operator

        String formula100 = "3+5*2";
        assertEquals(1, sheet.indexOfMainOp(formula100)); // Main operator is '+'

        String formula200 = "(3+5)*2";
        assertEquals(5, sheet.indexOfMainOp(formula200)); // Main operator is '*'

        String formula300 = "((3+2)*(3-2))+(1-1)*3";
        assertEquals(13, sheet.indexOfMainOp(formula300)); // Main operator is '+'

        String formula400 = "(3+5)-((2*3)+1)";
        assertEquals(5, sheet.indexOfMainOp(formula400)); // Main operator is '-'


        String formula10 = "((3+2)*(3-2))+(1-1)*3";
        assertEquals(13, sheet.indexOfMainOp(formula10)); // Still identifies '*' as main operator
    }
}
