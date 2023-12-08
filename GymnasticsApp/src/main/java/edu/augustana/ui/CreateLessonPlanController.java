package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.*;
import edu.augustana.filters.*;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    @FXML private TextField lessonTitleField;

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
    TreeViewManager treeViewManager = new TreeViewManager(App.getCurrentLessonPlan());

    private UndoRedoHandler undoRedoHandler;
    private TitleEditor titleEditor;
    private final FilterHandler filterHandler = new FilterHandler();

    @FXML
    private void initialize() throws MalformedURLException {
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        undoRedoHandler = new UndoRedoHandler(App.getCurrentLessonPlan());
        if (eventDropdown.getItems().isEmpty()) {
            createDropdowns();
        }
        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            CardView newCardView = new CardView(fullCardCollection.getCardByID(cardId));
            cardViewList.add(newCardView);
        }
        cardsTabPane.getSelectionModel().select(allCardsTab);
        drawCardSet(findAndSetFlowPane(), cardViewList);
        setUpTreeView();
        titleEditor = new TitleEditor(lessonTitleField, new Font("Georgia", 36.0), new Font("Georgia Bold", 36.0), 'L');
        titleEditor.initializeTitleFieldEvents();
        titleEditor.setTitleFieldText();
        undoRedoHandler.saveState();
        setDisableButtons(true);
    }

    private void setUpTreeView(){
        //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
        //To help with tree view
        root = new TreeItem<>(App.getCurrentLessonPlan().getTitle());
        lessonPlanTreeView.setRoot(root);
        lessonPlanTreeView.setShowRoot(false);
        treeViewManager.setUpTreeView(root);
    }

    private void createDropdowns() {
        eventDropdown.getItems().addAll(eventFilterChoices);
        genderDropdown.getItems().addAll(genderFilterChoices);
        levelDropdown.getItems().addAll(levelFilterChoices);
        modelSexDropdown.getItems().addAll(modelSexFilterChoices);
        listOfDropdowns = Arrays.asList(eventDropdown, genderDropdown, levelDropdown, modelSexDropdown);
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            dropdown.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) e -> updateFilteredVisibleCards());
        }
    }

    private void drawCardSet(FlowPane cardsFlowPane, List<CardView> cardViewList) {
        for (CardView cardView : cardViewList) {
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
            checkSelectedCardsStatus();
        }
    }

    private void checkSelectedCardsStatus() {
        if (selectedCards.isEmpty()) {
            setDisableButtons(true);
        } else {
            setDisableButtons(false);
        }
    }

    private void setDisableButtons(boolean disable) {
        addCardBtn.setDisable(disable);
        favoriteBtn.setDisable(disable);
        removeFavoriteBtn.setDisable(disable);
    }

    // Used code from MovieTrackerApp
    private void updateFilteredVisibleCards() {
        List<String> searchTermList = Arrays.asList(searchField.getText().split("\\s+"));
        List<String> checkedEvents = getCheckedItems(eventDropdown);
        List<String> checkedGenders = getCheckedItems(genderDropdown);
        List<String> checkedLevels = getCheckedItems(levelDropdown);
        List<String> checkedModelSexes = getCheckedItems(modelSexDropdown);
        for (CardView cardView : cardViewList) {
            CardFilter combinedFilter = filterHandler.getCombinedFilter(searchTermList, checkedEvents, checkedGenders, checkedLevels, checkedModelSexes);
            boolean includeThisCard = combinedFilter.matchesFilters(cardView.getCard());
            cardView.setVisible(includeThisCard);
            cardView.setManaged(includeThisCard);
            cardView.setOnMouseClicked(this::selectCardAction);
        }
    }

    @FXML
    void clearFiltersAction() {
        for (CardView cardView : cardViewList) {
            cardView.setVisible(true);
            cardView.setManaged(true);
        }

        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            List<String> checkedItems = getCheckedItems(dropdown);
            if (checkedItems != null) {
                for (int i = checkedItems.size() - 1; i >= 0; i--) {
                    dropdown.getCheckModel().toggleCheckState(checkedItems.get(i));
                }
            }
        }
    }

    @FXML void searchAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            updateFilteredVisibleCards();
        }
    }

    @FXML
    void addCardsToLessonPlan() {
        if (!selectedCards.isEmpty()) {
            for (CardView cardView : selectedCards) {
                treeViewManager.addToTreeView(cardView.getCard(), root);
                cardView.setEffect(null);
            }
            undoRedoHandler.saveState();
            selectedCards.clear();
        }
    }
    @FXML void addCardsToFavorites() throws IOException {
        if (!selectedCards.isEmpty()) {
            for (CardView cardView : selectedCards) {
                Card card = cardView.getCard();
                App.getFavoriteCards().addFavorite(card);
                cardView.setEffect(null);
            }
            selectedCards.clear();
        }
    }

    @FXML
    void removeFavoriteAction() throws IOException {
        if (!selectedCards.isEmpty()) {
            for (CardView cardView : selectedCards) {
                Card card = cardView.getCard();
                App.getFavoriteCards().deleteFavorite(card);
                App.getFavoriteCards().removeFavoriteCardView(cardView);
                favoriteCardsFlowPane.getChildren().remove(cardView);
            }
            selectedCards.clear();
        } else {
            giveWarning("No card selected.");
        }
    }

    @FXML
    public void removeCardFromLessonPlan() {
        if (lessonPlanTreeView.getSelectionModel().getSelectedItem() != null) {
            String cardToRemove = (lessonPlanTreeView.getSelectionModel().getSelectedItem().getValue());
            App.getCurrentLessonPlan().removeCard(cardToRemove);
            treeViewManager.removeFromTreeView(root);
            undoRedoHandler.saveState();
        }
    }

    public void undo() {
        undoRedoHandler.undo();
        System.out.println("AFTER UNDO in GUI: app current lesson plan =");
        System.out.println(App.getCurrentLessonPlan());
        setUpTreeView();
        titleEditor.setTitleFieldText();
    }

    public void redo() {
        undoRedoHandler.redo();
        setUpTreeView();
        titleEditor.setTitleFieldText();
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

        boolean equipmentDisplay = promptForEquipment();

        new PrintStaging(lessonPlanTitle, eventToCardMap, "lesson_plan_creator", cardDisplay, landscapeDisplay, equipmentDisplay);
        App.setRoot("print_preview");
    }

    private boolean promptCardDisplay() {
        // Used https://stackoverflow.com/questions/36309385/how-to-change-the-text-of-yes-no-buttons-in-javafx-8-alert-dialogs
        ButtonType cardImageBtn = new ButtonType("Card Image", ButtonBar.ButtonData.OK_DONE);
        ButtonType textOnlyBtn = new ButtonType("Text Only", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like the page to be show card images, or be text only?", cardImageBtn, textOnlyBtn);
        alert.setTitle("Confirm");

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(textOnlyBtn) == cardImageBtn;
    }

    private boolean promptPageFormat() {
        // Used https://stackoverflow.com/questions/36309385/how-to-change-the-text-of-yes-no-buttons-in-javafx-8-alert-dialogs
        ButtonType landscapeBtn = new ButtonType("Landscape", ButtonBar.ButtonData.OK_DONE);
        ButtonType portraitBtn = new ButtonType("Portrait", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like the page to be in Landscape or Portrait mode?", landscapeBtn, portraitBtn);
        alert.setTitle("Confirm");

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(portraitBtn) == landscapeBtn;
    }

    private boolean promptForEquipment() {
        // Used https://stackoverflow.com/questions/36309385/how-to-change-the-text-of-yes-no-buttons-in-javafx-8-alert-dialogs
        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like a list of all needed equipment printed with your lesson plan?", yesBtn, noBtn);
        alert.setTitle("Confirm");

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(noBtn) == yesBtn;
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
        allCardsTab.getContent().setVisible(false);
        favoriteCardsTab.getContent().setVisible(true);
        if(favoriteCardsFlowPane.getChildren().isEmpty()){
            drawCardSet(favoriteCardsFlowPane, App.getFavoriteCards().getFavoritesCardView());
        }else if(favoriteCardsFlowPane.getChildren().size() < App.getFavoriteCards().getFavoriteCardsList().size()){
            favoriteCardsFlowPane.getChildren().clear();
            drawCardSet(favoriteCardsFlowPane, App.getFavoriteCards().getFavoritesCardView());
        }
    }
    private FlowPane findAndSetFlowPane(){
        if(favoriteCardsTab.isSelected()){
            return favoriteCardsFlowPane;
        }else {
            return allCardsFlowPane;
        }
    }
}