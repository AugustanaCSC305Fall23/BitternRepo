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
import java.net.MalformedURLException;
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
    private TextField titleField;

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
    private static List<ImageView> cardViewList = App.getCardViewList();
    private static Map<Card, ImageView> selectedCards = new HashMap<>();
    TreeItem<String> root = new TreeItem<>();

    @FXML
    private void initialize() throws MalformedURLException {
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        ImageView buttonImageView = new ImageView(new Image(getClass().getResource("plusSign.png").toString()));
        buttonImageView.setFitHeight(20.0);
        buttonImageView.setFitWidth(20.0);
        addCardButton.setMaxSize(25.0, 25.0);
        addCardButton.setGraphic(buttonImageView);
        if (eventDropdown.getItems().isEmpty()) {
            createDropdowns();
        }
        drawCardSet();
        //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
        //To help with tree view
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

    private void createDropdowns() {
        eventDropdown.getItems().addAll(eventFilterChoices);
        genderDropdown.getItems().addAll(genderFilterChoices);
        levelDropdown.getItems().addAll(levelFilterChoices);
        modelSexDropdown.getItems().addAll(modelSexFilterChoices);
        listOfDropdowns = Arrays.asList(eventDropdown, genderDropdown, levelDropdown, modelSexDropdown);
    }

    private void drawCardSet() {
        for (ImageView cardImageView : cardViewList) {
            cardImageView.setOnMouseClicked(this::selectCardAction);
            cardsFlowPane.getChildren().add(cardImageView);
        }
    }

    @FXML void setTitle(String newTitle) {
        if (!newTitle.isEmpty()) {
            App.getCurrentLessonPlan().setTitle(newTitle);
        } else {
            giveWarning("Cannot have empty title.");
        }
    }

    @FXML void goToHome() throws IOException {
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
    void clearFiltersAction() throws MalformedURLException {
        FilterControl.resetDesiredFiltersLists();
        cardsFlowPane.getChildren().clear();
        drawCardSet();
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            List<String> checkedItems = getCheckedItems(dropdown);
            if (checkedItems != null) {
                for (int i = checkedItems.size(); i >= 0; i--) {
                    dropdown.getCheckModel().toggleCheckState(checkedItems.get(i));
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

    //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
    //To help with tree view
    private void addToTreeView(Card card){
        if (!App.getCurrentLessonPlan().eventInPlanList(card)){
            App.getCurrentLessonPlan().addEventToPlanList(card);
            TreeItem<String> newEvent = new TreeItem<>(card.getEvent());
            newEvent.getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
            root.getChildren().add(newEvent);
        } else{
            if (!App.getCurrentLessonPlan().cardInPlanList(card)){
                App.getCurrentLessonPlan().addCardToEvent(card);
                int eventIndex = App.getCurrentLessonPlan().getEventIndexes().indexOf(card.getEvent());
                root.getChildren().get(eventIndex).getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
            }
        }
    }

    @FXML public void editTitle(MouseEvent event) {
        titleField.setFont(new Font("Georgia", 40.0));
        titleField.setEditable(true);
    }

    @FXML public void lockInTitle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            titleField.setFont(new Font("Georgia Bold", 36.0));
            titleField.setEditable(false);
            setTitle(titleField.getText());
        }
    }

    @FXML
    public void removeCardFromLessonPlan() {
    }

    @FXML private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /* @FXML void switchToEditTitleView() {
        cardTitleListView.setVisible(false);
        doneButton.setVisible(true);
        editTitleButton.setVisible(false);
        cancelButton.setVisible(true);
    } */


    /* @FXML private void switchToLessonOutlineView() {
        cardTitleListView.setVisible(true);
        doneButton.setVisible(false);
        cancelButton.setVisible(false);
        editTitleButton.setVisible(true);
    } */
}