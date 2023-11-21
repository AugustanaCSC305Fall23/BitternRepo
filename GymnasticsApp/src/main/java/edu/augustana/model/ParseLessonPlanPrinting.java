// Throughout the creation of this class, I heavily utilized https://coderanch.com/t/709329/java/JavaFX-approach-dividing-text-blob
// and this class is based off of one of the classes shown on this website

package edu.augustana.model;

import edu.augustana.ui.CardView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.tk.Toolkit;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ParseLessonPlanPrinting {

    private double pageWidth;
    private double pageHeight;
    private static ArrayList<Pane> pages = new ArrayList<>();

    private Map<String, List<Card>> eventToCardsMap;

    private Map<Label, FlowPane> labelToFlowPaneMap;

    private String lessonPlanTitleS;


    // ---------- Experimental FXML ----------
    @FXML
    private Label lessonPlanTitleL = new Label();
    @FXML
    private Font eventTitleTemplate = new Font("Times New Roman", 15);
    @FXML
    private FlowPane eventCardsTemplate = new FlowPane();
    @FXML
    private CardView cardTemplate;


    // The goal of this class is to set up the layout of each page and to set up the
    // list of Nodes that will represent the pages of the lesson plan
    public ParseLessonPlanPrinting(){
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        PageLayout pgLayout = printerJob.getJobSettings().getPageLayout();
        pageHeight = pgLayout.getPrintableHeight();
        pageWidth = pgLayout.getPrintableWidth();
        parseLessonPlan();
    }

    private void parseLessonPlan() {
        labelToFlowPaneMap = new HashMap<Label, FlowPane>();

        // LessonPlan Title
        eventToCardsMap = PrintStaging.getEventToCardMap();
        lessonPlanTitleS = PrintStaging.getLessonPlanTitle();
        Font titleFont = new Font("Times New Roman", 30);
        lessonPlanTitleL.setText(lessonPlanTitleS);
        lessonPlanTitleL.setFont(titleFont);


    // Parsing through the Event to Card Map

        // Used https://codegym.cc/groups/posts/how-to-iterate-a-map-in-java to run through a Map
        // Sets up a new Map that initializes JavaFX objects
        for (Map.Entry<String, List<Card>> event : eventToCardsMap.entrySet()) {
            double runningHeight = 0;

            Label eventLabel = new Label();
            eventLabel.setFont(eventTitleTemplate);
            eventLabel.setText(event.getKey());
            eventLabel.setPadding(new Insets(10,0, 10, 0));
            FlowPane eventCardsPane = new FlowPane();
            eventCardsPane.setMaxWidth(pageWidth + 5);
            eventCardsPane.setPrefWrapLength(pageWidth + 5);
            for (Card card : event.getValue()) {
                CardView cardView = new CardView(card);
                cardView.setFitWidth(pageWidth / 3.0);
                cardView.setFitHeight(pageWidth / 4.0);
                eventCardsPane.getChildren().add(cardView);
            }
            labelToFlowPaneMap.put(eventLabel, eventCardsPane);
        }

// Sets up pages
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
        pageContents.setAlignment(Pos.CENTER);
        pageContents.setMaxHeight(pageHeight);
        currentPane.getChildren().add(pageContents);

        pageContents.getChildren().add(lessonPlanTitleL);

        // Runs layout onto a dummy Node to get the actual Height
        dummyRoot.getChildren().add(dummyBox);
        dummyBox.getChildren().add(currentPane);
        dummyRoot.applyCss();
        dummyRoot.layout();

        double runningPageHeight = lessonPlanTitleL.getHeight();


    // Runs through the JavaFX Objects map to add the given events and cards to the page
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

    public static ArrayList<Pane> getPages() {
        return pages;
    }


}