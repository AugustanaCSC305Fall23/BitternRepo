package edu.augustana.model;

import edu.augustana.structures.*;

import java.util.*;

public class LessonPlan implements Cloneable{
    private String title;
    private Map<String, List<String>> eventInPlanList;
    private List<String> eventIndexes;
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
    }
    //rename this method
    public void addCardToEvent(Card card){
        Category addTo = lessonPlan.get(lessonPlan.get(card.getEvent()));
        addTo.addCardToList(card.getUniqueID());
    }
    public boolean eventInPlanList(Card card){
        if(lessonPlan.contains(card.getEvent())){
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

    public void setEventInPlanList(IndexedMap lessonPlan){
        this.lessonPlan = lessonPlan;
    }

    public Map<String, List<String>> getEventInPlanList(){
        return eventInPlanList;
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
                if (CardDatabase.getFullCardCollection().getCardByID(id).getDisplayedTitle().equals(cardDisplayedTitle)) {
                    cardIDToRemove = id;
                    eventToChange = event.getCategoryHeading();
                }
            }
        }
        if (cardIDToRemove != null) {
            //eventInPlanList.get(eventToChange).remove(cardIDToRemove);
            lessonPlan.get(lessonPlan.get(eventToChange)).getCardsInList().remove(cardIDToRemove);
            System.out.println(lessonPlan.toString());
        }
        if(lessonPlan.get(lessonPlan.get(eventToChange)).getCardsInList().isEmpty()){
            lessonPlan.remove(lessonPlan.get(lessonPlan.get(eventToChange)));
        }


    }

    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public LessonPlan clone() {
        /**
         * Used https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone-- to
         * understand how to use the clone() method
         * Based this method on the clone() method in Drawing class of
         * DrawingApp
         */
                LessonPlan clone = new LessonPlan();
                clone.title = this.getTitle();
                clone.lessonPlan = this.lessonPlan.clone();
                return clone;
        }


    @Override
    public String toString() {
        return "LessonPlan{" +
                "title='" + title + '\'' +
                ", eventInPlanList=" + eventInPlanList +
                ", eventIndexes=" + eventIndexes +
                ", lessonPlan=" + lessonPlan +
                '}';
    }


    /**
     * For use by the Undo/Redo mechanism
     * @param copyOfPreviousState
     */
    public void restoreState(LessonPlan copyOfPreviousState) {
        this.title = copyOfPreviousState.title;
        this.lessonPlan = copyOfPreviousState.lessonPlan;

    }
}
