package edu.augustana;

import com.opencsv.exceptions.CsvValidationException;
import edu.augustana.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Course currentCourse;
    private static File currentCourseFile;
    private static LessonPlan currentLessonPlan;
    private static FavoriteCards favoriteCards;
    private static RecentFilesManager recentFilesManager = new RecentFilesManager();

    // Used https://genuinecoder.com/javafx-get-screen-size-of-all-connected-monitors/
    // to help figure out how to get the dimensions of the screen.
    // Used https://stackoverflow.com/questions/68768778/javafx-button-hover-effect for adding stylesheet
    @Override
    public void start(Stage stage) {
        try {
            CardDatabase.addCardsFromAllCSVFiles();
            favoriteCards = new FavoriteCards();
            double height = Screen.getPrimary().getBounds().getHeight();
            double width = Screen.getPrimary().getBounds().getWidth();
            scene = new Scene(loadFXML("home"), width - 25, height - 80);
            scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("mainStylesheet.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            giveWarning("Could not load software.");
            Platform.exit();
        } catch (CsvValidationException e) {
            giveWarning("Error reading from csv files. Check your card pack folders.");
            Platform.exit();
        }
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            giveWarning("Failed to load");
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Course getCurrentCourse() { return currentCourse; }

    public static LessonPlan getCurrentLessonPlan() { return currentLessonPlan; }
    public static File getCurrentCourseFile() { return currentCourseFile;}
    public static void setCurrentCourse(Course course) {
        currentCourse = course;
    }

    public static void setCurrentCourseFile(File file) {
        currentCourseFile = file;
    }

    public static void setCurrentLessonPlan(LessonPlan lessonPlan) {
        currentLessonPlan = lessonPlan;
    }
    public static FavoriteCards getFavoriteCards(){return favoriteCards;}

    public static RecentFilesManager getRecentFilesManager() {
        return recentFilesManager;
    }

    public static void setRecentFilesManager(RecentFilesManager recentFilesManager) { App.recentFilesManager = recentFilesManager; }

    public static void main(String[] args) {
        launch();
    }

    public static void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

}