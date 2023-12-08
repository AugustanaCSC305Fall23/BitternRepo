package edu.augustana.ui;

import edu.augustana.*;
import edu.augustana.filters.CardFilter;
import edu.augustana.model.*;
import edu.augustana.filters.SearchFilter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.CheckComboBox;

public class CardBrowserController {

    @FXML private Button homeButton;
    @FXML private Button printCardBtn;
    @FXML private Button zoomBtn;

    @FXML private FlowPane cardsFlowPane;

    @FXML private Pane cardsAnchorPane;

    public static final ObservableList<String> eventFilterChoices = FXCollections.observableArrayList(new String[]{"Beam", "Floor",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Uneven Bars", "Vault"});
    public static final ObservableList<String> genderFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    public static final ObservableList<String> levelFilterChoices = FXCollections.observableArrayList(new String[]{"Beginner", "Advanced Beginner", "Intermediate", "Advanced"});
    public static final ObservableList<String> modelSexFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});

    List<CheckComboBox<String>> listOfDropdowns;

    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;

    // zoomed-in card elements
    @FXML private VBox zoomedInCardVBox;
    @FXML private Label eventLabel;
    @FXML private ImageView zoomedInCard;
    @FXML private Label equipmentLabel;
    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();

    @FXML private TextField searchField;

    private List<CardView> selectedCards = new ArrayList<>();
    private final List<CardView> cardViewList = new ArrayList<>();
    private final FilterHandler filterHandler = new FilterHandler();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws MalformedURLException {
        createDropdowns();
        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            CardView newCardView = new CardView(fullCardCollection.getCardByID(cardId));
            cardViewList.add(newCardView);
        }
        drawCardSet();
        disableButtons(true);
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

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }

    private void drawCardSet() throws MalformedURLException {
        for (CardView cardView : cardViewList) {
            cardView.setOnMouseClicked(this::selectCardAction);
            cardsFlowPane.getChildren().add(cardView);
        }
    }

    @FXML void zoomAction() throws MalformedURLException {
        zoomInOnImage(selectedCards.get(selectedCards.size() - 1));
    }

    private void zoomInOnImage(CardView cardView) throws MalformedURLException {
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
        /* GaussianBlur blur = new GaussianBlur();
        for (Node child : cardsAnchorPane.getChildren()) {
            if (child != zoomedInCardVBox) {
                child.setEffect(blur);
            }
        } */
    }

    @FXML void exitZoomedView() {
        zoomedInCardVBox.setVisible(false);
        for (Node child : cardsAnchorPane.getChildren()) {
            child.setEffect(null);
        }
    }

    private static List<String> getCheckedItems(CheckComboBox<String> dropdown) {
        return dropdown.getCheckModel().getCheckedItems();
    }

    private void selectCardAction(MouseEvent event){
        if (event.getTarget() instanceof CardView) {
            CardView cardViewSelected = (CardView) event.getTarget();
            if (!selectedCards.contains(cardViewSelected)) {
                cardViewSelected.setEffect(new InnerShadow(20, Color.PURPLE));
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

    // Used code from MovieTrackerApp
    @FXML void updateFilteredVisibleCards() {
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
    private void clearFiltersAction() {
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

    @FXML void searchAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            updateFilteredVisibleCards();
        }
    }

    @FXML
    void printSelectedCards() throws IOException {
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
}