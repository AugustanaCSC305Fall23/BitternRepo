package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EventFilter extends CardFilter {
    public static final ObservableList<String> eventFilters = FXCollections.observableArrayList(new String[]{"Beam", "Floor", "Horizontal Bars",
            "Parallel Bars", "Pommel Horse", "Rings", "Strength", "Trampoline", "Vault"});
    private static List<String> checkedEventFilters = new ArrayList<>();
    private String filter;
    public boolean matchCheckbox(Card card) {
        if ((checkedEventFilters.isEmpty()) || checkedEventFilters.contains(card.getEvent()) || card.getEvent().equals("ALL")) {
            return true;
        }
        return false;
    }
    @Override
    public boolean match(Card card){
        if(filter != null){
            if(card.getEvent().equalsIgnoreCase(filter) || card.getEvent().toLowerCase().contains(filter.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    public ObservableList<String> getFilter(){
        return eventFilters;
    }
    public void setCheckedFilters(List<String> checkedFilters){
        checkedEventFilters.addAll(checkedFilters);
    }
    public void setFilter(String filter){
        this.filter = filter;
    }
    public void resetFilter(){
        checkedEventFilters.clear();
        filter = "";
    }
}
