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


public class ModifyProductController implements Initializable {
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
    private Product product;

    /**
     * This method is here so that the ModifyProductController can be passed the specific product
     * that was selected on the Main Screen, and all the methods in this controller class can manipulate it.
     * @param product
     */
    public void setProduct(Product product){
        this.product = product;
    }

    /**
     * This method is called when the Modify button under the Product table on the Main Screen is clicked.
     * When the Modify Product window is opened idField, nameField, invField, priceField, maxField, minField, machineIdField
     * are all auto-populated with the values of the selected part.
     * @throws IOException
     */
    public void fillFields() throws IOException {
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        invField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        maxField.setText(String.valueOf(product.getMax()));
        minField.setText(String.valueOf(product.getMin()));

        //populate bottom table with any associated parts
        associatedPartTable2.setItems(product.getAllAssociatedParts());
        partIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This method works exactly the same as the partSearch method in the MainScreenController.
     * @see MainScreenController
     * @param event
     * @throws IOException
     */
    @FXML
    public void partSearch(ActionEvent event) throws IOException {
        String search = searchField.getText();
        errorMessage.setText("");

        if (search.isEmpty()) {
            associatedPartTable1.setItems(Inventory.getAllParts());
        } else if (search.matches("\\d+")) {
            int idSearched = Integer.parseInt(search);
            Part found = Inventory.lookupPart(idSearched);
            if (found != null) {
                ObservableList<Part> foundParts = FXCollections.observableArrayList();
                foundParts.add(found);
                associatedPartTable1.setItems(foundParts);
            } else {
                errorMessage.setText("No part with ID \"" + idSearched + "\" found.");
                searchField.clear();
                associatedPartTable1.setItems(Inventory.getAllParts());
            }
        } else {
            ObservableList<Part> foundParts = Inventory.lookupPart(search);
            if (foundParts != null) {
                associatedPartTable1.setItems(foundParts);
            } else {
                errorMessage.setText("No part containing \"" + search + "\" found.");
                searchField.clear();
                associatedPartTable1.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * This method is called when the save button in the Modify Product window is clicked.
     * First it finds the index of the modified product in the allProducts ObservableList in the Inventory class.
     * That index is saved as an int. Then the method gets all user inputs from corresponding text fields and saves
     * them as Strings. The product name is set immediately because no manipulation is needed, it is already a String.
     * Then the method tries to convert inv, min and max Strings from user input into Integers, and price into a Double.
     * If any of the user input cannot be converted into the appropriate types, a catch statement at the bottom executes.
     * A series of if-statements check to make sure all numerical inputs are positive, and print errors to the UI if not.
     * More if-statements check to make sure min is less than max and the inv input is between the min and the max inputs,
     * printing appropriate errors to the UI as needed. Once all the numerical values pass those if-statments, products
     * setters are used and the values for inv, min, max, and price are updated. Finally, the Inventory.updateProduct() method
     * is passed the index of the original product and the updated instance of that product, and all modifications are saved.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void saveClicked(ActionEvent event) throws IOException{
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        int index = allProducts.indexOf(product);

        String name = nameField.getText();
        product.setName(name);

        String invInput = invField.getText();
        String priceInput = priceField.getText();
        String minInput = minField.getText();
        String maxInput = maxField.getText();

        try {
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

            product.setStock(inv);
            product.setPrice(price);
            product.setMax(max);
            product.setMin(min);

            errorMessage.setText("");

            Inventory.updateProduct(index, product);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter valid values. Id, Inv, Min, and Max must be positive integers. \n" +
                    "Price must be a number with two decimal points.");
        }
    }

    /**
     * This method is called with the Add button under the top Part table is clicked.
     * First it gets the selected part from top part table, then it checks to see if the selected part is
     * already associated with this product. If it is, an error message is printed to the UI so that the
     * same part will not be associated with a product multiple times. If not, the part is added to the
     * associatedParts Observable list for this instance of this product and the bottom part table is populated
     * with the updated associatedParts list.
     * @param event
     * @throws IOException
     */
    public void addPartClicked(ActionEvent event) throws IOException{
        SelectionModel<Part> selectionModel = associatedPartTable1.getSelectionModel();
        Part selectedPart = selectionModel.getSelectedItem();

        if(product.getAllAssociatedParts().contains(selectedPart)){
            errorMessage.setText("This part is already associated with this product");
        }else {
            product.addAssociatedPart(selectedPart);
            associatedPartTable2.setItems(product.getAllAssociatedParts());
            partIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
            partInvColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * This method is called when the Remove Associated Part button under the lower Part table is clicked.
     * It prints out a message asking the user to confirm that the want to remove the selected part. If they
     * click the Remove Associated Part button again the part is removed using the deleteAssociatedPart() method
     * of the Product class. If the delete executes successfully, the associated parts table refreshes. If the
     * delete is not successful, an error message is printed to the UI.
     * @param event
     * @throws IOException
     */
    public void rapButtonClicked(ActionEvent event) throws IOException{
        SelectionModel<Part> selectionModel = associatedPartTable2.getSelectionModel();
        Part partToRemove = selectionModel.getSelectedItem();

        if (errorMessage.getText().equals("Are you sure you want to remove this part? Part ID: " + partToRemove.getId()
                +"\nIf yes, click the Remove Associated Part button again.")) {

            boolean deleted = product.deleteAssociatedPart(partToRemove);
            if (deleted) {
                associatedPartTable2.refresh();
            } else {
                errorMessage.setText("The selected part could not be removed from associated parts.");
            }
            associatedPartTable2.setItems(product.getAllAssociatedParts());

            errorMessage.setText("");
        } else {
            errorMessage.setText("Are you sure you want to remove this part? Part ID: " + partToRemove.getId()
                    +"\nIf yes, click the Remove Associated Part button again.");
        }
    }

    /**
     * When the Cancel button is clicked, the window closes.
     * @param event
     * @throws IOException
     */
    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * When the window is opened the top Parts table is populated with the allParts observable list
     * from the Inventory class.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        associatedPartTable1.setItems(Inventory.getAllParts());
        partIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}