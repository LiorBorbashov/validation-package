package il.ac.hit.validation;

import java.util.Comparator;

public class AdditionalTests {

    public static void main(String[] args) {
        System.out.println("=== ADDITIONAL EDGE CASE TESTS ===\n");

        testNullValueHandling();
        testEmptyStringHandling();
        testBoundaryValues();
        testComplexCombinations();
        testFactoryErrorHandling();
        testSortingEdgeCases();
    }

    public static void testNullValueHandling() {
        System.out.println("1. TESTING NULL VALUE HANDLING");
        System.out.println("-------------------------------");

        try {
            // Test with null values
            User nullUser = new User(null, null, null, 20);

            ValidationResult result = UserValidation.emailEndsWithIL().apply(nullUser);
            System.out.println("✓ Null email handled: " + !result.isValid());

            result = UserValidation.passwordLengthBiggerThan8().apply(nullUser);
            System.out.println("✓ Null password handled: " + !result.isValid());

            result = UserValidation.usernameLengthBiggerThan8().apply(nullUser);
            System.out.println("✓ Null username handled: " + !result.isValid());

        } catch (Exception e) {
            System.out.println("✗ Error with null handling: " + e.getMessage());
        }
        System.out.println();
    }

    public static void testEmptyStringHandling() {
        System.out.println("2. TESTING EMPTY STRING HANDLING");
        System.out.println("--------------------------------");

        try {
            User emptyUser = new User("", "", "", 20);

            ValidationResult result = UserValidation.emailLengthBiggerThan10().apply(emptyUser);
            System.out.println("✓ Empty email length check: " + !result.isValid());

            result = UserValidation.passwordLengthBiggerThan8().apply(emptyUser);
            System.out.println("✓ Empty password length check: " + !result.isValid());

            result = UserValidation.usernameLengthBiggerThan8().apply(emptyUser);
            System.out.println("✓ Empty username length check: " + !result.isValid());

        } catch (Exception e) {
            System.out.println("✗ Error with empty strings: " + e.getMessage());
        }
        System.out.println();
    }

    public static void testBoundaryValues() {
        System.out.println("3. TESTING BOUNDARY VALUES");
        System.out.println("--------------------------");

        // Test exact boundary values
        User boundaryUser1 = new User("exactly8c", "exactly10c", "exactly8", 18);
        User boundaryUser2 = new User("exactly9ch", "exactly11ch", "exactly9c", 19);

        // Test age boundary (exactly 18 vs 19)
        ValidationResult result = UserValidation.ageBiggerThan18().apply(boundaryUser1);
        System.out.println("✓ Age exactly 18: " + !result.isValid());

        result = UserValidation.ageBiggerThan18().apply(boundaryUser2);
        System.out.println("✓ Age exactly 19: " + result.isValid());

        // Test username length boundary (exactly 8 vs 9)
        result = UserValidation.usernameLengthBiggerThan8().apply(boundaryUser1);
        System.out.println("✓ Username exactly 8 chars: " + !result.isValid());

        result = UserValidation.usernameLengthBiggerThan8().apply(boundaryUser2);
        System.out.println("✓ Username exactly 9 chars: " + result.isValid());

        // Test email length boundary (exactly 10 vs 11)
        result = UserValidation.emailLengthBiggerThan10().apply(boundaryUser1);
        System.out.println("✓ Email exactly 10 chars: " + !result.isValid());

        result = UserValidation.emailLengthBiggerThan10().apply(boundaryUser2);
        System.out.println("✓ Email exactly 11 chars: " + result.isValid());

        System.out.println();
    }

    public static void testComplexCombinations() {
        System.out.println("4. TESTING COMPLEX COMBINATIONS");
        System.out.println("-------------------------------");

        User testUser = new User("complexuser", "complex@email.il", "complex123$", 25);

        // Test complex nested combinations
        UserValidation complex = UserValidation.all(
                UserValidation.ageBiggerThan18(),
                UserValidation.emailEndsWithIL(),
                UserValidation.passwordLengthBiggerThan8().and(
                        UserValidation.passwordIsDifferentFromUsername()
                )
        );

        ValidationResult result = complex.apply(testUser);
        System.out.println("✓ Complex nested combination: " + result.isValid());

        // Test combination with conflicting requirements
        UserValidation conflicting = UserValidation.passwordIncludesLettersNumbersOnly()
                .and(UserValidation.passwordIncludesDollarSign());

        result = conflicting.apply(testUser);
        System.out.println("✓ Conflicting requirements handled: " + !result.isValid());

        System.out.println();
    }

    public static void testFactoryErrorHandling() {
        System.out.println("5. TESTING FACTORY ERROR HANDLING");
        System.out.println("---------------------------------");

        try {
            // Test invalid user type
            User user = UserFactory.createUser("invalid", "test", "test@test.com", "pass", 20);
            System.out.println("✗ Should have thrown exception for invalid type");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Invalid user type handled correctly: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected exception: " + e.getMessage());
        }

        try {
            // Test case sensitivity
            User basic = UserFactory.createUser("BASIC", "test", "test@test.com", "pass", 20);
            System.out.println("✓ Case insensitive factory: " + basic.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("✗ Case sensitivity issue: " + e.getMessage());
        }

        System.out.println();
    }

    public static void testSortingEdgeCases() {
        System.out.println("6. TESTING SORTING EDGE CASES");
        System.out.println("-----------------------------");

        try {
            // Test null array
            UserUtils.sort(null, Comparator.comparing(User::getUsername));
            System.out.println("✓ Null array handled");

            // Test empty array
            User[] emptyArray = {};
            UserUtils.sort(emptyArray, Comparator.comparing(User::getUsername));
            System.out.println("✓ Empty array handled");

            // Test single element array
            User[] singleArray = {new User("single", "single@test.com", "pass", 20)};
            UserUtils.sort(singleArray, Comparator.comparing(User::getUsername));
            System.out.println("✓ Single element array handled");

            // Test array with identical elements
            User[] identicalArray = {
                    new User("same", "same@test.com", "pass", 20),
                    new User("same", "same@test.com", "pass", 20)
            };
            UserUtils.sort(identicalArray, Comparator.comparing(User::getUsername));
            System.out.println("✓ Identical elements handled");

        } catch (Exception e) {
            System.out.println("✗ Sorting edge case error: " + e.getMessage());
        }

        System.out.println();
    }
}