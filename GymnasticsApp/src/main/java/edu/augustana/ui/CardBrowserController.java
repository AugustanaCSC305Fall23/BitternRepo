package edu.augustana.ui;

import edu.augustana.*;
import edu.augustana.filters.CardFilter;
import edu.augustana.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.net.MalformedURLException;
import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.CheckComboBox;

public class CardBrowserController {

    @FXML private Button printCardBtn;
    @FXML private Button zoomBtn;

    @FXML private FlowPane cardsFlowPane;
    @FXML private Pane cardsAnchorPane;

    // filter dropdowns and search field
    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;
    @FXML private TextField searchField;

    // zoomed-in card elements
    @FXML private VBox zoomedInCardVBox;
    @FXML private Label eventLabel;
    @FXML private ImageView zoomedInCard;
    @FXML private Label equipmentLabel;

    // non-fxml elements
    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();
    public static final ObservableList<String> eventFilterChoices = FXCollections.observableArrayList(new String[]{"Beam", "Floor",
            "Horizontal Bar", "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Uneven Bars", "Vault"});
    public static final ObservableList<String> genderFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    public static final ObservableList<String> levelFilterChoices = FXCollections.observableArrayList(new String[]{"Beginner", "Advanced Beginner", "Intermediate", "Advanced"});
    public static final ObservableList<String> modelSexFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});
    private List<CheckComboBox<String>> listOfDropdowns;
    private List<CardView> selectedCards = new ArrayList<>();
    private final List<CardView> cardViewList = new ArrayList<>();
    private final FilterHandler filterHandler = new FilterHandler();

    // Initializes the UI for the card browser
    @FXML void initialize() {
        createDropdowns();
        try {
            for (String cardId : fullCardCollection.getSetOfCardIds()) {
                CardView newCardView = new CardView(fullCardCollection.getCardByID(cardId));
                cardViewList.add(newCardView);
            }
            drawCardSet();
            disableButtons(true);
        } catch (MalformedURLException e) {
            App.giveWarning("Couldn't load card images. Check your thumbnails folder in the card pack.");
        }
    }

    // Adds the filter choices to the dropdowns and adds listeners
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

    // Draws the cards on the screen
    private void drawCardSet() {
        for (CardView cardView : cardViewList) {
            cardView.setOnMouseClicked(this::selectCardAction);
            cardsFlowPane.getChildren().add(cardView);
        }
    }

    /**
     * Zooms in on the most recently selected image
     */
    @FXML void zoomAction() {
        zoomInOnImage(selectedCards.get(selectedCards.size() - 1));
    }

    private void zoomInOnImage(CardView cardView) {
        try {
            zoomedInCard.setImage(cardView.getCard().getImage());
            eventLabel.setText(cardView.getCard().getEvent());
            String equipment = "Equipment: ";
            for (int i = 0; i < cardView.getCard().getEquipment().length; i++) {
                if (i != 0) {
                    equipment = equipment + ", ";
                }
                equipment = equipment + cardView.getCard().getEquipment()[i];
            }
            equipmentLabel.setText(equipment);
            zoomedInCardVBox.setVisible(true);
        } catch (MalformedURLException e) {
            App.giveWarning("Couldn't find image to zoom in on. Check your card pack folder.");
        }
    }

    /**
     * Closes out of the zoomed view of the card
     */
    @FXML void exitZoomedView() {
        zoomedInCardVBox.setVisible(false);
        for (Node child : cardsAnchorPane.getChildren()) {
            child.setEffect(null);
        }
    }

    private static List<String> getCheckedItems(CheckComboBox<String> dropdown) {
        return dropdown.getCheckModel().getCheckedItems();
    }

    // Sets the shadow effect on the cards and sets buttons to be enabled when a card is selected
    private void selectCardAction(MouseEvent event){
        if (event.getTarget() instanceof CardView) {
            CardView cardViewSelected = (CardView) event.getTarget();
            if (!selectedCards.contains(cardViewSelected)) {
                cardViewSelected.setEffect(new InnerShadow(40, Color.valueOf("#78c6f7")));
                selectedCards.add(cardViewSelected);
            } else {
                cardViewSelected.setEffect(null);
                selectedCards.remove(cardViewSelected);
            }
            disableButtons(selectedCards.isEmpty());
        }
    }

    private void disableButtons(boolean disable) {
        printCardBtn.setDisable(disable);
        zoomBtn.setDisable(disable);
    }

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

    // Clears the applied filters, resetting the dropdowns and the cards displayed
    @FXML private void clearFiltersAction() {
        for (CardView cardView : cardViewList) {
            cardView.setVisible(true);
            cardView.setManaged(true);
        }

        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            if (dropdown.getCheckModel().getCheckedItems() != null){
                List<Integer> checkedIndices = dropdown.getCheckModel().getCheckedIndices();
                for (int i = checkedIndices.size() - 1; i >= 0; i--) {
                    dropdown.getCheckModel().toggleCheckState(checkedIndices.get(i));
                }
            }
        }
    }

    // Updates the visible cards to match the search text when the Enter key is pressed
    @FXML void searchAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            updateFilteredVisibleCards();
        }
    }

    /**
     * Sets up the printing for the selected cards
     */
    @FXML void printSelectedCards() {
        if (selectedCards != null){
            List<Card> cardsToPrint = new ArrayList<>();
            for (CardView cardView : selectedCards) {
                cardsToPrint.add(cardView.getCard());
            }
            PrintStaging printCardList = new PrintStaging(cardsToPrint, "card_browser");
            selectedCards.clear();
            App.setRoot("print_preview");
        }
    }

    /**
     * Switches to the home screen.
     */
    @FXML private void goToHome() {
        App.setRoot("home");
    }
}