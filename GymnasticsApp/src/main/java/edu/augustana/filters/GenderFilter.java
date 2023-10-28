package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class GenderFilter implements CardFilter {
    public static final ObservableList<String> genderFilters = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    private static List<Character> checkedGenderFilters = new ArrayList<>();

    public GenderFilter(){

    }

    public boolean filter(Card card){
        if ((checkedGenderFilters.isEmpty()) || checkedGenderFilters.contains(card.getGender())){
            return true;
        }
        return false;
    }

    public ObservableList<String> getFilter(){
        return genderFilters;
    }

    public void setChecked(List<Character> checkedFilters){
        checkedGenderFilters.addAll(checkedFilters);
    }

    public void resetFilter(){
        checkedGenderFilters.clear();
    }
}
