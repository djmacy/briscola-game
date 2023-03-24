package runnable;

public class HandTests {
    public static void main(String[] args) {
        new HandTests();
    }
    private int passed = 0;
    private int failed = 0;

    public HandTests() {
        testHand();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testHand() {

    }
}
