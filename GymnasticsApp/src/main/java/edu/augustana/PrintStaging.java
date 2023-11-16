package edu.augustana;

import javafx.scene.image.Image;

import java.util.List;
import java.util.Map;

public class PrintStaging {
    private static String past_fxml;

    private static String lessonPlanTitle;
    private static Map<String, List<Card>> eventToCardMap;
    private static Image printCard;
    private static List<Card> printCardList;

    PrintStaging(Image card, String fxml) {
        printCard = card;
        past_fxml = fxml;
    }

    PrintStaging(List<Card> cardList, String fxml) {
        past_fxml = fxml;
        printCardList = cardList;
    }

    public PrintStaging(String title, Map<String, List<Card>> map, String fxml) {
        lessonPlanTitle = title;
        eventToCardMap = map;
        past_fxml = fxml;
    }

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
