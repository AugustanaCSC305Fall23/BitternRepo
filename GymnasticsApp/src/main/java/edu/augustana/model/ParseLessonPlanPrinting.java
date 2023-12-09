// Throughout the creation of this class, I heavily utilized https://coderanch.com/t/709329/java/JavaFX-approach-dividing-text-blob
// and this class is based off of one of the classes shown on this website

package edu.augustana.model;

import edu.augustana.App;
import edu.augustana.ui.CardView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.MalformedURLException;
import java.util.*;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;


public class ParseLessonPlanPrinting {

    private double pageWidth;
    private double pageHeight;
    private ArrayList<Pane> pages = new ArrayList<>();

    private Map<String, List<Card>> eventToCardsMap;

    private Map<Label, FlowPane> labelToFlowPaneMap;

    private Map<Label, List<Label>> labelToCardLabelsMap;

    private String lessonPlanTitle;

    private boolean isPrinting;

    private Font titleFont = new Font("Times New Roman Bold", 30);

    private Font eventTitleTemplate = new Font("Times New Roman Bold", 15);

    double landscapeHeightScale = 1.25;

    double portraitHeightScale = 1.75;


    // ---------- FXML ----------
    @FXML
    private Label lessonPlanTitleLabel = new Label();


    /**
     * Creates an instance of the ParseLessonPlanPrininting Class. This class utillizes info from
     * the PrintStaging class to parse through the information and crate the pages for printing and the
     * print preview.
     *
     * @param printerJob - the PrinterJob that representsthe printer
     * @param is_printing - true if the Parsing is for actual printing (not the preview displayed on screen).
     * @throws MalformedURLException
     */
    public ParseLessonPlanPrinting(PrinterJob printerJob, boolean is_printing) throws MalformedURLException {

        isPrinting = is_printing;
        PageLayout pgLayout = printerJob.getJobSettings().getPageLayout();

        if (PrintStaging.getLandscapeDisplay()) {
            // Switches the height and width, so it can parse through landscape mode
            // Used only for the print preview
            if (isPrinting) {
                pageHeight = pgLayout.getPrintableWidth();
                pageWidth = pgLayout.getPrintableHeight();
            } else {
                pageHeight = pgLayout.getPrintableWidth() * landscapeHeightScale;
                pageWidth = pgLayout.getPrintableHeight() * 1.75;
                eventTitleTemplate = new Font("Times New Roman Bold", 20);
                titleFont = new Font("Times New Roman Bold", 35);
            }
        }  else {
            if (isPrinting) {
                pageHeight = pgLayout.getPrintableHeight();
                pageWidth = pgLayout.getPrintableWidth();
            } else {
                pageHeight = pgLayout.getPrintableHeight() * portraitHeightScale;
                pageWidth = pgLayout.getPrintableWidth() * 1.75;
                eventTitleTemplate = new Font("Times New Roman Bold", 20);
                titleFont = new Font("Times New Roman Bold", 35);
            }
        }

        if (PrintStaging.getCardDisplay()) {
            parseLessonPlanImages(pgLayout);
        } else {
            parseLessonPlanText(pgLayout);
        }
    }

    /**
     * Parses through the lesson plan and creates the pages for the landscape and portrait version of a lesson plan
     * with cards.
     *
     * @param pgLayout - The PageLayout of the given printer.
     * @throws MalformedURLException
     */
    private void parseLessonPlanImages(PageLayout pgLayout) throws MalformedURLException {
        labelToFlowPaneMap = new LinkedHashMap<Label, FlowPane>();
        eventToCardsMap = PrintStaging.getEventToCardMap();

        initializeTitle();


        // Used https://codegym.cc/groups/posts/how-to-iterate-a-map-in-java to run through a Map
        initializeLabelToFlowPaneMap();

        // Sets up dummy scene so that we can render the actual height/width of the JavaFX Objects
        // Used https://stackoverflow.com/questions/26152642/get-the-height-of-a-node-in-javafx-generate-a-layout-pass
        double runningHeight = 0;
        Group dummyRoot = new Group();
        VBox dummyBox = new VBox();
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        Scene scene = new Scene(dummyRoot, width, height);

        // Sets up title (only first page)

        Pane currentPane = new Pane();
        currentPane.setMaxHeight(pageHeight);
        currentPane.setMaxWidth(pageWidth);

        VBox pageContents = new VBox();
        pageContents.setAlignment(Pos.CENTER);
        pageContents.setMaxHeight(pageHeight);
        currentPane.getChildren().add(pageContents);

        pageContents.getChildren().add(lessonPlanTitleLabel);
        pageContents.setAlignment(Pos.CENTER);

        // Runs layout onto a dummy Node to get the actual Height
        dummyRoot.getChildren().add(dummyBox);
        dummyBox.getChildren().add(currentPane);
        dummyRoot.applyCss();
        dummyRoot.layout();

        double runningPageHeight = lessonPlanTitleLabel.getHeight();


        // Runs through the JavaFX Objects map to add the given events and cards to the page
        addCardImageToPage(dummyBox, dummyRoot, runningPageHeight, pageContents, currentPane);

        TextFlow equipmentTF = new TextFlow();
        // Adds equipment
        if (PrintStaging.getEquipmentDisplay()) {
            if (PrintStaging.getLandscapeDisplay()) {
                addEquipment(landscapeHeightScale, equipmentTF, dummyBox, dummyRoot, currentPane, pageContents);
            } else {
                addEquipment(portraitHeightScale, equipmentTF, dummyBox, dummyRoot, currentPane, pageContents);
            }
        }



    }

