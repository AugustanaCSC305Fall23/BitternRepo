package edu.augustana;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateLessonPlanController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="browseCardsBtn"
    private Button browseCardsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="doneButton"
    private Button doneButton; // Value injected by FXMLLoader

    @FXML // fx:id="homeBtn"
    private Button homeBtn; // Value injected by FXMLLoader

    @FXML
    void browseCards(ActionEvent event) throws IOException {
        App.setRoot("card_browser");
    }

    @FXML //home button does not want to work
    void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert browseCardsBtn != null : "fx:id=\"browseCardsBtn\" was not injected: check your FXML file 'create_lesson_plan.fxml'.";
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'create_lesson_plan.fxml'.";

    }

}

