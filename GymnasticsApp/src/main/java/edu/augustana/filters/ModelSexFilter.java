package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ModelSexFilter implements CardFilter {
    public static final ObservableList<String> modelSexFilters = FXCollections.observableArrayList(new String[]{"Boy", "Girl"});
    private static List<Character> checkedModelSexFilters = new ArrayList<>();

    public ModelSexFilter(){

    }

    public boolean filter(Card card){
        if ((checkedModelSexFilters.isEmpty()) || checkedModelSexFilters.contains(card.getModelSex())){
            return true;
        }
        return false;
    }

    public ObservableList<String> getFilter(){
        return modelSexFilters;
    }

    public void setChecked(List<Character> checkedFilter){
        checkedModelSexFilters.addAll(checkedFilter);
    }

    public void resetFilter(){
        checkedModelSexFilters.clear();
    }

}