    private void addCardImageToPage(VBox dummyBox, Group dummyRoot, double runningPageHeight, VBox pageContents, Pane currentPane) {
        for (Map.Entry<Label, FlowPane> event : labelToFlowPaneMap.entrySet()) {

            Label eventLabel = event.getKey();
            FlowPane cardFlowPane = event.getValue();

            // Runs layout onto a dummy Node to get the actual Height
            dummyBox.getChildren().add(eventLabel);
            dummyBox.getChildren().add(cardFlowPane);
            dummyRoot.applyCss();
            dummyRoot.layout();

            if (runningPageHeight + event.getKey().getHeight() + event.getValue().getHeight() < pageHeight) {
                pageContents.getChildren().add(eventLabel);
                pageContents.getChildren().add(cardFlowPane);
                runningPageHeight = runningPageHeight + event.getKey().getHeight() + event.getValue().getHeight();


            } else {
                pages.add(currentPane);

                currentPane = new Pane();
                currentPane.setMaxHeight(pageHeight);
                currentPane.setMaxWidth(pageWidth);

                pageContents = new VBox();
                pageContents.setAlignment(Pos.CENTER);
                pageContents.setMaxHeight(pageHeight);
                currentPane.getChildren().add(pageContents);

                pageContents.getChildren().add(eventLabel);
                pageContents.getChildren().add(cardFlowPane);

                runningPageHeight = event.getKey().getHeight() + event.getValue().getHeight();
            }
        }

        pages.add(currentPane);
    }

    private void initializeLabelToFlowPaneMap() throws MalformedURLException {
        for (Map.Entry<String, List<Card>> event : eventToCardsMap.entrySet()) {
            Label eventLabel = new Label();
            eventLabel.setFont(eventTitleTemplate);
            eventLabel.setText(event.getKey());
            eventLabel.setPadding(new Insets(10,0, 10, 0));
            FlowPane eventCardsPane = new FlowPane();
            eventCardsPane.setMaxWidth(pageWidth + 5);
            eventCardsPane.setPrefWrapLength(pageWidth + 5);
            int numCards = event.getValue().size();
            for (Card card : event.getValue()) {
                ImageView cardImageView = new ImageView(card.getImage());
                if (numCards > 12) {
                    // Used https://stackoverflow.com/questions/7446710/how-to-round-up-integer-division-and-have-int-result-in-java
                    int numRows;
                    if (PrintStaging.getLandscapeDisplay()) {
                        numRows = (int) Math.ceil(numCards / 4.0);
                    } else {
                        numRows = (int) Math.ceil(numCards / 3.0);
                    }

                    // Calculates usable page height for the card pane only with dummy layout objects
                    Group dummyRoot = new Group();
                    VBox dummyBox = new VBox();
                    double height = Screen.getPrimary().getBounds().getHeight();
                    double width = Screen.getPrimary().getBounds().getWidth();
                    Scene scene = new Scene(dummyRoot, width, height);

                    dummyRoot.getChildren().add(dummyBox);
                    dummyBox.getChildren().add(lessonPlanTitleLabel);
                    dummyBox.getChildren().add(eventLabel);
                    dummyRoot.applyCss();
                    dummyRoot.layout();

                    double usablePageHeight = pageHeight - lessonPlanTitleLabel.getHeight() - eventLabel.getHeight() - 10;

                    double cardHeight = usablePageHeight / numRows + 0.0;
                    cardImageView.setFitWidth(cardHeight * (4/3.0));
                    cardImageView.setFitHeight(cardHeight);
                } else if (PrintStaging.getLandscapeDisplay()) {
                    double cardWidth = pageWidth / 4.0;
                    double cardHeight = cardWidth * 0.75;
                    cardImageView.setFitWidth(cardWidth);
                    cardImageView.setFitHeight(cardHeight);
                } else {
                    cardImageView.setFitWidth(pageWidth / 3.0);
                    cardImageView.setFitHeight(pageWidth / 4.0);
                }
                eventCardsPane.getChildren().add(cardImageView);
                eventCardsPane.setAlignment(Pos.CENTER);
            }
            labelToFlowPaneMap.put(eventLabel, eventCardsPane);
        }
    }

