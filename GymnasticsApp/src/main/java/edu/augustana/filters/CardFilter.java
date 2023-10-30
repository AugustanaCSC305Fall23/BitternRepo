package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Objects;

public interface CardFilter {
    boolean filter(Card card);

    ObservableList<String> getFilter();

    void resetFilter();

}
