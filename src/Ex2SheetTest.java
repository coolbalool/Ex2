    import org.junit.Test;

    import java.util.ArrayList;
    import java.util.Arrays;

    import static org.junit.Assert.*;

    public class Ex2SheetTest {


        @Test
        public void testIsLoop() {
            Ex2Sheet sheet = new Ex2Sheet(3, 3);

            // Test 2: Direct Circular Dependency
            sheet.set(0, 0, "=A1"); // Cell A1 refers to itself
            ArrayList<String> seen = new ArrayList<>();

            // Note: A1 means column 0 (A), row 1
            boolean hasLoop = sheet.isLoop("A1", seen);
            assertTrue("Test 2 failed: Direct circular dependency", hasLoop);

            // Add some debugging output
            SCell cell = (SCell) sheet.get("A1");
            System.out.println("Cell A1 content: " + cell.getData());
            System.out.println("Cell A1 dependencies: " + Arrays.toString(cell.getDepended()));
            System.out.println("Cell A1 type: " + cell.getType());
        }

    }
