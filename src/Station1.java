/**
 * Station 1 - Warm-up.
 *
 * Nothing here is broken. The goal is to get comfortable with the controls
 * you saw in the video, and to notice something in the Variables view that
 * you have probably never paid attention to before.
 */
public class Station1 {

    static int[] scores = { 88, 94, 71, 100, 65 };

    public static void main(String[] args) {
        int total = sum(scores);
        int count = scores.length;
        double average = (double) total / count;
        System.out.println("Average: " + average);
    }

    static int sum(int[] values) {
        int running = 0;
        for (int i = 0; i < values.length; i++) {
            running = running + values[i];
        }
        return running;
    }
}
