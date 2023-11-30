package edu.augustana.structures;

import edu.augustana.*;
import edu.augustana.model.*;

import java.util.List;

import java.util.ArrayList;

public class Category {
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
}
