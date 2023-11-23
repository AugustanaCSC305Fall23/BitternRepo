package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController {

    @FXML private HBox buttonsHBox;
    @FXML private ImageView logoView = new ImageView();

    private ButtonControl buttonControl;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws MalformedURLException {
        buttonControl = new ButtonControl(10);
        setUpButtons();
        String logoURL = new File("Symbols/LOGO.jpeg").toURI().toURL().toString();
        logoView.setImage(new Image(logoURL));
    }

    @FXML
    private void setUpButtons() {
        for (Node node : buttonsHBox.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                btn.setOnMouseEntered(e -> buttonControl.enlargeButton(btn));
                btn.setOnMouseExited(e -> buttonControl.resetButton(btn));
            }
        }
    }

    @FXML
    void exitApp(ActionEvent event) throws IOException {
        App.getFavoriteCards().closeFileWriter();
        Platform.exit();

    }
    @FXML
    private void browseCardsHandler() throws IOException {
        App.setRoot("card_browser");
    }

    @FXML
    private void openCourseEditorAndCreator() throws IOException {
//        Course newCourse = new Course();
//        CourseViewController.setCurrentCourse(newCourse);
        App.setRoot("course_view");

    }
}
