package edu.augustana.structures;

import edu.augustana.model.Card;
import edu.augustana.model.CardDatabase;

import java.util.List;

import java.util.ArrayList;

public class EventSubcategory implements Cloneable {
    private String eventHeading;
    private List<String> cardIDList = new ArrayList<>(); //a list of the card IDs in the heading

    /**
     * Makes an EventSubheading object setting the heading and
     * adding the card's unique ID to the
     * @param heading String - the name of the subcategory
     * @param cardID String - the UniqueID of the card
     */
    public EventSubcategory(String heading, String cardID){
        eventHeading = heading;
        cardIDList.add(cardID);
    }
    public List<String> getCardIDList(){
        return cardIDList;
    }

    /**
     * Adds the uniqueID of the card to the end of the list of card ID's
     * @param cardId String - unique ID of the card to be added
     *               to the subcategory
     */
    public void addCardIDToList(String cardId){
        cardIDList.add(cardId);
    }

    public void setEventHeading(String newEventHeading){
        eventHeading = newEventHeading;
    }

    public String getEventHeading(){
        return eventHeading;
    }

    /**
     * Moves a card by its unique ID higher or lower by one in
     * the list of card ID's
     * @param direction int (-1 for up and 1 for down)
     * @param index of the card's unique ID in the list
     */
    public void moveCardByOne(int direction, int index){
        if (direction + index >= 0 && direction + index < cardIDList.size()) {
            String temp = cardIDList.get(direction + index);
            cardIDList.set(direction + index, cardIDList.get(index));
            cardIDList.set(index, temp);
        }
    }

    /**
     * Checks if a given card's uniqueID is in the list of card ID's for
     * this specific EventSubcategory
     * @param cardID String - uniqueID of a card to be checked
     * @return true if the ID is in the list and false otherwise
     */
    public boolean containsCardID(String cardID){
        for(String id : cardIDList){
            if(cardID.equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    public List<Card> getListOfCards(){
        List<Card> listOfCards = new ArrayList<>();
        for(String id : cardIDList){
            listOfCards.add(CardDatabase.getFullCardCollection().getCardByID(id));
        }
        return listOfCards;
    }

    @Override
    public EventSubcategory clone() {
        try {
            EventSubcategory clone = (EventSubcategory) super.clone();
            clone.cardIDList = new ArrayList<>();
            for (String cardID : cardIDList) {
                clone.addCardIDToList(cardID);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
