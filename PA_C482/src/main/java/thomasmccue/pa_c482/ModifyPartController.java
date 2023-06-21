package thomasmccue.pa_c482;

import javafx.collections.ObservableList;
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

public class ModifyPartController implements Initializable {
    @FXML
    private RadioButton inHouseRadioButton, outsourcedRadioButton;
    @FXML
    private TextField idField, nameField, invField, priceField, maxField, minField, machineIdField;
    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private Label changingLabel, errorMessage;
    @FXML
    private AnchorPane modifyPartPane;

    private Part part;

    public void setPart(Part part){
        this.part = part;
    }

    public void fillFields(){
        //when the window is opened idField, nameField, invField, priceField, maxField, minField, machineIdField
        //are all auto-populated with the values of the selected part
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        invField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));
        //check to see if the part that has been passed is an InHouse Part or an Outsourced Part
        //and set the radio button and Machine ID/Company Name as needed
        if(part instanceof InHouse){
            InHouse inHousePart = (InHouse) part;
            inHouseRadioButton.setSelected(true);
            changingLabel.setText("Machine ID");
            machineIdField.setText(String.valueOf(inHousePart.getMachineId()));
        }else if(part instanceof Outsourced){
            Outsourced outsourcedPart = (Outsourced) part;
            outsourcedRadioButton.setSelected(true);
            changingLabel.setText("Company Name");
            machineIdField.setText(outsourcedPart.getCompanyName());
        }
    }
    public void selectSource(ActionEvent event){
        if(inHouseRadioButton.isSelected()){
            changingLabel.setText("Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            changingLabel.setText("Company Name");

        }
    }

    public void saveClicked(ActionEvent event) throws IOException {
       ObservableList<Part> allParts = Inventory.getAllParts();
       int index = allParts.indexOf(part);
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

                InHouse updatedInHouse = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.updatePart(index, updatedInHouse);

            } else if (outsourcedRadioButton.isSelected()) {
                String companyName = machineIdField.getText();
                Outsourced updatedOutsourced = new Outsourced(id, name, price, inv, min, max);
                updatedOutsourced.setCompanyName(companyName);

                Inventory.updatePart(index, updatedOutsourced);
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

    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    // Implement the necessary methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Other methods and fields
}
