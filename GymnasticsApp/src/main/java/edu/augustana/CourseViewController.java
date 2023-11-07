package edu.augustana;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class CourseViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="courseTitleLabel"
    private AnchorPane courseTitleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="courseListView"
    private ListView<String> courseListView = new ListView<>(); // Value injected by FXMLLoader

    //private static Course currentCourse;
    private static File currentCourseFile = null;

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
        LessonPlan lessonPlan = App.getCurrentCourse().createNewLessonPlan();
        CreateLessonPlanController.setCurrentLessonPlan(lessonPlan);
        App.setRoot("lesson_plan_creator");
    }

    @FXML
    private void addLessonsToCourseList() {
        if (!(App.getCurrentCourse().getLessonPlanList().isEmpty())) {
            for (LessonPlan lesson: App.getCurrentCourse().getLessonPlanList()) {
                courseListView.getItems().add(lesson.getTitle());
            }
        }
    }

    @FXML private void menuActionOpenCourse(ActionEvent event) {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gymnastics Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        Window mainWindow = courseListView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                Course.loadFromFile(chosenFile);
                courseListView.getItems().clear();
                App.changeCurrentCourse(Course.loadFromFile(chosenFile));
                currentCourseFile = chosenFile;
                courseListView.getItems().addAll(App.getCurrentCourse().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML private void menuActionSaveAs(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = courseListView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        App.getCurrentCourse().saveToFile(chosenFile);

    }

    @FXML private void menuActionCreateNewCourse(ActionEvent event) {
        App.changeCurrentCourse(new Course());
        courseListView.getItems().addAll(App.getCurrentCourse().getLessonPlanList().toString());
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert courseTitleLabel != null : "fx:id=\"courseTitleLabel\" was not injected: check your FXML file 'course_view.fxml'.";
        assert courseListView != null : "fx:id=\"lessonPlanListView\" was not injected: check your FXML file 'course_view.fxml'.";
        addLessonsToCourseList();
    }

    public static void setCurrentCourse(Course course) {
        App.changeCurrentCourse(course);
    }



}