package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.*;
import edu.augustana.filters.*;
import edu.augustana.structures.*;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class CreateLessonPlanController {

    // dropdowns
    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;
    List<CheckComboBox<String>> listOfDropdowns;

    @FXML private FlowPane allCardsFlowPane;
    @FXML private Tab allCardsTab;
    @FXML private FlowPane favoriteCardsFlowPane;
    @FXML private TabPane cardsTabPane;
    @FXML private Tab favoriteCardsTab;

    @FXML private TextField searchField;
    @FXML private Button addCardBtn;
    @FXML private Button favoriteBtn;
    @FXML private Button removeFavoriteBtn;
    @FXML private TextField titleField;

    // zoomed-in card elements
    @FXML private VBox zoomedInCardVBox;
    @FXML private Label eventLabel;
    @FXML private ImageView zoomedInCard;
    @FXML private Label equipmentLabel;

    @FXML private AnchorPane lessonOutlinePane;

    // filter choices
    public static final ObservableList<String> eventFilterChoices = FXCollections.observableArrayList(new String[]{"Beam", "Floor",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Uneven Bars", "Vault"});
    public static final ObservableList<String> genderFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    public static final ObservableList<String> levelFilterChoices = FXCollections.observableArrayList(new String[]{"Beginner", "Advanced Beginner", "Intermediate", "Advanced"});
    public static final ObservableList<String> modelSexFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});
    @FXML private TreeView<String> lessonPlanTreeView;


    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();
    private List<CardView> selectedCards = new ArrayList<>();
    private List<CardView> cardViewList = new ArrayList<>();
    TreeItem<String> root = new TreeItem<>();

    @FXML
    private void initialize() throws MalformedURLException {
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        //addImagesToButton("Symbols/plusSign.png", addCardBtn);
        //addImagesToButton("Symbols/heart.png", favoriteBtn);
        setUpTitle();
        if (eventDropdown.getItems().isEmpty()) {
            createDropdowns();
        }
        // loop through all cards, making a CardView for each card and adding it to the cardViewList
        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            CardView newCardView = new CardView(fullCardCollection.getCardByID(cardId));
            cardViewList.add(newCardView);
        }
        cardsTabPane.getSelectionModel().select(allCardsTab);
        drawCardSet(findAndSetFlowPane(), cardViewList);
        setUpTreeView();
    }
    /* private void addImagesToButton(String path, Button toAddImageTo) throws MalformedURLException {
        String imageURL = new File(path).toURI().toURL().toString();
        ImageView buttonImageView = new ImageView(new Image(imageURL));
        buttonImageView.setFitHeight(20.0);
        buttonImageView.setFitWidth(20.0);
        toAddImageTo.setMaxSize(25.0, 25.0);
        toAddImageTo.setGraphic(buttonImageView);
    } */

    private void setUpTreeView(){
        //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
        //To help with tree view
        root = new TreeItem<>(App.getCurrentLessonPlan().getTitle());
        lessonPlanTreeView.setRoot(root);
        lessonPlanTreeView.setShowRoot(false);
        //System.out.println(App.getCurrentLessonPlan().getLessonPlan().toString());
        if(!App.getCurrentLessonPlan().isLessonPlanEmpty()){
            Card card;
      /*      for(String event : App.getCurrentLessonPlan().getEventInPlanList().keySet()){
                TreeItem<String> newEvent = new TreeItem<>(event);
                for(String cardID : App.getCurrentLessonPlan().getEventInPlanList().get(event)){
                    card = CardDatabase.getFullCardCollection().getCardByID(cardID);
                    newEvent.getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
                }
                root.getChildren().add(newEvent);
            }*/
            for (ListIterator<Category> it = App.getCurrentLessonPlan().getLessonPlan().listIterator(); it.hasNext();) {
                Category event = it.next();
                TreeItem<String> newEvent = new TreeItem<>(event.getCategoryHeading());
                for(String cardID : event.getCardsInList()){
                    card = CardDatabase.getFullCardCollection().getCardByID(cardID);
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
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            dropdown.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
                public void onChanged(ListChangeListener.Change<? extends String> c) {
                    applyFiltersAction();
                }
            });
        }
    }

    private void drawCardSet(FlowPane cardsFlowPane, List<CardView> cardViewList) {
        for (CardView cardView : cardViewList) {
            //cardView.setFitWidth(260.0);
            //cardView.setFitHeight(195.0);
            cardsFlowPane.getChildren().add(cardView);
            cardView.setOnMouseClicked(this::selectCardAction);
            Animation delayAnim = new PauseTransition(Duration.seconds(1));

            cardView.setOnMouseEntered(e -> {
                delayAnim.playFromStart();
                delayAnim.setOnFinished(event -> {
                    try {
                        zoomInOnImage(cardView);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            });

            cardView.setOnMouseExited(e -> {
                delayAnim.stop();
                exitZoomedView();
            });
        }
    }

    @FXML void zoomInOnImage(CardView cardView) throws MalformedURLException {
        eventLabel.setText(cardView.getCard().getEvent());
        zoomedInCard.setImage(cardView.getCard().getImage());
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

    @FXML void setUpTitle() {
        if (App.getCurrentLessonPlan().getTitle() != null) {
            titleField.setText(App.getCurrentLessonPlan().getTitle());
            titleField.setFont(new Font("Georgia Bold", 36.0));
        } else {
            titleField.setText(titleField.getPromptText());
            titleField.setFont(new Font("System Italic", 36.0));
        }
        TitleEditor titleEditor = new TitleEditor(titleField, new Font("Georgia", 40.0), new Font("Georgia Bold", 40.0));
        titleField.setOnMouseClicked(e -> titleEditor.editTitle());
        titleField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                titleEditor.lockInTitle();
                if (!titleField.getText().equals(titleField.getPromptText())) {
                    App.getCurrentLessonPlan().setTitle(titleField.getText());
                } else {
                    App.getCurrentLessonPlan().setTitle(null);
                }
            }
        });
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

    private void selectCardAction(MouseEvent event) {
        if (event.getTarget() instanceof CardView) {
            CardView cardViewSelected = (CardView) event.getTarget();
            if (!selectedCards.contains(cardViewSelected)) {
                cardViewSelected.setEffect(new InnerShadow(30, Color.ORCHID));
                selectedCards.add(cardViewSelected);
            } else {
                cardViewSelected.setEffect(null);
                selectedCards.remove(cardViewSelected);
            }
            exitZoomedView();
        }
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
            /*if (!App.getCurrentLessonPlan().cardInPlanList(card)){
                App.getCurrentLessonPlan().addCardToEvent(card);
                int eventIndex = App.getCurrentLessonPlan().getEventIndexes().indexOf(card.getEvent());
                root.getChildren().get(eventIndex).getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
            }*/
            if (!App.getCurrentLessonPlan().cardInPlanList(card)){
                //System.out.println("card is not in list");
                App.getCurrentLessonPlan().addCardToEvent(card);
                root.getChildren().get(App.getCurrentLessonPlan().getLessonPlan().get(card.getEvent())).getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
            }
        }
    }

    @FXML
    public void removeCardFromLessonPlan() {
        if (lessonPlanTreeView.getSelectionModel().getSelectedItem() != null) {
            String cardToRemove = (lessonPlanTreeView.getSelectionModel().getSelectedItem().getValue());
            System.out.println(cardToRemove);
            App.getCurrentLessonPlan().removeCard(cardToRemove);
            setUpTreeView();
        }
    }

    @FXML private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void printLessonPlan() throws IOException {
        Map<String, List<Card>> eventToCardMap = App.getCurrentLessonPlan().getMapOfCardsFromID(App.getCurrentLessonPlan().getLessonPlan());
        String lessonPlanTitle = App.getCurrentLessonPlan().getTitle();

        boolean cardDisplay;
        boolean landscapeDisplay = false;

        // If true, show cards. Else, show text only
        cardDisplay = promptCardDisplay();

        // If true shows landscape mode. Else, show portrait mode
        if (cardDisplay) {
            landscapeDisplay = promptPageFormat();
        }


        new PrintStaging(lessonPlanTitle, eventToCardMap, "lesson_plan_creator", cardDisplay, landscapeDisplay);
        App.setRoot("print_preview");
    }

    @FXML private boolean promptCardDisplay() {
        // Used https://stackoverflow.com/questions/36309385/how-to-change-the-text-of-yes-no-buttons-in-javafx-8-alert-dialogs
        ButtonType cardImageBtn = new ButtonType("Card Image", ButtonBar.ButtonData.OK_DONE);
        ButtonType textOnlyBtn = new ButtonType("Text Only", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like the page to be show card images, or be text only?", cardImageBtn, textOnlyBtn);
        alert.setTitle("Confirm");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(textOnlyBtn) == cardImageBtn) {
            return true;
        } else {
            return false;
        }
    }

    @FXML private boolean promptPageFormat() {
        // Used https://stackoverflow.com/questions/36309385/how-to-change-the-text-of-yes-no-buttons-in-javafx-8-alert-dialogs
        ButtonType landscapeBtn = new ButtonType("Landscape", ButtonBar.ButtonData.OK_DONE);
        ButtonType portraitBtn = new ButtonType("Portrait", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like the page to be in Landscape or Portrait mode?", landscapeBtn, portraitBtn);
        alert.setTitle("Confirm");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(portraitBtn) == landscapeBtn) {
            return true;
        } else {
            return false;
        }
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