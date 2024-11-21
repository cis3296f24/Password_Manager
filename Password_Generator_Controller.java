import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;

public class Password_Generator_Controller {
    @FXML
    private Label generatorLabel; // To display generated password or errors
    @FXML
    private TextField generatorInput; // To capture the input password length

    @FXML
    public void BackToDashClicked(ActionEvent event) {
        switchToDashboardScene(event);
    }

    private void switchToDashboardScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardScene.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This is the button click handler
    @FXML
    public void passwordGeneratorButtonClicked(ActionEvent event) {
        // Get the input from the TextField (user's desired password length)
        String lengthText = generatorInput.getText();

        try {
            // Parse the input to an integer
            int length = Integer.parseInt(lengthText);

            // Validate password length
            if (length < 8) {
                throw new IllegalArgumentException("Password length should be at least 8 characters.");
            }

            // Generate password using Password_Generator class
            String generatedPassword = Password_Generator.generateStrongPassword(length);

            // Display generated password in the label
            generatorLabel.setText(generatedPassword);

        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric or empty)
            generatorLabel.setText("Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Handle invalid length (less than 8)
            generatorLabel.setText(e.getMessage());
        }
    }

    private void passwordGenerator(ActionEvent event) {
        // Get the input from the TextField (user's desired password length)
        String lengthText = generatorInput.getText();

        try {
            // Parse the input to an integer
            int length = Integer.parseInt(lengthText);

            // Validate password length
            if (length < 8) {
                throw new IllegalArgumentException("Password length should be at least 8 characters.");
            }

            // Generate password using Password_Generator class
            String generatedPassword = Password_Generator.generateStrongPassword(length);

            // Display generated password in the label
            generatorLabel.setText(generatedPassword);

        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric or empty)
            generatorLabel.setText("Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Handle invalid length (less than 8)
            generatorLabel.setText(e.getMessage());
        }
    }
}
