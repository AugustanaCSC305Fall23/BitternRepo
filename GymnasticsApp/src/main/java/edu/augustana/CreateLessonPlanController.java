package edu.augustana;

import edu.augustana.filters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CreateLessonPlanController {
    private URL location;
    @FXML
    private CheckComboBox<String> eventDropdown;
    @FXML
    private CheckComboBox<String> genderDropdown;
    @FXML
    private CheckComboBox<String> levelDropdown;
    @FXML
    private CheckComboBox<String> modelSexDropdown;
    List<CheckComboBox<String>> listOfDropdowns;
    @FXML
    private FlowPane cardsFlowPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button addCardButton;
    @FXML
    private Button editTitleButton;
    @FXML
    private TextField titleField;
    @FXML
    private Button doneButton;
    @FXML
    private Label titleLabel = new Label();
    @FXML
    private Button cancelButton;

    public static final ObservableList<String> eventFilterChoices = FXCollections.observableArrayList(new String[]{"Beam", "Floor",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Uneven Bars", "Vault"});
    public static final ObservableList<String> genderFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    public static final ObservableList<String> levelFilterChoices = FXCollections.observableArrayList(new String[]{"A", "AB", "AB I", "B AB", "B AB I", "B I", "I", "I A"});
    public static final ObservableList<String> modelSexFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});
    @FXML private TreeView<String> lessonPlanTreeView;
    @FXML
    private ListView<String> cardTitleListView = new ListView<>();
    @FXML
    private Button returnToCourseBtn;
    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();
    //private static LessonPlan currentLessonPlan;
    private static Map<Card, ImageView> selectedCards = new HashMap<>();
    TreeItem<String> root = new TreeItem<>();

    //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
    //To help with tree view

    private void createDropdowns() {
        eventDropdown.getItems().addAll(eventFilterChoices);
        genderDropdown.getItems().addAll(genderFilterChoices);
        levelDropdown.getItems().addAll(levelFilterChoices);
        modelSexDropdown.getItems().addAll(modelSexFilterChoices);
        listOfDropdowns = Arrays.asList(eventDropdown, genderDropdown, levelDropdown, modelSexDropdown);
    }

    @FXML
    void goToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    void returnToCourseHandler() throws IOException {
        App.setRoot("course_view");
    }

    private static List<String> getCheckedItems(CheckComboBox<String> dropdown) {
        return dropdown.getCheckModel().getCheckedItems();
    }

    @FXML
    void applyFiltersAction() {
        cardsFlowPane.getChildren().clear();
        FilterControl.updateFilterLists(getCheckedItems(eventDropdown), getCheckedItems(genderDropdown), getCheckedItems(levelDropdown), getCheckedItems(modelSexDropdown));

        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            Card card = fullCardCollection.getCardByID(cardId);
            if (FilterControl.checkIfAllFiltersMatch(card)) {
                ImageView cardImageView = new ImageView(card.getImage());
                cardImageView.setOnMouseClicked(this::selectCardAction);
                cardsFlowPane.getChildren().add(cardImageView);
            }
        }
        FilterControl.resetDesiredFiltersLists();
    }

    @FXML
    void clearFiltersAction() {
        FilterControl.resetDesiredFiltersLists();
        cardsFlowPane.getChildren().clear();
        drawCardSet();
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            if (dropdown.getCheckModel().getCheckedItems() != null) {
                List<Integer> checkedIndices = dropdown.getCheckModel().getCheckedIndices();
                for (int i = checkedIndices.size() - 1; i >= 0; i--) {
                    dropdown.getCheckModel().toggleCheckState(checkedIndices.get(i));
                }
            }
        }
    }

    @FXML
    void searchAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            List<String> searchWordList = new ArrayList<>();
            for (String word : searchField.getText().split("\\s+")) {
                searchWordList.add(word.toLowerCase());
            }
            SearchFilter searchFilter = new SearchFilter(searchWordList);
            cardsFlowPane.getChildren().clear();
            for (String cardId : fullCardCollection.getSetOfCardIds()) {
                Card card = fullCardCollection.getCardByID(cardId);
                if (searchFilter.matchesFilters(card)) {
                    ImageView cardImageView = new ImageView(card.getImage());
                    cardImageView.setOnMouseClicked(this::selectCardAction);
                    cardsFlowPane.getChildren().add(cardImageView);
                }
            }
        }
    }

    private void selectCardAction(MouseEvent event) {
        if (event.getTarget().getClass() == ImageView.class) {
            ImageView cardView = (ImageView) event.getTarget();
            for (String cardId : fullCardCollection.getSetOfCardIds()) {
                Card card = fullCardCollection.getCardByID(cardId);
                if (card.getImage().equals(cardView.getImage())) {
                    if (!selectedCards.containsKey(card)) {
                        cardView.setEffect(new DropShadow(10, Color.BLACK));
                        selectedCards.put(card, cardView);
                    } else {
                        cardView.setEffect(null);
                        selectedCards.remove(card);
                    }
                }
            }
        }
    }

    private void selectCardInListView(MouseEvent event) {

    }

    private void drawCardSet() {
        List<Image> imageList = CardDatabase.getListOfImages();
        for (Image image : imageList) {
            ImageView cardImageView = new ImageView(image);
            cardImageView.setOnMouseClicked(this::selectCardAction);
            cardsFlowPane.getChildren().add(cardImageView);
        }
    }

    @FXML
    private void initialize() {
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        ImageView buttonImageView = new ImageView(new Image(getClass().getResource("images/plusSign.png").toString()));
        buttonImageView.setFitHeight(20.0);
        buttonImageView.setFitWidth(20.0);
        addCardButton.setMaxSize(25.0, 25.0);
        addCardButton.setGraphic(buttonImageView);
        titleLabel.setText(App.getCurrentLessonPlan().getTitle());
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
        if (eventDropdown.getItems().isEmpty()) {
            createDropdowns();
        }
        drawCardSet();
        //add all the cards from the lesson plan but have only code and title
        for (Card card : App.getCurrentLessonPlan().getCardList()) {
            cardTitleListView.getItems().add(card.getCode() + ", " + card.getTitle());
        }
        root = new TreeItem<String>(App.getCurrentLessonPlan().getTitle());
        lessonPlanTreeView.setRoot(root);
        lessonPlanTreeView.setShowRoot(false);
        if(!App.getCurrentLessonPlan().isLessonPlanEmpty()){
            for(String event : App.getCurrentLessonPlan().getEventInPlanList().keySet()){
                TreeItem<String> newEvent = new TreeItem<>(event);
                for(Card card : App.getCurrentLessonPlan().getEventInPlanList().get(event)){
                    newEvent.getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
                }
                root.getChildren().add(newEvent);
            }
        }
    }

    @FXML
    void switchToEditTitleView() {
        titleLabel.setVisible(false);
        lessonPlanTreeView.setVisible(false);
        titleField.setVisible(true);
        doneButton.setVisible(true);
        editTitleButton.setVisible(false);
        cancelButton.setVisible(true);
    }

    @FXML
    void setTitle() {
        String title = titleField.getText();
        if (!title.isEmpty()) {
            App.getCurrentLessonPlan().changeTitle(title);
            titleLabel.setText(title);
            Font titleFont = Font.font("Times New Roman", FontWeight.BOLD, 40);
            titleLabel.setFont(titleFont);
            switchToLessonOutlineView();
        } else {
            giveWarning("Cannot have empty title.");
        }
    }

    @FXML
    private void switchToLessonOutlineView() {
        titleLabel.setVisible(true);
        lessonPlanTreeView.setVisible(true);
        titleField.setVisible(false);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
        editTitleButton.setVisible(true);
    }

    @FXML
    private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void setCurrentLessonPlan(LessonPlan lessonPlan) {
        App.setCurrentLessonPlan(lessonPlan);
    }

    @FXML
    void addCardsToLessonPlan() {
        if (!selectedCards.isEmpty()) {
            for (Card card : selectedCards.keySet()) {
                App.getCurrentLessonPlan().addCardToList(card);
                addToTreeView(card);
                selectedCards.get(card).setEffect(null);
            }
            selectedCards.clear();
        } else {
            giveWarning("No card selected.");
        }
    }
    private void addToTreeView(Card card){
        if(!App.getCurrentLessonPlan().eventInPlanList(card)){
            App.getCurrentLessonPlan().addEventToPlanList(card);
            TreeItem<String> newEvent = new TreeItem<>(card.getEvent());
            newEvent.getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
            root.getChildren().add(newEvent);
        }else{
            App.getCurrentLessonPlan().addCardToEvent(card);
            int eventIndex = App.getCurrentLessonPlan().getEventIndexes().indexOf(card.getEvent());
            root.getChildren().get(eventIndex).getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
        }
    }

    @FXML
    public void removeCardFromLessonPlan() {
    }

}