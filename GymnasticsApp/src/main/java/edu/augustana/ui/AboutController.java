package edu.augustana.ui;

import edu.augustana.App;
import javafx.fxml.FXML;

/**
 * Controls the About screen
 */
public class AboutController {

    @FXML
    private void returnToCourseHandler() {
        App.setRoot("course_view");
    }
}
