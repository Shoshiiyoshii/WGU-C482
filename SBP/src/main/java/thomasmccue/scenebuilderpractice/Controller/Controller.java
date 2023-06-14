package thomasmccue.scenebuilderpractice.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Circle myCircle;
    private double x;
    private double y;
    @FXML
    public void up(ActionEvent e) {
        //System.out.println("Up");
        myCircle.setCenterY(y-=10);

    }

    @FXML
    public void down(ActionEvent e) {
        //System.out.println("Down");
        myCircle.setCenterY(y+=10);

    }

    @FXML
    public void left(ActionEvent e) {
        //System.out.println("Left");
        myCircle.setCenterX(x-=10);

    }

    @FXML
    public void right(ActionEvent e) {
        //System.out.println("Right");
        myCircle.setCenterX(x+=10);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Controller is Connected");
    }
}