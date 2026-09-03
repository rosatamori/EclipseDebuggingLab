/**
 * Station 4 - One bad number in a thousand.
 *
 * The registrar's spreadsheet says this section averaged about 77.
 * This program disagrees. Somewhere in 1000 scores, something is wrong.
 *
 * You are NOT allowed to add print statements to find it.
 */
public class Station4 {

    static int[] scores = new int[1000];

    public static void main(String[] args) {
        fillScores();

        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            total = total + scores[i];
        }

        System.out.println("Total:   " + total);
        System.out.println("Average: " + (total / scores.length));
    }

    /** Loads scores the way the campus system exports them. */
    static void fillScores() {
        java.util.Random rng = new java.util.Random(2490);
        for (int i = 0; i < scores.length; i++) {
            scores[i] = 55 + rng.nextInt(46);
            if (rng.nextInt(50) == 0) {
                scores[i] = -999;
            }
        }
    }
}
