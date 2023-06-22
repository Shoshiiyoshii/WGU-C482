package thomasmccue.pa_c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class AddProductController implements Initializable {
    @FXML
    private TextField idField, nameField, invField, priceField, maxField, minField, searchField;
    @FXML
    private Button addButton, saveButton, cancelButton, rapButton;
    @FXML
    private Label errorMessage;
    @FXML
    private AnchorPane addProductPane;
    @FXML
    private TableView<Part> associatedPartTable1, associatedPartTable2;
    @FXML
    private TableColumn<Part, Integer> partIdColumn1, partInvColumn1, partIdColumn2, partInvColumn2;
    @FXML
    private TableColumn<Part, String> partNameColumn1, partNameColumn2;
    @FXML
    private TableColumn<Part, Double> partPriceColumn1, partPriceColumn2;

    private Product newProduct;

    @FXML
    public void saveClicked(ActionEvent event) throws IOException{
        //get user inputs from corresponding text fields and save as Strings
        String name = nameField.getText();
        //set product name
        newProduct.setName(name);

        String invInput = invField.getText();
        String priceInput = priceField.getText();
        String minInput = minField.getText();
        String maxInput = maxField.getText();

        //try to convert inv, min and max Strings from user input into Integers, and price into a Double
        try {
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

            newProduct.setStock(inv);
            newProduct.setPrice(price);
            newProduct.setMax(max);
            newProduct.setMin(min);

            // Clear the error message
            errorMessage.setText("");
            //update main screen product table FIXME NEXT

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter valid values. Id, Inv, Min, and Max must be positive integers. \n" +
                    "Price must be a number with two decimal points.");
        }
    }

    public void addPartClicked(ActionEvent event) throws IOException{
            //get selected part from top part table
            SelectionModel<Part> selectionModel = associatedPartTable1.getSelectionModel();
            Part selectedPart = selectionModel.getSelectedItem();
            newProduct.addAssociatedPart(selectedPart);

            //populate bottom part table with selected part
            associatedPartTable2.setItems(newProduct.getAllAssociatedParts());
            partIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
            partInvColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void rapButtonClicked(ActionEvent event) throws IOException{
        //hold the selected part item
        SelectionModel<Part> selectionModel = associatedPartTable2.getSelectionModel();
        Part partToRemove = selectionModel.getSelectedItem();
        //attempt to delete the selected part item from associated parts
        boolean deleted = newProduct.deleteAssociatedPart(partToRemove);
        //if deleted successfully, refresh table view to reflect current associated parts
        if(deleted){
            associatedPartTable2.refresh();
        //if not deleted successfully, display error message
        } else {
            errorMessage.setText("The selected part could not be removed from associated parts.");
        }
    }

    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // Implement the necessary methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create a random number to serve as the id for the new product
        Random random = new Random();
        int randomId;
        //try different random positive numbers less than a million against the id's that already exist in the system
        //keep trying until it is confirmed that the randomId is not already assigned to either a product or a part
        do {
            randomId = random.nextInt(1000000);
        } while (Inventory.lookupPart(randomId) != null || Inventory.lookupProduct(randomId) != null);
        //populate the ID field with the new ID number
        idField.setText(String.valueOf(randomId));

        //create an empty Product object to be manipulated as the form is filled out
        newProduct = new Product(randomId, "",0.00,0, 0, 0);

        //populate the top part table with all available parts
        associatedPartTable1.setItems(Inventory.getAllParts());
        partIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}




