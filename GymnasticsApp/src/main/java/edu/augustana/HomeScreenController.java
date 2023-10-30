package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController {

    private static Course currentCourse;
    private static LessonPlan currentLessonPlan;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cardBrowserBtn"
    private Button cardBrowserBtn; // Value injected by FXMLLoader

    @FXML // fx:id="newLessonBtn"
    private Button newLessonBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewSavedPlansBtn"
    private Button viewSavedPlansBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewSamplePlansBtn"
    private Button viewSamplePlansBtn; // Value injected by FXMLLoader


    @FXML
    void exitApp(ActionEvent event) {
        Platform.exit();

    }
    @FXML
    private void browseCardsHandler() throws IOException {
        App.setRoot("card_browser");
    }

    @FXML
    private void createLessonPlanHandler() throws IOException {
        currentLessonPlan = currentCourse.createNewLessonPlan();
        CreateLessonPlanController.setCurrentCourse(currentCourse);
        CreateLessonPlanController.setCurrentLessonPlan(currentLessonPlan);
        App.setRoot("lesson_plan_creator");
    }

    @FXML
    private void viewSavedPlansHandler() throws IOException {
        App.setRoot("saved_lesson_plans");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cardBrowserBtn != null : "fx:id=\"cardBrowserBtn\" was not injected: check your FXML file 'card_browser.fxml'.";
        assert newLessonBtn != null : "fx:id=\"newLessonBtn\" was not injected: check your FXML file 'lesson_plan_creator.fxml'.";
        assert viewSavedPlansBtn != null : "fx:id=\"viewSavedPlansBtn\" was not injected: check your FXML file 'saved_lesson_plans.fxml'.";
    }

    public static void setCurrentCourse(Course course) {
        currentCourse = course;
    }

    public static void setCurrentLessonPlan(LessonPlan lessonPlan) {
        currentLessonPlan = lessonPlan;
    }


}



