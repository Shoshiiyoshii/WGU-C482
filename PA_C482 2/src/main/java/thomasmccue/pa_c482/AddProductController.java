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

    /**
     * This partSearch method is the same as the partSearch() methods in the MainScreenController and the
     * ModifyProductController.
     *
     * @param event
     * @throws IOException
     * @see MainScreenController
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
     * This saveClicked method is similar to the saveClicked method in the ModifyProductController,
     * the empty Product object that was made on initialization is updated with the data entered by the user.
     * The empty Product is then updated in the allProducts ObservableList in Inventory.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void saveClicked(ActionEvent event) throws IOException {
        String name = nameField.getText();
        newProduct.setName(name);

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

            newProduct.setStock(inv);
            newProduct.setPrice(price);
            newProduct.setMax(max);
            newProduct.setMin(min);

            errorMessage.setText("");

            Inventory.addProduct(newProduct);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter valid values. Id, Inv, Min, and Max must be positive integers. \n" +
                    "Price must be a number with two decimal points.");
        }
    }

    /**
     * This method works identically to the addPartClicked method in the ModifyProductController.
     *
     * @param event
     * @throws IOException
     * @see ModifyProductController
     */
    public void addPartClicked(ActionEvent event) throws IOException {
        SelectionModel<Part> selectionModel = associatedPartTable1.getSelectionModel();
        Part selectedPart = selectionModel.getSelectedItem();

        if (newProduct.getAllAssociatedParts().contains(selectedPart)) {
            errorMessage.setText("This part is already associated with this product");
        } else {
            newProduct.addAssociatedPart(selectedPart);
            associatedPartTable2.setItems(newProduct.getAllAssociatedParts());
            partIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
            partInvColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * This method works identically to the rapButtonClicked method in the ModifyProductController.
     *
     * @param event
     * @throws IOException
     * @see ModifyProductController
     */
    public void rapButtonClicked(ActionEvent event) throws IOException {
        SelectionModel<Part> selectionModel = associatedPartTable2.getSelectionModel();
        Part partToRemove = selectionModel.getSelectedItem();
        if (errorMessage.getText().equals("Are you sure you want to remove this part? Part ID: " + partToRemove.getId()
                + "\nIf yes, click the Remove Associated Part button again.")) {
            boolean deleted = newProduct.deleteAssociatedPart(partToRemove);

            if (deleted) {
                associatedPartTable2.refresh();
            } else {
                errorMessage.setText("The selected part could not be removed from associated parts.");
            }

            associatedPartTable2.setItems(newProduct.getAllAssociatedParts());
            errorMessage.setText("");

        } else {
            errorMessage.setText("Are you sure you want to remove this part? Part ID: " + partToRemove.getId()
                    + "\nIf yes, click the Remove Associated Part button again.");
        }
    }

    /**
     * When the cancel button is clicked the Modify Product window is closed and the user is returned
     * to the Main Screen.
     *
     * @param event
     * @throws IOException
     */
    public void cancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * RUNTIME ERROR: One of the runtime errors that I encountered while building this project was this null pointer exception error:
     *
     * "Caused by: java.lang.NullPointerException: Cannot invoke "thomasmccue.pa_c482.Part.getId()" because "this.part" is null"
     *
     * Originally when calling the AddProductController from the MainScreenController I was encountering this null pointer exception
     * when I tried to manipulate the product before I clicked the save button or filled out the fields. That's
     * because my product object wasn't being created until the saveClicked method was called, so it was impossible to add
     * associated parts to it, it didn't exist yet. I fixed this by adding this line of code to the initialize method:
     *
     * newProduct = new Product(randomId, "", 0.00, 0, 0, 0);
     *
     * this way a product object was created as soon as the AddProductController was initialized, and my pointer was no
     * longer null.
     *---------------------------------------------------------------------------------------------------------------------------
     *
     *When the Modify Product window is opened, the ID field is populated with a random number that has been checked
     * against the ID's of all other products and parts in inventory. A new random ID is generated until a unique ID
     * is found. An empty Product object is created on opening the AddProductController so that parts can be associated
     * and disassociated with the Product before the user enters other product data. The top part table is populated
     * with all existing parts.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random random = new Random();
        int randomId;

        do {
            randomId = random.nextInt(1000000);
        } while (Inventory.lookupPart(randomId) != null || Inventory.lookupProduct(randomId) != null);

        idField.setText(String.valueOf(randomId));

        newProduct = new Product(randomId, "", 0.00, 0, 0, 0);

        associatedPartTable1.setItems(Inventory.getAllParts());
        partIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}




