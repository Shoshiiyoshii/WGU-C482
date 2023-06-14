package thomasmccue.sbp_switchscenes;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    Stage stage;
    Scene scene;
    

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException {
        System.out.println("hello button clicked");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Scene2.fxml"));
        scene = new Scene(fxmlLoader.load(),800,800);

        stage.setTitle("Scene2");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void backToHello(ActionEvent event) throws IOException {
        System.out.println("back to hello button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 800, 800);

        stage.setTitle("Scene1");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("controller connected");
    }
}