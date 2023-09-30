package edu.augustana;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateLessonPlanController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML private Button browseCardsBtn; // Value injected by FXMLLoader
    @FXML private Button doneButton; // Value injected by FXMLLoader
    @FXML private Button homeBtn; // Value injected by FXMLLoader
    @FXML private Button printButton; // Value injected by FXMLLoader
    @FXML private Button saveButton; // Value injected by FXMLLoader
    @FXML private Button titleChangeButton; // Value injected by FXMLLoader
    @FXML private TextField titleField; // Value injected by FXMLLoader
    @FXML private VBox titleBox; // Value injected by FXMLLoader


    @FXML void browseCards(ActionEvent event) throws IOException {
        App.setRoot("card_browser");
    }

    @FXML void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML void setTitle(ActionEvent event) {

    }


}

