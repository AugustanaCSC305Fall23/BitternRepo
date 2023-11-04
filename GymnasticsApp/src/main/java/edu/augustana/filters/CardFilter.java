package edu.augustana.filters;

import edu.augustana.Card;

import java.util.List;

public abstract class CardFilter implements Filters {
    private static List<String> listOfDesiredFilters;
    public CardFilter(List<String> listOfDesiredFilters) {
        this.listOfDesiredFilters = listOfDesiredFilters;
    }
    public boolean matchesFilters(Card card){
        return false;
    }

    public boolean checkIfListEmpty(List<String> listOfCheckedFilters) {
        if (listOfCheckedFilters.isEmpty()) {
            return true;
        }

        return false;
    }
    public void updateListOfDesiredFilters(List<String> newListOfDesiredFilters){
        this.listOfDesiredFilters = listOfDesiredFilters;
    }
    public void resetDesiredFiltersList(){
        this.listOfDesiredFilters.clear();
    }

    public List<String> getListOfDesiredFilters() {
        return listOfDesiredFilters;
    }
}
