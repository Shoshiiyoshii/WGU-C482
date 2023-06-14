package thomasmccue.pa_c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {
    @FXML
    private TextField idField, nameField, invField, priceField, maxField, minField, searchField;
    @FXML
    private Button addButton, saveButton, cancelButton, rapButton;
    @FXML
    private AnchorPane modifyProductPane;

    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    // Implement the necessary methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic goes here
    }

    // Other methods and fields
}
