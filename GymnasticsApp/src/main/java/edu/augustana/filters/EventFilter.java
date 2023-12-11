package edu.augustana.filters;

import edu.augustana.model.Card;

import java.util.List;

public class EventFilter implements CardFilter {
    private final List<String> eventFilterList;

    public EventFilter(List<String> eventFilterList) {
        this.eventFilterList = eventFilterList;
    }

    @Override
    public boolean matchesFilters(Card card){
        return eventFilterList.isEmpty() || eventFilterList.contains(card.getEvent()) || card.getEvent().equals("ALL");
    }
}
