package edu.augustana.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.augustana.App;
import edu.augustana.model.Course;
import edu.augustana.model.LessonPlan;
import edu.augustana.model.RecentFilesManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class CourseMenuControl {

    ListView<LessonPlan> courseListView;
    TextField courseTitleField;
    public CourseMenuControl(ListView<LessonPlan> listView, TextField titleField) {
        courseListView = listView;
        courseTitleField = titleField;
    }

    @FXML public void createNewCourse() {
        courseListView.getItems().clear();
        App.setCurrentCourse(new Course());
        App.setCurrentCourseFile(null);
        App.getCurrentCourse().getLessonPlanList().add(new LessonPlan());
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
        setUpTitle();
    }

    @FXML public void openCourseFromFiles() {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gymnastics Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        Window mainWindow = courseListView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                Course openedCourse = loadFromFile(chosenFile);
                App.setCurrentCourse(openedCourse);
                App.setCurrentCourseFile(chosenFile);
                courseTitleField.setText(openedCourse.getCourseTitle());
                courseListView.getItems().clear();
                for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                    courseListView.getItems().add(lessonPlan);
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error loading Course: " + chosenFile).show();
            }
        }
        App.getUserPreferences().addRecentFile(App.getCurrentCourseFile().getAbsolutePath());
    }

    @FXML public void saveCourse() throws IOException{
        if (App.getCurrentCourseFile() != null) {
            saveToFile(App.getCurrentCourseFile());
        } else {
            System.out.println("error");
        }
    }

    @FXML public void saveCourseAs() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = courseListView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveToFile(chosenFile);
        App.setCurrentCourseFile(chosenFile);
        App.getUserPreferences().addRecentFile(App.getCurrentCourseFile().getPath());
    }

    public static Course loadFromFile(File courseFile) throws IOException {
        FileReader reader = new FileReader(courseFile);
        Gson gson = new Gson();
        return gson.fromJson(reader, Course.class);
    }

    public void saveToFile(File courseFile) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedCourseText = gson.toJson(App.getCurrentCourse());
        PrintWriter writer = new PrintWriter(new FileWriter(courseFile));
        writer.println(serializedCourseText);
        writer.close();
    }

    public void setUpTitle() {
        if (App.getCurrentCourse().getCourseTitle() != null) {
            courseTitleField.setText(App.getCurrentCourse().getCourseTitle());
            courseTitleField.setStyle("-fx-text-fill: white;" + "-fx-background-color: transparent");
            courseTitleField.setFont(new Font("Britannic Bold", 45.0));
        } else {
            courseTitleField.setText(courseTitleField.getPromptText());
            courseTitleField.setStyle("-fx-text-fill: lightGray;" + "-fx-background-color: transparent");
            courseTitleField.setFont(new Font("System Italic", 45.0));
        }
        TitleEditor titleEditor = new TitleEditor(courseTitleField, new Font("Britannic Bold", 45.0), new Font("Britannic Bold", 45.0));
        courseTitleField.setOnMouseClicked(e -> titleEditor.editTitle());
        courseTitleField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                titleEditor.lockInTitle();
                if (!courseTitleField.getText().equals(courseTitleField.getPromptText())) {
                    App.getCurrentCourse().setTitle(courseTitleField.getText());
                } else {
                    App.getCurrentCourse().setTitle(null);
                }
            }
        });
    }
}
