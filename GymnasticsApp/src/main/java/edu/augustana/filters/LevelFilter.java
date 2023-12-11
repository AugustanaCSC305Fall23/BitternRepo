package edu.augustana.filters;

import edu.augustana.model.Card;

import java.util.List;

public class LevelFilter implements CardFilter {
    private final List<String> levelFilterList;

    public LevelFilter(List<String> levelFilterList) {
        this.levelFilterList = levelFilterList;
    }

    @Override
    public boolean matchesFilters(Card card){
        if (levelFilterList.isEmpty() || card.getLevel().equals("ALL")) return true;
        String[] levelOfCard = card.getLevel().split("\\s+");
        for (String string : levelOfCard) {
            if (levelFilterList.contains(string)) return true;
        }
        return false;
    }
}
