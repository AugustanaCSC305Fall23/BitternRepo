package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

    private Label title = new Label();
    private ListView<?> lessonPlan = new ListView<>();
    private String getTitle;

    @FXML void browseCards(ActionEvent event) throws IOException {
        App.setRoot("card_browser");
    }

    @FXML void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML void setTitle(ActionEvent event) {
        //setting up the title
        getTitle = titleField.getText();
        title.setText(getTitle);
        Font titleFont = Font.font("Times New Roman", FontWeight.BOLD, 35);
        title.setFont(titleFont);

        //removing the done button and text field
        titleBox.getChildren().remove(doneButton);
        titleBox.getChildren().remove(titleField);

        //adding the title and then a place for the cards to go
        titleBox.getChildren().add(title);
        titleBox.getChildren().add(lessonPlan);
    }

    //I don't know if this is messing up the lesson plan
    @FXML void editTitle(ActionEvent event) {
        titleBox.getChildren().remove(title);
        titleBox.getChildren().remove(lessonPlan);

        titleBox.getChildren().add(titleField);
        titleField.setText(getTitle);
        titleBox.getChildren().add(doneButton);
    }

}

