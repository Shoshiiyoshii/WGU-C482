package thomasmccue.scenebuilderpractice2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //what's the difference between these 2?? FIXME
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        Scene scene = new Scene(root);
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/Main.fxml"));
        //Scene scene = new Scene(fxmlLoader.load());

        //for applying to 1 scene:
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        //for applying to more than 1 scene: DON'T FORGET FILE PATH if css is in view directory!!
        String css = this.getClass().getResource("view/application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}