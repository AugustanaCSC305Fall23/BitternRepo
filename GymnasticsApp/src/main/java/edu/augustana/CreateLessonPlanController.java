package edu.augustana;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private static final ObservableList<String> eventFilters = FXCollections.observableArrayList(new String[]{"Beam", "Floor", "Horizontal Bars",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Vault"});
    private static final ObservableList<String> genderFilters = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    private static final ObservableList<String> levelFilters = FXCollections.observableArrayList(new String[]{"ALL", "A", "AB", "AB I", "B AB", "B AB I", "B I", "I", "I A"});
    private static final ObservableList<String> modelSexFilters = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});

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
    private static Course currentCourse;
    private static LessonPlan currentLessonPlan;
    private static List<String> checkedEventFilters = new ArrayList<>();
    private static List<Character> checkedGenderFilters = new ArrayList<>();
    private static List<String> checkedLevelFilters = new ArrayList<>();
    private static List<Character> checkedModelSexFilters = new ArrayList<>();

    private void createDropdowns() {
        genderDropdown.getItems().addAll(genderFilters);
        levelDropdown.getItems().addAll(levelFilters);
        eventDropdown.getItems().addAll(eventFilters);
        modelSexDropdown.getItems().addAll(modelSexFilters);
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
                        if ((checkedModelSexFilters.isEmpty()) || checkedModelSexFilters.contains(card.getModelSex())) {
                            ImageView cardImageView = new ImageView(card.getImage());
                            cardsFlowPane.getChildren().add(cardImageView);
                        }
                    }
                }
            }
        }
        checkedEventFilters.clear();
        checkedGenderFilters.clear();
        checkedLevelFilters.clear();
        checkedModelSexFilters.clear();
    }

    @FXML void clearFilters(ActionEvent event) {
        if (genderDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> genderCheckIndex = genderDropdown.getCheckModel().getCheckedIndices();
            for (int i = 0; i < genderDropdown.getCheckModel().getCheckedItems().size(); i++){
                genderDropdown.getCheckModel().toggleCheckState(genderCheckIndex.get(i));
            }
        }
        if (levelDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> levelCheckIndex = levelDropdown.getCheckModel().getCheckedIndices();
            for (int i = 0; i < levelDropdown.getCheckModel().getCheckedItems().size(); i++){
                levelDropdown.getCheckModel().toggleCheckState(levelCheckIndex.get(i));
            }
        }
        if (eventDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> eventCheckIndex = eventDropdown.getCheckModel().getCheckedIndices();
            for (int i = 0; i < eventDropdown.getCheckModel().getCheckedItems().size(); i++){
                eventDropdown.getCheckModel().toggleCheckState(eventCheckIndex.get(i));
            }
        }
        if (modelSexDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> eventCheckIndex = modelSexDropdown.getCheckModel().getCheckedIndices();
            for (int i = 0; i < modelSexDropdown.getCheckModel().getCheckedItems().size(); i++){
                modelSexDropdown.getCheckModel().toggleCheckState(eventCheckIndex.get(i));
            }
        }
        cardsFlowPane.getChildren().clear();
        drawCardSet();
    }

    @FXML void searchAction(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){

        }
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
        titleLabel.setText(currentLessonPlan.getTitle());
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
        if(eventDropdown.getItems().isEmpty()){
            createDropdowns();
        }
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
        String title = titleField.getText();
        if (!title.isEmpty()) {
            currentLessonPlan.changeTitle(title);
            titleLabel.setText(title);
            Font titleFont = Font.font("Times New Roman", FontWeight.BOLD, 40);
            titleLabel.setFont(titleFont);
            titleLabel.setVisible(true);
            lessonPlanListView.setVisible(true);
            titleField.setVisible(false);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);
            editTitleButton.setVisible(true);
        } else {
            Warning("Cannot have empty title.");
        }
    }

    @FXML private void cancelSetTitle() {
        editTitleButton.setVisible(true);
        titleLabel.setVisible(true);
        lessonPlanListView.setVisible(true);
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    @FXML private void Warning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void setCurrentCourse(Course course) {
        currentCourse = course;
    }

    public static void setCurrentLessonPlan(LessonPlan lessonPlan) {
        currentLessonPlan = lessonPlan;
    }
}