    /**
     * Parses through the lesson plan and creates the pages for the text only version of a lesson plan.
     *
     * @param pgLayout - The PageLayout of the given printer.
     * @throws MalformedURLException
     */
    private void parseLessonPlanText(PageLayout pgLayout) throws MalformedURLException {
        labelToCardLabelsMap = new LinkedHashMap<Label, List<Label>>();
        eventToCardsMap = PrintStaging.getEventToCardMap();

        initializeTitle();

        // Used https://codegym.cc/groups/posts/how-to-iterate-a-map-in-java to run through a Map
        initializeLabelToCardLabelsMap();

        // Sets up dummy scene so that we can render the actual height/width of the JavaFX Objects
        // Used https://stackoverflow.com/questions/26152642/get-the-height-of-a-node-in-javafx-generate-a-layout-pass
        Group dummyRoot = new Group();
        VBox dummyBox = new VBox();
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        Scene scene = new Scene(dummyRoot, width, height);

        // Sets up title (only first page)

        Pane currentPane = new Pane();
        currentPane.setMaxHeight(pageHeight);
        currentPane.setMaxWidth(pageWidth);

        VBox pageContents = new VBox();
        pageContents.setAlignment(Pos.CENTER_LEFT);
        pageContents.setMaxHeight(pageHeight);
        currentPane.getChildren().add(pageContents);

        pageContents.getChildren().add(lessonPlanTitleLabel);

        // Runs layout onto a dummy Node to get the actual Height
        dummyRoot.getChildren().add(dummyBox);
        dummyBox.getChildren().add(currentPane);
        dummyRoot.applyCss();
        dummyRoot.layout();

        double runningPageHeight = lessonPlanTitleLabel.getHeight();


        // Runs through the JavaFX Objects map to add the given events and cards to the page
        addTextToPage(dummyBox, dummyRoot, runningPageHeight, pageContents, currentPane);


        TextFlow equipmentTF = new TextFlow();
        // Adds equipment
        if (PrintStaging.getEquipmentDisplay()) {
            addEquipment(portraitHeightScale, equipmentTF, dummyBox, dummyRoot, currentPane, pageContents);
        }
    }

    private void initializeLabelToCardLabelsMap() {

        for (Map.Entry<String, List<Card>> event : eventToCardsMap.entrySet()) {
            double runningHeight = 0;

            Label eventLabel = new Label();
            eventLabel.setFont(eventTitleTemplate);
            eventLabel.setText(event.getKey());
            eventLabel.setPadding(new Insets(10,0, 10, 0));
            List<Label> eventCards = new ArrayList<>();
            for (Card card : event.getValue()) {
                Label cardInfo = new Label();
                String cardInfoS = "-   " + card.getTitle() +", " + card.getCode();
                cardInfo.setText(cardInfoS);
                if (!isPrinting) {
                    cardInfo.setFont(new Font("Times New Roman", 15 * 1.75));
                }
                eventCards.add(cardInfo);
            }

            if (!isPrinting) {
                eventLabel.setFont(new Font("Times New Roman", 20 * 1.75));
            }
            labelToCardLabelsMap.put(eventLabel, eventCards);
        }
    }

