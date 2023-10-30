package edu.augustana.filters;

import edu.augustana.Card;

public interface CardFilter {
    boolean match(Card card);
    void setFilter(String filter);
    void resetFilter();

}
