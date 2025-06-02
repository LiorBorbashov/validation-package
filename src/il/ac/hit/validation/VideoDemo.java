package il.ac.hit.validation;

import java.util.Comparator;

/**
 * Demo for video recording.
 * Shows all combinator combinations (AND, OR, XOR, ALL, NONE) and Template Method sorting.
 */
public class VideoDemo {

    public static void main(String[] args) {
        System.out.println("=== COMPLETE VALIDATION LIBRARY DEMONSTRATION ===\n");

        // Demo all individual validation functions
        demoAllValidationFunctions();

        // Demo all boolean combinations
        demoBooleanCombinations();

        // Demo NONE and ALL methods
        demoNoneAndAllMethods();

        // Demo Factory Method Pattern
        demoFactoryPattern();

        // Demo Template Method (sorting)
        demoTemplateMethodPattern();

        System.out.println("✅ All Design Patterns and Functions Successfully Implemented!");
    }

    public static void demoAllValidationFunctions() {
        System.out.println("1. ALL VALIDATION FUNCTIONS:");
        System.out.println("----------------------------");

        // Test user with valid values for most validations
        User testUser = new User("validuser123", "validuser@company.il", "password123$", 25);

        System.out.println("Testing user: " + testUser.getUsername() + " (" + testUser.getEmail() + ")\n");

        // Test each validation function
        testFunction("emailEndsWithIL", UserValidation.emailEndsWithIL(), testUser);
        testFunction("emailLengthBiggerThan10", UserValidation.emailLengthBiggerThan10(), testUser);
        testFunction("passwordLengthBiggerThan8", UserValidation.passwordLengthBiggerThan8(), testUser);

        // Test with letters/numbers only password
        User lettersNumbersUser = new User("testuser", "test@company.il", "password123", 25);
        testFunction("passwordIncludesLettersNumbersOnly", UserValidation.passwordIncludesLettersNumbersOnly(), lettersNumbersUser);

        testFunction("passwordIncludesDollarSign", UserValidation.passwordIncludesDollarSign(), testUser);
        testFunction("passwordIsDifferentFromUsername", UserValidation.passwordIsDifferentFromUsername(), testUser);
        testFunction("ageBiggerThan18", UserValidation.ageBiggerThan18(), testUser);
        testFunction("usernameLengthBiggerThan8", UserValidation.usernameLengthBiggerThan8(), testUser);

        System.out.println();
    }

    public static void demoBooleanCombinations() {
        System.out.println("2. BOOLEAN COMBINATIONS (AND, OR, XOR):");
        System.out.println("----------------------------------------");

        // Create users for all combinations
        User bothValid = new User("longuser123", "user@test.il", "password", 25);      // Email=T, Age=T
        User emailValid = new User("longuser123", "user@test.il", "password", 15);     // Email=T, Age=F
        User ageValid = new User("longuser123", "user@test.com", "password", 25);      // Email=F, Age=T
        User bothInvalid = new User("longuser123", "user@test.com", "password", 15);   // Email=F, Age=F

        System.out.println("Testing: Email ends with IL + Age > 18\n");

        // AND combinations
        System.out.println("AND Results:");
        testCombination("AND(T,T)", UserValidation.emailEndsWithIL().and(UserValidation.ageBiggerThan18()), bothValid);
        testCombination("AND(T,F)", UserValidation.emailEndsWithIL().and(UserValidation.ageBiggerThan18()), emailValid);
        testCombination("AND(F,T)", UserValidation.emailEndsWithIL().and(UserValidation.ageBiggerThan18()), ageValid);
        testCombination("AND(F,F)", UserValidation.emailEndsWithIL().and(UserValidation.ageBiggerThan18()), bothInvalid);

        // OR combinations
        System.out.println("\nOR Results:");
        testCombination("OR(T,T)", UserValidation.emailEndsWithIL().or(UserValidation.ageBiggerThan18()), bothValid);
        testCombination("OR(T,F)", UserValidation.emailEndsWithIL().or(UserValidation.ageBiggerThan18()), emailValid);
        testCombination("OR(F,T)", UserValidation.emailEndsWithIL().or(UserValidation.ageBiggerThan18()), ageValid);
        testCombination("OR(F,F)", UserValidation.emailEndsWithIL().or(UserValidation.ageBiggerThan18()), bothInvalid);

        // XOR combinations
        System.out.println("\nXOR Results:");
        testCombination("XOR(T,T)", UserValidation.emailEndsWithIL().xor(UserValidation.ageBiggerThan18()), bothValid);
        testCombination("XOR(T,F)", UserValidation.emailEndsWithIL().xor(UserValidation.ageBiggerThan18()), emailValid);
        testCombination("XOR(F,T)", UserValidation.emailEndsWithIL().xor(UserValidation.ageBiggerThan18()), ageValid);
        testCombination("XOR(F,F)", UserValidation.emailEndsWithIL().xor(UserValidation.ageBiggerThan18()), bothInvalid);

        System.out.println();
    }

