package edu.augustana;

import com.opencsv.exceptions.CsvValidationException;
import edu.augustana.Model.CardDatabase;
import edu.augustana.Model.Course;
import edu.augustana.Model.LessonPlan;
import edu.augustana.UI.CardView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Course currentCourse = new Course();
    private static File currentCourseFile;
    private static LessonPlan currentLessonPlan;
    //private static List<CardView> cardViewList = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException, CsvValidationException {
        CardDatabase.addCardsFromAllCSVFiles();
        // Used https://genuinecoder.com/javafx-get-screen-size-of-all-connected-monitors/
        // to help figure out how to get the dimensions of the screen.
        //createCardViewList();
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        scene = new Scene(loadFXML("home"), width - 25, height - 80);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
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

    public static void main(String[] args) {
        launch();
    }

}