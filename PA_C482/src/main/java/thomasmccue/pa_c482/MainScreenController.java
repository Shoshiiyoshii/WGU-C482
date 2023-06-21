package thomasmccue.pa_c482;

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
    private TextField partSearchBar,productSearchBar;
    @FXML
    private Button partAddButton, partModifyButton, partDeleteButton, productAddButton, productModifyButton, productDeleteButton, exitButton;
    @FXML
    private AnchorPane mainScreenPane;
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
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickPartModify(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
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
        //stage.setTitle("");
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
    public void clickProductAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void clickProductModify(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickProductDelete(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productDeleteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);

        //create an instance of the delete dialog controller
        ProductDeleteDialogController dialogController = fxmlLoader.getController();

        //pass the selected part to the delete confirmation dialog box
        SelectionModel<Product> selectionModel = productTable.getSelectionModel();
        Product selectedProduct = selectionModel.getSelectedItem();
        dialogController.setProduct(selectedProduct);
        dialogController.setProductToBeDeleted();

        //show the delete dialog pop-up
        stage.show();
    }

    @FXML
    public void exitClicked(ActionEvent event) throws IOException{
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void partSearch(ActionEvent event) throws IOException{
        //grab what is typed into the partSearchBar and hold it in a String called "search"
        String search = partSearchBar.getText();
        //check if search input is an int or a string so that the proper method can be called.
        //using regex to see in search input is digits
        if(search.matches("\\d+")){
            int idSearched = Integer.parseInt(search);
            Part found = Inventory.lookupPart(idSearched);

        }
    }

    @FXML
    public void onProductSearch(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


       // productTable.setItems(Inventory.getAllProducts());
    }

}

