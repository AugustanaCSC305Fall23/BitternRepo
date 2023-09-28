package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * Sample Skeleton for 'card_browser.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class CardBrowserController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="homeButton"
    private Button homeButton; // Value injected by FXMLLoader

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'card_browser.fxml'.";

    }
}