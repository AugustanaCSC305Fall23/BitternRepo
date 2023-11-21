package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.*;
import edu.augustana.filters.*;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
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
    //@FXML
    //private FlowPane cardsFlowPane;
    @FXML
    private FlowPane allCardsFlowPane;
    @FXML
    private Tab allCardsTab;
    @FXML
    private FlowPane favoriteCardsFlowPane;
    @FXML
    private TabPane cardsTabPane;
    @FXML
    private Tab favoriteCardsTab;
    @FXML
    private TextField searchField;
    @FXML
    private Button addCardButton;
    @FXML
    private Button favoriteBtn;
    @FXML
    private Button removeFavoriteBtn;
    @FXML
    private TextField titleField;

    @FXML private VBox zoomedInCardVBox;
    @FXML private Label eventLabel;
    @FXML private ImageView zoomedInCard;
    @FXML private Label equipmentLabel;
    @FXML private AnchorPane lessonOutlinePane;

    public static final ObservableList<String> eventFilterChoices = FXCollections.observableArrayList(new String[]{"Beam", "Floor",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Uneven Bars", "Vault"});
    public static final ObservableList<String> genderFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    public static final ObservableList<String> levelFilterChoices = FXCollections.observableArrayList(new String[]{"Beginner", "Advanced Beginner", "Intermediate", "Advanced"});
    public static final ObservableList<String> modelSexFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});
    @FXML private TreeView<String> lessonPlanTreeView;
    @FXML
    private Button returnToCourseBtn;
    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();
    private List<CardView> selectedCards = new ArrayList<>();
    private List<CardView> cardViewList = new ArrayList<>();
    TreeItem<String> root = new TreeItem<>();

    @FXML
    private void initialize() throws MalformedURLException {
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        addImagesToButton("Symbols/plusSign.png", addCardButton);
        addImagesToButton("Symbols/heart.png", favoriteBtn);
        if (eventDropdown.getItems().isEmpty()) {
            createDropdowns();
        }
        // loop through all cards, making a CardView for each card
        // and adding it to the cardViewList
        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            CardView newCardView = new CardView(fullCardCollection.getCardByID(cardId));
            cardViewList.add(newCardView);
        }
        cardsTabPane.getSelectionModel().select(allCardsTab);
        //cardsTabPane.
        drawCardSet(findAndSetFlowPane(), cardViewList);
        setUpTreeView();
    }
    private void addImagesToButton(String path, Button toAddImageTo) throws MalformedURLException {
        String imageURL = new File(path).toURI().toURL().toString();
        ImageView buttonImageView = new ImageView(new Image(imageURL));
        buttonImageView.setFitHeight(20.0);
        buttonImageView.setFitWidth(20.0);
        toAddImageTo.setMaxSize(25.0, 25.0);
        toAddImageTo.setGraphic(buttonImageView);
    }
    private void setUpTreeView(){
        //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
        //To help with tree view
        root = new TreeItem<>(App.getCurrentLessonPlan().getTitle());
        lessonPlanTreeView.setRoot(root);
        lessonPlanTreeView.setShowRoot(false);
        if(!App.getCurrentLessonPlan().isLessonPlanEmpty()){
            Card card;
            for(String event : App.getCurrentLessonPlan().getEventInPlanList().keySet()){
                TreeItem<String> newEvent = new TreeItem<>(event);
                for(String cardID : App.getCurrentLessonPlan().getEventInPlanList().get(event)){
                    card = CardDatabase.getFullCardCollection().getCardByID(cardID);
                    newEvent.getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
                }
                root.getChildren().add(newEvent);
            }
            titleField.setFont(new Font("Georgia Bold", 36.0));
            titleField.setEditable(false);
            titleField.setText(App.getCurrentLessonPlan().getTitle());
        }
    }

    private void createDropdowns() {
        eventDropdown.getItems().addAll(eventFilterChoices);
        genderDropdown.getItems().addAll(genderFilterChoices);
        levelDropdown.getItems().addAll(levelFilterChoices);
        modelSexDropdown.getItems().addAll(modelSexFilterChoices);
        listOfDropdowns = Arrays.asList(eventDropdown, genderDropdown, levelDropdown, modelSexDropdown);
    }

    private void drawCardSet(FlowPane cardsFlowPane, List<CardView> cardViewList) {
        for (CardView cardView : cardViewList) {
            cardView.setFitWidth(260.0);
            cardView.setFitHeight(195.0);
            cardsFlowPane.getChildren().add(cardView);
            cardView.setOnMouseClicked(this::selectCardAction);
            Animation delayAnim = new PauseTransition(Duration.seconds(1));

            cardView.setOnMouseEntered(e -> {
                delayAnim.playFromStart();
                delayAnim.setOnFinished(event -> zoomInOnImage(cardView));
            });

            cardView.setOnMouseExited(e -> {
                delayAnim.stop();
                exitZoomedView();
            });
        }
    }

    @FXML void zoomInOnImage(CardView cardView) {
        eventLabel.setText(cardView.getCard().getEvent());
        zoomedInCard.setImage(cardView.getImage());
            String equipment = "Equipment: ";
            for (int i = 0; i < cardView.getCard().getEquipment().length; i++) {
                if (i != 0) {
                    equipment = equipment + ", ";
                }
                equipment = equipment + cardView.getCard().getEquipment()[i];
            }
            equipmentLabel.setText(equipment);
            zoomedInCardVBox.setVisible(true);
            GaussianBlur blur = new GaussianBlur();
            for (Node child : lessonOutlinePane.getChildren()) {
                if (child != zoomedInCardVBox) {
                    child.setEffect(blur);
                }
            }
    }

    @FXML void exitZoomedView() {
        zoomedInCardVBox.setVisible(false);
        for (Node child : lessonOutlinePane.getChildren()) {
            child.setEffect(null);
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
        FlowPane cardsFlowPane = findAndSetFlowPane();
        cardsFlowPane.getChildren().clear();
        FilterControl.updateFilterLists(getCheckedItems(eventDropdown), getCheckedItems(genderDropdown), getCheckedItems(levelDropdown), getCheckedItems(modelSexDropdown));
        for (CardView cardView : cardViewList) {
            if (FilterControl.checkIfAllFiltersMatch(cardView.getCard()) && searchFromSearchBar().matchesFilters(cardView.getCard())) {
                cardsFlowPane.getChildren().add(cardView);
                cardView.setOnMouseClicked(this::selectCardAction);
            }
        }
    }

    @FXML
    void clearFiltersAction() throws MalformedURLException {
        FilterControl.resetDesiredFiltersLists();
        findAndSetFlowPane().getChildren().clear();
        drawCardSet(findAndSetFlowPane(), cardViewList);
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            List<String> checkedItems = getCheckedItems(dropdown);
            if (checkedItems != null) {
                for (int i = checkedItems.size() - 1; i >= 0; i--) {
                    dropdown.getCheckModel().toggleCheckState(checkedItems.get(i));
                }
            }
        }
    }

    private SearchFilter searchFromSearchBar(){
        List<String> searchWordList = new ArrayList<>();
        for (String word : searchField.getText().split("\\s+")) {
            searchWordList.add(word.toLowerCase());
        }
        return new SearchFilter(searchWordList);
    }

    @FXML
    void searchAction(KeyEvent event) {
        FlowPane cardsFlowPane = findAndSetFlowPane();
        if (event.getCode() == KeyCode.ENTER) {
            SearchFilter searchFilter = searchFromSearchBar();
            cardsFlowPane.getChildren().clear();
            for (CardView cardView : cardViewList) {
                if (FilterControl.checkIfAllFiltersMatch(cardView.getCard()) && searchFilter.matchesFilters(cardView.getCard())) {
                    cardsFlowPane.getChildren().add(cardView);
                    cardView.setOnMouseClicked(this::selectCardAction);
                }
            }
        }
    }

    private void selectCardAction(MouseEvent event) {
        if (event.getTarget() instanceof CardView) {
            CardView cardViewSelected = (CardView) event.getTarget();
            if (!selectedCards.contains(cardViewSelected)) {
                cardViewSelected.setEffect(new DropShadow(15, Color.BLACK));
                selectedCards.add(cardViewSelected);
            } else {
                cardViewSelected.setEffect(null);
                selectedCards.remove(cardViewSelected);
            }
            exitZoomedView();
        }
    }

    public static void setCurrentLessonPlan(LessonPlan lessonPlan) {
        App.setCurrentLessonPlan(lessonPlan);
    }

    @FXML
    void addCardsToLessonPlan() {
        if (!selectedCards.isEmpty()) {
            for (CardView cardView : selectedCards) {
                addToTreeView(cardView.getCard());
                cardView.setEffect(null);
            }
            selectedCards.clear();
        } else {
            giveWarning("No card selected.");
        }
    }
    @FXML void addCardsToFavorites() throws IOException {
        if (!selectedCards.isEmpty()) {
            for (CardView cardView : selectedCards) {
                Card card = cardView.getCard();
                App.getFavoriteCards().addFavorite(card);
            }
            selectedCards.clear();
        } else {
            giveWarning("No card selected.");
        }
    }

    @FXML
    void removeFavoriteAction(ActionEvent event) {

    }

    /*
    I used https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm and
    https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm to help with the tree view
    thoughout this class
     */
    private void addToTreeView(Card card){
        if (!App.getCurrentLessonPlan().eventInPlanList(card)){
            App.getCurrentLessonPlan().addEventToPlanList(card);
            TreeItem<String> newEvent = new TreeItem<>(card.getEvent());
            newEvent.getChildren().add(new TreeItem<>(card.getCode() + ", " + card.getTitle()));
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

    @FXML
    void printLessonPlan() throws IOException {
        Map<String, List<Card>> eventToCardMap = App.getCurrentLessonPlan().getMapOfCardsFromID(App.getCurrentLessonPlan().getEventInPlanList());
        String lessonPlanTitle = App.getCurrentLessonPlan().getTitle();

        new PrintStaging(lessonPlanTitle, eventToCardMap, "lesson_plan_creator");
        App.setRoot("print_preview");
    }
    @FXML
    void switchToAllCards() {
        if(!(favoriteCardsTab == null)){
            favoriteCardsTab.getContent().setVisible(false);
        }
        allCardsTab.getContent().setVisible(true);
    }

    @FXML
    void switchToFavoriteCards() {
        //System.out.println(cardsTabPane.getSelectionModel().isSelected(0));
        allCardsTab.getContent().setVisible(false);
        favoriteCardsTab.getContent().setVisible(true);
        //System.out.println(cardsTabPane);
        //cardsTabPane.getSelectionModel().isSelected(0);
        //cardsTabPane.getSelectionModel().select(favoriteCardsTab);
        if(favoriteCardsFlowPane.getChildren().isEmpty()){
            drawCardSet(favoriteCardsFlowPane, App.getFavoriteCards().getFavoritesCardView());
        }else if(favoriteCardsFlowPane.getChildren().size() < App.getFavoriteCards().getFavoriteCardsList().size()){
            favoriteCardsFlowPane.getChildren().clear();
            drawCardSet(favoriteCardsFlowPane, App.getFavoriteCards().getFavoritesCardView());
        }
        //drawCardSet(favoriteCardsFlowPane, App.getFavoriteCards().getFavoritesCardView());
    }
    private FlowPane findAndSetFlowPane(){
        if(favoriteCardsTab.isSelected()){
            return favoriteCardsFlowPane;
        }else {
            return allCardsFlowPane;
        }
    }
}