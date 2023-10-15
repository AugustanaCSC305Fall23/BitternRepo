package edu.augustana;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;

public class CreateLessonPlanController {

    private final ObservableList<String> filters = FXCollections.observableArrayList();

    @FXML CheckComboBox<String> filterDropdown = new CheckComboBox<>();

    private ObservableList<String> createListOfFilters() {
        filters.addAll("Easy", "Medium", "Hard", "Boy", "Girl", "Neutral", "Beam", "Floor", "Horizontal Bars",
                "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Vault");
        return filters;
    }
    @FXML
    void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void initialize(){
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        //For checkbox where I can select multiple items
        filterDropdown.getItems().addAll(createListOfFilters());
    }
}

