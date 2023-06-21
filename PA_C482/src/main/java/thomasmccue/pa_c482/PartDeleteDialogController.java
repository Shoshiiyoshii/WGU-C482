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

public class PartDeleteDialogController implements Initializable {
    @FXML
    private Button deleteButton, cancelButton;
    @FXML
    private Label confirmationDialog, partToBeDeleted;

    private Part part;

    public void setPart(Part part){
        this.part = part;
    }

    public void setPartToBeDeleted() throws IOException{
        String partName = part.getName();
        int partId = part.getId();
        partToBeDeleted.setText("Part Name: " + partName + "    Part ID: " + String.valueOf(partId));
    }

    @FXML
    public void onDeleteClicked(ActionEvent event) throws IOException {
        boolean deleted = Inventory.deletePart(part);
        if(deleted) {
            confirmationDialog.setText("Delete Successful");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        } else{
            confirmationDialog.setText("The selected part could not be deleted");
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
