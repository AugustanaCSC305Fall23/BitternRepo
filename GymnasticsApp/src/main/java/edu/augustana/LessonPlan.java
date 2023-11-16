package edu.augustana;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private String title;
    private static List<Card> lessonPlanList = new ArrayList<>();
    //private boolean isSaved;
    private Map<String, List<Card>> eventInPlanList = new TreeMap<>();
    private List<String> eventIndexes = new ArrayList<>();
    private List<String> cardIDList = new ArrayList<>(); //card ID's that are in the lesson plan

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
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
        List<Card> cardDisplay = new ArrayList<>();
        cardDisplay.add(card);
        eventInPlanList.put(card.getEvent(), cardDisplay);
        eventIndexes.add(card.getEvent());
    }
    //rename this method
    public void addCardToEvent(Card card){
        eventInPlanList.get(card.getEvent()).add(card);
    }
    public boolean eventInPlanList(Card card){
        if(eventInPlanList.containsKey(card.getEvent())){
            return true;
        }
        return false;
    }
    public boolean isLessonPlanEmpty(){
        if(eventInPlanList.isEmpty()) return true;
        return false;
    }

    public List<String> getEventIndexes() {
        return eventIndexes;
    }
    public Map<String, List<Card>> getEventInPlanList(){
        return eventInPlanList;
    }

    public boolean cardInPlanList(Card card){
        if(eventInPlanList.get(card.getEvent()).contains(card)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return title;
    }
}
