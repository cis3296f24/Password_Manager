import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginAuthenticationForTextFile {
    private static final String USERNAME_PATTERN = "USER: ";
    private static final String PASSWORD_PATTERN = "PASS: ";
    private static final String KEY_PATTERN = "KEY: ";
    private static final String QUESTION1_PATTERN = "QUESTION1: ";
    private static final String QUESTION2_PATTERN = "QUESTION2: ";
    private static final String ANSWER1_PATTERN = "ANSWER1: ";
    private static final String ANSWER2_PATTERN = "ANSWER2: ";

    private final UsernameEncryption EncryptUsername = new UsernameEncryption(); // Instance of UsernameEncryption
    private final PasswordEncryptionForExistingLogin encryptLoginPassword = new PasswordEncryptionForExistingLogin(); // Instance of PasswordEncryptionForExistingLogin

    public boolean AuthenticationForTextFile(String usernameInput, String passwordInput) throws Exception {
        if (usernameInput == null || passwordInput == null) {
            System.out.println("Error: empty username or password ");
            return false;
        }

        List<String> AccountInfo;

        // Encrypt the username input
        String encryptedLoginUsername = EncryptUsername.EncryptedUsername(usernameInput);
        //System.out.println("Encrypted Username: " + encryptedLoginUsername);

        // parse the txt file for the username
        AccountInfo = parseFile("StoredCredentials.txt", encryptedLoginUsername);
        if (AccountInfo == null || AccountInfo.size() < 3)
            return false;
        String AssociatedUsername = AccountInfo.get(0);
        String AssociatedPassword = AccountInfo.get(1);
        String AssociatedKey = AccountInfo.get(2);

        // Encrypt the password input and get the result
        String encryptedLoginPassword = encryptLoginPassword.EncryptedLoginPassword(passwordInput, AssociatedKey);

        /*
        //Testing output
        System.out.println("Associated Username: " + AssociatedUsername);
        System.out.println("Associated Password: " + AssociatedPassword);
        System.out.println("Associated Key: " + AssociatedKey);
        System.out.println("Encrypted Username Input: " + encryptedLoginUsername);
        System.out.println("Encrypted Password Input: " + encryptedLoginPassword);

         */

        if (!AssociatedUsername.equals(encryptedLoginUsername))
            return false;

        return AssociatedPassword.equals(encryptedLoginPassword);
    }


    public List<String> parseFile(String fileName, String usernameSearched) {
        List<String> accountInfo = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                int usernameStartIndex = line.indexOf(USERNAME_PATTERN);
                int passwordStartIndex = line.indexOf(PASSWORD_PATTERN);
                int keyStartIndex = line.indexOf(KEY_PATTERN);
                int question1StartIndex = line.indexOf(QUESTION1_PATTERN);
                int question2StartIndex = line.indexOf(QUESTION2_PATTERN);
                int answer1StartIndex = line.indexOf(ANSWER1_PATTERN);
                int answer2StartIndex = line.indexOf(ANSWER2_PATTERN);
                if (passwordStartIndex == 0 && usernameStartIndex > passwordStartIndex && keyStartIndex > usernameStartIndex && question1StartIndex > keyStartIndex) {
                    System.out.println("|" + usernameSearched + "|");

                    String password = line.substring(passwordStartIndex + PASSWORD_PATTERN.length(), usernameStartIndex).replace(",", "").trim();
                    String username = line.substring(usernameStartIndex + USERNAME_PATTERN.length(), keyStartIndex).replace(",", "").trim();
                    String key = line.substring(keyStartIndex + KEY_PATTERN.length(), question1StartIndex).replace(",", "").trim();
                    String question1 = line.substring(question1StartIndex + QUESTION1_PATTERN.length(), answer1StartIndex).replace(",", "").trim();
                    String answer1 = line.substring(answer1StartIndex + ANSWER1_PATTERN.length(), question2StartIndex).replace(",", "").trim();
                    String question2 = line.substring(question2StartIndex + QUESTION2_PATTERN.length(), answer2StartIndex).replace(",", "").trim();
                    String answer2 = line.substring(answer2StartIndex + ANSWER2_PATTERN.length()).replace(",", "").trim();

                    /*
                    System.out.println("Parsed Username: |" + username + "|");
                    System.out.println("Parsed Password: |" + password + "|");
                    System.out.println("Parsed Key: |" + key + "|");

                     */

                    if (username.equals(usernameSearched)) {
                       // System.out.println("username found");
                        accountInfo.add(username);
                        accountInfo.add(password);
                        accountInfo.add(key);
                        accountInfo.add(question1);
                        accountInfo.add(answer1);
                        accountInfo.add(question2);
                        accountInfo.add(answer2);
                        return accountInfo;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println("Login Fail, username not found");
        return null; // Username was not found in .txt file
    }

}