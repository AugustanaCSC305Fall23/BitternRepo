package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelSexFilter extends CardFilter {
    public ModelSexFilter(List<String> listOfCheckedEventFilters) {
        super(listOfCheckedEventFilters);
    }
    @Override
    public boolean matchesFilters(Card card){
        List<String> checkedModelSexFilters = super.getListOfDesiredFilters();
        if (super.checkIfListEmpty(checkedModelSexFilters) || checkedModelSexFilters.contains(card.getModelSex())) {
            return true;
        };
        return false;
    }

    /* public void setFilter(String filter){
        if(filter.equalsIgnoreCase("boy") || filter.equalsIgnoreCase("male") || filter.equalsIgnoreCase("man")){
            this.filter = 'M';
        }else if(filter.equalsIgnoreCase("girl") || filter.equalsIgnoreCase("woman") || filter.equalsIgnoreCase("female")){
            this.filter = 'F';
        }
    } */
}
