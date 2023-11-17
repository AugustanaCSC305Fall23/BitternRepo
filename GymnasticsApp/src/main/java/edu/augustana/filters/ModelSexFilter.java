package edu.augustana.filters;

import edu.augustana.Model.Card;

import java.util.List;

public class ModelSexFilter extends CardFilter {
    public ModelSexFilter(List<String> listOfCheckedEventFilters) {
        super(listOfCheckedEventFilters);
    }
    @Override
    public boolean matchesFilters(Card card){
        List<String> checkedModelSexFilters = getListOfDesiredFilters();
        return checkIfListEmpty(checkedModelSexFilters) || checkedModelSexFilters.contains(card.getModelSex());
    }

    /* public void setFilter(String filter){
        if(filter.equalsIgnoreCase("boy") || filter.equalsIgnoreCase("male") || filter.equalsIgnoreCase("man")){
            this.filter = 'M';
        }else if(filter.equalsIgnoreCase("girl") || filter.equalsIgnoreCase("woman") || filter.equalsIgnoreCase("female")){
            this.filter = 'F';
        }
    } */
}
