package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.text.FontWeight;



public class StartupController {

    Font f;
    @FXML public Button resume;
    //public Button newGame;
    public StartupController(){ }
    @FXML public void initialize(){
        resume.setOpacity(.2);
        resume.setMouseTransparent(true);
        //resume.removeEventHandler(MouseEvent.MOUSE_ENTERED,this::onHovered);

    }

    public void onHovered(MouseEvent e){
        //if(((Button)e.getSource()).getOpacity() == 1.0)
        ((Button)e.getSource()).setStyle("-fx-border-width: 2; -fx-background-insets: 0; -fx-border-radius: 90; -fx-background-radius: 90; -fx-text-fill: black; -fx-border-color: black; -fx-background-color: white;");
        //resume.setOpacity(.2);

    }
    public void mouseExit(MouseEvent e){
        //if(((Button)e.getSource()).getOpacity() == 1.0)
        ((Button)e.getSource()).setStyle("-fx-border-width: 2; -fx-border-radius: 90; -fx-background-radius: 90; -fx-text-fill: white; -fx-border-color: white; -fx-background-color: black;");
    }
    public void newGameClicked(MouseEvent click) throws java.io.IOException
    {
        // load the next scene
        Parent GameScreenParent = FXMLLoader.load(getClass().getResource("PlayerMode.fxml"));
        Scene GameScreenScene = new Scene(GameScreenParent);

        // get the stage... getSource = get object that was clicked on (the button) from the event, getScene = get the scene the button is a part of, getWindow = get the stage the scene is a part of
        Stage window = (Stage)((Node)click.getSource()).getScene().getWindow();

        // set stage to display the next scene
        window.setScene(GameScreenScene);

        window.show();
    }
}