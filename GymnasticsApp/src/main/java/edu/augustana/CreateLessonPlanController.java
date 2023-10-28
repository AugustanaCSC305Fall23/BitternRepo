package edu.augustana;

import edu.augustana.filters.EventFilter;
import edu.augustana.filters.GenderFilter;
import edu.augustana.filters.LevelFilter;
import edu.augustana.filters.ModelSexFilter;
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
    private static final EventFilter eventFilter = new EventFilter();
    private static final GenderFilter genderFilter = new GenderFilter();
    private static final ModelSexFilter modelSexFilter = new ModelSexFilter();
    private static final LevelFilter levelFilter = new LevelFilter();

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

    private void createDropdowns() {
        genderDropdown.getItems().addAll(genderFilter.getFilter());
        levelDropdown.getItems().addAll(levelFilter.getFilter());
        eventDropdown.getItems().addAll(eventFilter.getFilter());
        modelSexDropdown.getItems().addAll(modelSexFilter.getFilter());
    }
    @FXML void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    private void fillLists() {
        eventFilter.setChecked(eventDropdown.getCheckModel().getCheckedItems());
        genderFilter.setChecked(switchToCharacters(genderDropdown));
        modelSexFilter.setChecked(switchToCharacters(modelSexDropdown));
        levelFilter.setChecked(levelDropdown.getCheckModel().getCheckedItems());
    }

    private List<Character> switchToCharacters(CheckComboBox<String> dropdown) {
        List<Character> checkedFilters = new ArrayList<>();
        for (int i = 0; i < dropdown.getCheckModel().getCheckedItems().size(); i++) {
            if (dropdown.getCheckModel().getCheckedItems().get(i).equals("Boy")) {
                checkedFilters.add('M');
            } else if (dropdown.getCheckModel().getCheckedItems().get(i).equals("Girl")) {
                checkedFilters.add('F');
            } else {
                checkedFilters.add('N');
            }
        }
        return checkedFilters;
    }

    @FXML void applyFilters(ActionEvent event) {
        cardsFlowPane.getChildren().clear();
        fillLists();
        for (Card card : FileReader.getCardCollection().getCardList()) {
            if (eventFilter.filter(card) && genderFilter.filter(card) && levelFilter.filter(card) && modelSexFilter.filter(card)){
                ImageView cardImageView = new ImageView(card.getImage());
                cardsFlowPane.getChildren().add(cardImageView);
            }
        }
        eventFilter.resetFilter();
        genderFilter.resetFilter();
        levelFilter.resetFilter();
        modelSexFilter.resetFilter();
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