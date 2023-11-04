package edu.augustana.filters;

import edu.augustana.Card;

public interface Filters {
    public boolean matchesFilters(Card card);
    public void resetDesiredFiltersList();
}
