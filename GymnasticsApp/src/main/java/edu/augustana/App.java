package edu.augustana;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    //https://www.callicoder.com/java-read-write-csv-file-opencsv/
    public static CardCollection setUpCards() {
        CardCollection cardCollection = new CardCollection();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/edu/augustana/Data/DEMO1.csv").toAbsolutePath());
            CSVReader csvReader = new CSVReader(reader);
            String[] firstLine = csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                cardCollection.addCard(new Card(nextRecord));
            }
            //System.out.println(nextRecord);
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return cardCollection;
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