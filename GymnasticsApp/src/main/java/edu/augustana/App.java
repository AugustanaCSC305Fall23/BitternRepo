package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static LessonPlan lessonPlan;

    @Override
    public void start(Stage stage) throws IOException {
        // Used https://genuinecoder.com/javafx-get-screen-size-of-all-connected-monitors/
        // to help figure out how to get the dimensions of the screen.

        FileReader.fillCardCollection();
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

    public static void createNewLessonPlan() {
        lessonPlan = new LessonPlan();
    }

    public static LessonPlan getLessonPlan() {
       return lessonPlan;
    }

    public static void main(String[] args) {
        launch();
    }

}