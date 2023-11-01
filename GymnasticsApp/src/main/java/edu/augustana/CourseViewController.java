package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class CourseViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="courseTitleLabel"
    private AnchorPane courseTitleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="courseListView"
    private ListView<String> courseListView = new ListView<>(); // Value injected by FXMLLoader

    private static Course currentCourse;

    @FXML
    private Button createNewLessonPlanBtn;

    @FXML
    private Button homeButton;

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void createLessonPlanHandler() throws IOException {
        LessonPlan lessonPlan = currentCourse.createNewLessonPlan();
        CreateLessonPlanController.setCurrentLessonPlan(lessonPlan);
        App.setRoot("lesson_plan_creator");
    }

    @FXML
    private void addLessonsToCourseList() {
        if (!(currentCourse.getLessonPlanList().isEmpty())) {
            for (LessonPlan lesson: currentCourse.getLessonPlanList()) {
                courseListView.getItems().add(lesson.getTitle());
            }
        }
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert courseTitleLabel != null : "fx:id=\"courseTitleLabel\" was not injected: check your FXML file 'course_view.fxml'.";
        assert courseListView != null : "fx:id=\"lessonPlanListView\" was not injected: check your FXML file 'course_view.fxml'.";
        addLessonsToCourseList();
    }

    public static void setCurrentCourse(Course course) {
        currentCourse = course;
    }



}