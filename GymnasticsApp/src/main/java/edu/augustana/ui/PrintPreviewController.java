package edu.augustana.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.augustana.App;
import edu.augustana.model.Card;
import edu.augustana.model.ParseLessonPlanPrinting;
import edu.augustana.model.PrintStaging;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class PrintPreviewController {

    // Throughout this class, I utilized https://coderanch.com/t/709329/java/JavaFX-approach-dividing-text-blob
    // and https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/pagination.htm
    // which heavily influenced the creation of this class.

    // ---------- FXML Data Fields ----------
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button printAllButton;

    @FXML
    private Button printPageButton;

    @FXML
    private Button returnButton;

    @FXML
    private Pane mainPane;

    @FXML
    private Label titleLabel;

    // ---------- Non-FXML Data Fields ----------
    private PrinterJob printerJob;

    private List<Card> cardsToPrint;

    private ParseLessonPlanPrinting lessonPlan;


    // ---------- Experimental Data Fields ----------



    @FXML
    void initialize() throws MalformedURLException {
        printerJob = PrinterJob.createPrinterJob();
        if (PrintStaging.getFXML().equals("card_browser")) {

            cardsToPrint = PrintStaging.getPrintCardList();

            int numPages = cardsToPrint.size();
            Pagination pagination = new Pagination(numPages);
            pagination.setStyle("-fx-border-color:white;");
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= numPages) {
                    return null;
                } else {
                    try {
                        return PrintStaging.createPage(pageIndex, cardsToPrint, printerJob);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainPane.getChildren().addAll(pagination);
        } else {
            lessonPlan = new ParseLessonPlanPrinting(printerJob);
            ArrayList<Pane> pages = lessonPlan.getPages();
            int numPages = lessonPlan.getPages().size();
            Pagination pagination = new Pagination(numPages);
            pagination.setStyle("-fx-border-color:white;");
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= numPages) {
                    return null;
                } else {
                    return PrintStaging.createPage(pageIndex, pages, printerJob);
                }
            });
            mainPane.getChildren().addAll(pagination);
        }
    }

    @FXML
    void printScreen(ActionEvent event) {
        // Used http://www.java2s.com/example/java/javafx/printing-with-javafx.html
        // https://stackoverflow.com/questions/28847757/how-to-display-print-dialog-in-java-fx-and-print-node

        Window window = mainPane.getScene().getWindow();
        if (printerJob != null && printerJob.showPrintDialog(window)){
            boolean success = printerJob.printPage(mainPane);
            if (success) {
                printerJob.endJob();
                endPrinting();
            }

        }

    }

    @FXML
    void printAllCards(ActionEvent event) throws MalformedURLException {
        Window window = mainPane.getScene().getWindow();
        if (printerJob != null && printerJob.showPrintDialog(window)) {
            PageRange pgRange = new PageRange(1, 1);
            if (PrintStaging.getFXML().equals("card_browser")) {
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
                    if (PrintStaging.getFXML().equals("card_browser")) {
                        printerJob.getJobSettings().setPageLayout(pageLandscapeLayout);
                        Card card = cardsToPrint.get(p - 1);
                        ImageView cardImageView = PrintStaging.createFullSizeImageView(card, printerJob.getJobSettings().getPageLayout());
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
                    if (!printed) {
                        System.out.println("Printing failed."); // for testing
                        break;
                    }
                }
            }
            if(printed) printerJob.endJob();
            endPrinting();
        }


    }

    @FXML
    void returnToPrevScreen(ActionEvent event) throws IOException {
        App.setRoot(PrintStaging.getFXML());
    }


    public void endPrinting() {
        titleLabel.setText("Printing sent.\n" +
                "If saved as PDF, you can find it in the saved file.\n" );

        printAllButton.setVisible(false);
        printPageButton.setVisible(false);
        mainPane.setVisible(false);
    }

}
