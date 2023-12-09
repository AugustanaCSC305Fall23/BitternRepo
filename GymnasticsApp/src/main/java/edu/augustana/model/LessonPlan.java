package edu.augustana.model;

import edu.augustana.structures.*;

import java.util.*;

public class LessonPlan implements Cloneable, Undoable{
    private String title;
    private IndexedMap lessonPlanIndexedMap;

    public LessonPlan() {
        lessonPlanIndexedMap = new IndexedMap();
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
        lessonPlanIndexedMap.addEventSubcategory(new EventSubcategory(card.getEvent(), card.getUniqueID()));
    }

    public void addCardToEvent(Card card){
        EventSubcategory addTo = lessonPlanIndexedMap.get(lessonPlanIndexedMap.get(card.getEvent()));
        addTo.addCardIDToList(card.getUniqueID());
    }
    public boolean eventInPlanList(Card card){
        return lessonPlanIndexedMap.contains(card.getEvent());
    }
    public boolean isLessonPlanEmpty(){
        return (lessonPlanIndexedMap.isEmpty());
    }

    public void setEventInPlanList(IndexedMap lessonPlan){
        this.lessonPlanIndexedMap = lessonPlan;
    }

    public boolean cardInPlanList(Card card){
        return (lessonPlanIndexedMap.get(lessonPlanIndexedMap.get(card.getEvent())).containsCardID(card.getUniqueID()));
    }

    public Map<String, List<Card>> getMapOfCardsFromID(IndexedMap mapOfIDs){
        Map<String, List<Card>> mapOfCardsFromID = new LinkedHashMap<>();
        if(!mapOfIDs.isEmpty()) {
            for (ListIterator<EventSubcategory> it = mapOfIDs.listIterator(); it.hasNext(); ) {
                EventSubcategory eventSubcategory = it.next();
                List<Card> cardsFromID = new ArrayList<>();
                for (String cardID : eventSubcategory.getCardIDList()) {
                    cardsFromID.add(CardDatabase.getFullCardCollection().getCardByID(cardID));
                }
                mapOfCardsFromID.put(eventSubcategory.getEventHeading(), cardsFromID);
            }
        }
        return  mapOfCardsFromID;
    }
    public IndexedMap getLessonPlanIndexedMap(){
        return lessonPlanIndexedMap;
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
        for (String equipment : equipmentList) {
            equipment = equipment.trim();
            if (!equipmentListFinal.contains(equipment)) {
                if (!equipment.equals("None")) {
                    equipmentListFinal.add(equipment);
                }
            }
        }

        return equipmentListFinal;
    }

    public void removeCard(String cardDisplayedTitle, UndoRedoHandler undoRedoHandler) {
        String cardIDToRemove = null;
        String eventToChange = null;
        for (ListIterator<EventSubcategory> it = lessonPlanIndexedMap.listIterator(); it.hasNext(); ) {
            EventSubcategory event = it.next();
            for (String id : event.getCardIDList()) {
                if (CardDatabase.getFullCardCollection().getCardByID(id).getDisplayedTitle().equals(cardDisplayedTitle)) {
                    cardIDToRemove = id;
                    eventToChange = event.getEventHeading();
                }
            }
        }
        if (cardIDToRemove != null) {
            lessonPlanIndexedMap.get(lessonPlanIndexedMap.get(eventToChange)).getCardIDList().remove(cardIDToRemove);
            //undoRedoHandler.saveState();
        }
        if(lessonPlanIndexedMap.get(lessonPlanIndexedMap.get(eventToChange)).getCardIDList().isEmpty()){
            lessonPlanIndexedMap.remove(lessonPlanIndexedMap.get(lessonPlanIndexedMap.get(eventToChange)));
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
        clone.lessonPlanIndexedMap = this.lessonPlanIndexedMap.clone();
        return clone;
        }


    /**
     * For use by the Undo/Redo mechanism
     * @param copyOfPreviousState
     */
    public void restoreState(Undoable copyOfPreviousState) {
        LessonPlan copyOfPreviousLessonState = (LessonPlan) copyOfPreviousState;
        this.title = copyOfPreviousLessonState.title;
        this.lessonPlanIndexedMap = copyOfPreviousLessonState.lessonPlanIndexedMap;
    }

    @Override
    public String toString() {
        return "LessonPlan{" +
                "title='" + title + '\'' +
                ", lessonPlan=" + lessonPlanIndexedMap +
                '}';
    }
}
