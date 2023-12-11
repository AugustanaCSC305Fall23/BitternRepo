package edu.augustana.filters;

import edu.augustana.model.Card;

public interface CardFilter {

    // Determines if a card matches the desired filters
    boolean matchesFilters(Card card);
}
