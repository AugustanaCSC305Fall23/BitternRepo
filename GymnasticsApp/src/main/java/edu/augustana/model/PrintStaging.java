package edu.augustana.model;
import edu.augustana.ui.CardView;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.window;

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

    public static void printAllCards(Window window, PrinterJob printerJob, List<Card> cardsToPrint, ParseLessonPlanPrinting lessonPlan) throws MalformedURLException {
        if (printerJob != null && printerJob.showPrintDialog(window)) {
            PageRange pgRange = new PageRange(1, 1);
            if (getFXML().equals("card_browser")) {
                pgRange = new PageRange(1, cardsToPrint.size());
            } else {
                pgRange = new PageRange(1, lessonPlan.getPages().size());
            }


            // Used https://stackoverflow.com/questions/28102141/javafx-8-webengine-print-method-unable-to-print-in-landscape
            // to create landscape mode for a lesson plan
            Printer printer = Printer.getDefaultPrinter();

            PageLayout pageLandscapeLayout = printer.createPageLayout(Paper.A4,
                    PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);

            printerJob.getJobSettings().setPageRanges(pgRange);
            PageLayout pgLayout = printerJob.getJobSettings().getPageLayout();
            JobSettings js = printerJob.getJobSettings();

            boolean printed = false;
            for (PageRange pr : js.getPageRanges()) {
                for (int p = pr.getStartPage(); p <= pr.getEndPage(); p++){        // This loops through the selected page range
                    Pane printNode = new Pane();
                    printNode.setPrefHeight(pgLayout.getPrintableHeight());
                    printNode.setPrefWidth(pgLayout.getPrintableWidth());
                    if (getFXML().equals("card_browser")) {
                        printerJob.getJobSettings().setPageLayout(pageLandscapeLayout);
                        Card card = cardsToPrint.get(p - 1);
                        ImageView cardImageView = createFullSizeImageView(card, printerJob.getJobSettings().getPageLayout());
                        printNode.getChildren().add(cardImageView);
                    } else {
                        Pane page = lessonPlan.getPages().get(p - 1);
                        if (PrintStaging.getLandscapeDisplay()) {
                            printerJob.getJobSettings().setPageLayout(pageLandscapeLayout);
                            printNode.setPrefHeight(printerJob.getJobSettings().getPageLayout().getPrintableWidth());
                            printNode.setPrefWidth(printerJob.getJobSettings().getPageLayout().getPrintableHeight());

                        }
                        printNode.getChildren().add(page);
                    }

                    printed = printerJob.printPage(printerJob.getJobSettings().getPageLayout(), printNode);
                }
            }
            if(printed) printerJob.endJob();
        }
    }





}
