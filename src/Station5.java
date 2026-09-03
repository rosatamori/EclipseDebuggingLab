/**
 * Station 5 - Who is changing size?
 *
 * We add four students, remove one, tidy up, and end with a roster
 * whose size is NEGATIVE. Four different methods touch `size`.
 *
 * With print statements you would have to edit every one of them.
 * You are going to ask the JVM to tell you instead.
 */
public class Station5 {

    static String[] names = new String[10];
    static int size = 0;

    public static void main(String[] args) {
        add("Ada");
        add("Grace");
        add("Alan");
        add("Katherine");

        removeAt(1);
        compact();

        System.out.println("size = " + size);
        printAll();
    }

    static void add(String name) {
        names[size] = name;
        size++;
    }

    static void removeAt(int index) {
        for (int i = index; i < size - 1; i++) {
            names[i] = names[i + 1];
        }
        size--;
    }

    /** Meant to tidy up leftover empty slots. */
    static void compact() {
        for (int i = 0; i < names.length; i++) {
            if (names[i] == null) {
                size--;
            }
        }
    }

    static void printAll() {
        for (int i = 0; i < size; i++) {
            System.out.println(names[i]);
        }
    }
}
