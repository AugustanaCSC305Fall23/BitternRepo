package edu.augustana.filters;

import edu.augustana.Card;

import java.util.List;

public abstract class CardFilter implements Filters {
    private List<String> listOfDesiredFilters;
    public CardFilter(List<String> listOfDesiredFilters) {
        this.listOfDesiredFilters = listOfDesiredFilters;
    }
    public abstract boolean matchesFilters(Card card);

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
