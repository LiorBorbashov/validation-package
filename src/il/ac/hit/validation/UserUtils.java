package il.ac.hit.validation;

import java.util.Comparator;

/**
 * The {@code UserUtils} class provides utility methods for working with {@code User} objects.
 * <p>
 * This class includes a static method to sort an array of users using a custom comparator.
 */
public class UserUtils {
     /**
     * Sorts an array of {@code User} objects using Bubble Sort.
     * Uses a {@code Comparator<User>} to define the sorting order.
     * Demonstrates the Template Method pattern.
      * @param users the array of users to sort
      * @param comparator the comparator used to define the sorting order
     */
    public static void sort(User[] users, Comparator<User> comparator) {
        // check if the array is null or has one or fewer elements — no need to sort
        if (users == null || users.length <= 1) {
            return;
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator must not be null");
        }

        // Template Method algorithm - bubble sort
        int n = users.length;
        boolean swapped = true;

        while (swapped) {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (comparator.compare(users[i - 1], users[i]) > 0) {
                    swap(users, i - 1, i);
                    swapped = true;
                }
            }
            n--;
        }
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
