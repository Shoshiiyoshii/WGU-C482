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

    public void setProduct(Product product){
        this.product = product;
    }

    public void setProductToBeDeleted() throws IOException{
        String productName = product.getName();
        int productId = product.getId();
        productToBeDeleted.setText("Product Name: " + productName + "    Product ID: " + String.valueOf(productId));
    }

    @FXML
    public void onDeleteClicked(ActionEvent event) throws IOException {
        boolean deleted = Inventory.deleteProduct(product);
        if(deleted) {
            confirmationDialog.setText("Delete Successful");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        } else{
            confirmationDialog.setText("The selected product could not be deleted");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        }
    }
    @FXML
    public void onCancelClicked(ActionEvent event) throws IOException{
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
