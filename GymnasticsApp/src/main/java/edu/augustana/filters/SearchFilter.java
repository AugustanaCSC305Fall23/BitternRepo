package edu.augustana.filters;

import edu.augustana.model.Card;

import java.util.ArrayList;
import java.util.Arrays;
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

    public boolean termMatches(String term, Card card) {
        return (card.getTitle().toLowerCase().contains(term)
                || card.getEvent().toLowerCase().contains(term)
                || card.getEvent().equalsIgnoreCase("ALL")
                || matchesGender(term, card)
                || matchesLevel(term, card)
                || card.getCategory().toLowerCase().contains(term)
                || card.getCode().toLowerCase().contains(term)
                || isKeyword(term, card)
                || isInEquipmentList(term, card));
    }

    private boolean isKeyword(String term, Card card) {
        for (String keyword : card.getKeywords()) {
            if (keyword.toLowerCase().contains(term)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInEquipmentList(String term, Card card) {
        for (String equipment : card.getEquipment()) {
            if (equipment.toLowerCase().contains(term)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesLevel(String term, Card card) {
        String[] cardLevelSymbols = card.getLevel().split("\\+s");
        List<String> cardLevelWords = new ArrayList<>();
        for (String level : cardLevelSymbols) {
            if (level.contains("AB")) {
                cardLevelWords.add("advanced beginner");
            } else if (level.contains("A")) {
                cardLevelWords.add("advanced");
            } else if (level.contains("I")) {
                cardLevelWords.add("intermediate");
            } else if (level.contains("B")) {
                cardLevelWords.add("beginner");
            }
        }

        // advanced beginner never used since it is two words
        for (String word : cardLevelWords) {
            if (word.equals(term)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesGender(String term, Card card) {
        if (card.getGender().equalsIgnoreCase("M")) {
            return card.getGender().equalsIgnoreCase("N") || isBoySynonym(term);
        } else if (card.getGender().equalsIgnoreCase("F")) {
            return card.getGender().equalsIgnoreCase("N") || isGirlSynonym(term);
        } else {
            return isNeutralSynonym(term);
        }
    }

    private boolean isBoySynonym(String term) {
        List<String> boySynonyms = List.of("m, male, man, men, boy");
        return (boySynonyms.contains(term));
    }

    private boolean isGirlSynonym(String term) {
        List<String> girlSynonyms = List.of("f, woman, female, women, girl");
        return (girlSynonyms.contains(term));
    }

    private boolean isNeutralSynonym(String term) {
        List<String> neutralSynonyms = List.of("n, neutral");
        return (neutralSynonyms.contains(term));
    }
}
