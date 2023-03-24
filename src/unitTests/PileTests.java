package unitTests;

public class PileTests {
    public static void main(String[] args) {
        new PileTests();
    }
    private int passed = 0;
    private int failed = 0;

    public PileTests() {
        testPile();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testPile() {

    }
}
