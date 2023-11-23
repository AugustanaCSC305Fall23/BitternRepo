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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class CourseViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="courseListView"
    //private ListView<LessonPlan> courseListView = new ListView<>(); // Value injected by FXMLLoader
    @FXML private TreeView<LessonPlan> courseTreeView = new TreeView<>();

    @FXML
    private Button createNewLessonPlanBtn;

    @FXML
    private Button homeButton;

    @FXML
    private TextField courseTitleField;

    // Non FXML
    private static Course currentCourse;
    private TreeItem<LessonPlan> root = new TreeItem<>(new LessonPlan());

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert courseTitleField != null : "fx:id=\"courseTitleField\" was not injected: check your FXML file 'course_view.fxml'.";
        //assert courseListView != null : "fx:id=\"lessonPlanListView\" was not injected: check your FXML file 'course_view.fxml'.";
        courseTreeView.setRoot(root);
        courseTreeView.setShowRoot(false);
        courseTreeView.getRoot().setExpanded(true);
        addLessonsToCourseList();

        if (currentCourse == null) {
            currentCourse = new Course();
        }
        setUpTitle();
    }

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML void setUpTitle() {
        if (App.getCurrentCourse().getCourseTitle() != null) {
            courseTitleField.setText(App.getCurrentCourse().getCourseTitle());
            courseTitleField.setFont(new Font("Britannic Bold", 36.0));
        } else {
            courseTitleField.setText(courseTitleField.getPromptText());
            courseTitleField.setFont(new Font("System Italic", 40.0));
        }
        TitleEditor titleEditor = new TitleEditor(courseTitleField, new Font("Britannic Bold", 45.0), new Font("Britannic Bold", 45.0));
        courseTitleField.setOnMouseClicked(e -> titleEditor.editTitle());
        courseTitleField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                titleEditor.lockInTitle();
                App.getCurrentCourse().setTitle(courseTitleField.getText());
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
        //LessonPlan lessonPlanToEdit = courseListView.getSelectionModel().getSelectedItem();
        LessonPlan lessonPlanToEdit = courseTreeView.getSelectionModel().getSelectedItem().getValue();
        if (lessonPlanToEdit != null) {
            App.setCurrentLessonPlan(lessonPlanToEdit);
            CreateLessonPlanController.setCurrentLessonPlan(lessonPlanToEdit);
            App.setRoot("lesson_plan_creator");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Lesson Plan first.");
        }
    }

    @FXML private void removeLessonPlanHandler() {
        //LessonPlan lessonPlanToDelete = courseListView.getSelectionModel().getSelectedItem();
        LessonPlan lessonPlanToDelete = courseTreeView.getSelectionModel().getSelectedItem().getValue();
        if (lessonPlanToDelete != null) {
            //courseListView.getItems().remove(lessonPlanToDelete);
            courseTreeView.getSelectionModel().getSelectedItem().setValue(null);
            App.getCurrentCourse().getLessonPlanList().remove(lessonPlanToDelete);
            App.setCurrentLessonPlan(null);
        }
    }

    @FXML
    private void addLessonsToCourseList() {
        List<LessonPlan> lessonPlanList = App.getCurrentCourse().getLessonPlanList();
        if (!(lessonPlanList.isEmpty())) {
            TreeItem<LessonPlan> lessonPlan = new TreeItem<>(lessonPlanList.get(lessonPlanList.size() - 1));
            for (LessonPlan lesson: lessonPlanList) {
                //courseListView.getItems().add(lesson);
                lessonPlan.setValue(lesson);
                root.getChildren().add(lessonPlan);
                // add a case for if the lesson plan doesn't have a name (somehow call it "Untitled" in course view)
            }
        }
    }

    @FXML private void duplicateLessonPlanHandler() {
        LessonPlan lessonPlanToDuplicate = courseTreeView.getSelectionModel().getSelectedItem().getValue();
        if (lessonPlanToDuplicate != null) {
            LessonPlan copyOfLessonPlan = new LessonPlan();
            copyOfLessonPlan.setTitle(lessonPlanToDuplicate.getTitle());
            copyOfLessonPlan.setEventInPlanList(lessonPlanToDuplicate.getEventInPlanList());
            App.getCurrentCourse().getLessonPlanList().add(lessonPlanToDuplicate);
            TreeItem<LessonPlan> newLesson = new TreeItem<>();
            newLesson.setValue(lessonPlanToDuplicate);
            root.getChildren().add(newLesson);
        }
    }

    @FXML private void menuActionOpenCourse(ActionEvent event) {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gymnastics Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        Window mainWindow = courseTreeView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                Course.loadFromFile(chosenFile);
                courseTreeView.getRoot().getChildren().clear();
                App.setCurrentCourse(Course.loadFromFile(chosenFile));
                App.setCurrentCourseFile(chosenFile);
                for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                    TreeItem<LessonPlan> lessonInCourse = new TreeItem<>();
                    lessonInCourse.setValue(lessonPlan);
                    root.getChildren().add(lessonInCourse);
                    //courseTreeView.getRoot().(lessonPlan);
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error loading Course: " + chosenFile).show();
            }
        }
    }

    @FXML private void menuActionSaveAs(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = courseTreeView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        App.getCurrentCourse().saveToFile(chosenFile);
        App.setCurrentCourseFile(chosenFile);

    }

    @FXML private void menuActionSave(ActionEvent event) throws IOException{
        if (App.getCurrentCourseFile() != null) {
            App.getCurrentCourse().saveToFile(App.getCurrentCourseFile());
        } else {
            System.out.println("error");
        }
    }

    @FXML private void menuActionCreateNewCourse(ActionEvent event) {
        courseTreeView.getRoot().getChildren().clear();
        App.setCurrentCourse(new Course());
        App.setCurrentCourseFile(null);
        App.getCurrentCourse().getLessonPlanList().add(new LessonPlan());
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            TreeItem<LessonPlan> lessonInCourse = new TreeItem<>();
            lessonInCourse.setValue(lessonPlan);
            root.getChildren().add(lessonInCourse);
        }
    }
}