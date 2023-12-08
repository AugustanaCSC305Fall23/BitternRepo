package edu.augustana.structures;

import java.util.List;

import java.util.ArrayList;

public class Category implements Cloneable {
    private String categoryHeading;
    private List<String> cardsInList = new ArrayList<>(); //a list of the card IDs in the heading
    public Category(String heading, String cardID){
        categoryHeading = heading;
        cardsInList.add(cardID);
    }
    public Category(String heading, List<String> IDList){
        categoryHeading = heading;
        cardsInList = IDList;
    }
    public List<String> getCardsInList(){
        return cardsInList;
    }

    public void addCardToList(String cardId){
        cardsInList.add(cardId);
    }

    public void setCategoryHeading(String newEventHeading){
        categoryHeading = newEventHeading;
    }

    public String getCategoryHeading(){
        return categoryHeading;
    }
    public void setCardsInList(List<String> IDList){
        cardsInList.addAll(IDList);
    }

    public boolean contains(String cardID){
        for(String id : cardsInList){
            if(cardID.equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Category clone() {
        try {
            Category clone = (Category) super.clone();
            clone.cardsInList = new ArrayList<>();
            for (String cardID : cardsInList) {
                clone.addCardToList(cardID);
            }
            //clone.cardsInList = new ArrayList<>(clone.cardsInList);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
