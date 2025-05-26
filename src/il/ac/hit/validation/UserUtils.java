package il.ac.hit.validation;

import java.util.Comparator;

public class UserUtils {

    /**
     * Template Method for sorting users array.
     * Uses Java's built-in Comparator interface to delegate comparison logic.
     */
    public static void sort(User[] users, Comparator<User> comparator) {
        if (users == null || users.length <= 1) {
            return;
        }

        // Template Method algorithm - bubble sort
        int n = users.length;
        boolean swapped;

        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                // Delegate the comparison to the provided comparator
                if (comparator.compare(users[i-1], users[i]) > 0) {
                    swap(users, i-1, i);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    /**
     * Helper method for swapping elements
     */
    private static void swap(User[] users, int i, int j) {
        User temp = users[i];
        users[i] = users[j];
        users[j] = temp;
    }
}