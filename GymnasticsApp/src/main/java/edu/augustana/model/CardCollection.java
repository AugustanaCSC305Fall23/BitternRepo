package edu.augustana.model;

import java.util.*;

/**
 * Represents a Map of cardIDs being mapped to their respective Card object.
 */
public class CardCollection {
    private Map<String, Card> mapFromIdToCard;

    /**
     * Creates a CardCollection
     */
    public CardCollection() {
        mapFromIdToCard = new TreeMap<String, Card>();
    }

    /**
     * Creates a new mapping from a cardID to a card
     * @param newCard The new card to add to the map
     */
    public void addCard(Card newCard) {
        mapFromIdToCard.put(newCard.getUniqueID(), newCard);
    }

    /**
     * Gets the card a specific ID is mapped to
     * @param cardId The cardID to get the mapping of
     * @return The card mapped to the passed-in cardID
     */
    public Card getCardByID(String cardId) {
        return mapFromIdToCard.get(cardId);
    }

    /**
     * Gets the key set of mapFromIDToCard
     * @return the key set of the map
     */
    public Set<String> getSetOfCardIds() {
        return mapFromIdToCard.keySet();
    }

}
