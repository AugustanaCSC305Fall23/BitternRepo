package edu.augustana;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private ListView<LessonPlan> courseListView = new ListView<>(); // Value injected by FXMLLoader

    @FXML
    private Button createNewLessonPlanBtn;

    @FXML
    private Button homeButton;

    //Title UI Components

    @FXML
    private Button doneBtn;

    @FXML
    private Button editTitleBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField titleField;

    @FXML
    private Label titleLabel;

    // Non FXML
    private static Course currentCourse;


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
    private void editLessonPlanHandler() throws  IOException {
        LessonPlan lessonPlanToEdit = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToEdit != null) {
            App.changeCurrentLessonPlan(lessonPlanToEdit);
            CreateLessonPlanController.setCurrentLessonPlan(lessonPlanToEdit);
            App.setRoot("lesson_plan_creator");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Must select a Lesson Plan first.");
        }
    }

    @FXML
    private void addLessonsToCourseList() {
        if (!(App.getCurrentCourse().getLessonPlanList().isEmpty())) {
            for (LessonPlan lesson: App.getCurrentCourse().getLessonPlanList()) {
                courseListView.getItems().add(lesson);
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
                App.changeCurrentCourseFile(chosenFile);
                for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                    courseListView.getItems().add(lessonPlan);
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
        Window mainWindow = courseListView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        App.getCurrentCourse().saveToFile(chosenFile);
        App.changeCurrentCourseFile(chosenFile);

    }

    @FXML private void menuActionSave(ActionEvent event) throws IOException{
        if (App.getCurrentCourseFile() != null) {
            App.getCurrentCourse().saveToFile(App.getCurrentCourseFile());
        } else {
            System.out.println("error");
        }
    }

    @FXML private void menuActionCreateNewCourse(ActionEvent event) {
        courseListView.getItems().clear();
        App.changeCurrentCourse(new Course());
        App.changeCurrentCourseFile(null);
        App.getCurrentCourse().getLessonPlanList().add(new LessonPlan("My Lesson Plan"));
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert courseTitleLabel != null : "fx:id=\"courseTitleLabel\" was not injected: check your FXML file 'course_view.fxml'.";
        assert courseListView != null : "fx:id=\"lessonPlanListView\" was not injected: check your FXML file 'course_view.fxml'.";
        addLessonsToCourseList();

        titleField.setVisible(false);
        doneBtn.setVisible(false);
        cancelBtn.setVisible(false);

        if (currentCourse == null) {
            currentCourse = new Course();
            currentCourse.changeTitle(titleLabel.toString());
        }
    }

    @FXML
    void setTitle(ActionEvent event) {
        String title = titleField.getText();
        if (!title.isEmpty()) {
            currentCourse.changeTitle(title);
            titleLabel.setText(title);
            Font titleFont = Font.font("Times New Roman", FontWeight.BOLD, 40);
            titleLabel.setFont(titleFont);
            switchToCourseOutlineView();
        } else {
            giveWarning("Cannot have empty title.");
        }
    }

    @FXML
    void switchToCourseOutlineView() {
        titleLabel.setVisible(true);
        titleField.setVisible(false);
        doneBtn.setVisible(false);
        cancelBtn.setVisible(false);
        editTitleBtn.setVisible(true);
    }

    @FXML
    void switchToEditTitleView(ActionEvent event) {
        titleLabel.setVisible(false);
        titleField.setVisible(true);
        doneBtn.setVisible(true);
        editTitleBtn.setVisible(false);
        cancelBtn.setVisible(true);
    }

    @FXML private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void setCurrentCourse(Course course) {
        App.changeCurrentCourse(course);
    }



}