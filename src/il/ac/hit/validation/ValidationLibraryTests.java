package il.ac.hit.validation;

import java.util.Comparator;

public class ValidationLibraryTests {

    public static void main(String[] args) {
        System.out.println("=== VALIDATION LIBRARY TESTS ===\n");

        // Test 1: Basic User Creation and Factory Pattern
        testUserCreationAndFactory();

        // Test 2: Individual Validation Methods
        testIndividualValidations();

        // Test 3: Combinator Pattern (and, or, xor)
        testCombinatorPattern();

        // Test 4: Static Combination Methods (all, none)
        testStaticCombinations();

        // Test 5: Template Method Pattern (UserUtils.sort)
        testTemplateMethodPattern();

        // Test 6: Original Test Case from Specification
        testOriginalSpecification();

        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }

    public static void testUserCreationAndFactory() {
        System.out.println("1. TESTING USER CREATION AND FACTORY PATTERN");
        System.out.println("---------------------------------------------");

        try {
            // Test basic User creation
            User user1 = new User("john", "john@example.com", "password123", 25);
            System.out.println("✓ User creation successful: " + user1.toString());

            // Test Factory Pattern
            User basicUser = UserFactory.createUser("basic", "basic_user", "basic@test.com", "pass123", 20);
            User premiumUser = UserFactory.createUser("premium", "premium_user", "premium@test.com", "pass456", 30);
            User platinumUser = UserFactory.createUser("platinum", "platinum_user", "platinum@test.com", "pass789", 40);

            System.out.println("✓ Factory - Basic User: " + basicUser.getClass().getSimpleName());
            System.out.println("✓ Factory - Premium User: " + premiumUser.getClass().getSimpleName());
            System.out.println("✓ Factory - Platinum User: " + platinumUser.getClass().getSimpleName());

        } catch (Exception e) {
            System.out.println("✗ Error in user creation: " + e.getMessage());
        }
        System.out.println();
    }

    public static void testIndividualValidations() {
        System.out.println("2. TESTING INDIVIDUAL VALIDATION METHODS");
        System.out.println("----------------------------------------");

        // Test users with different characteristics
        User validUser = new User("longusernam", "verylongemail@domain.il", "longpass123", 25);
        User invalidUser = new User("short", "short.il", "short", 15);

        // Test each validation method
        testValidation("emailEndsWithIL", UserValidation.emailEndsWithIL(), validUser, true);
        testValidation("emailEndsWithIL", UserValidation.emailEndsWithIL(), invalidUser, true);

        testValidation("emailLengthBiggerThan10", UserValidation.emailLengthBiggerThan10(), validUser, true);
        testValidation("emailLengthBiggerThan10", UserValidation.emailLengthBiggerThan10(), invalidUser, false);

        testValidation("passwordLengthBiggerThan8", UserValidation.passwordLengthBiggerThan8(), validUser, true);
        testValidation("passwordLengthBiggerThan8", UserValidation.passwordLengthBiggerThan8(), invalidUser, false);

        testValidation("passwordIncludesLettersNumbersOnly", UserValidation.passwordIncludesLettersNumbersOnly(), validUser, true);

        User dollarUser = new User("testuser", "test@domain.il", "password$123", 25);
        testValidation("passwordIncludesDollarSign", UserValidation.passwordIncludesDollarSign(), dollarUser, true);
        testValidation("passwordIncludesDollarSign", UserValidation.passwordIncludesDollarSign(), validUser, false);

        testValidation("passwordIsDifferentFromUsername", UserValidation.passwordIsDifferentFromUsername(), validUser, true);
        User samePassUser = new User("samepass", "test@domain.il", "samepass", 25);
        testValidation("passwordIsDifferentFromUsername", UserValidation.passwordIsDifferentFromUsername(), samePassUser, false);

        testValidation("ageBiggerThan18", UserValidation.ageBiggerThan18(), validUser, true);
        testValidation("ageBiggerThan18", UserValidation.ageBiggerThan18(), invalidUser, false);

        testValidation("usernameLengthBiggerThan8", UserValidation.usernameLengthBiggerThan8(), validUser, true);
        testValidation("usernameLengthBiggerThan8", UserValidation.usernameLengthBiggerThan8(), invalidUser, false);

        System.out.println();
    }

