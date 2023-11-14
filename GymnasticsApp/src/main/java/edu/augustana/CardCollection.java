package edu.augustana;

import java.util.*;

public class CardCollection {
    private int count = 1;
    //private List<Card> cardList;
    private Map<String, Card> mapFromIdToCard;
    public CardCollection() {
        mapFromIdToCard = new HashMap<String, Card>();
        //cardList = new ArrayList<>();
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
