package edu.augustana.filters;

import edu.augustana.model.Card;

import java.util.List;

public class GenderFilter implements CardFilter {
    private List<String> genderFilterList;

    public GenderFilter(List<String> genderFilterList) {
        this.genderFilterList = genderFilterList;
    }

    @Override
    public boolean matchesFilters(Card card){
        return (genderFilterList.isEmpty() || genderFilterList.contains(card.getGender()) || card.getGender().equals("N"));
    }
}
