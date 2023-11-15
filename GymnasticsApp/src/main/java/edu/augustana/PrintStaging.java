package edu.augustana;

import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class PrintStaging {
    private static String past_fxml;

    private static Image printCard;
    private static List<Card> printCardList;
    private static Node printLessonPlan;
    private static Node printCoursePlan;

    PrintStaging(Image card, String fxml) {
        printCard = card;
        past_fxml = fxml;
    }

    PrintStaging(List<Card> cardList, String fxml) {
        past_fxml = fxml;
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

    public static List<Card> getPrintCardList() {
        return printCardList;
    }




}
