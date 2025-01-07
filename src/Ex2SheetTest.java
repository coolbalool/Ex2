    import org.junit.Test;

    import java.util.Arrays;

    import static org.junit.Assert.*;

    public class Ex2SheetTest {


        @Test
        public void testDepthMixedCases() {
            Ex2Sheet sheet = new Ex2Sheet(4, 4);

            // Define cell dependencies
            // A1 = B1 + C1 (depends on B1 and C1)
            sheet.set(0, 0, "=B1+C1");
            // B1 = C1 * D1 (depends on C1 and D1)
            sheet.set(1, 0, "=C1*D1");
            // C1 = 5 (number, no dependencies)
            sheet.set(2, 0, "5");
            // D1 = 10 (number, no dependencies)
            sheet.set(3, 0, "10");
            // A2 = C1 (depends on C1)
            sheet.set(0, 1, "=C1");

            // Unused cells: A3, B3, C3, D3, and others
            // Add nothing to them, leaving as default empty cells.

            // Run depth calculation
            int[][] depths = sheet.depth();

            // Print the depth matrix for debugging
            System.out.println("Depth Matrix for Mixed Cases:");
            for (int i = 0; i < depths.length; i++) {
                for (int j = 0; j < depths[i].length; j++) {
                    System.out.print(depths[i][j] + " ");
                }
                System.out.println();
            }

            // Validate the results
            assertArrayEquals("Depth calculation failed for mixed cases.", new int[][] {
                    {2, 1, -1, -1}, // A1 (depth=2), A2 (depth=1), unused cells (-1)
                    {1, 0, -1, -1}, // B1 (depth=1), unused cells (-1)
                    {0, -1, -1, -1}, // C1 (depth=0), unused cells (-1)
                    {0, -1, -1, -1}, // D1 (depth=0), unused cells (-1)
            }, depths);
        }



        @Test
        public void testDepth1() {
            Ex2Sheet sheet = new Ex2Sheet(3, 3);

            // Case 1: Simple Dependencies
            sheet.set(0, 0, "=B1+C1"); // A1 depends on B1 and C1
            sheet.set(1, 0, "=5");  // B1 depends on C1
            sheet.set(2, 0, "10");     // C1 is a number

            int[][] depths = sheet.depth();

            System.out.println("d");
            System.out.println("");
            for (int i = 0; i < depths.length; i++) {
                for (int j = 0; j < depths[i].length; j++)
                    System.out.print(depths[i][j]);
                    System.out.println("");
            }


            assertArrayEquals("Depth calculation failed for simple dependencies.", new int[][] {
                    {1, 0, 0}, // A1 (depth=2), B1 (depth=1), C1 (depth=0)
                    {-1, -1, -1}, // Other cells unused.
                    {-1, -1, -1}  // Other cells unused.
            }, depths);
        }



        @Test
        public void testDepth() {
            Ex2Sheet sheet = new Ex2Sheet(3, 3);

            // Case 1: Simple Dependencies
            sheet.set(0, 0, "=B1+C1"); // A1 depends on B1 and C1
            sheet.set(1, 0, "=C1+5");  // B1 depends on C1
            sheet.set(2, 0, "10");     // C1 is a number
            int[][] depths = sheet.depth();

            assertArrayEquals("Depth calculation failed for simple dependencies.", new int[][] {
                    {2, 1, 0},
                    {0, 0, 0}, // Unused cells are marked as depth 0
                    {0, 0, 0}  // Unused cells are marked as depth 0
            }, depths);

            // Case 2: Cyclic Dependencies
            sheet.set(0, 0, "=B1");    // A1 depends on B1
            sheet.set(1, 0, "=A1");    // B1 depends on A1 (cycle)
            depths = sheet.depth();

            assertArrayEquals("Depth calculation failed for cyclic dependencies.", new int[][] {
                    {-1, -1, 0}, // A1 and B1 are in a cycle (-1), other cells unused (0)
                    {0, 0, 0},   // Unused cells are marked as depth 0
                    {0, 0, 0}    // Unused cells are marked as depth 0
            }, depths);
        }

    }
