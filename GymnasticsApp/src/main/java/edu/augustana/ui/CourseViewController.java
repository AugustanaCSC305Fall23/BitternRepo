package edu.augustana.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.augustana.App;
import edu.augustana.model.Course;
import edu.augustana.model.CourseModel;
import edu.augustana.model.LessonPlan;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class CourseViewController {

    @FXML private ListView<LessonPlan> courseListView = new ListView<>();

    @FXML private HBox buttonBar;
    @FXML private Button createNewLessonPlanBtn;
    @FXML private Button homeButton;

    @FXML private TextField courseTitleField;

    // Non FXML
    private static Course currentCourse;
    private CourseModel courseMenuControl;
    private TitleEditor titleEditor;
    private List<Button> buttonsList = new ArrayList<>();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (currentCourse == null) {
            currentCourse = new Course();
        }
        courseListView.setOnMouseClicked(e -> checkIfItemSelected());
        courseMenuControl = new CourseModel();
        addLessonsToCourseList();
        titleEditor = new TitleEditor(courseTitleField, new Font("Britannic Bold", 45.0), new Font("Britannic Bold", 45.0));
        titleEditor.setUpTitle();
        for (Node node : buttonBar.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                buttonsList.add(btn);
            }
        }
    }

    @FXML private void goToHome() throws IOException {
        App.setRoot("home");
    }

    private void checkIfItemSelected() {
        if (!courseListView.getSelectionModel().isEmpty()) {
            for (Button btn : buttonsList) {
                btn.setDisable(false);
            }
            courseMenuControl.setSelectedLessonPlan(courseListView.getSelectionModel().getSelectedItem());
        } else {
            for (Button btn : buttonsList) {
                if (btn != createNewLessonPlanBtn) {
                    btn.setDisable(true);
                }
            }
            courseMenuControl.setSelectedLessonPlan(null);
        }
    }

    @FXML private void createNewCourseHandler() {
        courseMenuControl.createNewCourse();
        courseListView.getItems().clear();
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
        titleEditor.setUpTitle();
    }

    @FXML private void openCourseHandler() {
        Window mainWindow = courseListView.getScene().getWindow();
        courseMenuControl.openCourseFromFiles(mainWindow);
        courseTitleField.setText(App.getCurrentCourse().getCourseTitle());
        courseListView.getItems().clear();
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
    }

    @FXML private void saveCourseHandler() {
        try {
            courseMenuControl.saveCourse();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No course file is open").show();
        }
    }

    @FXML private void saveCourseAsHandler() {
        Window mainWindow = courseListView.getScene().getWindow();
        try {
            courseMenuControl.saveCourseAs(mainWindow);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No course file is open").show();
        }
    }

    @FXML private void createLessonPlanHandler() throws IOException {
        LessonPlan lessonPlan = App.getCurrentCourse().createNewLessonPlan();
        CreateLessonPlanController.setCurrentLessonPlan(lessonPlan);
        App.setRoot("lesson_plan_creator");
        //might need to move parts of method
    }

    @FXML private void editLessonPlanHandler() throws  IOException {
        LessonPlan lessonPlanToEdit = courseListView.getSelectionModel().getSelectedItem();
        //LessonPlan lessonPlanToEdit = courseTreeView.getSelectionModel().getSelectedItem().getValue();
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
        //LessonPlan lessonPlanToDelete = courseTreeView.getSelectionModel().getSelectedItem().getValue();
        if (lessonPlanToDelete != null) {
            courseListView.getItems().remove(lessonPlanToDelete);
            //courseTreeView.getSelectionModel().getSelectedItem().setValue(null);
            App.getCurrentCourse().getLessonPlanList().remove(lessonPlanToDelete);
            App.setCurrentLessonPlan(null);
        }
    }

    @FXML
    private void addLessonsToCourseList() {
        List<LessonPlan> lessonPlanList = App.getCurrentCourse().getLessonPlanList();
        if (!(lessonPlanList.isEmpty())) {
            //TreeItem<LessonPlan> lessonPlan = new TreeItem<>(lessonPlanList.get(lessonPlanList.size() - 1));
            for (LessonPlan lesson: lessonPlanList) {
                courseListView.getItems().add(lesson);
                //lessonPlan.setValue(lesson);
                //root.getChildren().add(lessonPlan);
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