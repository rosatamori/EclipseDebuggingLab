/**
 * The file used in the pre-class video.
 *
 * Seven students took an exam. The passing mark is 60. Five of them passed.
 * This program insists that three did.
 *
 * Follow along with the video and find out why.
 */
public class VideoDemo {

    static int[] scores = { 72, 60, 91, 45, 88, 60, 39 };

    public static void main(String[] args) {
        int passing = countPassing(scores, 60);
        System.out.println("Passing: " + passing + " out of " + scores.length);
    }

    static int countPassing(int[] values, int threshold) {
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > threshold) {
                count++;
            }
        }
        return count;
    }
}
