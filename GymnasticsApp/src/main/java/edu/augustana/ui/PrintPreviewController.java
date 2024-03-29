package edu.augustana.ui;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import edu.augustana.App;
import edu.augustana.model.Card;
import edu.augustana.model.ParseLessonPlanPrinting;
import edu.augustana.model.PrintStaging;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Window;


/**
 * Controls the print preview screen
 */

public class PrintPreviewController {

    // Throughout this class, I utilized https://coderanch.com/t/709329/java/JavaFX-approach-dividing-text-blob
    // and https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/pagination.htm
    // which heavily influenced the creation of this class.

    // ---------- FXML Data Fields ----------
    @FXML
    private Button printAllButton;

    @FXML
    private Button returnButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label titleLabel;

    // ---------- Non-FXML Data Fields ----------
    private PrinterJob printerJob;

    private List<Card> cardsToPrint;

    private ParseLessonPlanPrinting lessonPlan;

    @FXML
    void initialize() {
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
            try {
                lessonPlan = new ParseLessonPlanPrinting(printerJob, false);
            } catch (MalformedURLException e) {
                App.giveWarning("Failed to print lesson plans");
                Platform.exit();
            }
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

            mainPane.getChildren().add(pagination);

        }
    }

    /**
     * When the print button is clicked, initializes the process of printing.
     */
    @FXML
    void printAllCards() {
        Window window = mainPane.getScene().getWindow();

        ParseLessonPlanPrinting lessonPlanPrint = null;


        try {

            if (PrintStaging.getFXML().equals("card_browser")) {
                PrintStaging.printAllCards(window, printerJob, cardsToPrint, lessonPlanPrint);
            } else {
                lessonPlanPrint = new ParseLessonPlanPrinting(printerJob, true);
                PrintStaging.printAllCards(window, printerJob, cardsToPrint, lessonPlanPrint);
            }
            endPrinting();
        } catch (MalformedURLException e) {
            App.giveWarning("Failed to print cards");
        }
    }

    /**
     * Returns to the screen before the print preview.
     */
    @FXML
    void returnToPrevScreen() {
        App.setRoot(PrintStaging.getFXML());
    }

    /**
     * Ends the process of printing.
     */
    private void endPrinting() {
        titleLabel.setText("Success!");
        titleLabel.setAlignment(Pos.CENTER);
        printAllButton.setVisible(false);
        mainPane.setVisible(false);
    }
}
