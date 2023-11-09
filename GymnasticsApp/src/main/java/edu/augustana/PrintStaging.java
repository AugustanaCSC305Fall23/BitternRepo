package edu.augustana;

import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.List;

public class PrintStaging {
    private static String past_fxml;

    private static Card printCard;
    private static List<Card> printCardList;
    private static Node printLessonPlan;
    private static Node printCoursePlan;

    /*PrintStaging(Image card, String fxml) {
        printCard = card;
        past_fxml = fxml;
    } */

    /*PrintStaging(List<Card> cardList) {
        printCardList = cardList;
    } */

    //PrintPreview(Node lessonPlan) {
    //    printLessonPlan = lessonPlan;
    //}

    //PrintPreview(Node coursePlan) {
    //    printCoursePlan = coursePlan;
    //}

    /*public static Card getPrintCard() {
        return printCard;
    } */

    public static void setPrintCardList(List<Card> cardList) {
        printCardList = cardList;
    }

    public static List<Card> getPrintCardList() {
        return printCardList;
    }

    public static void setFXML(String fxml) {
        past_fxml = fxml;
    }

    public static String getFXML() {
        return past_fxml;
    }




}
