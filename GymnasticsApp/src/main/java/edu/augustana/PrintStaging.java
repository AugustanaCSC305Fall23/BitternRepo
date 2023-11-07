package edu.augustana;

import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.List;

public class PrintStaging {
    private static String past_fxml;

    private static Image printCard;
    private static List<Image> printCardList;
    private static Node printLessonPlan;
    private static Node printCoursePlan;

    PrintStaging(Image card, String fxml) {
        printCard = card;
        past_fxml = fxml;
    }

    PrintStaging(List<Image> cardList) {
        printCardList = cardList;
    }

    //PrintPreview(Node lessonPlan) {
    //    printLessonPlan = lessonPlan;
    //}

    //PrintPreview(Node coursePlan) {
    //    printCoursePlan = coursePlan;
    //}

    public static Image getPrintCard() {
        return printCard;
    }

    public static String getFXML() {
        return past_fxml;
    }




}
