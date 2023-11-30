package edu.augustana.model;

import edu.augustana.App;
import edu.augustana.structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private String title;
    private Map<String, List<String>> eventInPlanList;
    private List<String> eventIndexes;
    private IndexedMap lessonPlan = new IndexedMap();

    public LessonPlan() {
        eventInPlanList = new TreeMap<>();
        eventIndexes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void addEventToPlanList(Card card){
        List<String> cardDisplay = new ArrayList<>();
        cardDisplay.add(card.getUniqueID());
        /*eventInPlanList.put(card.getEvent(), cardDisplay);
        eventIndexes.add(card.getEvent());*/
        lessonPlan.add(new Category(card.getEvent(), card.getUniqueID()));
        System.out.println(lessonPlan.toString());
    }
    //rename this method
    public void addCardToEvent(Card card){
        //eventInPlanList.get(card.getEvent()).add(card.getUniqueID());
        Category addTo = lessonPlan.get(lessonPlan.get(card.getEvent()));
        addTo.addCardToList(card.getUniqueID());
        //l
        System.out.println(lessonPlan.toString());
    }
    public boolean eventInPlanList(Card card){
        if(/*eventInPlanList.containsKey(card.getEvent()) && */lessonPlan.contains(card.getEvent())){
            return true;
        }
        return false;
    }
    public boolean isLessonPlanEmpty(){
        return (/*eventInPlanList.isEmpty() && */ lessonPlan.isEmpty());
    }

    public List<String> getEventIndexes() {
        return eventIndexes;
    }

    public void setEventInPlanList(Map<String, List<String>> eventList) {
        eventInPlanList = eventList;
        //lessonPlan.
    }

    public Map<String, List<String>> getEventInPlanList(){
        return eventInPlanList;
    }

    public boolean cardInPlanList(Card card){
        return (/*eventInPlanList.get(card.getEvent()).contains(card) || */lessonPlan.get(lessonPlan.get(card.getEvent())).contains(card.getUniqueID()));
    }

    public Map<String, List<Card>> getMapOfCardsFromID(Map<String, List<String>> mapOfIDs){
        Map<String, List<Card>> mapOfCardsFromID = new TreeMap<>();
        for(String event : mapOfIDs.keySet()){
            List<Card> cardsFromID = new ArrayList<>();
            for(String cardID : mapOfIDs.get(event)) {
                cardsFromID.add(CardDatabase.getFullCardCollection().getCardByID(cardID));
            }
            mapOfCardsFromID.put(event, cardsFromID);
        }
        return  mapOfCardsFromID;
    }
    public IndexedMap getLessonPlan(){
        return lessonPlan;
    }

    public void removeCard(String cardDisplayedTitle) {
        System.out.println(eventInPlanList);
        String cardIDToRemove = null;
        String eventToChange = null;
        for (String event : eventInPlanList.keySet()) {
            for (String id : eventInPlanList.get(event)) {
                System.out.println(eventInPlanList);
                if (CardDatabase.getFullCardCollection().getCardByID(id).getDisplayedTitle().equals( cardDisplayedTitle)) {
                    //eventInPlanList.get(event).remove(id);
                    //eventInPlanList.values().contains(id);
                    System.out.println(eventInPlanList);
                    cardIDToRemove = id;
                    eventToChange = event;
                }
            }
        }
        if (cardIDToRemove != null) {
            eventInPlanList.get(eventToChange).remove(cardIDToRemove);
            System.out.println(eventInPlanList);
        }

        }


    @Override
    public String toString() {
        return title;
    }
}
