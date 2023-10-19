package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class CardCollection {
    private List<Card> cardList;
    public CardCollection() {
        cardList = new ArrayList<>();
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void addCard(Card newCard) {
        cardList.add(newCard);
    }
}
