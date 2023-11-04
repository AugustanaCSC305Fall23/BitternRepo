package edu.augustana.filters;

import edu.augustana.Card;

public interface Filters {
    public boolean match(Card card);
    public void setFilter(String filter);
    public void resetFilter();
}
