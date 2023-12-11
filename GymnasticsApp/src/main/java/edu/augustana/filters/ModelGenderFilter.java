package edu.augustana.filters;

import edu.augustana.model.Card;

import java.util.List;

public class ModelGenderFilter implements CardFilter {
    private List<String> modelGenderFilterList;

    public ModelGenderFilter(List<String> modelGenderFilterList) {
        this.modelGenderFilterList = modelGenderFilterList;
    }

    @Override
    public boolean matchesFilters(Card card){
        return modelGenderFilterList.isEmpty() || modelGenderFilterList.contains(card.getModelSex());
    }
}
