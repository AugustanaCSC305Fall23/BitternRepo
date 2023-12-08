package edu.augustana.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.augustana.App;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class CourseModel {

    private static LessonPlan selectedLessonPlan;
    public void createNewCourse() {
        App.setCurrentCourse(new Course());
        App.setCurrentCourseFile(null);
        App.getCurrentCourse().getLessonPlanList().add(new LessonPlan());
    }

    public boolean openCourseFromFiles(Window mainWindow) {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gymnastics Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                Course openedCourse = loadFromFile(chosenFile);
                App.setCurrentCourse(openedCourse);
                App.setCurrentCourseFile(chosenFile);
                return true;
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error loading Course: " + chosenFile).show();
            }
        }
        return false;
    }


    //has some repeated code from openCourseFromFiles method in this class
    public void openRecentFile(String filePath) throws IOException {
        File recentFile = new File(filePath);
        App.setCurrentCourseFile(recentFile);
        Course openedCourse = loadFromFile(recentFile);
        App.setCurrentCourse(openedCourse);
        App.setCurrentCourseFile(recentFile);
    }

    public void saveCourse() throws IOException {
        if (App.getCurrentCourseFile() != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String serializedCourseText = gson.toJson(App.getCurrentCourse());
            PrintWriter writer = new PrintWriter(new FileWriter(App.getCurrentCourseFile()));
            writer.println(serializedCourseText);
            writer.close();
            saveToFile(App.getCurrentCourseFile());
        }
    }

    public void saveCourseAs(Window mainWindow) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        fileChooser.getExtensionFilters().add(filter);
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveToFile(chosenFile);
        App.setCurrentCourseFile(chosenFile);
    }

    public Course loadFromFile(File courseFile) throws IOException {
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

    public static void setSelectedLessonPlan(LessonPlan lessonPlan) {
        selectedLessonPlan = lessonPlan;
    }

    public static LessonPlan getSelectedLessonPlan() {
        return selectedLessonPlan;
    }
}
