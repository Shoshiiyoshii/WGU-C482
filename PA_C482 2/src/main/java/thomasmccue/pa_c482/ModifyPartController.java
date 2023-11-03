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

    /**
     * This is very similar to the setProduct() method in the ModifyProductController.
     * This method is here so that the ModifyPartController can be passed the specific part
     * that was selected on the Main Screen, and all the methods in this controller class can manipulate it.
     * @param part
     */
    public void setPart(Part part){
        this.part = part;
    }

    /**
     *  This is very similar to the fillFields() method in the ModifyProductController.
     *  This method is called from the MainScreenController so that when the window is opened
     *  the idField, nameField, invField, priceField, maxField, minField, and the
     *  machineIdField/companyNameField are all auto-populated with the values of the selected part.
     *  In order to correctly fill the machineId/companyNameField (i.e. changingLabel), an if/else
     *  statement checks to see if the part that has been passed is an instance of an InHouse Part
     *  or an Outsourced Part. Then the radio button and Machine ID/Company Name label are set as needed.
     * @throws IOException
     */
    public void fillFields() throws IOException{
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        invField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));

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

    /**
     * This method is called when the radio buttons are changed, it causes the
     * changingLabel to switch between "Machine ID" and "Company Name" accordingly.
     * @param event
     * @throws IOException{
     */
    public void selectSource(ActionEvent event) throws IOException{
        if(inHouseRadioButton.isSelected()){
            changingLabel.setText("Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            changingLabel.setText("Company Name");

        }
    }

    /**
     * This method works very similarly to the saveClicked method in the ModifyProductController,
     * and the saveClicked method in the AddProductController. The key difference is that
     * this method also checks to see which radio button is selected, since there are two possible
     * types of part. It saves the part as either an InHouse or Outsourced part depending on which
     * radio button has been selected, and sets either the Company name or Machine ID accordingly.
     * There is also a very similar saveClicked method in the AddPartController;
     * @param event
     * @throws IOException
     */
    public void saveClicked(ActionEvent event) throws IOException {
       ObservableList<Part> allParts = Inventory.getAllParts();
       int index = allParts.indexOf(part);

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
                errorMessage.setText("Please enter a positive number with decimal point for Price(i.e. 19.00, 100.00, 0.97 etc.");
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

                InHouse updatedInHouse = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.updatePart(index, updatedInHouse);

            } else if (outsourcedRadioButton.isSelected()) {
                String companyName = machineIdField.getText();
                Outsourced updatedOutsourced = new Outsourced(id, name, price, inv, min, max, companyName);

                Inventory.updatePart(index, updatedOutsourced);
            }

            errorMessage.setText("");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter valid values. Id, Inv, Min, Max and Machine ID must be positive integers. \n" +
                    "Price must be a number with a decimal point.");
        }
    }

    /**
     * When the cancel button is clicked, the window closes and the user is returned to the Main Screen.
     * @param event
     * @throws IOException
     */
    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This is an auto-generated initialize method by intelliJ CE, it's purpose is to
     * initialize the controller and set up the UI for the modifyPart.fxml file.
     * I did not end up editing it here.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
