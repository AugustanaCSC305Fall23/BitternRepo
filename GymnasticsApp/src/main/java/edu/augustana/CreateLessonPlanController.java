package edu.augustana;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;

public class CreateLessonPlanController {

    private final ObservableList<String> filters = FXCollections.observableArrayList();

    @FXML CheckComboBox<String> filterDropdown = new CheckComboBox<>();

    @FXML private TextField titleField;
    @FXML private Button doneButton;
    @FXML private Label titleLabel = new Label();
    @FXML private ListView<?> lessonPlanListView = new ListView<>();
    private String title = App.getLessonPlan().getTitle();

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
        titleLabel.setText(title);
        titleField.setVisible(false);
        doneButton.setVisible(false);

    }

    @FXML void openTitleTextBox() {
        titleLabel.setVisible(false);
        lessonPlanListView.setVisible(false);
        titleField.setVisible(true);
        doneButton.setVisible(true);
    }

    @FXML void setTitle() {
        title = titleField.getText();
        App.getLessonPlan().changeTitle(title);
        titleLabel.setText(title);
        Font titleFont = Font.font("Times New Roman", FontWeight.BOLD, 40);
        titleLabel.setFont(titleFont);
        titleLabel.setVisible(true);
        lessonPlanListView.setVisible(true);
        titleField.setVisible(false);
        doneButton.setVisible(false);
    }
}

