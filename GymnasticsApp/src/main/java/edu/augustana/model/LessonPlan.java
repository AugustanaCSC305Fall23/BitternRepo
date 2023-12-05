package edu.augustana.model;

import edu.augustana.structures.*;

import java.util.*;

public class LessonPlan {
    private String title;
    private IndexedMap lessonPlan;

    public LessonPlan() {
        lessonPlan = new IndexedMap();
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
        lessonPlan.add(new Category(card.getEvent(), card.getUniqueID()));
        System.out.println(lessonPlan.toString());
    }

    //rename this method
    public void addCardToEvent(Card card){
        Category addTo = lessonPlan.get(lessonPlan.get(card.getEvent()));
        addTo.addCardToList(card.getUniqueID());
        System.out.println(lessonPlan.toString());
    }
    public boolean eventInPlanList(Card card){
        if(lessonPlan.contains(card.getEvent())){
            return true;
        }
        return false;
    }
    public boolean isLessonPlanEmpty(){
        return (lessonPlan.isEmpty());
    }

    public void setEventInPlanList(IndexedMap lessonPlan){
        this.lessonPlan = lessonPlan;
    }

    public boolean cardInPlanList(Card card){
        return (lessonPlan.get(lessonPlan.get(card.getEvent())).contains(card.getUniqueID()));
    }

    public Map<String, List<Card>> getMapOfCardsFromID(IndexedMap mapOfIDs){
        Map<String, List<Card>> mapOfCardsFromID = new TreeMap<>();
        if(!mapOfIDs.isEmpty()) {
            for (ListIterator<Category> it = mapOfIDs.listIterator(); it.hasNext(); ) {
                Category category = it.next();
                List<Card> cardsFromID = new ArrayList<>();
                for (String cardID : category.getCardsInList()) {
                    cardsFromID.add(CardDatabase.getFullCardCollection().getCardByID(cardID));
                }
                mapOfCardsFromID.put(category.getCategoryHeading(), cardsFromID);
            }
        }
        return  mapOfCardsFromID;
    }
    public IndexedMap getLessonPlan(){
        return lessonPlan;
    }

    public void removeCard(String cardDisplayedTitle) {
        System.out.println(lessonPlan.toString());
        String cardIDToRemove = null;
        String eventToChange = null;
        for (ListIterator<Category> it = lessonPlan.listIterator(); it.hasNext(); ) {
            Category event = it.next();
            for (String id : event.getCardsInList()) {
                System.out.println(lessonPlan.toString());
                if (CardDatabase.getFullCardCollection().getCardByID(id).getDisplayedTitle().equals(cardDisplayedTitle)) {
                    cardIDToRemove = id;
                    eventToChange = event.getCategoryHeading();
                }
            }
        }
        if (cardIDToRemove != null) {
            lessonPlan.get(lessonPlan.get(eventToChange)).getCardsInList().remove(cardIDToRemove);
            System.out.println(lessonPlan.toString());
        }
        if(lessonPlan.get(lessonPlan.get(eventToChange)).getCardsInList().isEmpty()){
            lessonPlan.remove(lessonPlan.get(lessonPlan.get(eventToChange)));
        }
    }

    @Override
    public String toString() {
        return title;
    }
}
