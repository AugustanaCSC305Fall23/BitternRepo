package edu.augustana.filters;

import edu.augustana.model.Card;

import java.util.List;

public class ModelSexFilter implements CardFilter {
    private List<String> modelSexFilterList;

    public ModelSexFilter(List<String> modelSexFilterList) {
        this.modelSexFilterList = modelSexFilterList;
    }

    @Override
    public boolean matchesFilters(Card card){
        return modelSexFilterList.isEmpty() || modelSexFilterList.contains(card.getModelSex());
    }
}