    private void addEquipment(double equipmentHeightScale, TextFlow equipmentTF, VBox dummyBox, Group dummyRoot, Pane currentPane, VBox pageContents) {
        List<String> equipmentList = App.getCurrentLessonPlan().getEquipmentFromMap(eventToCardsMap);
        if (equipmentList != null && (!(equipmentList.isEmpty()))) {
            String equipmentSentence = "EQUIPMENT: \n" + equipmentList.get(0);
            String equipmentString = "Equipment: \n" + "   *" + equipmentList.get(0);
            for (int x = 1; x < equipmentList.size(); x++) {
                equipmentString = equipmentString + "\n   *" + equipmentList.get(x);
                equipmentSentence = equipmentSentence + ", " + equipmentList.get(x);
            }

            Text equipmentText = new Text(equipmentString);
            Font font = new Font("Times New Roman", 15 * equipmentHeightScale);
            if (isPrinting) {
                font = new Font("Times New Roman", 15 );
            }
            equipmentText.setFont(font);
            equipmentTF = new TextFlow(equipmentText);
            equipmentTF.setMaxWidth(pageWidth);

            dummyBox = new VBox();
            dummyRoot = new Group();
            dummyBox.getChildren().add(equipmentTF);
            dummyRoot.getChildren().add(dummyBox);
            dummyRoot.applyCss();
            dummyRoot.layout();

            if (!(dummyBox.getHeight() > pageHeight)) {

                currentPane = new Pane();
                currentPane.setMaxHeight(pageHeight);
                currentPane.setMaxWidth(pageWidth);

                pageContents = new VBox();
                pageContents.setAlignment(Pos.CENTER);
                pageContents.setMaxHeight(pageHeight);
                currentPane.getChildren().add(pageContents);

                pageContents.getChildren().add(equipmentTF);

                pages.add(currentPane);
            } else {
                equipmentText = new Text(equipmentSentence);
                equipmentText.setFont(font);
                equipmentTF = new TextFlow(equipmentText);
                equipmentTF.setMaxWidth(pageWidth);

                currentPane = new Pane();
                currentPane.setMaxHeight(pageHeight);
                currentPane.setMaxWidth(pageWidth);

                pageContents = new VBox();
                pageContents.setAlignment(Pos.CENTER);
                pageContents.setMaxHeight(pageHeight);
                currentPane.getChildren().add(pageContents);

                pageContents.getChildren().add(equipmentTF);

                pages.add(currentPane);
            }
        }
    }

    private void addTextToPage(VBox dummyBox, Group dummyRoot, double runningPageHeight, VBox pageContents, Pane currentPane) {
        for (Map.Entry<Label, List<Label>> event : labelToCardLabelsMap.entrySet()) {

            Label eventLabel = event.getKey();
            List<Label> cardsInEvent = event.getValue();

            // Creates dummy box to see if there is enough room
            dummyBox.getChildren().add(eventLabel);
            for (Label card : cardsInEvent) {
                dummyBox.getChildren().add(card);
            }
            dummyRoot.applyCss();
            dummyRoot.layout();
            runningPageHeight = dummyBox.getHeight();

            // If there is enough room, adds the cards. Else, it creates a new page
            if (runningPageHeight < pageHeight) {
                pageContents.getChildren().add(eventLabel);

                for (Label card : cardsInEvent) {
                    pageContents.getChildren().add(card);
                }
            } else {
                pages.add(currentPane);

                currentPane = new Pane();
                currentPane.setMaxHeight(pageHeight);
                currentPane.setMaxWidth(pageWidth);

                // Creates dummy box to calcualte new height
                dummyBox = new VBox();
                dummyBox.getChildren().add(eventLabel);
                for (Label card : cardsInEvent) {
                    dummyBox.getChildren().add(card);
                }
                dummyRoot.applyCss();
                dummyRoot.layout();
                runningPageHeight = dummyBox.getHeight();

                pageContents = new VBox();
                pageContents.setAlignment(Pos.CENTER_LEFT);
                pageContents.setMaxHeight(pageHeight);
                currentPane.getChildren().add(pageContents);


                pageContents.getChildren().add(eventLabel);
                for (Label card : cardsInEvent) {
                    pageContents.getChildren().add(card);
                }
            }
        }

        pages.add(currentPane);
    }

    private void initializeTitle() {
        lessonPlanTitle = PrintStaging.getLessonPlanTitle();
        lessonPlanTitleLabel.setText(lessonPlanTitle);
        lessonPlanTitleLabel.setFont(titleFont);
    }


    /**
     * Returns the pages of the lesson plan.
     *
     * @return a List of pages, which represents the Nodes on which the Lesson Plan is displayed/printed.
     */
    public ArrayList<Pane> getPages() {
        return pages;
    }


}
