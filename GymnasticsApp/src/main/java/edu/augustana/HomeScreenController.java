package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeScreenController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cardBrowserBtn"
    private Button cardBrowserBtn; // Value injected by FXMLLoader

    @FXML // fx:id="newLessonBtn"
    private Button newLessonBtn; // Value injected by FXMLLoader

    @FXML
    private void browseCards() throws IOException {
        App.setRoot("card_browser");
    }

    @FXML
    public void createLessonPlan() throws IOException {
        App.setRoot("create_lesson_plan");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cardBrowserBtn != null : "fx:id=\"cardBrowserBtn\" was not injected: check your FXML file 'home.fxml'.";
        assert newLessonBtn != null : "fx:id=\"newLessonBtn\" was not injected: check your FXML file 'home.fxml'.";

    }


}



