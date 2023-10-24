package edu.augustana;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.CheckComboBox;
import java.io.IOException;
import java.util.List;

public class CreateLessonPlanController {

    private final ObservableList<String> genderFilters = FXCollections.observableArrayList();
    private final ObservableList<String> levelFilters = FXCollections.observableArrayList();
    private final ObservableList<String> eventFilters = FXCollections.observableArrayList();

    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private FlowPane cardsFlowPane;
    @FXML private TextField searchField;
    @FXML private Button applyFiltersButton;
    @FXML private Button clearFiltersButton;
    @FXML private Button editTitleButton;
    @FXML private TextField titleField;
    @FXML private Button doneButton;
    @FXML private Label titleLabel = new Label();
    @FXML private ListView<?> lessonPlanListView = new ListView<>();
    private String title = App.getLessonPlan().getTitle();
    private List<String> checkedEventFilters;
    private List<Character> checkedGenderFilters;
    private List<String> checkedLevelFilters;

    private ObservableList<String> createListOfFilters(String[] categoryFilters, ObservableList<String> category) {
        category.addAll(categoryFilters);
        return category;
    }
    @FXML void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    private void fillLists() {
        checkedEventFilters.addAll(eventDropdown.getCheckModel().getCheckedItems());

        for (int i = 0; i < genderDropdown.getCheckModel().getCheckedItems().size(); i++) {
            if (genderDropdown.getCheckModel().getCheckedItems().get(i) == "Boy") {
                checkedGenderFilters.add('B');
            } else if (genderDropdown.getCheckModel().getCheckedItems().get(i) == "Girl") {
                checkedGenderFilters.add('G');
            } else {
                checkedGenderFilters.add('N');
            }
        }

        checkedLevelFilters.addAll(levelDropdown.getCheckModel().getCheckedItems());


    }
    @FXML void applyFilters(ActionEvent event) {
        //Card cardToBeChecked;
        //probably also want to make it where they uncheck an item
        fillLists();
        for (Card card : FileReader.getCardCollection().getCardList()) {
            //cardToBeChecked = FileReader.getCardCollection().getCard(i);
            if ((checkedEventFilters == null) || (checkedEventFilters.contains(card.getEvent()))) {
                if ((checkedGenderFilters == null) || (checkedGenderFilters.contains(card.getGender()))) {
                    if ((checkedLevelFilters == null) || (checkedLevelFilters.contains(card.getLevel()))) {
                        //have card displayed
                    }
                }
            }
        }
    }

    @FXML void clearFilters(ActionEvent event) {
        if(genderDropdown.getCheckModel().getCheckedItems() != null){

        }
        if(levelDropdown.getCheckModel().getCheckedItems() != null){

        }
        if(eventDropdown.getCheckModel().getCheckedItems() != null){

        }
        //checkedItems.clear();
    }

    @FXML void searchAction(KeyEvent event) {

    }

    @FXML
    private void initialize(){
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        //For checkbox where I can select multiple items
        genderDropdown.getItems().addAll(createListOfFilters(new String[]{"Boy", "Girl", "Neutral"}, genderFilters));
        levelDropdown.getItems().addAll(createListOfFilters(new String[]{"Easy", "Medium", "Hard"}, levelFilters));
        eventDropdown.getItems().addAll(createListOfFilters(new String[]{"Beam", "Floor", "Horizontal Bars",
                "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Vault"}, eventFilters));

        titleLabel.setText(title);
        titleField.setVisible(false);
        doneButton.setVisible(false);

        List<Image> imageList = FileReader.getImageList();
        for (Image image : imageList) {
            ImageView cardImageView = new ImageView(image);
            cardsFlowPane.getChildren().add(cardImageView);
        }

    }

    @FXML void openTitleTextBox() {
        titleLabel.setVisible(false);
        lessonPlanListView.setVisible(false);
        titleField.setVisible(true);
        doneButton.setVisible(true);
        editTitleButton.setVisible(false);
    }

    @FXML void setTitle() {
        editTitleButton.setVisible(true);
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

