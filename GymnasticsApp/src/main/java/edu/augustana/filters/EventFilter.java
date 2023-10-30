package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EventFilter implements CardFilter {
    public static final ObservableList<String> eventFilters = FXCollections.observableArrayList(new String[]{"Beam", "Floor", "Horizontal Bars",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Vault"});
    private static List<String> checkedEventFilters = new ArrayList<>();
    public EventFilter(){

    }
    public boolean filter(Card card){
        if ((checkedEventFilters.isEmpty()) || checkedEventFilters.contains(card.getEvent()) || card.getEvent().equals("ALL")) {
            return true;
        }
        return false;
    }
    public ObservableList<String> getFilter(){
        return eventFilters;
    }

    public void setChecked(List<String> checkedFilters){
        checkedEventFilters.addAll(checkedFilters);
    }

    public void resetFilter(){
        checkedEventFilters.clear();
    }
}
