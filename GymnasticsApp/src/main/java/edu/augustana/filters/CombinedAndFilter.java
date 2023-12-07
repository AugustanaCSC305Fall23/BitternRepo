package edu.augustana.filters;

import edu.augustana.model.Card;

public class CombinedAndFilter implements CardFilter {
    CardFilter[] filters;

    public CombinedAndFilter(CardFilter... filters) {
            this.filters = filters;
        }

    @Override
    public boolean matchesFilters(Card card) {
        for (CardFilter filter : filters) {
            if (!filter.matchesFilters(card)) {
                return false;
            }
        }
        return true;
    }
}
