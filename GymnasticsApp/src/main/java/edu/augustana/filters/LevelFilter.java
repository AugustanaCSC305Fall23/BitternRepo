package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class LevelFilter implements CardFilter {
    public static final ObservableList<String> levelFilters = FXCollections.observableArrayList(new String[]{"ALL", "A", "AB", "AB I", "B AB", "B AB I", "B I", "I", "I A"});
    private static List<String> checkedLevelFilters = new ArrayList<>();

    public LevelFilter(){

    }

    public boolean filter(Card card){
        if ((checkedLevelFilters.isEmpty()) || checkedLevelFilters.contains(card.getLevel()) || card.getLevel().equals("ALL")){
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

    @Override
    public void resetFilter() {
        checkedLevelFilters.clear();
    }
}
