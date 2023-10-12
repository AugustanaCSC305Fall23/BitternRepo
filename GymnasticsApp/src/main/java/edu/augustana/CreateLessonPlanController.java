package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML private ListView<?> lessonPlanListView = new ListView<>();
    @FXML private Label titleLabel = new Label();
    private String title = App.getLessonPlan().getTitle();


    @FXML void browseCards() throws IOException {
        App.setRoot("card_browser");
    }

    @FXML void goToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML void openTitleTextBox() {
        titleLabel.setVisible(false);
        lessonPlanListView.setVisible(false);
        titleField.setVisible(true);
        doneButton.setVisible(true);
    }

    @FXML void setTitle() {
        title = titleField.getText();
        App.getLessonPlan().changeTitle(title);
        titleLabel.setText(title);
        Font titleFont = Font.font("Times New Roman", FontWeight.BOLD, 40);
        titleLabel.setFont(titleFont);
        titleLabel.setVisible(true);
        lessonPlanListView.setVisible(true);
        titleField.setVisible(false);
        doneButton.setVisible(false);
    }

    @FXML void initialize() {
        titleLabel.setText(title);
    }

}

