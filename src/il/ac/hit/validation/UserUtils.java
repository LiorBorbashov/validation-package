package il.ac.hit.validation;

import java.util.Comparator;
import java.util.Arrays;

public class UserUtils {
    public static void sort(User[] users, Comparator<User> userComparator) {
        Arrays.sort(users, userComparator);
    }
}
