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
    @FXML private TextField courseTitleField;

    // Non FXML
    private static Course currentCourse;
    private CourseModel courseModel;
    private TitleEditor titleEditor;
    private List<Button> buttonsList = new ArrayList<>();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (currentCourse == null) {
            currentCourse = new Course();
        }
        courseListView.setOnMouseClicked(e -> checkIfItemSelected());
        courseModel = new CourseModel();
        addLessonsToCourseList();
        titleEditor = new TitleEditor(courseTitleField, new Font("Britannic Bold", 45.0), new Font("Britannic Bold", 45.0), 'C');
        titleEditor.initializeTitleFieldEvents();
        titleEditor.setTitleFieldText();
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

    private void setCourseTitle() {
        if (courseTitleField.getText() != null) {
            App.getCurrentCourse().setTitle(courseTitleField.getText());
            System.out.println("Listener worked for course!!!");
        } else {
            App.getCurrentCourse().setTitle("Untitled");
        }
    }

    private void checkIfItemSelected() {
        if (!courseListView.getSelectionModel().isEmpty()) {
            for (Button btn : buttonsList) {
                btn.setDisable(false);
            }
            courseModel.setSelectedLessonPlan(courseListView.getSelectionModel().getSelectedItem());
        } else {
            for (Button btn : buttonsList) {
                if (btn != createNewLessonPlanBtn) {
                    btn.setDisable(true);
                }
            }
            courseModel.setSelectedLessonPlan(null);
        }
    }

    @FXML private void createNewCourseHandler() {
        courseModel.createNewCourse();
        courseListView.getItems().clear();
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
        //titleEditor.setUpTitleActions();
    }

    @FXML private void openCourseHandler() {
        Window mainWindow = courseListView.getScene().getWindow();
        courseModel.openCourseFromFiles(mainWindow);
        courseTitleField.setText(App.getCurrentCourse().getTitle());
        courseListView.getItems().clear();
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
    }

    @FXML private void saveCourseHandler() {
        try {
            courseModel.saveCourse();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No course file is open").show();
        }
    }

    @FXML private void saveCourseAsHandler() {
        Window mainWindow = courseListView.getScene().getWindow();
        try {
            courseModel.saveCourseAs(mainWindow);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No course file is open").show();
        }
    }

    @FXML private void createLessonPlanHandler() throws IOException {
        LessonPlan lessonPlan = App.getCurrentCourse().createNewLessonPlan();
        CreateLessonPlanController.setCurrentLessonPlan(lessonPlan);
        App.setRoot("lesson_plan_creator");
    }

    @FXML private void editLessonPlanHandler() throws  IOException {
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