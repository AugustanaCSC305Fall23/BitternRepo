package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cardBrowserBtn"
    private Button cardBrowserBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewSavedPlansBtn"
    private Button openExistingCourseBtn; // Value injected by FXMLLoader

    @FXML // fx:id="createNewCourseBtn"
    private Button createNewCourseBtn; // Value injected by FXMLLoader

    @FXML private ImageView logo = new ImageView();


    @FXML
    void exitApp(ActionEvent event) {
        Platform.exit();

    }
    @FXML
    private void browseCardsHandler() throws IOException {
        App.setRoot("card_browser");
    }

    @FXML
    private void openExistingCourseHandler() throws IOException {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI4
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
    }

    @FXML
    private void createNewCourseHandler() throws IOException {
        CourseViewController.setCurrentCourse(new Course());
        App.setRoot("course_view");
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cardBrowserBtn != null : "fx:id=\"cardBrowserBtn\" was not injected: check your FXML file 'card_browser.fxml'.";
        assert createNewCourseBtn != null : "fx:id=\"newLessonBtn\" was not injected: check your FXML file 'lesson_plan_creator.fxml'.";
        assert openExistingCourseBtn != null : "fx:id=\"viewSavedPlansBtn\" was not injected: check your FXML file 'saved_lesson_plans.fxml'.";
        logo.setImage(new Image(String.valueOf(getClass().getResource("images/LOGO.jpeg"))));
    }
}



