package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LevelFilter extends CardFilter {
    public LevelFilter(List<String> listOfCheckedLevelFilters) {
        super(listOfCheckedLevelFilters);
    }
    @Override
    public boolean matchesFilters(Card card){
        List<String> checkedLevelFilters = super.getListOfDesiredFilters();
        if (super.checkIfListEmpty(checkedLevelFilters) || checkedLevelFilters.contains(card.getLevel()) || card.getLevel().equals("ALL")) {
            return true;
        }
        return false;
    }

    /*public boolean match(Card card){
        if(card.getLevel().substring(0, 1).equals(filter)){
            return true;
        }
        return false;
    } */


    /* public void setFilter(String filter){
        if(filter.equalsIgnoreCase("beginner") || filter.equalsIgnoreCase("beginning") || filter.equalsIgnoreCase("easy")) this.filter = "B";
        else if(filter.equalsIgnoreCase("advanced") ||filter.equalsIgnoreCase("advance") || filter.equalsIgnoreCase("hard")) this.filter = "A";
        else if(filter.equalsIgnoreCase("intermediate") || filter.equalsIgnoreCase("medium")) this.filter = "I";
    } */

}
