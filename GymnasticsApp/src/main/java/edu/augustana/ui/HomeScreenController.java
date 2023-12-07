package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class HomeScreenController {

    @FXML private ImageView logoView = new ImageView();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws MalformedURLException {
        String logoURL = new File("Symbols/LOGO.jpeg").toURI().toURL().toString();
        logoView.setImage(new Image(logoURL));
        logoView.setEffect(new DropShadow(5, Color.BLACK));
    }

    @FXML
    void exitApp(ActionEvent event) throws IOException {
        Platform.exit();

    }
    @FXML
    private void browseCardsHandler() throws IOException {
        App.setRoot("card_browser");
    }

    @FXML
    private void openCourseEditorAndCreator() throws IOException {
        if (App.getCurrentCourse() == null) {
            App.setCurrentCourse(new Course());
        }
        App.setRoot("course_view");
    }
}
