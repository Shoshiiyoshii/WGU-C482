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
    private Label errorMessage;
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

    @FXML
    public void clickPartAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickPartModify(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(scene);

        //create an instance of the modifyPartController
        ModifyPartController modifyPartController = fxmlLoader.getController();

        //pass the part that has been selected in the table to the modifyPartController to be edited
        //and auto-fill the text fields in the modify part window with the selected parts data
        SelectionModel<Part> selectionModel = partTable.getSelectionModel();
        Part selectedPart = selectionModel.getSelectedItem();
        modifyPartController.setPart(selectedPart);
        modifyPartController.fillFields();

        stage.show();
    }


    @FXML
    public void clickPartDelete(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("partDeleteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Delete Part");
        stage.setScene(scene);

        //create an instance of the delete dialog controller
        PartDeleteDialogController dialogController = fxmlLoader.getController();

        //pass the selected part to the delete confirmation dialog box
        SelectionModel<Part> selectionModel = partTable.getSelectionModel();
        Part selectedPart = selectionModel.getSelectedItem();
        dialogController.setPart(selectedPart);
        dialogController.setPartToBeDeleted();

        //show the delete dialog pop-up
        stage.show();
    }

    @FXML
    public void partSearch(ActionEvent event) throws IOException {
        // Grab what is typed into the partSearchBar and hold it in a String called "search"
        String search = partSearchBar.getText();
        //If an error message was previously displayed in the UI here, clear it.
        errorMessage.setText("");

        // If the search field is empty, repopulate the table with all available parts
        if (search.isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
            // Check if search input is an int or a string so that the proper method can be called
            // Using regex to see if search input is digits
        } else if (search.matches("\\d+")) {
            int idSearched = Integer.parseInt(search);
            Part found = Inventory.lookupPart(idSearched);
            //check to see if lookupPart(int) returned a value. If it did, show the matching value in the table view
            if (found != null) {
                ObservableList<Part> foundParts = FXCollections.observableArrayList();
                foundParts.add(found);
                partTable.setItems(foundParts);
                //if lookupPart(int) returned null, no part was found. Show an explanatory error in the UI
                //reset the search bar and refill the table view with all parts
            } else {
                errorMessage.setText("No part with ID " + idSearched + " found.");
                partSearchBar.clear();
                partTable.setItems(Inventory.getAllParts());
            }
            //if the search entry isn't a number, then it is a string. Call the appropriate lookUpPart(String) method
            //and show only the matching parts in the table
        } else {
            ObservableList<Part> foundParts = Inventory.lookupPart(search);
            if (foundParts != null) {
                partTable.setItems(foundParts);
                //if no parts containing the searched for String are found, show an error message and reset search bar and table view.
            } else {
                errorMessage.setText("No part containing " + search + " found.");
                partSearchBar.clear();
                partTable.setItems(Inventory.getAllParts());
            }
        }
    }

    @FXML
    public void clickProductAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickProductModify(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickProductDelete(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productDeleteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Product Delete");
        stage.setScene(scene);

        //create an instance of the delete dialog controller
        ProductDeleteDialogController dialogController = fxmlLoader.getController();

        //pass the selected product to the delete confirmation dialog box
        SelectionModel<Product> selectionModel = productTable.getSelectionModel();
        Product selectedProduct = selectionModel.getSelectedItem();
        dialogController.setProduct(selectedProduct);
        dialogController.setProductToBeDeleted();

        //show the delete dialog pop-up
        stage.show();
    }

    @FXML
    public void onProductSearch(ActionEvent event) {
    }

    @FXML
    public void exitClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate part table
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //populate product table
        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

