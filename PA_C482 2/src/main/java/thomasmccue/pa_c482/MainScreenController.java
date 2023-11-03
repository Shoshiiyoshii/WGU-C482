package thomasmccue.pa_c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private TextField partSearchBar, productSearchBar;
    @FXML
    private Button partAddButton, partModifyButton, partDeleteButton, productAddButton, productModifyButton, productDeleteButton, exitButton;
    @FXML
    private AnchorPane mainScreenPane;
    @FXML
    private Label errorMessage, errorMessageProducts, exitWarning, partMessage, productMessage;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn, partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn, productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    Stage stage;

    /**
     * This method runs when the add button under the Parts table is clicked.
     * It launches the Add Part screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickPartAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the modify button under the Parts table is clicked.
     * It checks to see if a part has been selected. If not, it prompts the user to select a part
     * in order to continue. If a part has been selected, it launches the modifyPart screen,
     * creates an instance of the modifyPartController, passes the part that has been selected
     * to the modifyPartController to be edited, and calls a method that auto-fills
     * the text fields in the modify part window with the selected parts data.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickPartModify(ActionEvent event) throws IOException {
        SelectionModel<Part> selectionModel = partTable.getSelectionModel();
        Part selectedPart = selectionModel.getSelectedItem();

        if(selectedPart == null) {
            partMessage.setText("Select a part to modify.");

        } else {
            partMessage.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyPart.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.setScene(scene);

            ModifyPartController modifyPartController = fxmlLoader.getController();

            modifyPartController.setPart(selectedPart);
            modifyPartController.fillFields();

            stage.show();
        }
    }

    /**
     * This method is called when the delete button under the Parts table is clicked.
     * It creates an instance of the part delete dialog controller, and passes the selected
     * part to the delete confirmation dialog box so that it can confirm the correct part to delete.
     * If no part has been selected, it shows an error message indicating that a part must first be selected.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickPartDelete(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("partDeleteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Delete Part");
        stage.setScene(scene);

        PartDeleteDialogController dialogController = fxmlLoader.getController();

        SelectionModel<Part> selectionModel = partTable.getSelectionModel();
        Part selectedPart = selectionModel.getSelectedItem();
        if(selectedPart != null) {
            partMessage.setText("");
            dialogController.setPart(selectedPart);
            dialogController.setPartToBeDeleted();

            stage.show();
        } else{
            partMessage.setText("First select a part to delete.");
        }
    }

    /**
     * This method is called when the search bar is selected and the enter key is typed.
     * It grabs what is typed into the partSearchBar and holds that in a String called "search".
     * If the search field is empty, the table is repopulated with all available parts.
     * If the search field is not empty when the enter key is clicked, the method checks
     * if search input is an int or a string so that the proper method in Inventory can be called.
     * It uses regex to see if search input is digits, i.e. a number or not. If lookupPart(int) returned
     * a value the method shows the matching value in the Parts table view. If the search was digits, but
     * no matching ID is found, the method shows an error in the UI, resets the search bar, and resets the table.
     *
     * If the search entry is a String, the method calls the lookupPart(String) method in Inventory
     * and adds all parts whose names contain the searched for string to a new observable list, which then
     * populates the Parts table view. If no parts containing the searched for String are found, an error message
     * is shown, and the search bar and table view are reset.
     * @param event
     * @throws IOException
     */
    @FXML
    public void partSearch(ActionEvent event) throws IOException {
        String search = partSearchBar.getText();
        errorMessage.setText("");

        if (search.isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
        } else if (search.matches("\\d+")) {
            int idSearched = Integer.parseInt(search);
            Part found = Inventory.lookupPart(idSearched);

            if (found != null) {
                ObservableList<Part> foundParts = FXCollections.observableArrayList();
                foundParts.add(found);
                partTable.setItems(foundParts);
            } else {
                errorMessage.setText("No part with ID \"" + idSearched + "\" found.");
                partSearchBar.clear();
                partTable.setItems(Inventory.getAllParts());
            }
        } else {
            ObservableList<Part> foundParts = Inventory.lookupPart(search);
            if (foundParts != null) {
                partTable.setItems(foundParts);

            } else {
                errorMessage.setText("No part containing \"" + search + "\" found.");
                partSearchBar.clear();
                partTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * This method works essentially the same as the clickPartAdd method.
     * This method launches the Add Product window when the Add button
     * under the Product table view is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickProductAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method works very similarly to the clickPartModify method.
     * This method is called when the modify button under the Products table view is clicked.
     * It checks to see if a product has been selected, and if not prompts the user to select one.
     * Once a product has been selected, the modifyProduct screen is launched with an instance
     * of the modifyProductController. This method passes the part that has been selected from the table
     * to the modifyProductController to be edited and prompts the modifyProductController to auto-fill
     * the text fields in the modify part window with the selected products data.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickProductModify(ActionEvent event) throws IOException {
        SelectionModel<Product> selectionModel = productTable.getSelectionModel();
        Product selectedProduct = selectionModel.getSelectedItem();

        if(selectedProduct == null) {
            productMessage.setText("Select a product to modify.");

        } else {
            productMessage.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyProduct.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Modify Product");
            stage.setScene(scene);

            ModifyProductController modifyProductController = fxmlLoader.getController();

            modifyProductController.setProduct(selectedProduct);
            modifyProductController.fillFields();

            stage.show();
        }
    }

    /**
     * This method works essentially the same as the clickPartDelete method.
     * This method is called when the delete button under the Products table is clicked.
     * It creates an instance of the product delete dialog controller, and passes the selected
     * product to the delete confirmation dialog box so that it can confirm the correct product to delete.
     * If no product has been selected, it shows an error message indicating that a product must first be selected.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickProductDelete(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productDeleteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Product Delete");
        stage.setScene(scene);

        ProductDeleteDialogController dialogController = fxmlLoader.getController();

        SelectionModel<Product> selectionModel = productTable.getSelectionModel();
        Product selectedProduct = selectionModel.getSelectedItem();
        if(selectedProduct != null) {
            productMessage.setText("");
            dialogController.setProduct(selectedProduct);
            dialogController.setProductToBeDeleted();

            stage.show();
        } else{
            productMessage.setText("First select a product to delete.");
        }
    }

    /**
     * This method works essentially the same as the partSearch method.
     * This method is called when the search bar is selected and the enter key is typed.
     * It grabs what is typed into the productSearchBar and holds that in a String called "search".
     * If the search field is empty, the table is repopulated with all available products.
     * If the search field is not empty when the enter key is clicked, the method checks
     * if search input is an int or a string so that the proper method in Inventory can be called.
     * It uses regex to see if search input is digits, i.e. a number or not. If lookupProduct(int) returned
     * a value the method shows the matching value in the Products table view. If the search was digits, but
     * no matching ID is found, the method shows an error in the UI, resets the search bar, and resets the table.
     *
     * If the search entry is a String, the method calls the lookupProduct(String) method in Inventory
     * and adds all products whose names contain the searched for string to a new observable list, which then
     * populates the Products table view. If no products containing the searched for String are found, an error message
     * is shown, and the search bar and table view are reset.
     * @param event
     * @throws IOException
     */
    @FXML
    public void productSearch(ActionEvent event) throws IOException{
        String search = productSearchBar.getText();
        errorMessageProducts.setText("");

        if (search.isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());

        } else if (search.matches("\\d+")) {
            int idSearched = Integer.parseInt(search);
            Product found = Inventory.lookupProduct(idSearched);

            if (found != null) {
                ObservableList<Product> foundProducts = FXCollections.observableArrayList();
                foundProducts.add(found);
                productTable.setItems(foundProducts);

            } else {
                errorMessageProducts.setText("No product with ID \"" + idSearched + "\" found.");
                productSearchBar.clear();
                productTable.setItems(Inventory.getAllProducts());
            }

        } else {
            ObservableList<Product> foundProducts = Inventory.lookupProduct(search);
            if (foundProducts != null) {
                productTable.setItems(foundProducts);
            } else {
                errorMessageProducts.setText("No products containing \"" + search + "\" found.");
                productSearchBar.clear();
                productTable.setItems(Inventory.getAllProducts());
            }
        }
    }

    /**
     * This method is called when the exit button is clicked. It prompts the user to
     * confirm that they would like to exit. If the prompt has been given and the user clicks
     * the Exit button again, the Main Screen window closes.
     * @param event
     * @throws IOException
     */
    @FXML
    public void exitClicked(ActionEvent event) throws IOException {
        if(exitWarning.getText().equals("Are you sure you'd like to exit?")){
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }else{
            exitWarning.setText("Are you sure you'd like to exit?");
            exitButton.setText("Yes Exit");
        }
    }

    /**
     * This method causes the Main Screen to open with all parts populating the parts table and
     * all products populating the products table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

