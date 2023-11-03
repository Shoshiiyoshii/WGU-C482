package thomasmccue.pa_c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductDeleteDialogController implements Initializable {
    @FXML
    private Button deleteButton, cancelButton;
    @FXML
    private Label confirmationDialog, productToBeDeleted;
    private Product product;

    /**
     * This method is passed a specific product that was selected on the Main Screen,
     * and sets it to the global variable product so that all the methods in this controller class can access it.
     *
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * This method displays the name and ID of the product that has been selected to be deleted so
     * that the user can see a detailed confirmation dialog before confirming deletion.
     *
     * @throws IOException
     */
    public void setProductToBeDeleted() throws IOException {
        String productName = product.getName();
        int productId = product.getId();
        productToBeDeleted.setText("Product Name: " + productName + "    Product ID: " + String.valueOf(productId));
    }

    /**
     * Once the user confirms deletion of the selected product, this method is called.
     * It uses the deleteProduct method from Inventory, and displays either a confirmation
     * if the delete was successful or a detailed error dialog if the product could not be deleted.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onDeleteClicked(ActionEvent event) throws IOException {
        boolean deleted = Inventory.deleteProduct(product);
        if (deleted) {
            confirmationDialog.setText("Delete Successful");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        } else {
            //"The user should not delete a product that has a part associated with it."
            confirmationDialog.setText("The selected product could not be deleted. \nPlease remove any associated parts first.");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        }
    }

    /**
     * When the cancel button is clicked, this method causes the delete dialog window to close
     * and returns the user to the Main Screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onCancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This is an auto-generated initialize method by intelliJ CE, it's purpose is to
     * initialize the controller and set up the UI for the modifyPart.fxml file.
     * I did not end up editing it here.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