    public static void demoNoneAndAllMethods() {
        System.out.println("3. NONE AND ALL METHODS:");
        System.out.println("------------------------");

        // Test ALL method
        System.out.println("Testing ALL method:");
        User allTestUser = new User("validuser123", "valid@company.il", "password123", 25);
        UserValidation allValidation = UserValidation.all(
                UserValidation.emailEndsWithIL(),
                UserValidation.ageBiggerThan18(),
                UserValidation.usernameLengthBiggerThan8()
        );
        testCombination("ALL(T,T,T)", allValidation, allTestUser);

        // Test with one failing
        User oneFailUser = new User("short", "fail@company.il", "password123", 25);
        testCombination("ALL(F,T,T)", allValidation, oneFailUser);

        // Test NONE method
        System.out.println("\nTesting NONE method:");
        User nonePass = new User("user", "user@test.com", "pass", 15);        // Both fail -> NONE should pass
        User onePass = new User("user", "user@test.il", "pass", 15);          // Email passes -> NONE should fail
        User bothPass = new User("user", "user@test.il", "pass", 25);         // Both pass -> NONE should fail

        UserValidation noneValidation = UserValidation.none(
                UserValidation.emailEndsWithIL(),
                UserValidation.ageBiggerThan18()
        );

        testCombination("NONE(F,F)", noneValidation, nonePass);
        testCombination("NONE(T,F)", noneValidation, onePass);
        testCombination("NONE(T,T)", noneValidation, bothPass);

        System.out.println();
    }

    public static void demoFactoryPattern() {
        System.out.println("4. FACTORY METHOD PATTERN:");
        System.out.println("--------------------------");

        // Create different user types
        User basicUser = UserFactory.createUser("basic", "john", "john@company.il", "password123", 25);
        User premiumUser = UserFactory.createUser("premium", "jane", "jane@company.il", "password456", 30);
        User platinumUser = UserFactory.createUser("platinum", "admin", "admin@company.il", "password789", 35);

        System.out.println("Basic user created: " + basicUser.getClass().getSimpleName());
        System.out.println("Premium user created: " + premiumUser.getClass().getSimpleName());
        System.out.println("Platinum user created: " + platinumUser.getClass().getSimpleName());

        System.out.println();
    }

    public static void demoTemplateMethodPattern() {
        System.out.println("5. TEMPLATE METHOD PATTERN (SORTING):");
        System.out.println("-------------------------------------");

        User[] users = {
                new User("charlie", "charlie@test.il", "password", 30),
                new User("alice", "alice@test.il", "password", 20),
                new User("bob", "bob@test.il", "password", 25)
        };

        System.out.println("Original order:");
        printUsers(users);

        // Sort by username (Template Method with different comparator)
        UserUtils.sort(users, Comparator.comparing(User::getUsername));
        System.out.println("\nSorted by username:");
        printUsers(users);

        // Sort by age (Template Method with different comparator)
        UserUtils.sort(users, Comparator.comparing(User::getAge));
        System.out.println("\nSorted by age:");
        printUsers(users);

        System.out.println();
    }

    // Helper methods
    private static void testFunction(String functionName, UserValidation validation, User user) {
        ValidationResult result = validation.apply(user);
        String status = result.isValid() ? "✅ PASS" : "❌ FAIL";
        System.out.println(functionName + ": " + status);
    }

    private static void testCombination(String description, UserValidation validation, User user) {
        ValidationResult result = validation.apply(user);
        String status = result.isValid() ? "✅ PASS" : "❌ FAIL";
        System.out.println(description + ": " + status);
    }

    private static void printUsers(User[] users) {
        for (User user : users) {
            System.out.println("  " + user.getUsername() + " (age: " + user.getAge() + ")");
        }
    }
}