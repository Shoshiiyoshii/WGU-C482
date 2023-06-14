package thomasmccue.pa_c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {
    @FXML
    private TextField partSearchBar,productSearchBar;
    @FXML
    private Button partAddButton, partModifyButton, partDeleteButton, productAddButton, productModifyButton, productDeleteButton, exitButton;
    @FXML
    private AnchorPane mainScreenPane;
    Stage stage;
    public void clickPartAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public void clickProductAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public void clickPartModify(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    public void clickProductModify(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modifyProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public void exitClicked(ActionEvent event) throws IOException{
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    // Implement the necessary methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic goes here
    }

    // Other methods and fields
}

