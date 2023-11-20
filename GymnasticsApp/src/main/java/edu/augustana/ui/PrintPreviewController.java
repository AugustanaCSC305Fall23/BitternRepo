package edu.augustana.ui;

import java.io.IOException;
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
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
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

    private List<CardView> cardsToPrint;


    // ---------- Experimental Data Fields ----------



    @FXML
    void initialize() {
        printerJob = PrinterJob.createPrinterJob();
        if (PrintStaging.getFXML().equals("card_browser")) {
            cardsToPrint = new ArrayList<>();
            for (Card card : PrintStaging.getPrintCardList()) {
                CardView printCardView = new CardView(card);
                cardsToPrint.add(printCardView);
            }
            int numPages = cardsToPrint.size();
            Pagination pagination = new Pagination(numPages);
            pagination.setStyle("-fx-border-color:white;");
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= numPages) {
                    return null;
                } else {
                    return createPage(pageIndex, cardsToPrint, printerJob);
                }
            });
            mainPane.getChildren().addAll(pagination);
        } else {
            ParseLessonPlanPrinting lessonPlan = new ParseLessonPlanPrinting();
            int numPages = ParseLessonPlanPrinting.getPages().size();
            Pagination pagination = new Pagination(numPages);
            pagination.setStyle("-fx-border-color:white;");
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= numPages) {
                    return null;
                } else {
                    return createPage(pageIndex, ParseLessonPlanPrinting.getPages(), printerJob);
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
    void printAllCards(ActionEvent event) {
        Window window = mainPane.getScene().getWindow();
        if (printerJob != null && printerJob.showPrintDialog(window)) {
            PageRange pgRange = new PageRange(1, ParseLessonPlanPrinting.getPages().size());
            if (PrintStaging.getFXML().equals("card_browser")) {
                pgRange = new PageRange(1, cardsToPrint.size());
            } else

            printerJob.getJobSettings().setPageRanges(pgRange);
            PageLayout pgLayout = printerJob.getJobSettings().getPageLayout();
            JobSettings js = printerJob.getJobSettings();

            boolean printed = false;
            for (PageRange pr : js.getPageRanges()) {
                for(int p = pr.getStartPage(); p <= pr.getEndPage(); p++){        // This loops through the selected page range
                    //List<CardView> cardsToPrintClone = new ArrayList<>(cardsToPrint);
                    //CardView cardView = cardsToPrintClone.get(p - 1);
                    Pane printNode = new Pane();
                    printNode.setPrefHeight(pgLayout.getPrintableHeight());
                    printNode.setPrefWidth(pgLayout.getPrintableWidth());
                    if (PrintStaging.getFXML().equals("card_browser")) {
                        CardView cardView = cardsToPrint.get(p - 1);
                        printNode.getChildren().add(cardView);
                    } else {
                        printNode.getChildren().add(ParseLessonPlanPrinting.getPages().get(p - 1));
                    }

                    printed = printerJob.printPage(pgLayout, printNode);
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




    // Number of items per page (represents 1 page)
    public int itemsPerPage() {
        return 1;
    }

    public VBox createPage(int pageIndex, List<CardView> cardsToPrint, PrinterJob pj) {
        PageLayout pg = pj.getJobSettings().getPageLayout();
        VBox box = new VBox();
        int page = pageIndex * itemsPerPage();

        for (int p = page; p < page + itemsPerPage(); p++) {

            Pane whitePaperPane = new Pane();
            whitePaperPane.setStyle("-fx-background-color:white;");
            whitePaperPane.setPrefHeight(pg.getPrintableHeight());
            whitePaperPane.setPrefWidth(pg.getPrintableWidth());

            CardView cardView = cardsToPrint.get(p);
            cardView.setFitWidth(pg.getPrintableWidth() - 10);
            cardView.setFitHeight(pg.getPrintableWidth() * .75);

            // Adding the given print objects to the screen
            if (PrintStaging.getFXML().equals("card_browser")) {
                whitePaperPane.getChildren().add(cardsToPrint.get(p));
                box.getChildren().add(whitePaperPane);
            }

        }
        return box;
    }

    public VBox createPage(int pageIndex, ArrayList<Pane> pages, PrinterJob pj) {
        PageLayout pg = pj.getJobSettings().getPageLayout();
        VBox box = new VBox();
        int page = pageIndex * itemsPerPage();

        for (int p = page; p < page + itemsPerPage(); p++) {
            Pane pagePane = new Pane(pages.get(p));
            pagePane.setStyle("-fx-background-color: white");
            pagePane.setPrefHeight(pg.getPrintableHeight());
            pagePane.setPrefWidth(pg.getPrintableWidth());
            box.getChildren().add(pagePane);
        }
        return box;
    }

    public void endPrinting() {
        titleLabel.setText("Printing sent.\n" +
                "If saved as PDF, you can find it in the saved file.\n" );

        printAllButton.setVisible(false);
        printPageButton.setVisible(false);
        mainPane.setVisible(false);
    }

}
