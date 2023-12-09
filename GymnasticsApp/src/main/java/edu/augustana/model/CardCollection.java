package edu.augustana.model;

import java.util.*;

public class CardCollection {
    private Map<String, Card> mapFromIdToCard;
    public CardCollection() {
        mapFromIdToCard = new TreeMap<String, Card>();
    }

    public void addCard(Card newCard) {
        mapFromIdToCard.put(newCard.getUniqueID(), newCard);
    }


    public Card getCardByID(String cardId) {
        return mapFromIdToCard.get(cardId);
    }

    public Set<String> getSetOfCardIds() {
        return mapFromIdToCard.keySet();
    }

}
