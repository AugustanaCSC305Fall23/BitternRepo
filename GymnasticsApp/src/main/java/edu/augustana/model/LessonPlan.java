package edu.augustana.model;

import edu.augustana.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private String title;
    private Map<String, List<String>> eventInPlanList = new TreeMap<>();
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
        List<String> cardDisplay = new ArrayList<>();
        cardDisplay.add(card.getUniqueID());
        eventInPlanList.put(card.getEvent(), cardDisplay);
        eventIndexes.add(card.getEvent());
    }
    //rename this method
    public void addCardToEvent(Card card){
        eventInPlanList.get(card.getEvent()).add(card.getUniqueID());
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
    public Map<String, List<String>> getEventInPlanList(){
        return eventInPlanList;
    }

    public boolean cardInPlanList(Card card){
        if(eventInPlanList.get(card.getEvent()).contains(card)){
            return true;
        }
        return false;
    }

    public Map<String, List<Card>> getMapOfCardsFromID(Map<String, List<String>> mapOfIDs){
        Map<String, List<Card>> mapOfCardsFromID = new TreeMap<>();
        for(String event : mapOfIDs.keySet()){
            List<Card> cardsFromID = new ArrayList<>();
            for(String cardID : mapOfIDs.get(event)) {
                cardsFromID.add(CardDatabase.getFullCardCollection().getCardByID(cardID));
            }
        }
        return  mapOfCardsFromID;
    }

    @Override
    public String toString() {
        return title;
    }
}
