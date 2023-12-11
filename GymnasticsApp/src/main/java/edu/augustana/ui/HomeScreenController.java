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
    /**
     * Adds the logo onto the screen when opened
     */
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
    /**
     * Exits the app when the exit button is pushed
     */
    void exitApp() {
        Platform.exit();
    }
    @FXML
    /**
     * Switched to the card_browser.fxml when the "Browse all cards" button is pushed
     */
    private void browseCardsHandler() {
        App.setRoot("card_browser");
    }

    @FXML
    /**
     * Creates a new course if there is not a course currently being worked on
     * and switches to course_view.fxml when the "Open course editor/creator" button
     * is pushed
     */
    private void openCourseEditorAndCreator() {
        if (App.getCurrentCourse() == null) {
            App.setCurrentCourse(new Course());
        }
        App.setRoot("course_view");
    }
}
