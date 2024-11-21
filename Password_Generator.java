import java.security.SecureRandom;
import java.util.Scanner;

public class Password_Generator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+<>?";

    private static final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS + SYMBOLS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateStrongPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length should be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder(length);

        // Ensure the password contains at least one character from each category
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));

        // Fill the remaining characters randomly from all categories
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(RANDOM.nextInt(ALL_CHARACTERS.length())));
        }

        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        StringBuilder shuffled = new StringBuilder(input.length());
        input.chars()
                .mapToObj(c -> (char) c)
                .sorted((a, b) -> RANDOM.nextInt(3) - 1)
                .forEach(shuffled::append);
        return shuffled.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the desired password length: ");
        int length = scanner.nextInt();

        try {
            String strongPassword = generateStrongPassword(length);
            System.out.println("Generated Strong Password: " + strongPassword);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
