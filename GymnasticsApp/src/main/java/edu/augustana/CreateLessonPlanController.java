package edu.augustana;

import edu.augustana.filters.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private static final SearchFilter searchFilter = new SearchFilter();

    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;
    @FXML private FlowPane cardsFlowPane;
    @FXML private TextField searchField;
    @FXML private Button addCardButton;
    @FXML private Button editTitleButton;
    @FXML private TextField titleField;
    @FXML private Button doneButton;
    @FXML private Label titleLabel = new Label();
    @FXML private Button cancelButton;
    @FXML private ListView<String> lessonPlanListView = new ListView<>();
    private static Course currentCourse;
    private static LessonPlan currentLessonPlan;
    private static Card selectedCard;
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
    @FXML void applyFilters() {
        cardsFlowPane.getChildren().clear();
        fillLists();
        for (Card card : FileReader.getCardCollection().getCardList()) {
            if (eventFilter.matchCheckbox(card) && genderFilter.matchCheckbox(card) && levelFilter.matchCheckbox(card) && modelSexFilter.matchCheckbox(card)){
                ImageView cardImageView = new ImageView(card.getImage());
                cardImageView.setOnMouseClicked(this::selectedCard);
                cardsFlowPane.getChildren().add(cardImageView);
            }
        }
        eventFilter.resetFilter();
        genderFilter.resetFilter();
        levelFilter.resetFilter();
        modelSexFilter.resetFilter();
    }
    @FXML void clearFilters() {
        if (genderDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> genderCheckIndex = genderDropdown.getCheckModel().getCheckedIndices();
            for (int i = genderDropdown.getCheckModel().getCheckedItems().size() - 1; i >= 0; i--){
                genderDropdown.getCheckModel().toggleCheckState(genderCheckIndex.get(i));
            }
        }
        if (levelDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> levelCheckIndex = levelDropdown.getCheckModel().getCheckedIndices();
            for (int i = levelDropdown.getCheckModel().getCheckedItems().size() - 1; i >= 0; i--){
                levelDropdown.getCheckModel().toggleCheckState(levelCheckIndex.get(i));
            }
        }
        if (eventDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> eventCheckIndex = eventDropdown.getCheckModel().getCheckedIndices();
            for (int i = eventDropdown.getCheckModel().getCheckedItems().size() - 1; i >= 0; i--){
                eventDropdown.getCheckModel().toggleCheckState(eventCheckIndex.get(i));
            }
        }
        if (modelSexDropdown.getCheckModel().getCheckedItems() != null){
            List<Integer> modelSexCheckIndex = modelSexDropdown.getCheckModel().getCheckedIndices();
            for (int i = modelSexDropdown.getCheckModel().getCheckedItems().size() - 1; i >= 0; i--){
                modelSexDropdown.getCheckModel().toggleCheckState(modelSexCheckIndex.get(i));
            }
        }
        cardsFlowPane.getChildren().clear();
        drawCardSet();
    }
    @FXML void searchAction(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            searchFilter.setFilter(searchField.getText());
            cardsFlowPane.getChildren().clear();
            for(Card card : FileReader.getCardCollection().getCardList()){
                if(searchFilter.match(card)){
                    ImageView cardImageView = new ImageView(card.getImage());
                    cardImageView.setOnMouseClicked(this::selectedCard);
                    cardsFlowPane.getChildren().add(cardImageView);
                }
            }
            searchFilter.resetFilter();
        }
    }
    private void selectedCard(MouseEvent event){
        if(event.getTarget().getClass() == ImageView.class){
            ImageView cardView = (ImageView) event.getTarget();
            Image selectedImage = cardView.getImage();
            for(Card card : FileReader.getCardCollection().getCardList()){
                if(card.getImage().equals(selectedImage)){
                    selectedCard = card;
                }
            }
        }
    }
    private void drawCardSet(){
        List<Image> imageList = FileReader.getImageList();
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
        //For checkbox where I can select multiple items
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
        //add all the cards from the lessonplan but have only code and title
        /*for(Card card : currentLessonPlan.getLessonPlanList()){
            lessonPlanListView.getItems().add(card.getCode() + ", " + card.getTitle());
        }*/
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
    @FXML void addCardToLessonPlan() {
        if(selectedCard != null){
            currentLessonPlan.addCardToList(selectedCard);
            lessonPlanListView.getItems().add(selectedCard.getCode() + ", " + selectedCard.getTitle());
        }
    }
}