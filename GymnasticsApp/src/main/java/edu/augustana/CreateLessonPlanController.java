package edu.augustana;

import edu.augustana.filters.*;
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
import java.util.Arrays;
import java.util.List;

public class CreateLessonPlanController {
    private URL location;
    private static final EventFilter eventFilter = new EventFilter();
    private static final GenderFilter genderFilter = new GenderFilter();
    private static final ModelSexFilter modelSexFilter = new ModelSexFilter();
    private static final LevelFilter levelFilter = new LevelFilter();

    private static final CategoryFilter categoryFilter = new CategoryFilter();
    private static final CodeFilter codeFilter = new CodeFilter();
    private static final EquipmentFilter equipmentFilter = new EquipmentFilter();
    private static final KeywordsFilter keywordsFilter = new KeywordsFilter();
    private static final TitleFilter titleFilter = new TitleFilter();
    List<CardFilter> listOfFilters = Arrays.asList(eventFilter, genderFilter, levelFilter, modelSexFilter,
            categoryFilter, codeFilter, equipmentFilter, keywordsFilter, titleFilter);
    private static final SearchFilter searchFilter = new SearchFilter();

    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;
    List<CheckComboBox<String>> listOfDropdowns;
    @FXML private FlowPane cardsFlowPane;
    @FXML private TextField searchField;
    @FXML private Button addCardButton;
    @FXML private Button editTitleButton;
    @FXML private TextField titleField;
    @FXML private Button doneButton;
    @FXML private Label titleLabel = new Label();
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private ListView<String> cardsListView = new ListView<>();
    @FXML private Button returnToCourseBtn;
    CardCollection fullCardCollection = CardDatabase.getFullCardCollection();
    private static LessonPlan currentLessonPlan;
    private static Course currentCourse;
    private static Card selectedCard;

    private void createDropdowns() {
        eventDropdown.getItems().addAll(eventFilter.getFilter());
        genderDropdown.getItems().addAll(genderFilter.getFilter());
        levelDropdown.getItems().addAll(levelFilter.getFilter());
        modelSexDropdown.getItems().addAll(modelSexFilter.getFilter());
        listOfDropdowns = Arrays.asList(eventDropdown, genderDropdown, levelDropdown, modelSexDropdown);
    }
    @FXML void goToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML void returnToCourseHandler() throws IOException {
        App.setRoot("course_view");
    }

    private void fillLists() {
        eventFilter.setCheckedFilters(eventDropdown.getCheckModel().getCheckedItems());
        genderFilter.setChecked(switchToCharacters(genderDropdown));
        modelSexFilter.setChecked(switchToCharacters(modelSexDropdown));
        levelFilter.setCheckedFilters(levelDropdown.getCheckModel().getCheckedItems());
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
    @FXML void applyFilters() {
        cardsFlowPane.getChildren().clear();
        fillLists();
        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            Card card = fullCardCollection.getCard(cardId);
            if (eventFilter.matchCheckbox(card) && genderFilter.matchCheckbox(card) && levelFilter.matchCheckbox(card) && modelSexFilter.matchCheckbox(card)){
                ImageView cardImageView = new ImageView(card.getImage());
                cardImageView.setOnMouseClicked(this::selectedCard);
                cardsFlowPane.getChildren().add(cardImageView);
            }
        }
        for (int i = 0; i < 4; i++) {
            listOfFilters.get(i).resetFilter();
        }
    }
    @FXML void clearFilters() {
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            if (dropdown.getCheckModel().getCheckedItems() != null){
                List<Integer> checkIndex = dropdown.getCheckModel().getCheckedIndices();
                for (int i = dropdown.getCheckModel().getCheckedItems().size() - 1; i >= 0; i--){
                    dropdown.getCheckModel().toggleCheckState(checkIndex.get(i));
                }
            }
        }
        cardsFlowPane.getChildren().clear();
        drawCardSet();
    }
    private void setFilters(){
        for (CardFilter filter : listOfFilters) {
            filter.setFilter(searchField.getText());
        }
    }

    private void resetFilters(){
        for (CardFilter filter : listOfFilters) {
            filter.resetFilter();
        }
    }
    @FXML void searchAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchFilter.setFilter(searchField.getText());
            cardsFlowPane.getChildren().clear();
            for (String cardId : fullCardCollection.getSetOfCardIds()) {
                Card card = fullCardCollection.getCard(cardId);
                if (categoryFilter.match(card) || codeFilter.match(card) || equipmentFilter.match(card) ||
                        eventFilter.match(card) || genderFilter.match(card) || keywordsFilter.match(card) || levelFilter.match(card) ||
                        modelSexFilter.match(card) || titleFilter.match(card)) {
                    for (Card card : FileReader.getCardCollection().getCardList()) {
                        if (searchFilter.match(card)) {
                            ImageView cardImageView = new ImageView(card.getImage());
                            cardImageView.setOnMouseClicked(this::selectedCard);
                            cardsFlowPane.getChildren().add(cardImageView);
                        }
                    }
                    searchFilter.resetFilter();
                }
            }
        }
    }
    
    private void selectedCard(MouseEvent event){
        if(event.getTarget().getClass() == ImageView.class){
            ImageView cardView = (ImageView) event.getTarget();
            Image selectedImage = cardView.getImage();
            for(String cardId : fullCardCollection.getSetOfCardIds()){
                Card card = fullCardCollection.getCard(cardId);
                if (card.getImage().equals(selectedImage)){
                    selectedCard = card;
                }
            }
        }
    }
    private void drawCardSet(){
        List<Image> imageList = CardDatabase.getListOfImages();
        for (Image image : imageList) {
            ImageView cardImageView = new ImageView(image);
            cardImageView.setOnMouseClicked(this::selectedCard);
            cardsFlowPane.getChildren().add(cardImageView);
        }
    }
    @FXML
    private void initialize(){
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        ImageView buttonImageView = new ImageView(new Image(getClass().getResource("images/plusSign.png").toString()));
        buttonImageView.setFitHeight(20.0);
        buttonImageView.setFitWidth(20.0);
        addCardButton.setMaxSize(25.0, 25.0);
        addCardButton.setGraphic(buttonImageView);
        titleLabel.setText(currentLessonPlan.getTitle());
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
        if(eventDropdown.getItems().isEmpty()){
            createDropdowns();
        }
        drawCardSet();
        //add all the cards from the lesson plan but have only code and title
        for(Card card : currentLessonPlan.getLessonPlanList()){
            cardsListView.getItems().add(card.getCode() + ", " + card.getTitle());
        }
    }
    @FXML void openTitleTextBox() {
        titleLabel.setVisible(false);
        cardsListView.setVisible(false);
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
            cardsListView.setVisible(true);
            titleField.setVisible(false);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);
            editTitleButton.setVisible(true);
        } else {
            giveWarning("Cannot have empty title.");
        }
    }
    @FXML private void cancelSetTitle() {
        editTitleButton.setVisible(true);
        titleLabel.setVisible(true);
        cardsListView.setVisible(true);
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    @FXML private void giveWarning(String message) {
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
    @FXML void addCardToLessonPlan() {
        if(selectedCard != null){
            currentLessonPlan.addCardToList(selectedCard);
            cardsListView.getItems().add(selectedCard.getCode() + ", " + selectedCard.getTitle());
        }
    }

    @FXML void saveLessonPlan() {
        currentCourse.getLessonPlanList().add(currentLessonPlan);
    }
}