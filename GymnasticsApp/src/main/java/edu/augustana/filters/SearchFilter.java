package edu.augustana.filters;

import edu.augustana.model.Card;
import edu.augustana.model.FilterHandler;

import java.util.List;

public class SearchFilter implements CardFilter {
    private List<String> searchTermList;

    public SearchFilter(List<String> searchTermList) {
        this.searchTermList = searchTermList;
    }

    @Override
    public boolean matchesFilters(Card card){
        if (searchTermList.isEmpty()) {
            return true;
        }
        for (String term : searchTermList) {
            term = term.toLowerCase();
            if (!termMatches(term, card)) {
                return false;
            }
        }
        return true;
    }

    // Helper method to check all the different fields of the card to see if they match the search term
    private boolean termMatches(String term, Card card) {
        return (card.getTitle().toLowerCase().contains(term)
                || card.getEvent().toLowerCase().contains(term)
                || card.getEvent().equalsIgnoreCase("ALL")
                || matchesLevel(term, card)
                || card.getCategory().toLowerCase().contains(term)
                || card.getCode().toLowerCase().contains(term)
                || isKeyword(term, card)
                || isInEquipmentList(term, card))
                || matchesGender(term, card);
    }

    // Checks to see if the term is one of the keywords for the card
    private boolean isKeyword(String term, Card card) {
        for (String keyword : card.getKeywords()) {
            if (keyword.toLowerCase().contains(term)) {
                return true;
            }
        }
        return false;
    }

    // Checks to see if the term is one of the pieces of equipment for the card
    private boolean isInEquipmentList(String term, Card card) {
        for (String equipment : card.getEquipment()) {
            if (equipment.toLowerCase().contains(term)) {
                return true;
            }
        }
        return false;
    }

    // Checks to see if the term matches the level for the card
    private boolean matchesLevel(String term, Card card) {
        FilterHandler filterHandler = new FilterHandler();
        List<String> cardLevelWords = filterHandler.convertLevelKeysToWords(card.getLevel());

        for (String word : cardLevelWords) {
            if (word.equalsIgnoreCase("all") || word.contains(term)) {
                return true;
            }
        }
        return false;
    }

    // Checks to see if the term matches the card's gender or a synonym
    private boolean matchesGender(String term, Card card) {
        if (isNeutralSynonym(term)) {
            return true;
        } else if (card.getGender().equalsIgnoreCase("M") && isBoySynonym(term)) {
            return true;
        } else if (card.getGender().equalsIgnoreCase("F") && isGirlSynonym(term)) {
            return true;
        } else {
            return false;
        }
    }

    // Checks to see if the term is a synonym for boy
    private boolean isBoySynonym(String term) {
        List<String> boySynonyms = List.of("m, male, man, men, boy");
        return (boySynonyms.contains(term));
    }

    // Checks to see if the term is a synonym for girl
    private boolean isGirlSynonym(String term) {
        List<String> girlSynonyms = List.of("f, woman, female, women, girl");
        return (girlSynonyms.contains(term));
    }

    // Checks to see if the term is a synonym for neutral
    private boolean isNeutralSynonym(String term) {
        List<String> neutralSynonyms = List.of("n, neutral, all, any");
        return (neutralSynonyms.contains(term));
    }
}