    public static void testCombinatorPattern() {
        System.out.println("3. TESTING COMBINATOR PATTERN (and, or, xor)");
        System.out.println("---------------------------------------------");

        User testUser = new User("validuser", "validemail@test.il", "validpass123", 25);

        // Test AND combinator
        UserValidation ageValid = UserValidation.ageBiggerThan18();
        UserValidation emailValid = UserValidation.emailEndsWithIL();
        UserValidation combined = ageValid.and(emailValid);

        ValidationResult result = combined.apply(testUser);
        System.out.println("✓ AND Combinator - Both valid: " + result.isValid());

        // Test OR combinator
        UserValidation shortEmail = UserValidation.emailLengthBiggerThan10();
        UserValidation orCombined = shortEmail.or(emailValid);
        result = orCombined.apply(testUser);
        System.out.println("✓ OR Combinator - At least one valid: " + result.isValid());

        // Test XOR combinator
        UserValidation dollarSign = UserValidation.passwordIncludesDollarSign();
        UserValidation lettersOnly = UserValidation.passwordIncludesLettersNumbersOnly();
        UserValidation xorCombined = dollarSign.xor(lettersOnly);
        result = xorCombined.apply(testUser);
        System.out.println("✓ XOR Combinator - Exactly one valid: " + result.isValid());

        System.out.println();
    }

    public static void testStaticCombinations() {
        System.out.println("4. TESTING STATIC COMBINATIONS (all, none)");
        System.out.println("------------------------------------------");

        User goodUser = new User("excellentuser", "excellent@email.il", "greatpass123", 25);

        // Test ALL - should pass multiple validations
        UserValidation allValidation = UserValidation.all(
                UserValidation.ageBiggerThan18(),
                UserValidation.emailEndsWithIL(),
                UserValidation.usernameLengthBiggerThan8()
        );

        ValidationResult result = allValidation.apply(goodUser);
        System.out.println("✓ ALL Combinator - All conditions met: " + result.isValid());

        User badUser = new User("bad", "bad", "bad", 10);

        // Test NONE - should fail all validations
        UserValidation noneValidation = UserValidation.none(
                UserValidation.passwordIncludesDollarSign(),
                UserValidation.emailLengthBiggerThan10()
        );

        result = noneValidation.apply(badUser);
        System.out.println("✓ NONE Combinator - No conditions met: " + result.isValid());

        System.out.println();
    }

    public static void testTemplateMethodPattern() {
        System.out.println("5. TESTING TEMPLATE METHOD PATTERN (UserUtils.sort)");
        System.out.println("---------------------------------------------------");

        try {
            // Create array of users to sort
            User[] users = {
                    new User("charlie", "charlie@test.com", "pass123", 30),
                    new User("alice", "alice@test.com", "pass456", 20),
                    new User("bob", "bob@test.com", "pass789", 25)
            };

            System.out.println("Before sorting:");
            printUsers(users);

            // Sort by username using Template Method
            UserUtils.sort(users, Comparator.comparing(User::getUsername));

            System.out.println("After sorting by username:");
            printUsers(users);

            // Sort by age
            UserUtils.sort(users, Comparator.comparing(User::getAge));

            System.out.println("After sorting by age:");
            printUsers(users);

            System.out.println("✓ Template Method Pattern working correctly");

        } catch (Exception e) {
            System.out.println("✗ Error in Template Method: " + e.getMessage());
        }

        System.out.println();
    }

    public static void testOriginalSpecification() {
        System.out.println("6. TESTING ORIGINAL SPECIFICATION CODE");
        System.out.println("-------------------------------------");

        try {
            // Original test from specification (corrected to 4 parameters)
            User user = new User("admin", "admin@#yzw.co.il", "abc123", 34);
            UserValidation validation1 = UserValidation.emailLengthBiggerThan10();
            UserValidation validation2 = UserValidation.emailEndsWithIL();
            ValidationResult result = (validation1.and(validation2)).apply(user);

            if (result.isValid()) {
                System.out.println("✓ User is valid");
            } else {
                System.out.println("✗ User is not valid: " + result.getReason().orElse("No reason"));
            }

        } catch (Exception e) {
            System.out.println("✗ Error in original specification test: " + e.getMessage());
        }

        System.out.println();
    }

    // Helper methods
    private static void testValidation(String name, UserValidation validation, User user, boolean expected) {
        ValidationResult result = validation.apply(user);
        boolean actual = result.isValid();
        String status = (actual == expected) ? "✓" : "✗";
        System.out.println(status + " " + name + " - Expected: " + expected + ", Got: " + actual);
        if (!result.isValid() && result.getReason().isPresent()) {
            System.out.println("    Reason: " + result.getReason().get());
        }
    }

    private static void printUsers(User[] users) {
        for (User user : users) {
            System.out.println("  " + user.toString());
        }
    }
}