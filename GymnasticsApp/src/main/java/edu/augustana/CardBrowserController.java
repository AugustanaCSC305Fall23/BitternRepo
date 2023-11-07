package edu.augustana;

import edu.augustana.filters.SearchFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.print.*;
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

/*    @FXML // fx:id="beamCheckBox"
    private CheckBox beamCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="boyCheckBox"
    private CheckBox boyCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="easyCheckBox"
    private CheckBox easyCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="floorCheckBox"
    private CheckBox floorCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="girlCheckBox"
    private CheckBox girlCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="hardCheckBox"
    private CheckBox hardCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="horizontalBarsCheckBox"
    private CheckBox horizontalBarsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="mediumCheckBox"
    private CheckBox mediumCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="neutralCheckBox"
    private CheckBox neutralCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="parallelBarsCheckBox"
    private CheckBox parallelBarsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="pommelHorseCheckBox"
    private CheckBox pommelHorseCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="ringsCheckBox"
    private CheckBox ringsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="strengthCheckBox"
    private CheckBox strengthCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="unevenBarsCheckBox"
    private CheckBox unevenBarsCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="vaultCheckBox"
    private CheckBox vaultCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="trampolineCheckBox"
    private CheckBox trampolineCheckBox;*/

    @FXML // fx:id="homeButton"
    private Button homeButton; // Value injected by FXMLLoader

    @FXML
    private FlowPane cardsFlowPane;

    public static final ObservableList<String> eventFilterChoices = FXCollections.observableArrayList(new String[]{"Beam", "Floor",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Uneven Bars", "Vault"});
    public static final ObservableList<String> genderFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    public static final ObservableList<String> levelFilterChoices = FXCollections.observableArrayList(new String[]{"A", "AB", "AB I", "B AB", "B AB I", "B I", "I", "I A"});
    public static final ObservableList<String> modelSexFilterChoices = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});

    List<CheckComboBox<String>> listOfDropdowns;

    @FXML private CheckComboBox<String> eventDropdown;
    @FXML private CheckComboBox<String> genderDropdown;
    @FXML private CheckComboBox<String> levelDropdown;
    @FXML private CheckComboBox<String> modelSexDropdown;
    private static final CardCollection fullCardCollection = CardDatabase.getFullCardCollection();

    private List<String> filters = new ArrayList<>();
    @FXML private TextField searchField;

    private ImageView clickedImageView = new ImageView();

    private Image prevImage;

    private final Image checkImage = new Image(getClass().getResource("images/Checkmark.png").toString(), 400, 300, true, true);


/*    private List<CheckBox> createListOfFilters() {
        return Arrays.asList(easyCheckBox, mediumCheckBox, hardCheckBox, boyCheckBox, girlCheckBox,
                neutralCheckBox, beamCheckBox, unevenBarsCheckBox, strengthCheckBox, floorCheckBox,
                vaultCheckBox, ringsCheckBox, pommelHorseCheckBox, parallelBarsCheckBox, horizontalBarsCheckBox,
                trampolineCheckBox);
    }*/
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

    @FXML
    private void returnToLessonPlanHandler(ActionEvent event) throws IOException {
        App.setRoot("lesson_plan_creator");
    }
    private void drawCardSet(){
        List<Image> imageList = CardDatabase.getListOfImages();
        for (Image image : imageList) {
            ImageView cardImageView = new ImageView(image);
            //cardImageView.setOnMouseClicked(this::selectedCard);
            cardsFlowPane.getChildren().add(cardImageView);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        createDropdowns();
        drawCardSet();
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'card_browser.fxml'.";
    }
    private static List<String> getCheckedItems(CheckComboBox<String> dropdown) {
        return dropdown.getCheckModel().getCheckedItems();
    }
    @FXML private void applyFiltersAction(ActionEvent event) throws IOException {
        cardsFlowPane.getChildren().clear();
        FilterControl.updateFilterLists(getCheckedItems(eventDropdown), getCheckedItems(genderDropdown), getCheckedItems(levelDropdown), getCheckedItems(modelSexDropdown));

        for (String cardId : fullCardCollection.getSetOfCardIds()) {
            Card card = fullCardCollection.getCard(cardId);
            if (FilterControl.checkIfAllFiltersMatch(card)) {
                ImageView cardImageView = new ImageView(card.getImage());
                //cardImageView.setOnMouseClicked(this::selectedCard);
                cardsFlowPane.getChildren().add(cardImageView);
            }
        }
        FilterControl.resetDesiredFiltersLists();
    }

    @FXML
    private void clearFiltersAction(ActionEvent event) throws IOException {
        FilterControl.resetDesiredFiltersLists();
        cardsFlowPane.getChildren().clear();
        drawCardSet();
        for (CheckComboBox<String> dropdown : listOfDropdowns) {
            if (dropdown.getCheckModel().getCheckedItems() != null){
                List<Integer> checkedIndices = dropdown.getCheckModel().getCheckedIndices();
                for (int i = 0; i < checkedIndices.size(); i++) {
                    dropdown.getCheckModel().toggleCheckState(checkedIndices.get(i));
                }
            }
        }
    }
    @FXML void searchAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            List<String> searchWordList = new ArrayList<>();
            for (String word: searchField.getText().split("\\s+")) {
                searchWordList.add(word.toLowerCase());
            }
            SearchFilter searchFilter = new SearchFilter(searchWordList);
            cardsFlowPane.getChildren().clear();
            for (String cardId : fullCardCollection.getSetOfCardIds()) {
                Card card = fullCardCollection.getCard(cardId);
                if (searchFilter.matchesFilters(card)) {
                    ImageView cardImageView = new ImageView(card.getImage());
                    //cardImageView.setOnMouseClicked(this::selectedCard);
                    cardsFlowPane.getChildren().add(cardImageView);
                }
            }
        }
    }

    @FXML
    private void getImageClicked(MouseEvent event) throws IOException {
        if (event.getTarget().getClass() == ImageView.class) {
            if (clickedImageView == null) {
                clickedImageView = (ImageView) event.getTarget();
                prevImage = clickedImageView.getImage();
                clickedImageView.setImage(checkImage);
            } else if (event.getTarget().equals(clickedImageView)) {
                clickedImageView.setImage(prevImage);
                clickedImageView = null;
                prevImage = null;
            } else {
                clickedImageView.setImage(prevImage);
                clickedImageView = (ImageView) event.getTarget();
                prevImage = clickedImageView.getImage();
                clickedImageView.setImage(checkImage);
            }
        }
    }
    @FXML
    void printSelectedCard(ActionEvent event) throws IOException {
        PrintStaging printCard = new PrintStaging(prevImage, "card_browser");
        App.setRoot("print_preview");
    }
}