package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EventFilter extends CardFilter {
    public EventFilter(List<String> listOfCheckedEventFilters) {
        super(listOfCheckedEventFilters);
    }

    @Override
    public boolean matchesFilters(Card card){
        List<String> checkedEventFilters = getListOfDesiredFilters();
        return checkIfListEmpty(checkedEventFilters) || checkedEventFilters.contains(card.getEvent()) || card.getEvent().equals("ALL");
    }
}
