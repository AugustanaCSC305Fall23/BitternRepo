package edu.augustana.ui;

import edu.augustana.*;
import edu.augustana.model.*;
import edu.augustana.filters.SearchFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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

import javafx.scene.paint.Color;
import org.controlsfx.control.CheckComboBox;

public class CardBrowserController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="applyFiltersBtn"
    private Button applyFiltersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="clearFiltersBtn"
    private Button clearFiltersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="backToLessonPlanBtn"
    private Button backToLessonPlanBtn; // Value injected by FXMLLoader

    @FXML // fx:id="homeButton"
    private Button homeButton; // Value injected by FXMLLoader

    @FXML
    private FlowPane cardsFlowPane;

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
    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();

    @FXML private TextField searchField;

    private List<CardView> selectedCards = new ArrayList<>();
    private List<CardView> cardViewList = new ArrayList<>();

    //private Image prevImage;

    //private final Image checkImage = new Image(getClass().getResource("images/Checkmark.png").toString(), 400, 300, true, true);

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws MalformedURLException {
        createDropdowns();
        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            CardView newCardView = new CardView(fullCardCollection.getCardByID(cardId));
            cardViewList.add(newCardView);
        }
        drawCardSet();
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'card_browser.fxml'.";
    }
    
    private void createDropdowns() {
        eventDropdown.getItems().addAll(eventFilterChoices);
        genderDropdown.getItems().addAll(genderFilterChoices);
        levelDropdown.getItems().addAll(levelFilterChoices);
        modelSexDropdown.getItems().addAll(modelSexFilterChoices);
        listOfDropdowns = Arrays.asList(eventDropdown, genderDropdown, levelDropdown, modelSexDropdown);
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    private void drawCardSet() throws MalformedURLException {
        for (ImageView cardView : cardViewList) {
            cardView.setOnMouseClicked(this::selectCardAction);
            cardView.setFitWidth(350.0);
            cardView.setFitHeight(275.0);
            cardsFlowPane.getChildren().add(cardView);
        }
    }

    private static List<String> getCheckedItems(CheckComboBox<String> dropdown) {
        return dropdown.getCheckModel().getCheckedItems();
    }
    @FXML private void applyFiltersAction() throws IOException {
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
    private void clearFiltersAction(ActionEvent event) throws IOException {
        FilterControl.resetDesiredFiltersLists();
        cardsFlowPane.getChildren().clear();
        drawCardSet();
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            if (dropdown.getCheckModel().getCheckedItems() != null){
                List<Integer> checkedIndices = dropdown.getCheckModel().getCheckedIndices();
                for (int i = checkedIndices.size() - 1; i >= 0; i--) {
                    dropdown.getCheckModel().toggleCheckState(checkedIndices.get(i));
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

    private void selectCardAction(MouseEvent event){
        if (event.getTarget() instanceof CardView) {
            CardView cardViewSelected = (CardView) event.getTarget();
            if (!selectedCards.contains(cardViewSelected)) {
                cardViewSelected.setEffect(new InnerShadow(10, Color.PURPLE));
                selectedCards.add(cardViewSelected);
            } else {
                cardViewSelected.setEffect(null);
                selectedCards.remove(cardViewSelected);
            }
        }
    }

    @FXML
    void printSelectedCards() throws IOException {
        //PrintStaging printCard = new PrintStaging(prevImage, "card_browser");
        if (selectedCards != null){
            /* for (Card card : selectedCards.keySet()) {
                PrintStaging printCard = new PrintStaging(card.getImage(), "card_browser");
                selectedCards.get(card).setEffect(null);
            } */
            List<Card> cardsToPrint = new ArrayList<>();
            for (CardView cardView : selectedCards) {
                cardsToPrint.add(cardView.getCard());
            }
            PrintStaging printCardList = new PrintStaging(cardsToPrint, "card_browser");
            //System.out.println(printCardList.getPrintCardList());
            selectedCards.clear();
            App.setRoot("print_preview");
        }
    }
}