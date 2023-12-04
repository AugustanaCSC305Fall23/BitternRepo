package edu.augustana.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import edu.augustana.App;
import edu.augustana.model.Course;
import edu.augustana.model.LessonPlan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class CourseViewController {

    @FXML private ListView<LessonPlan> courseListView = new ListView<>();

    @FXML private HBox buttonBar;
    @FXML private Button createNewLessonPlanBtn;
    @FXML private Button homeButton;

    // menu items
    @FXML private MenuItem createNewCourseMenuItem;
    @FXML private MenuItem openCourseMenuItem;
    @FXML private MenuItem saveCourseMenuItem;
    @FXML private MenuItem saveAsMenuItem;

    @FXML
    private TextField courseTitleField;

    // Non FXML
    private static Course currentCourse;
    private ButtonControl buttonControl;
    private CourseMenuControl courseMenuControl;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        courseListView.setOnMouseClicked(e -> checkIfItemSelected());
        courseMenuControl = new CourseMenuControl(courseListView, courseTitleField);
        setUpMenuActions();
        addLessonsToCourseList();

        if (currentCourse == null) {
            currentCourse = new Course();
        }
        courseMenuControl.setUpTitle();
    }

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }

    // see if some kind of listener or observer can be added to do this more easily
    @FXML private void checkIfItemSelected() {
        for (Node node : buttonBar.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                if (!courseListView.getSelectionModel().isEmpty()) {
                    btn.setDisable(false);
                } else {
                    if (btn != createNewLessonPlanBtn) {
                        btn.setDisable(true);
                    }
                }
            }
        }
    }

    private void setUpMenuActions() {
        createNewCourseMenuItem.setOnAction(e -> courseMenuControl.createNewCourse());
        openCourseMenuItem.setOnAction(e -> courseMenuControl.openCourseFromFiles());
        saveCourseMenuItem.setOnAction(e -> {
            try {
                courseMenuControl.saveCourse();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        saveAsMenuItem.setOnAction(e -> {
            try {
                courseMenuControl.saveCourseAs();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void createLessonPlanHandler() throws IOException {
        LessonPlan lessonPlan = App.getCurrentCourse().createNewLessonPlan();
        CreateLessonPlanController.setCurrentLessonPlan(lessonPlan);
        App.setRoot("lesson_plan_creator");
        //might need to move parts of method
    }

    @FXML
    private void editLessonPlanHandler() throws  IOException {
        LessonPlan lessonPlanToEdit = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToEdit != null) {
            App.setCurrentLessonPlan(lessonPlanToEdit);
            CreateLessonPlanController.setCurrentLessonPlan(lessonPlanToEdit);
            App.setRoot("lesson_plan_creator");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Lesson Plan first.");
        }
    }

    @FXML private void removeLessonPlanHandler() {
        LessonPlan lessonPlanToDelete = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToDelete != null) {
            courseListView.getItems().remove(lessonPlanToDelete);
            App.getCurrentCourse().getLessonPlanList().remove(lessonPlanToDelete);
            App.setCurrentLessonPlan(null);
        }
    }

    @FXML
    private void addLessonsToCourseList() {
        List<LessonPlan> lessonPlanList = App.getCurrentCourse().getLessonPlanList();
        if (!(lessonPlanList.isEmpty())) {
            for (LessonPlan lesson: lessonPlanList) {
                courseListView.getItems().add(lesson);

                // add a case for if the lesson plan doesn't have a name (somehow call it "Untitled" in course view)
            }
        }
    }

    @FXML private void duplicateLessonPlanHandler() {
        LessonPlan lessonPlanToDuplicate = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToDuplicate != null) {
            LessonPlan copyOfLessonPlan = new LessonPlan();
            copyOfLessonPlan.setTitle(lessonPlanToDuplicate.getTitle());
            copyOfLessonPlan.setEventInPlanList(lessonPlanToDuplicate.getLessonPlan());
            App.getCurrentCourse().getLessonPlanList().add(lessonPlanToDuplicate);
            courseListView.getItems().add(copyOfLessonPlan);
        }
    }
}