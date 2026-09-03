/**
 * Station 6 - Rewinding time.
 *
 * Nothing here is broken. This station exists to show you one feature
 * that has no equivalent anywhere in print-statement debugging:
 * you can un-run code, change your mind, and run it again.
 */
public class Station6 {

    public static void main(String[] args) {
        int[] data = { 4, 8, 15, 16, 23, 42 };
        int result = largestMultipleOf(data, 3);
        System.out.println("result = " + result);
    }

    static int largestMultipleOf(int[] values, int k) {
        int best = -1;
        for (int i = 0; i < values.length; i++) {
            if (values[i] % k == 0 && values[i] > best) {
                best = values[i];
            }
        }
        return best;
    }
}
