package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.*;
import edu.augustana.filters.*;
import edu.augustana.structures.EventSubcategory;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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

    @FXML private VBox editSubheadingVBox;
    @FXML private TextField editEventHeadingTextField;
    @FXML private Button editEventHeadingBtn;
    @FXML private Button moveBtn;
    @FXML private Button deleteBtn;
    @FXML private Button customNoteBtn;
    @FXML private VBox enterCustomNoteVBox;
    @FXML private TextArea customNoteTextArea;
    @FXML private Label customNoteLabel;

    @FXML private VBox upArrow;
    @FXML private VBox downArrow;

    @FXML private VBox printSetupVBox;
    @FXML private CheckBox cardImagesCheckbox;
    @FXML private CheckBox textOnlyCheckbox;
    @FXML private CheckBox landscapeCheckbox;
    @FXML private CheckBox portraitCheckbox;
    @FXML private CheckBox yesEquipmentCheckbox;
    @FXML private CheckBox noEquipmentCheckbox;


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
    private List<CardView> favoriteCardsSelected = new ArrayList<>();
    private final List<CardView> cardViewList = new ArrayList<>();
    private TreeItem<String> root = new TreeItem<>();
    private final TreeViewManager treeViewManager = new TreeViewManager(App.getCurrentLessonPlan());

    private final UndoRedoHandler undoRedoHandler = new UndoRedoHandler(App.getCurrentLessonPlan());;
    private TitleEditor titleEditor;
    private final FilterHandler filterHandler = new FilterHandler();

    @FXML
    private void initialize() throws MalformedURLException {
        //https://stackoverflow.com/questions/26186572/selecting-multiple-items-from-combobox
        //and https://stackoverflow.com/questions/46336643/javafx-how-to-add-itmes-in-checkcombobox
        editEventHeadingTextField.setVisible(false);
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
        lessonPlanTreeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> treeViewItemSelectedAction());
        titleEditor = new TitleEditor(lessonTitleField, new Font("Georgia", 36.0), new Font("Georgia Bold", 36.0), 'L');
        titleEditor.initializeTitleFieldEvents(undoRedoHandler, App.getCurrentLessonPlan().clone());
        titleEditor.setTitleFieldText();
        undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
        disableButtons();
        if (App.getCurrentLessonPlan().getCustomNote() != null) {
            customNoteBtn.setText("Edit Custom Note");
        }
    }

    private void setUpTreeView(){
        //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
        //To help with tree view
        root = new TreeItem<>(App.getCurrentLessonPlan().getTitle());
        lessonPlanTreeView.setRoot(root);
        lessonPlanTreeView.setShowRoot(false);
        treeViewManager.displayTreeView(root);
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
            cardView.setOnMouseClicked(this::checkNumClicks);
            Animation delayAnim = new PauseTransition(Duration.seconds(1));

            cardView.setOnMouseEntered(e -> {
                cardView.setCursor(Cursor.HAND);
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

    private void checkNumClicks(MouseEvent e) {
        if (e.getTarget() instanceof CardView) {
            CardView cardView = (CardView) e.getTarget();
            if (e.getClickCount() == 2) {
                treeViewManager.addToTreeView(cardView.getCard(), root);
                cardView.setEffect(null);
                undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
            } else if (e.getClickCount() == 1) {
                selectCardAction(cardView);
            }
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

    @FXML void returnToCourseHandler() throws IOException {
        App.setRoot("course_view");
    }

    private static List<String> getCheckedItems(CheckComboBox<String> dropdown) {
        return dropdown.getCheckModel().getCheckedItems();
    }

    private void selectCardAction(CardView cardViewSelected) {
        if (!selectedCards.contains(cardViewSelected)) {
            cardViewSelected.setEffect(new InnerShadow(40, Color.valueOf("#78c6f7")));
            selectedCards.add(cardViewSelected);
            if (favoriteCardsTab.isSelected()) {
                favoriteCardsSelected.add(cardViewSelected);
            }
        } else {
            cardViewSelected.setEffect(null);
            selectedCards.remove(cardViewSelected);
            if (favoriteCardsTab.isSelected()) {
                favoriteCardsSelected.remove(cardViewSelected);
            }
        }
        exitZoomedView();
        checkSelectedCardsStatus();
        deselectTreeViewItem();
    }

    private void checkSelectedCardsStatus() {
        if (selectedCards.isEmpty()) {
            disableButtons();
        } else if (allCardsTab.isSelected()) {
            addCardBtn.setDisable(false);
            favoriteBtn.setDisable(false);
            removeFavoriteBtn.setDisable(true);
        } else if (favoriteCardsTab.isSelected()) {
            addCardBtn.setDisable(false);
            removeFavoriteBtn.setDisable(favoriteCardsSelected.isEmpty());
        }
    }

    private void disableButtons() {
        addCardBtn.setDisable(true);
        favoriteBtn.setDisable(true);
        removeFavoriteBtn.setDisable(true);
    }

    // Used code from MovieTrackerApp
    private void updateFilteredVisibleCards() {
        List<String> searchTermList = Arrays.asList(searchField.getText().split("\\s+"));
        List<String> checkedEvents = getCheckedItems(eventDropdown);
        List<String> checkedGenders = getCheckedItems(genderDropdown);
        List<String> checkedLevels = getCheckedItems(levelDropdown);
        List<String> checkedModelSexes = getCheckedItems(modelSexDropdown);
        CardFilter combinedFilter = filterHandler.getCombinedFilter(searchTermList, checkedEvents, checkedGenders, checkedLevels, checkedModelSexes);
        for (CardView cardView : cardViewList) {
            showMatchingCards(cardView, combinedFilter);
        }
        for (CardView favoriteCardView : App.getFavoriteCards().getFavoritesCardView()) {
            showMatchingCards(favoriteCardView, combinedFilter);
        }
    }

    private void showMatchingCards(CardView cardView, CardFilter combinedFilter) {
        boolean includeThisCard = combinedFilter.matchesFilters(cardView.getCard());
        cardView.setVisible(includeThisCard);
        cardView.setManaged(includeThisCard);
        cardView.setOnMouseClicked(this::checkNumClicks);
    }

    @FXML void clearFiltersAction() {
        for (CardView cardView : cardViewList) {
            cardView.setVisible(true);
            cardView.setManaged(true);
        }
        for (CardView cardView : App.getFavoriteCards().getFavoritesCardView()) {
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

    @FXML void addCardsToLessonPlan() {
        if (!selectedCards.isEmpty()) {
            for (CardView cardView : selectedCards) {
                treeViewManager.addToTreeView(cardView.getCard(), root);
                cardView.setEffect(null);
            }
            undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
            selectedCards.clear();
            disableButtons();
            lessonTitleField.deselect();
            lessonPlanTreeView.requestFocus();
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
            disableButtons();
            lessonTitleField.deselect();
            lessonPlanTreeView.requestFocus();
        }
    }

    @FXML void removeFavoriteAction() throws IOException {
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

    @FXML void removeCardFromLessonPlan() {
        if (lessonPlanTreeView.getSelectionModel().getSelectedItem() != null) {
            String cardToRemove = (lessonPlanTreeView.getSelectionModel().getSelectedItem().getValue());
            App.getCurrentLessonPlan().removeCard(cardToRemove, undoRedoHandler);
            treeViewManager.removeFromTreeView(root);
            undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
        }
    }

    @FXML void undo() {
        undoRedoHandler.undo(App.getCurrentLessonPlan());
        setUpTreeView();
        titleEditor.setTitleFieldText();
        undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
    }

    @FXML void redo() {
        undoRedoHandler.redo(App.getCurrentLessonPlan());
        setUpTreeView();
        titleEditor.setTitleFieldText();
        undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
    }

    private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void printLessonPlanHandler() {
        for (Node child : printSetupVBox.getChildren()) {
            if (child instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) child;
                checkbox.setSelected(false);
            }
        }
        printSetupVBox.setVisible(true);
        setCheckBoxActions();
    }

    private void setCheckBoxActions() {
        cardImagesCheckbox.setOnAction(e -> textOnlyCheckbox.setSelected(false));
        textOnlyCheckbox.setOnAction(e -> cardImagesCheckbox.setSelected(false));
        landscapeCheckbox.setOnAction(e -> portraitCheckbox.setSelected(false));
        portraitCheckbox.setOnAction(e -> landscapeCheckbox.setSelected(false));
        yesEquipmentCheckbox.setOnAction(e -> noEquipmentCheckbox.setSelected(false));
        noEquipmentCheckbox.setOnAction(e -> yesEquipmentCheckbox.setSelected(false));
    }

    @FXML void setUpPrint() throws IOException {
        Map<String, List<Card>> eventToCardMap = App.getCurrentLessonPlan().getMapOfCardsFromID(App.getCurrentLessonPlan().getLessonPlanIndexedMap());
        String lessonPlanTitle = App.getCurrentLessonPlan().getTitle();

        boolean cardDisplay = true;
        boolean landscapeDisplay = true;
        boolean equipmentDisplay = true;
        if (cardImagesCheckbox.isSelected()) {
            cardDisplay = true;
        } else if (textOnlyCheckbox.isSelected()) {
            cardDisplay = false;
        } else {
            giveWarning("Please make a selection for each prompt");
            printLessonPlanHandler();
        }

        if (landscapeCheckbox.isSelected()) {
            landscapeDisplay = true;
        } else if (portraitCheckbox.isSelected()) {
            landscapeDisplay = false;
        } else {
            giveWarning("Please make a selection for each prompt");
            printLessonPlanHandler();
        }

        if (yesEquipmentCheckbox.isSelected()) {
            equipmentDisplay = true;
        } else if (noEquipmentCheckbox.isSelected()) {
            equipmentDisplay = false;
        } else {
            giveWarning("Please make a selection for each prompt");
            printLessonPlanHandler();
        }

        new PrintStaging(lessonPlanTitle, eventToCardMap, "lesson_plan_creator", cardDisplay, landscapeDisplay, equipmentDisplay);
        App.setRoot("print_preview");
    }

    @FXML void cancelPrint() {
        printSetupVBox.setVisible(false);
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

    private void treeViewItemSelectedAction() {
        if (!lessonPlanTreeView.getSelectionModel().isEmpty() &&
                !lessonPlanTreeView.getSelectionModel().getSelectedItem().isLeaf()) {
            editEventHeadingBtn.setVisible(true);
            moveBtn.setVisible(false);
            int selectedIndex = lessonPlanTreeView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != 0) {
                upArrow.setOnMouseClicked(e -> moveEventSubheadingAction(-1));
                upArrow.setCursor(Cursor.HAND);
            } else {
                disableArrowButton(upArrow);
            }
            downArrow.setOnMouseClicked(e -> moveEventSubheadingAction(1));
            downArrow.setCursor(Cursor.HAND);
            deleteBtn.setDisable(true);
        } else if (lessonPlanTreeView.getSelectionModel().isEmpty()) {
            editEventHeadingBtn.setVisible(false);
            moveBtn.setVisible(false);
            disableArrowButton(upArrow);
            disableArrowButton(downArrow);
            deleteBtn.setDisable(true);
        } else if (lessonPlanTreeView.getSelectionModel().getSelectedItem().isLeaf()) {
            editEventHeadingBtn.setDisable(true);
            moveBtn.setVisible(true);
            upArrow.setOnMouseClicked(e -> moveEventSubheadingAction(-1));
            downArrow.setOnMouseClicked(e -> moveEventSubheadingAction(1));
            deleteBtn.setDisable(false);
        }
    }

    private void disableArrowButton(VBox arrowBtn) {
        arrowBtn.setOnMouseClicked(null);
        arrowBtn.setCursor(Cursor.DEFAULT);
    }

    @FXML void deselectTreeViewItem() {
        if (!lessonPlanTreeView.getSelectionModel().isEmpty()) {
            lessonPlanTreeView.getSelectionModel().clearSelection();
        }
        treeViewItemSelectedAction();
    }

    @FXML private void editEventHeadingAction() {
        editEventHeadingTextField.clear();
        editSubheadingVBox.setVisible(true);
        editEventHeadingTextField.setVisible(true);
    }

    @FXML private void setEventHeadingAction() {
        String eventToChange = lessonPlanTreeView.getSelectionModel().getSelectedItem().getValue();
        EventSubcategory eventSubcategory = App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(eventToChange));
        treeViewManager.setHeadingInTreeView(editEventHeadingTextField.getText(), eventSubcategory, root);
        editSubheadingVBox.setVisible(false);
    }

    @FXML private void cancelAction() {
        editSubheadingVBox.setVisible(false);
        enterCustomNoteVBox.setVisible(false);
    }

    private void moveEventSubheadingAction(int direction) {
        String eventHeading = lessonPlanTreeView.getSelectionModel().getSelectedItem().getValue();
        if(App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(eventHeading) >= 0){
            EventSubcategory eventSubcategoryToMove = App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(eventHeading));
            treeViewManager.moveEvent(eventSubcategoryToMove, direction, root);
            undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
        }else{
            String cardID = App.getCurrentLessonPlan().getIDFromDisplayTitle(eventHeading);
            Card cardFromID = CardDatabase.getFullCardCollection().getCardByID(cardID);
            EventSubcategory subcategory = App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(App.getCurrentLessonPlan().getLessonPlanIndexedMap().get(cardFromID.getEvent()));
            treeViewManager.moveCard(subcategory, cardID, direction, root);
            undoRedoHandler.saveState(App.getCurrentLessonPlan().clone());
        }
    }

    @FXML void moveCardAction() {

    }

    @FXML void enterCustomNoteHandler() {
        enterCustomNoteVBox.setVisible(true);
        if (App.getCurrentLessonPlan().getCustomNote() != null) {
            customNoteLabel.setText("Edit Custom Note");
            customNoteTextArea.setText(App.getCurrentLessonPlan().getCustomNote());
        } else {
            customNoteLabel.setText("Enter Custom Note");
            customNoteTextArea.setText(null);
        }
    }

    @FXML void confirmCustomNoteAction() {
        App.getCurrentLessonPlan().setCustomNote(customNoteTextArea.getText());
        if (App.getCurrentLessonPlan().getCustomNote() == null) {
            customNoteBtn.setText("Enter Custom Note");
        } else {
            customNoteBtn.setText("Edit Custom Note");
        }
        enterCustomNoteVBox.setVisible(false);
    }
}