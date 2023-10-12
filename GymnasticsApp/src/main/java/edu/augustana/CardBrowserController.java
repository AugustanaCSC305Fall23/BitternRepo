package edu.augustana;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Sample Skeleton for 'card_browser.fxml' Controller Class
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class CardBrowserController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="applyFiltersBtn"
    private Button applyFiltersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="clearFiltersBtn"
    private Button clearFiltersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="backToLessonPlanBtn"
    private Button backToLessonPlanBtn; // Value injected by FXMLLoader

    @FXML // fx:id="beamCheckBox"
    private CheckBox beamCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="boyCheckBox"
    private CheckBox boyCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="easyCheckBox"
    private CheckBox easyCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="floorCheckBox"
    private CheckBox floorCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="girlCheckBox"
    private CheckBox girlCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="hardCheckBox"
    private CheckBox hardCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="homeButton"
    private Button homeButton; // Value injected by FXMLLoader

    @FXML // fx:id="horizontalBarsCheckBox"
    private CheckBox horizontalBarsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="mediumCheckBox"
    private CheckBox mediumCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="neutralCheckBox"
    private CheckBox neutralCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="parallelBarsCheckBox"
    private CheckBox parallelBarsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="pommelHorseCheckBox"
    private CheckBox pommelHorseCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="ringsCheckBox"
    private CheckBox ringsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="strengthCheckBox"
    private CheckBox strengthCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="unevenBarsCheckBox"
    private CheckBox unevenBarsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="vaultCheckBox"
    private CheckBox vaultCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="trampolineCheckBox"
    private CheckBox trampolineCheckBox;

    List<String> filters = new ArrayList<>();

    private List<CheckBox> createListOfFilters() {
        return Arrays.asList(easyCheckBox, mediumCheckBox, hardCheckBox, boyCheckBox, girlCheckBox,
                neutralCheckBox, beamCheckBox, unevenBarsCheckBox, strengthCheckBox, floorCheckBox,
                vaultCheckBox, ringsCheckBox, pommelHorseCheckBox, parallelBarsCheckBox, horizontalBarsCheckBox,
                trampolineCheckBox);
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void returnToLessonPlanHandler(ActionEvent event) throws IOException {
        App.setRoot("create_lesson_plan");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'card_browser.fxml'.";

    }
    @FXML
    private void applyFiltersAction(ActionEvent event) throws IOException {
        for (CheckBox cb : createListOfFilters()) {
            if (cb.isSelected()) {
                filters.add(cb.getText());
            }
        }
    }

    @FXML
    private void clearFiltersAction(ActionEvent event) throws IOException {
        filters.clear();
        for (CheckBox cb : createListOfFilters()) {
            if (cb.isSelected()) {
                cb.fire();
            }
        }
    }
}