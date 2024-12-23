import org.junit.Test;

import static org.junit.Assert.*;

public class TestEx2
{

    @Test
    public void TestIsNumber()
    {
        assertTrue(Cell.isNumber("+3.55"));
        assertTrue(Cell.isNumber("-3.55"));
        assertTrue(Cell.isNumber("999"));
        assertTrue(Cell.isNumber("+0095"));

        assertFalse(Cell.isNumber("34.4.4"));
        assertFalse(Cell.isNumber("4-2"));
        assertFalse(Cell.isNumber("[23p4"));
        assertFalse(Cell.isNumber("34.4-4"));



    }


}
