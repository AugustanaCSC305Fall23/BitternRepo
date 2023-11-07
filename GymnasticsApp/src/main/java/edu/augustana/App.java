package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Course currentCourse = new Course();
    private static File currentCourseFile;

    @Override
    public void start(Stage stage) throws IOException {
        CardDatabase.addCardsFromAllCSVFiles();
        CardDatabase.getListOfImages();
        // Used https://genuinecoder.com/javafx-get-screen-size-of-all-connected-monitors/
        // to help figure out how to get the dimensions of the screen.
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        scene = new Scene(loadFXML("home"), width - 25, height - 80);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Course getCurrentCourse() { return currentCourse; }

    public static void changeCurrentCourse(Course course) {
        currentCourse = course;
    }

    public static void changeCurrentCourseFile(File file) {
        currentCourseFile = file;
    }


    public static void main(String[] args) {
        launch();
    }

}