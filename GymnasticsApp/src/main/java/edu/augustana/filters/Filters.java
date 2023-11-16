package edu.augustana.filters;

import edu.augustana.Model.Card;

public interface Filters {
    public boolean matchesFilters(Card card);
    public void resetDesiredFiltersList();
}
