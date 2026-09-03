/**
 * Run this as a Java Application to see which Roster methods are broken.
 *
 * When a test fails, do NOT start reading the source hunting for typos, and do
 * NOT add print statements. Set a breakpoint and run this under the debugger:
 *   Run > Debug As > Java Application
 *
 * Each test resets the roster first, so tests cannot contaminate each other.
 */
public class RosterTests {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        run("1  highestAverage");
        run("2  findByName with a literal");
        run("3  findByName with a typed-in name");
        run("4  findById, small ID");
        run("5  findById, large ID");
        run("6  backupScores is independent");
        run("7  removeAllFailing");

        System.out.println();
        System.out.println("passed: " + passed + "   failed: " + failed);
    }

    static void run(String label) {
        reset();
        try {
            switch (label.charAt(0)) {
                case '1': check(label, Roster.highestAverage() == 95); break;
                case '2': check(label, Roster.findByName("Ada") == 0); break;
                case '3': check(label, Roster.findByName(typeIn()) == 0); break;
                case '4': check(label, Roster.findById(42) == 0); break;
                case '5': check(label, Roster.findById(5001) == 1); break;
                case '6': check(label, backupIsIndependent()); break;
                case '7': check(label, failingStudentsAreGone()); break;
            }
        } catch (Exception e) {
            failed++;
            System.out.println("FAIL  " + label + "   threw " + e.getClass().getSimpleName());
        }
    }

    /** Builds the name "Ada" at run time, the way reading user input would. */
    static String typeIn() {
        String s = "Ad";
        s = s + "a";
        return s;
    }

    static boolean backupIsIndependent() {
        int[][] backup = Roster.backupScores();
        backup[0][0] = 0;
        return Roster.scores[0][0] == 90;
    }

    static boolean failingStudentsAreGone() {
        Roster.removeAllFailing();
        if (Roster.size != 3) {
            return false;
        }
        for (int i = 0; i < Roster.size; i++) {
            if (Roster.averageFor(i) < 60) {
                return false;
            }
        }
        return true;
    }

    static void check(String label, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("pass  " + label);
        } else {
            failed++;
            System.out.println("FAIL  " + label);
        }
    }

    /** Rebuilds a fresh roster so every test starts from the same place. */
    static void reset() {
        Roster.names = new String[Roster.CAPACITY];
        Roster.ids = new Integer[Roster.CAPACITY];
        Roster.scores = new int[Roster.CAPACITY][];
        Roster.size = 0;

        Roster.add("Ada", 42, new int[] { 90, 95, 100 });
        Roster.add("Grace", 5001, new int[] { 80, 85, 90 });
        Roster.add("Alan", 7, new int[] { 50, 55, 45 });
        Roster.add("Katherine", 128, new int[] { 40, 30, 50 });
        Roster.add("Barbara", 9, new int[] { 70, 75, 80 });
    }
}
