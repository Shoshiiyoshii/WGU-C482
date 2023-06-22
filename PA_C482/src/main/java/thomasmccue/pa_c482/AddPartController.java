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

    @FXML
    public void selectSource(ActionEvent event){
        if(inHouseRadioButton.isSelected()){
            MachineID.setText("Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            MachineID.setText("Company Name");

        }
    }
    @FXML
    public void saveClicked(ActionEvent event) {
        //get user inputs from corresponding text fields and save as Strings
        String idGenerated = idField.getText();
        String name = nameField.getText();
        String invInput = invField.getText();
        String priceInput = priceField.getText();
        String minInput = minField.getText();
        String maxInput = maxField.getText();

        //try to convert inv, min and max Strings from user input into Integers, and price into a Double
        try {
            int id = Integer.parseInt(idGenerated);
            int inv = Integer.parseInt(invInput);
            double price = Double.parseDouble(priceInput);
            int min = Integer.parseInt(minInput);
            int max = Integer.parseInt(maxInput);

            //check to make sure all numerical inputs are positive
            if (inv < 0 || min < 0 || max < 0) {
                errorMessage.setText("Please enter positive integers in the Inv, Min, and Max fields.");
                return;
            }
            if (price < 0.0) {
                errorMessage.setText("Please enter a positive number with two decimal point for Price(i.e. 19.00, 100.00, 0.97 etc.");
                return;
            }

            //check to make sure min is less than max
            if (min >= max) {
                errorMessage.setText("Min must be smaller than Max.");
                return;
            }

            //check that the inv input is between the min and the max inputs
            if (inv < min || inv > max) {
                errorMessage.setText("Inv must be between Min and Max.");
                return;
            }

            //create and add the correct type of part to Inventory depending on which radio button has been selected
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
                Outsourced newOutsourced = new Outsourced(id, name, price, inv, min, max);
                newOutsourced.setCompanyName(companyName);

                Inventory.addPart(newOutsourced);
            }

            // Clear the error message
            errorMessage.setText("");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter valid values. Id, Inv, Min, and Max must be positive integers. \n" +
                    "Price must be a number with two decimal points.");
        }
    }

    @FXML
    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create a random number to serve as the id for the new part
        Random random = new Random();
        int randomId;
        //try different random positive numbers less than a million against the id's that already exist in the system
        //keep trying until it is confirmed that the randomId is not already assigned to either a product or a part
        do{
            randomId = random.nextInt(1000000);
        } while (Inventory.lookupPart(randomId) != null || Inventory.lookupProduct(randomId) != null);
        //populate the ID field with the new ID number
        idField.setText(String.valueOf(randomId));
    }

}
