package edu.augustana.filters;

import edu.augustana.model.Card;

public interface CardFilter {
    public boolean matchesFilters(Card card);
    //public void resetDesiredFiltersList();
}
