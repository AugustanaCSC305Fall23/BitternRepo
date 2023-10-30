package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ModelSexFilter implements CardFilter {
    public static final ObservableList<String> modelSexFilters = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});
    private static List<Character> checkedModelSexFilters = new ArrayList<>();
    private Character filter = 'z';
    public boolean matchCheckbox(Card card) {
        if ((checkedModelSexFilters.isEmpty()) || checkedModelSexFilters.contains(card.getModelSex())) {
            return true;
        }
        return false;
    }
    public boolean match(Card card){
        if(card.getModelSex() == filter) return true;
        return false;
    }
    public ObservableList<String> getFilter(){
        return modelSexFilters;
    }
    public void setFilter(String filter){
        if(filter.equalsIgnoreCase("boy") || filter.equalsIgnoreCase("male") || filter.equalsIgnoreCase("man")){
            this.filter = 'M';
        }else if(filter.equalsIgnoreCase("girl") || filter.equalsIgnoreCase("woman") || filter.equalsIgnoreCase("female")){
            this.filter = 'F';
        }
    }

    public void setChecked(List<Character> checkedFilter){
        checkedModelSexFilters.addAll(checkedFilter);
    }

    public void resetFilter(){
        checkedModelSexFilters.clear();
        filter = 'z';
    }

}
