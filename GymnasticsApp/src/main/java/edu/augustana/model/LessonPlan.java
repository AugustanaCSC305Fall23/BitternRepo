package edu.augustana.model;

import edu.augustana.structures.*;

import java.util.*;

/**
 * Represents a lesson plan
 */
public class LessonPlan implements Cloneable, Undoable{
    private String title;
    private IndexedMap lessonPlanIndexedMap;
    private String customNote;

    /**
     * Constructs a new lesson plan
     */
    public LessonPlan() {
        lessonPlanIndexedMap = new IndexedMap();
        title = "Untitled";
    }

    /**
     * Adds a new event and card to the lesson plan based on the card's event
     * @param card The card to be added
     */
    public void addEventToPlanList(Card card){
        List<String> cardDisplay = new ArrayList<>();
        cardDisplay.add(card.getUniqueID());
        lessonPlanIndexedMap.addEventSubcategory(new EventSubcategory(card.getEvent(), card.getUniqueID()));
    }

    /**
     * Adds a new card an event
     * @param card The card to be added
     */
    public void addCardToEvent(Card card){
        EventSubcategory addTo = lessonPlanIndexedMap.getEventAtIndex(lessonPlanIndexedMap.getDirection(card.getEvent()));
        addTo.addCardIDToList(card.getUniqueID());
    }

    /**
     * Determines if the lesson plan contains an event
     * @param card The card to determine the event from
     * @return True if the IndexedMap contains the event, false if otherwise
     */
    public boolean isEventInPlanList(Card card){
        return lessonPlanIndexedMap.contains(card.getEvent());
    }

    /**
     * Determines if the lesson plan is empty or not
     * @return True if the lesson plan is empty, otherwise false
     */
    public boolean isLessonPlanEmpty(){
        return (lessonPlanIndexedMap.isEmpty());
    }

    /**
     * Determines if a specific card is in the lesson plan
     * @param card The card to check the lesson plan for
     * @return True if the lesson plan contains the card, otherwise false
     */
    public boolean cardInPlanList(Card card){
        return (lessonPlanIndexedMap.getEventAtIndex(lessonPlanIndexedMap.getDirection(card.getEvent())).containsCardID(card.getUniqueID()));
    }

    /**
     * Gets a map of cardIDs mapped to their cards
     * @param mapOfIDs The map of cardIDs and cards
     * @return
     */
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

    /**
     * Gets the equipment needed for each card in a lesson plan
     * @param eventToCardsMap All the cards in a lesson plan
     * @return The list of all equipment needed
     */
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

    /**
     * Gets a cardID based on the title that displays in the Lesson Plan Creator screen
     * @param displayTitle The title that is displayed
     * @return The cardID for the card with the passed in displayed title
     */
    public String getIDFromDisplayTitle(String displayTitle){
        for(EventSubcategory subcategory : lessonPlanIndexedMap.getEventSubcategoryList()){
            List<Card> cardList = subcategory.getListOfCards();
            for(Card card : cardList){
                if(card.getDisplayedTitle().equals(displayTitle)){
                    return card.getUniqueID();
                }
            }
        }
        return "";
    }

    /**
     * Removes a card from the lesson plan
     * @param cardDisplayedTitle The displayed title of the card to remove
     */
    public void removeCard(String cardDisplayedTitle) {
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
            lessonPlanIndexedMap.getEventAtIndex(lessonPlanIndexedMap.getDirection(eventToChange)).getCardIDList().remove(cardIDToRemove);
        }
        if(lessonPlanIndexedMap.getEventAtIndex(lessonPlanIndexedMap.getDirection(eventToChange)).getCardIDList().isEmpty()){
            lessonPlanIndexedMap.remove(lessonPlanIndexedMap.getEventAtIndex(lessonPlanIndexedMap.getDirection(eventToChange)));
        }
    }

    public void setCustomNote(String note) {
        customNote = note;
    }

    public void setEventInPlanList(IndexedMap lessonPlan){
        this.lessonPlanIndexedMap = lessonPlan;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public IndexedMap getLessonPlanIndexedMap(){
        return lessonPlanIndexedMap;
    }

    public String getCustomNote() {
        return customNote;
    }

    /**
     *Provides a deep clone of the lesson plan. For use in the Undo-Redo mechanism
     * @return The clone of the lesson plan
     * @throws CloneNotSupportedException If this method is called on a class that doesn't implement the Cloneable interface
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
        return title;
    }
}
