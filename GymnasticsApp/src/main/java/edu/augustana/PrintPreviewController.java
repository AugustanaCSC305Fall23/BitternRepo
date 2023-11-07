package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PrintPreviewController {

    // Throughout this class, I utilized https://coderanch.com/t/709329/java/JavaFX-approach-dividing-text-blob
    // and https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/pagination.htm
    // which heavily influenced the creation of this class.

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button printButton;

    @FXML
    private Button returnButton;

    @FXML
    private Pane mainPane;

    private static PrinterJob printerJob = PrinterJob.createPrinterJob();

    @FXML
    void printScreen(ActionEvent event) {
        // Used http://www.java2s.com/example/java/javafx/printing-with-javafx.html
        // https://stackoverflow.com/questions/28847757/how-to-display-print-dialog-in-java-fx-and-print-node


        if (printerJob != null && printerJob.showPageSetupDialog(null) && printerJob.showPrintDialog(null)){
            System.out.println(mainPane);
            boolean success = printerJob.printPage(mainPane);
            if (success) {
                printerJob.endJob();
            }

        }

    }

    @FXML
    void returnToPrevScreen(ActionEvent event) throws IOException {
        App.setRoot(PrintStaging.getFXML());
    }

    @FXML
    void initialize() {
        if (PrintStaging.getFXML().equals("card_browser")) {
            ImageView card = new ImageView(PrintStaging.getPrintCard());
            int numPages = 1;
            Pagination pagination = new Pagination(numPages);
            pagination.setStyle("-fx-border-color:white;");
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= numPages) {
                    return null;
                } else {
                    return createPage(pageIndex, card, printerJob);
                }

            });



        mainPane.getChildren().addAll(pagination);

        }
    }


    // Number of items per page (represents 1 page)
    public int itemsPerPage() {
        return 1;
    }
    public VBox createPage(int pageIndex, ImageView card, PrinterJob pj) {
        PageLayout pg = pj.getJobSettings().getPageLayout();
        VBox box = new VBox();
        int page = pageIndex * itemsPerPage();

        for (int p = page; p < page + itemsPerPage(); p++) {

            Pane whitePaperPane = new Pane();
            whitePaperPane.setStyle("-fx-background-color:white;");
            whitePaperPane.setPrefHeight(pg.getPrintableHeight());
            whitePaperPane.setPrefWidth(pg.getPrintableWidth());
            whitePaperPane.getChildren().add(card);
            box.getChildren().add(whitePaperPane);
        }
        return box;
    }

}
