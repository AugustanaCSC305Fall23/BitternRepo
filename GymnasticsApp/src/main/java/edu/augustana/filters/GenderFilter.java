package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GenderFilter extends CardFilter {
    public GenderFilter(List<String> listOfCheckedGenderFilters) {
        super(listOfCheckedGenderFilters);
    }
    @Override
    public boolean matchesFilters(Card card){
        List<String> checkedGenderFilters = getListOfDesiredFilters();
        if (checkIfListEmpty(checkedGenderFilters) || checkedGenderFilters.contains(card.getGender()) || card.getGender() == 'N') {
            return true;
        };
        return false;
    }

    /* public void setFilter(String filter){
        if(filter.equalsIgnoreCase("boy") || filter.equalsIgnoreCase("male") || filter.equalsIgnoreCase("man")){
            this.filter = 'M';
        }else if(filter.equalsIgnoreCase("girl") || filter.equalsIgnoreCase("woman") || filter.equalsIgnoreCase("female")){
            this.filter = 'F';
        }else if(filter.equalsIgnoreCase("neutral")){
            this.filter = 'N';
        }
    } */
}
