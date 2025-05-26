package il.ac.hit.validation;

import java.util.Comparator;
import java.util.Arrays;

/**
 * The {@code UserUtils} class provides utility methods for working with {@code User} objects.
 * <p>
 * This class includes a static method to sort an array of users using a custom comparator.
 */
public class UserUtils {
    /**
     * Sorts the given array of users using the specified comparator.
     *
     * @param users          the array of users to sort
     * @param userComparator the comparator that defines the sort order
     */
    public static void sort(User[] users, Comparator<User> userComparator) {
        Arrays.sort(users, userComparator);
    }
}
