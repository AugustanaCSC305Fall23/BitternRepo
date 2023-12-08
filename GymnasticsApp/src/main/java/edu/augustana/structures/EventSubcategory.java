package edu.augustana.structures;

import java.util.List;

import java.util.ArrayList;

public class EventSubcategory implements Cloneable {
    private String eventHeading;
    private List<String> cardIDList = new ArrayList<>(); //a list of the card IDs in the heading
    public EventSubcategory(String heading, String cardID){
        eventHeading = heading;
        cardIDList.add(cardID);
    }
    public EventSubcategory(String heading, List<String> IDList){
        eventHeading = heading;
        cardIDList = IDList;
    }
    public List<String> getCardIDList(){
        return cardIDList;
    }

    public void addCardIDToList(String cardId){
        cardIDList.add(cardId);
    }

    public void setEventHeading(String newEventHeading){
        eventHeading = newEventHeading;
    }

    public String getEventHeading(){
        return eventHeading;
    }
    public void setCardIDList(List<String> IDList){
        cardIDList.addAll(IDList);
    }

    public boolean containsCardID(String cardID){
        for(String id : cardIDList){
            if(cardID.equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
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
