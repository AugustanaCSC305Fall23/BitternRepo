package edu.augustana.UI;

import edu.augustana.App;
import edu.augustana.Model.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
    private Button courseEditorAndCreatorBtn; // Value injected by FXMLLoader

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
    private void openCourseEditorAndCreator() throws IOException {
//        Course newCourse = new Course();
//        CourseViewController.setCurrentCourse(newCourse);
        App.setRoot("course_view");

    }


    private void saveCurrentCourseToFile(File chosenFile,  Course currentCourse) throws IOException {
        if (chosenFile != null) {
            try {
                currentCourse.saveToFile(chosenFile);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving Course file: " + chosenFile).show();
            }
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws MalformedURLException {
        assert cardBrowserBtn != null : "fx:id=\"cardBrowserBtn\" was not injected: check your FXML file 'card_browser.fxml'.";
        assert createNewCourseBtn != null : "fx:id=\"newLessonBtn\" was not injected: check your FXML file 'lesson_plan_creator.fxml'.";
        assert courseEditorAndCreatorBtn != null : "fx:id=\"viewSavedPlansBtn\" was not injected: check your FXML file 'saved_lesson_plans.fxml'.";
        String logoURL = new File("Symbols/LOGO.jpeg").toURI().toURL().toString();
        logo.setImage(new Image(logoURL));
    }
}