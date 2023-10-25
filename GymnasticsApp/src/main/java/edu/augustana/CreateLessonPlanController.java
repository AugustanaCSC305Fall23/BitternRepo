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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CreateLessonPlanController {

    private URL location;
    private final ObservableList<String> eventFilters = FXCollections.observableArrayList();
    private final ObservableList<String> genderFilters = FXCollections.observableArrayList();
    private final ObservableList<String> levelFilters = FXCollections.observableArrayList();
    private final ObservableList<String> modelSexFilters = FXCollections.observableArrayList();

    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;
    @FXML private FlowPane cardsFlowPane;
    @FXML private TextField searchField;
    @FXML private Button applyFiltersButton;
    @FXML private Button clearFiltersButton;
    @FXML private Button editTitleButton;
    @FXML private TextField titleField;
    @FXML private Button doneButton;
    @FXML private Label titleLabel = new Label();
    @FXML private Button cancelButton;
    @FXML private ListView<?> lessonPlanListView = new ListView<>();
    private String title = App.getLessonPlan().getTitle();
    private List<String> checkedEventFilters = new ArrayList<>();
    private List<Character> checkedGenderFilters = new ArrayList<>();
    private List<String> checkedLevelFilters = new ArrayList<>();
    private List<Character> checkedModelSexFilters = new ArrayList<>();

    private ObservableList<String> createListOfFilters(String[] categoryFilters, ObservableList<String> category) {
        category.addAll(categoryFilters);
        return category;
    }
    @FXML void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    private void fillLists() {
        checkedEventFilters.addAll(eventDropdown.getCheckModel().getCheckedItems());
        switchToCharacters(genderDropdown, checkedGenderFilters);
        switchToCharacters(modelSexDropdown, checkedModelSexFilters);
        checkedLevelFilters.addAll(levelDropdown.getCheckModel().getCheckedItems());
    }

    private void switchToCharacters(CheckComboBox<String> dropdown, List<Character> checkedFilters) {
        for (int i = 0; i < dropdown.getCheckModel().getCheckedItems().size(); i++) {
            if (dropdown.getCheckModel().getCheckedItems().get(i).equals("Boy")) {
                checkedFilters.add('M');
            } else if (dropdown.getCheckModel().getCheckedItems().get(i).equals("Girl")) {
                checkedFilters.add('F');
            } else {
                checkedFilters.add('N');
            }
        }
    }

    @FXML void applyFilters(ActionEvent event) {
        cardsFlowPane.getChildren().clear();
        fillLists();
        for (Card card : FileReader.getCardCollection().getCardList()) {
            if ((checkedEventFilters.isEmpty()) || checkedEventFilters.contains(card.getEvent())) {
                if ((checkedGenderFilters.isEmpty()) || checkedGenderFilters.contains(card.getGender())) {
                    if ((checkedLevelFilters.isEmpty()) || checkedLevelFilters.contains(card.getLevel())) {
                        ImageView cardImageView = new ImageView(card.getImage());
                        cardsFlowPane.getChildren().add(cardImageView);
                    }
                }
            }
        }
        checkedGenderFilters.clear();
        checkedLevelFilters.clear();
        checkedEventFilters.clear();
    }

    @FXML void clearFilters(ActionEvent event) {
        if(genderDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> genderCheckIndex = genderDropdown.getCheckModel().getCheckedIndices();
            for(int i = 0; i < genderDropdown.getCheckModel().getCheckedItems().size(); i++){
                genderDropdown.getCheckModel().toggleCheckState(genderCheckIndex.get(i));
            }
        }
        if(levelDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> levelCheckIndex = levelDropdown.getCheckModel().getCheckedIndices();
            for(int i = 0; i < levelDropdown.getCheckModel().getCheckedItems().size(); i++){
                levelDropdown.getCheckModel().toggleCheckState(levelCheckIndex.get(i));
            }
        }
        if(eventDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> eventCheckIndex = eventDropdown.getCheckModel().getCheckedIndices();
            for(int i = 0; i < eventDropdown.getCheckModel().getCheckedItems().size(); i++){
                eventDropdown.getCheckModel().toggleCheckState(eventCheckIndex.get(i));
            }
        }
        cardsFlowPane.getChildren().clear();
        drawCardSet();
    }

    @FXML void searchAction(KeyEvent event) {

    }

    private void drawCardSet(){
        List<Image> imageList = FileReader.getImageList();
        for (Image image : imageList) {
            ImageView cardImageView = new ImageView(image);
            cardsFlowPane.getChildren().add(cardImageView);
        }
    }
    @FXML
    private void initialize(){
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        //For checkbox where I can select multiple items
        genderDropdown.getItems().addAll(createListOfFilters(new String[]{"Boy", "Girl", "Neutral"}, genderFilters));
        levelDropdown.getItems().addAll(createListOfFilters(new String[]{"ALL", "A", "AB", "AB I", "B AB", "B AB I", "B I", "I", "I A"}, levelFilters));
        eventDropdown.getItems().addAll(createListOfFilters(new String[]{"Beam", "Floor", "Horizontal Bars",
                "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Vault"}, eventFilters));

        titleLabel.setText(title);
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);

        drawCardSet();
    }

    @FXML void openTitleTextBox() {
        titleLabel.setVisible(false);
        lessonPlanListView.setVisible(false);
        titleField.setVisible(true);
        doneButton.setVisible(true);
        editTitleButton.setVisible(false);
        cancelButton.setVisible(true);
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
        cancelButton.setVisible(false);
    }

    @FXML private void cancelSetTitle() {
        editTitleButton.setVisible(true);
        titleLabel.setVisible(true);
        lessonPlanListView.setVisible(true);
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
    }
}

