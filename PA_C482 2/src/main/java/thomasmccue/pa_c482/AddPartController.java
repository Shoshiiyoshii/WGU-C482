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
import java.util.Random;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    @FXML
    private RadioButton inHouseRadioButton, outsourcedRadioButton;
    @FXML
    private TextField idField, nameField, invField, priceField, maxField, minField, machineIdField;
    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private Label MachineID, errorMessage;
    @FXML
    private AnchorPane addPartPane;

    /**
     * This is the same as the selectSource() method found in the ModifyPartController.
     * This method is called when the radio buttons are changed, it causes the
     * changingLabel to switch between "Machine ID" and "Company Name" accordingly.
     * @param event
     * @throws IOException{
     */
    @FXML
    public void selectSource(ActionEvent event) throws IOException{
        if(inHouseRadioButton.isSelected()){
            MachineID.setText("Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            MachineID.setText("Company Name");
        }
    }

    /**
     * This method works very similarly to the saveClicked method in the ModifyProductController,
     * and the saveClicked method in the AddProductController. The key difference is that
     * this method also checks to see which radio button is selected, since there are two possible
     * types of part. It creates a new part as either an InHouse or Outsourced part depending on which
     * radio button has been selected, and sets either the Company name or Machine ID accordingly.
     * There is also a very similar saveClicked method in the ModifyPartController;
     * @param event
     * @throws IOException
     */
    @FXML
    public void saveClicked(ActionEvent event) {
        String idGenerated = idField.getText();
        String name = nameField.getText();
        String invInput = invField.getText();
        String priceInput = priceField.getText();
        String minInput = minField.getText();
        String maxInput = maxField.getText();

        try {
            int id = Integer.parseInt(idGenerated);
            int inv = Integer.parseInt(invInput);
            double price = Double.parseDouble(priceInput);
            int min = Integer.parseInt(minInput);
            int max = Integer.parseInt(maxInput);

            if (inv < 0 || min < 0 || max < 0) {
                errorMessage.setText("Please enter positive integers in the Inv, Min, and Max fields.");
                return;
            }
            if (price < 0.0) {
                errorMessage.setText("Please enter a positive number with two decimal point for Price(i.e. 19.00, 100.00, 0.97 etc.");
                return;
            }
            if (min >= max) {
                errorMessage.setText("Min must be smaller than Max.");
                return;
            }
            if (inv < min || inv > max) {
                errorMessage.setText("Inv must be between Min and Max.");
                return;
            }
            if (inHouseRadioButton.isSelected()) {
                String machineIdInput = machineIdField.getText();
                int machineId = Integer.parseInt(machineIdInput);

                if (machineId < 0) {
                    errorMessage.setText("Please enter a valid Machine ID as a positive integer.");
                    return;
                }

                InHouse newInHouse = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.addPart(newInHouse);

            } else if (outsourcedRadioButton.isSelected()) {
                String companyName = machineIdField.getText();
                Outsourced newOutsourced = new Outsourced(id, name, price, inv, min, max, companyName);

                Inventory.addPart(newOutsourced);
            }
            errorMessage.setText("");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter valid values. Id, Inv, Min, and Max must be positive integers. \n" +
                    "Price must be a number with two decimal points.");
        }
    }

    /**
     * When the cancel button is clicked the window closes and returns the user the Main Screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * On the window being opened, a random positive integer smaller than 1000000 is generated.
     * That number is tested against existing ID numbers for both products and parts, and a new
     * random positive integer smaller than 1000000 is generated until one is made that doesn't
     * already exist as an ID. That ID number is then shown in the idField, which is not editable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random random = new Random();
        int randomId;

        do{
            randomId = random.nextInt(1000000);
        } while (Inventory.lookupPart(randomId) != null || Inventory.lookupProduct(randomId) != null);

        idField.setText(String.valueOf(randomId));
    }
}
