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

    /**
     * Creates a new course, adds a new lesson plan to the course, and sets App.currentCourse to null
     */
    public void createNewCourse() {
        App.setCurrentCourse(new Course());
        App.getCurrentCourse().getLessonPlanList().add(new LessonPlan());
        App.setCurrentCourseFile(null);
    }

    /**
     * Opens the file explorer and allows the user top open a file
     * @param mainWindow The parent window for the file chooser's open dialog
     * @return True if the user successfully opens a file, otherwise false
     */
    public boolean openCourseFromFiles(Window mainWindow) {
        //Used https://www.youtube.com/watch?v=hNz8Xf4tMI
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gymnastics Course File");
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                openFileHelper(chosenFile);
                return true;
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error loading Course: " + chosenFile).show();
            }
        }
        return false;
    }

    /**
     * Opens the passed-in file path
     * @param filePath
     * @throws IOException
     */
    public void openRecentFile(String filePath) throws IOException {
        File recentFile = new File(filePath);
        openFileHelper(recentFile);
    }

    private void openFileHelper(File file) throws IOException {
        Course openedCourse = loadFromFile(file);
        App.setCurrentCourse(openedCourse);
        App.setCurrentCourseFile(file);
    }

    /**
     * Serializes the current course and saves it to the current file
     * @throws IOException If the file passed into the new FileWriter cannot be opened
     */
    public void saveCourse() throws IOException {
        if (App.getCurrentCourseFile() != null) {
            saveToFileHelper(App.getCurrentCourseFile());
        }
    }

    /**
     * Serialiazes the current course and saves it to a new file
     * @param mainWindow The parent window for the dialog screen
     * @throws IOException If the file cannot be created
     */
    public void saveCourseAs(Window mainWindow) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Gymnastics Course (*.gymnasticscourse)", "*.gymnasticscourse");
        fileChooser.getExtensionFilters().add(filter);
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveToFileHelper(chosenFile);
        App.setCurrentCourseFile(chosenFile);
    }

    private void saveToFileHelper(File courseFile) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedCourseText = gson.toJson(App.getCurrentCourse());
        PrintWriter writer = new PrintWriter(new FileWriter(courseFile));
        writer.println(serializedCourseText);
        writer.close();
    }

    /**
     * Deserializes the passed in file
     * @param courseFile The file to deserialize
     * @return The course from the deserialized file
     * @throws IOException If the file cannot be opened
     */
    public Course loadFromFile(File courseFile) throws IOException {
        FileReader reader = new FileReader(courseFile);
        Gson gson = new Gson();
        return gson.fromJson(reader, Course.class);
    }

    public void setSelectedLessonPlan(LessonPlan lessonPlan) {
        selectedLessonPlan = lessonPlan;
    }

}
