package edu.augustana.model;

import edu.augustana.structures.*;

import java.util.*;

public class LessonPlan implements Cloneable{
    private String title;
    private IndexedMap lessonPlan;

    public LessonPlan() {
        lessonPlan = new IndexedMap();
        title = "Untitled";
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

    public List<String> getEquipmentFromMap(Map<String, List<Card>> eventToCardsMap) {
        List<String> equipmentList = new ArrayList<>();
        for (Map.Entry<String, List<Card>> event : eventToCardsMap.entrySet()) {
            List<Card> cardList = event.getValue();
            for (Card card : cardList) {
                String[] equipmentArray = card.getEquipment();
                equipmentList.addAll(Arrays.asList(equipmentArray));
            }
        }

        List<String> equipmentListFinal = new ArrayList<>();
        for (int i = 0; i < equipmentList.size(); i++) {
            String equipment = equipmentList.get(i);
            equipment = equipment.trim();
            if (!equipmentListFinal.contains(equipment)) {
                if (!equipment.equals("None")) {
                    equipmentListFinal.add(equipment);
                }
            }
        }

        return equipmentListFinal;
    }

    public void removeCard(String cardDisplayedTitle) {
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
            lessonPlan.get(lessonPlan.get(eventToChange)).getCardsInList().remove(cardIDToRemove);
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
        return title;
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
