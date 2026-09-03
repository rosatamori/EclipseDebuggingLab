/**
 * Station 2 - Two things that print the same.
 *
 * Run this normally first and look at the output. Then debug it.
 * The printed output and the Variables view tell you two different stories.
 */
public class Station2 {

    static String[] names = { "Ada", "Grace", "Alan" };

    public static void main(String[] args) {
        String typed = "Ad" + "a";      // assembled by the compiler
        String built = buildName();     // assembled while the program runs

        boolean sameAsTyped = (names[0] == typed);
        boolean sameAsBuilt = (names[0] == built);
        boolean equalsBuilt = names[0].equals(built);

        System.out.println(typed);
        System.out.println(built);
        System.out.println(sameAsTyped + " " + sameAsBuilt + " " + equalsBuilt);
    }

    static String buildName() {
        String s = "Ad";
        s = s + "a";
        return s;
    }
}
