import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ComputeFormulaTest {

    @Test
    public void testBasicArithmetic() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "10");
        sheet.set(0, 1, "20");
        sheet.set(1, 0, "=A0+B0");
        assertEquals("30.0", sheet.value(1, 0));
    }

    @Test
    public void testNestedFormulas() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");
        sheet.set(0, 1, "=A0*2");
        sheet.set(1, 0, "=B0+A0");
        sheet.set(1, 1, "=(C0+5)*2");
        assertEquals("40.0", sheet.value(1, 1));
    }

    @Test
    public void testDivisionByZero() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "10");
        sheet.set(0, 1, "0");
        sheet.set(1, 0, "=A0/B0");
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(1, 0));
    }

    @Test
    public void testCircularDependency() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B0");
        sheet.set(0, 1, "=A0");
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(0, 0));
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(0, 1));
    }

    @Test
    public void testInvalidFormula() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=A0+*");
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(0, 0));
    }

    @Test
    public void testParenthesesHandling() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");
        sheet.set(0, 1, "=((A0+5)*2)");
        assertEquals("20.0", sheet.value(0, 1));
    }

    @Test
    public void testComplexFormula() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");
        sheet.set(0, 1, "=A0*3+(4/2)-1");
        assertEquals("17.0", sheet.value(0, 1));
    }

    @Test
    public void testNestedParentheses() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");
        sheet.set(0, 1, "=(((A0+5)*2)+10)");
        assertEquals("30.0", sheet.value(0, 1));
    }

    @Test
    public void testEmptyCellReference() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B0+1");
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(0, 0));
    }

    @Test
    public void testWhitespaceInFormula() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "10");
        sheet.set(0, 1, "= A0 +  5 ");
        assertEquals("15.0", sheet.value(0, 1));
    }
}
