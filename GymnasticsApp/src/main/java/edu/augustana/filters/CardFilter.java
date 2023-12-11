package edu.augustana.filters;

import edu.augustana.model.Card;

public interface CardFilter {

    // Determines if a card matches the desired filters
    public boolean matchesFilters(Card card);
}
