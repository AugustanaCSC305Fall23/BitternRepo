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
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.stage.Screen;
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
    private Button returnButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label titleLabel;

    // ---------- Non-FXML Data Fields ----------
    private PrinterJob printerJob;

    private List<Card> cardsToPrint;

    private ParseLessonPlanPrinting lessonPlan;


    // ---------- Experimental Data Fields ----------

    @FXML
    private Group mainGroup;



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
            lessonPlan = new ParseLessonPlanPrinting(printerJob, false);
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

    @FXML
    void printAllCards(ActionEvent event) throws MalformedURLException {
        Window window = mainPane.getScene().getWindow();

        ParseLessonPlanPrinting lessonPlanPrint = new ParseLessonPlanPrinting(printerJob, true);

        PrintStaging.printAllCards(window, printerJob, cardsToPrint, lessonPlanPrint);
        endPrinting();

    }

    @FXML
    void returnToPrevScreen(ActionEvent event) throws IOException {
        App.setRoot(PrintStaging.getFXML());
    }


    public void endPrinting() {
        titleLabel.setText("Success!");
        titleLabel.setAlignment(Pos.CENTER);
        printAllButton.setVisible(false);
        mainPane.setVisible(false);
    }

}
