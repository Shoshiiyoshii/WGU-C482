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

public class modifyPartController implements Initializable {
    @FXML
    private RadioButton inHouseRadioButton, outsourcedRadioButton;
    @FXML
    private TextField idField, nameField, invField, priceField, maxField, minField, machineIdField;
    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private Label MachineID;
    @FXML
    private AnchorPane modifyPartPane;

    public void selectSource(ActionEvent event){
        if(inHouseRadioButton.isSelected()){
            MachineID.setText("Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            MachineID.setText("Company Name");

        }
    }

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
