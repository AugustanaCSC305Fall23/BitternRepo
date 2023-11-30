package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Course;
import edu.augustana.model.LessonPlan;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class CourseMenuControl {

    TreeView<LessonPlan> courseTreeView;
    ListView<LessonPlan> courseListView;
    public CourseMenuControl(TreeView<LessonPlan> treeView) {
        courseTreeView = treeView;
    }
    public CourseMenuControl(ListView<LessonPlan> listView) {
        courseListView = listView;
    }

    @FXML public void createNewCourse() {
        //courseTreeView.getRoot().getChildren().clear();
        courseListView.getItems().clear();
        App.setCurrentCourse(new Course());
        App.setCurrentCourseFile(null);
        App.getCurrentCourse().getLessonPlanList().add(new LessonPlan());
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            //TreeItem<LessonPlan> lessonInCourse = new TreeItem<>();
            //lessonInCourse.setValue(lessonPlan);
            //courseTreeView.getRoot().getChildren().add(lessonInCourse);
            courseListView.getItems().add(lessonPlan);
        }
    }

    @FXML public void openCourseFromFiles() {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gymnastics Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        Window mainWindow = courseTreeView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                Course.loadFromFile(chosenFile);
                //courseTreeView.getRoot().getChildren().clear();
                courseListView.getItems().clear();
                App.setCurrentCourse(Course.loadFromFile(chosenFile));
                App.setCurrentCourseFile(chosenFile);
                for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                    //TreeItem<LessonPlan> lessonInCourse = new TreeItem<>();
                    //lessonInCourse.setValue(lessonPlan);
                    //courseTreeView.getRoot().getChildren().add(lessonInCourse);
                    //courseTreeView.getRoot().(lessonPlan);
                    courseListView.getItems().add(lessonPlan);
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error loading Course: " + chosenFile).show();
            }
        }
    }

    @FXML public void saveCourse() throws IOException{
        if (App.getCurrentCourseFile() != null) {
            App.getCurrentCourse().saveToFile(App.getCurrentCourseFile());
        } else {
            System.out.println("error");
        }
    }

    @FXML public void saveCourseAs() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = courseTreeView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        App.getCurrentCourse().saveToFile(chosenFile);
        App.setCurrentCourseFile(chosenFile);

    }
}
