/**
 * Station 3 - Who else is holding this array?
 *
 * In C you could see references coming: you wrote * and &.
 * In Java they are invisible. The debugger makes them visible again.
 */
public class Station3 {

    public static void main(String[] args) {
        int[] original = { 10, 20, 30 };
        int[] alias = original;             // NOT a new array
        int[] copy = original.clone();      // a genuinely new array

        alias[0] = 999;
        copy[1] = 777;

        System.out.println(original[0] + " " + original[1] + " " + original[2]);

        curveAll(original, 5);
        System.out.println(original[0] + " " + original[1] + " " + original[2]);

        int firstScore = original[0];
        addTen(firstScore);
        System.out.println(firstScore + " " + original[0]);
    }

    static void curveAll(int[] values, int bonus) {
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] + bonus;
        }
    }

    static void addTen(int n) {
        n = n + 10;
    }
}
