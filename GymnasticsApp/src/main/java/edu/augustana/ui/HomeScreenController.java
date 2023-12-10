package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    void initialize() {
        String logoURL = null;
        try {
            logoURL = new File("Symbols/LOGO.jpeg").toURI().toURL().toString();
        } catch (MalformedURLException e) {
            App.giveWarning("Couldn't find logo symbol");
        }
        logoView.setImage(new Image(logoURL));
        logoView.setEffect(new DropShadow(5, Color.BLACK));
    }

    @FXML
    void exitApp() {
        Platform.exit();
    }
    @FXML
    private void browseCardsHandler() {
        App.setRoot("card_browser");
    }

    @FXML
    private void openCourseEditorAndCreator() {
        if (App.getCurrentCourse() == null) {
            App.setCurrentCourse(new Course());
        }
        App.setRoot("course_view");
    }
}
