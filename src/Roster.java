/**
 * A class roster stored as "parallel arrays": names[i], ids[i], and scores[i]
 * all describe the same student.
 *
 * This is how you build a record before you have objects. (Notice how easy it
 * would be to get the three arrays out of step with each other. Hold that
 * thought - it is the reason objects exist, and we will get there soon.)
 *
 * Five of the methods below contain bugs. RosterTests will tell you WHICH
 * tests fail. It will not tell you why, and in at least two cases the printed
 * evidence will actively mislead you.
 *
 * Do not add print statements. Find them with the debugger.
 */
public class Roster {

    static final int CAPACITY = 20;

    static String[] names = new String[CAPACITY];

    /** Integer rather than int so that a missing ID can be null. */
    static Integer[] ids = new Integer[CAPACITY];

    /** scores[i] is student i's own row of exam scores. */
    static int[][] scores = new int[CAPACITY][];

    static int size = 0;

    static void add(String name, Integer id, int[] examScores) {
        names[size] = name;
        ids[size] = id;
        scores[size] = examScores;
        size++;
    }

    static int averageFor(int index) {
        int[] row = scores[index];
        int total = 0;
        for (int i = 0; i < row.length; i++) {
            total = total + row[i];
        }
        return total / row.length;
    }

    /** Returns the best average in the roster. */
    static int highestAverage() {
        int best = 0;
        for (int i = 0; i <= size; i++) {
            int average = averageFor(i);
            if (average > best) {
                best = average;
            }
        }
        return best;
    }

    /** Returns the index of the student with this name, or -1. */
    static int findByName(String target) {
        for (int i = 0; i < size; i++) {
            if (names[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /** Returns the index of the student with this ID, or -1. */
    static int findById(Integer target) {
        for (int i = 0; i < size; i++) {
            if (ids[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a snapshot of everyone's scores that the caller can scribble on
     * without disturbing the real roster.
     */
    static int[][] backupScores() {
        return scores.clone();
    }

    /** Drops every student whose average is below 60. */
    static void removeAllFailing() {
        for (int i = 0; i < size; i++) {
            if (averageFor(i) < 60) {
                removeAt(i);
            }
        }
    }

    static void removeAt(int index) {
        for (int i = index; i < size - 1; i++) {
            names[i] = names[i + 1];
            ids[i] = ids[i + 1];
            scores[i] = scores[i + 1];
        }
        size--;
    }
}
