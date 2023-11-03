package thomasmccue.pa_c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class Main extends Application {
    /**
     * This method launched the Main Screen window when the program is started.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * FIND MY FUTURE ENHANCEMENT: My Future Enhancement comment can be found above the main method of Main.java (directly below).
     *
     * FIND MY RUNTIME ERROR: My Runtime Error comment can be found above the initialize method of AddProductController.java.
     *
     * FIND MY JAVADOCS: My JavaDocs are in the folder called JavaDoc (PA_C482/JavaDoc). Thank you for your time.
     * -----------------------------------------------------------------------------------------------------------------------
     *
     * FUTURE ENHANCEMENT: In the future the Inventory.lookupPart(int) methods could be improved by making partial ID's searchable.
     * The method could look something like this.
     *
     *     public static ObservableList<Part> lookupPart(int searched) {
     *         ObservableList<Part> searchResults = FXCollections.observableArrayList();
     *
     *         //iterate through every Part object in the allParts ObservableList
     *         for (Part part : allParts) {
     *             //check for partial ID matches as well as perfect match
     *             String partialId = String.valueOf(searched);
     *             String foundId = String.valueOf(part.getId());
     *             if (part.getId() == searched || foundId.contains(partialId)) {
     *                 searchResults.add(part);
     *             }
     *         }
     *        if (searchResults.isEmpty()) {
     *             return null;
     *         } else {
     *             return searchResults;
     *         }
     *     }
     *
     * and the Inventory.lookupProduct(int) method would be very similar.
     * If a user can search a partial name, they should also  be able to search a partial ID.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}