package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class LevelFilter implements CardFilter {
    public static final ObservableList<String> levelFilters = FXCollections.observableArrayList(new String[]{"ALL", "A", "AB", "AB I", "B AB", "B AB I", "B I", "I", "I A"});
    private static List<String> checkedLevelFilters = new ArrayList<>();
    private String filter;

    public boolean matchCheckbox(Card card) {
        if ((checkedLevelFilters.isEmpty()) || checkedLevelFilters.contains(card.getLevel()) || card.getLevel().equals("ALL")) {
            return true;
        }
        return false;
    }
    public boolean match(Card card){
        if(card.getLevel().substring(0, 1).equals(filter)){
            return true;
        }
        return false;
    }
    public ObservableList<String> getFilter(){
        return levelFilters;
    }
    public void setChecked(List<String> checkedList){
        checkedLevelFilters.addAll(checkedList);
    }
    public void setFilter(String filter){
        if(filter.equalsIgnoreCase("beginner") || filter.equalsIgnoreCase("beginning") || filter.equalsIgnoreCase("easy")) this.filter = "B";
        else if(filter.equalsIgnoreCase("advanced") ||filter.equalsIgnoreCase("advance") || filter.equalsIgnoreCase("hard")) this.filter = "A";
        else if(filter.equalsIgnoreCase("intermediate") || filter.equalsIgnoreCase("medium")) this.filter = "I";
    }
    @Override
    public void resetFilter() {
        checkedLevelFilters.clear();
        filter = "";
    }
}
