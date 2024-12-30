import org.junit.Test;

import static org.junit.Assert.*;

public class TestEx2
{

    @Test
    public void TestIsNumber()
    {
        assertTrue(Cell1.isNumber("+3.55"));
        assertTrue(Cell1.isNumber("-3.55"));
        assertTrue(Cell1.isNumber("999"));
        assertTrue(Cell1.isNumber("+0095"));

        assertFalse(Cell1.isNumber("34.4.4"));
        assertFalse(Cell1.isNumber("4-2"));
        assertFalse(Cell1.isNumber("[23p4"));
        assertFalse(Cell1.isNumber("34.4-4"));
    }

        @Test
        public void TestIsFormula()
        {

            assertTrue(Cell1.isForm("=1.3"));
            assertTrue(Cell1.isForm("=1+2*2"));
            assertTrue(Cell1.isForm("=(2)"));
            assertTrue(Cell1.isForm("=(1+2)*2"));
            assertTrue(Cell1.isForm("=1"));
            assertTrue(Cell1.isForm("=-1"));
            assertTrue(Cell1.isForm("=1+2"));
            assertTrue(Cell1.isForm("=1.23+4.56"));
            assertTrue(Cell1.isForm("=(1+2)*3"));
            assertTrue(Cell1.isForm("=(1+(2*3)-4)/5"));
            assertTrue(Cell1.isForm("=(((1+2)))"));
            assertTrue(Cell1.isForm("=1-(2.3)+(4/5)"));
            assertTrue(Cell1.isForm("=(0.5+0.5)*(2-1)"));
            assertTrue(Cell1.isForm("=(2)*(3/4)-5"));
            assertTrue(Cell1.isForm("=1+(-2)"));
            assertTrue(Cell1.isForm("=(-1)"));
            assertTrue(Cell1.isForm("=(1+(-2))"));
            assertTrue(Cell1.isForm("=1.0+2.3"));

            assertFalse(Cell1.isForm("=(1+-2)"));
            assertFalse(Cell1.isForm("123"));
            assertFalse(Cell1.isForm("=1..2"));
            assertFalse(Cell1.isForm("=1++2"));
            assertFalse(Cell1.isForm("=1+"));
            assertFalse(Cell1.isForm("=*3"));
            assertFalse(Cell1.isForm("=(1+2"));
            assertFalse(Cell1.isForm("=(1+2))"));
            assertFalse(Cell1.isForm("=(1+)2"));
            assertFalse(Cell1.isForm("=(1+(2*3)-)"));
            assertFalse(Cell1.isForm("=+1"));
            assertFalse(Cell1.isForm("=1(2)"));
            assertFalse(Cell1.isForm("=(1)2"));
            assertFalse(Cell1.isForm("=(1+2)3"));
            assertFalse(Cell1.isForm("=abc"));
            assertFalse(Cell1.isForm("=1+2&3"));
            assertFalse(Cell1.isForm("=12323.44.4"));
            assertFalse(Cell1.isForm("=.1"));
            assertFalse(Cell1.isForm("=1)"));
            assertFalse(Cell1.isForm("=(1+)"));
            assertFalse(Cell1.isForm("=(1+2)3"));
            assertFalse(Cell1.isForm("=(1)+(2)3"));
            assertFalse(Cell1.isForm("=1++2"));
            assertFalse(Cell1.isForm("=1..2"));
            assertFalse(Cell1.isForm("=(1+)2"));
            assertFalse(Cell1.isForm("=(1+2"));



        }


}
