package unitTests;

public class DiscardTests {
    public static void main(String[] args) {
        new DiscardTests();
    }
    private int passed = 0;
    private int failed = 0;

    public DiscardTests() {
        testDiscard();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testDiscard() {

    }
}
