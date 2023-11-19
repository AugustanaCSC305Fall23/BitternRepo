package edu.augustana.Model;

import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseLessonPlanPrinting {

    private double pageWidth;
    private double pageHeight;
    private ArrayList<Node> pages;

    private Map<String, List<Card>> eventToCardsMap;

    private String lessonPlanTitleS;

    // ---------- Experimental FXML ----------
    @FXML
    private Label lessonPlanTitleL;
    @FXML
    private Label eventTitleTemplate;
    @FXML
    private FlowPane eventCardsTemplate;
    @FXML
    private ImageView cardTemplate;

    // The goal of this class is to set up the layout of each page and to set up the
    // list of Nodes that will represent the pages of the lesson plan
    ParseLessonPlanPrinting(){
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        PageLayout pgLayout = printerJob.getJobSettings().getPageLayout();
        pageHeight = pgLayout.getPrintableHeight();
        pageWidth = pgLayout.getPrintableWidth();
        parseLessonPlan();
    }

    private void parseLessonPlan() {
        eventToCardsMap = PrintStaging.getEventToCardMap();
        lessonPlanTitleS = PrintStaging.getLessonPlanTitle();
        Font titleFont = new Font("Times New Roman", 30);
        lessonPlanTitleL.setText(lessonPlanTitleS);
        lessonPlanTitleL.setFont(titleFont);

    }


}
