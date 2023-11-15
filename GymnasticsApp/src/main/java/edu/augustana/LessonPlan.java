package edu.augustana;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private String title;
    private static List<Card> lessonPlanList = new ArrayList<>();
    //private boolean isSaved;
    private Map<String, List<String>> eventInPlanList = new TreeMap<>();
    private List<String> eventIndexes = new ArrayList<>();
    private List<String> cardIDList = new ArrayList<>();

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String newTitle) {
        title = newTitle;
    }
    public List<Card> getCardList(){
        List<Card> cardsInLesson = new ArrayList<>();
        for (String id : cardIDList) {
            cardsInLesson.add(CardDatabase.getFullCardCollection().getCardByID(id));
        }
        return cardsInLesson;
    }

    public void addCardToList(Card card){
        cardIDList.add(card.getUniqueID());
    }

    public void addEventToPlanList(Card card){
        List<String> cardDisplay = new ArrayList<>();
        cardDisplay.add(card.getCode() + ", " + card.getTitle());
        eventInPlanList.put(card.getEvent(), cardDisplay);
        eventIndexes.add(card.getEvent());
    }
    //rename this method
    public void addCardToEvent(Card card){
        eventInPlanList.get(card.getEvent()).add(card.getCode() + ", " + card.getTitle());
    }
    public boolean eventInPlanList(Card card){
        if(eventInPlanList.containsKey(card.getEvent())){
            return true;
        }
        return false;
    }

    public List<String> getEventIndexes() {
        return eventIndexes;
    }

    @Override
    public String toString() {
        return title;
    }
}
