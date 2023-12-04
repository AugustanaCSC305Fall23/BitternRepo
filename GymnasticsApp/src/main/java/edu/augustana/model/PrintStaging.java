package edu.augustana.model;
import edu.augustana.ui.CardView;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintStaging {
    private static String past_fxml;

    private static String lessonPlanTitle;
    private static Map<String, List<Card>> eventToCardMap;
    private static List<Card> printCardList;
    private static boolean landscapeDisplay;
    private static boolean cardDisplay;

 // Constructors
    public PrintStaging(List<Card> cardList, String fxml) {
        past_fxml = fxml;
        printCardList = cardList;
    }

    public PrintStaging(String title, Map<String, List<Card>> map, String fxml, boolean card_display, boolean landscape_display) {
        lessonPlanTitle = title;
        eventToCardMap = map;
        past_fxml = fxml;
        cardDisplay = card_display;
        landscapeDisplay = landscape_display;
    }

// Getters
    public static Map<String, List<Card>> getEventToCardMap() {
        return eventToCardMap;
    }

    public static String getLessonPlanTitle() {
        return lessonPlanTitle;
    }

    public static String getFXML() {
        return past_fxml;
    }

    public static List<Card> getPrintCardList() {
        return printCardList;
    }

    public static boolean getCardDisplay() {
        return cardDisplay;
    }

    public static boolean getLandscapeDisplay() {
        return landscapeDisplay;
    }

// Creates Pages for PrintPreviewController
    // Used https://coderanch.com/t/709329/java/JavaFX-approach-dividing-text-blob
    // and https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/pagination.htm
    // which heavily influenced the creation of the methods below.

    // Creates pages for individual cards (from Card Browser)
    public static VBox createPage(int pageIndex, List<Card> cardsToPrint, PrinterJob pj) throws MalformedURLException {
        PageLayout pg = pj.getJobSettings().getPageLayout();
        VBox box = new VBox();
        int page = pageIndex * itemsPerPage();

        for (int p = page; p < page + itemsPerPage(); p++) {

            Pane whitePaperPane = new Pane();
            whitePaperPane.setStyle("-fx-background-color:white;");
            whitePaperPane.setPrefHeight(pg.getPrintableWidth());
            whitePaperPane.setPrefWidth(pg.getPrintableHeight());

            Card cardToPrint = cardsToPrint.get(p);
            ImageView fullSizeImageView = createFullSizeImageView(cardToPrint, pg);



            whitePaperPane.getChildren().add(fullSizeImageView);

            box.getChildren().add(whitePaperPane);


        }
        return box;
    }

    // Creates pages for a lesson plan
    public static VBox createPage(int pageIndex, ArrayList<Pane> pages, PrinterJob pj) {
        PageLayout pgLayout = pj.getJobSettings().getPageLayout();
        VBox box = new VBox();
        int page = pageIndex * itemsPerPage();

        for (int p = page; p < page + itemsPerPage(); p++) {
            Pane pagePane = new Pane(pages.get(p));
            pagePane.setStyle("-fx-background-color: white");
            if (landscapeDisplay) {
                // "Rotates" pagination so that it is in landscape mode
                pagePane.setPrefHeight(pgLayout.getPrintableWidth());
                pagePane.setPrefWidth(pgLayout.getPrintableHeight());
                box.getChildren().add(pagePane);
            } else {
                pagePane.setPrefHeight(pgLayout.getPrintableHeight());
                pagePane.setPrefWidth(pgLayout.getPrintableWidth());
                box.getChildren().add(pagePane);
            }
        }
        return box;
    }

    // Number of items per page (represents 1 page)
    private static int itemsPerPage() {
        return 1;
    }

    public static ImageView createFullSizeImageView(Card card, PageLayout pgLayout) throws MalformedURLException {
        ImageView fullSizeImageView = new ImageView(card.getImage());
        fullSizeImageView.setFitWidth(pgLayout.getPrintableWidth() - 10);
        fullSizeImageView.setFitHeight(pgLayout.getPrintableWidth() * .75);
        return fullSizeImageView;
    }



}
