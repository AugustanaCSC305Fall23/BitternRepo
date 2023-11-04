package edu.augustana.filters;

import edu.augustana.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class GenderFilter extends CardFilter {
    public static final ObservableList<String> genderFilters = FXCollections.observableArrayList(new String[]{"Boy", "Girl", "Neutral"});
    private static List<Character> checkedGenderFilters = new ArrayList<>();
    private Character filter = 'z';
    public boolean matchCheckbox(Card card) {
        if ((checkedGenderFilters.isEmpty()) || checkedGenderFilters.contains(card.getGender())) {
            return true;
        }
        return false;
    }
    public boolean match(Card card){
        if(card.getGender() == filter){
            return true;
        }
        return false;
    }

    //@Override
    public ObservableList<String> getFilter(){
        return genderFilters;
    }
    public void setFilter(String filter){
        if(filter.equalsIgnoreCase("boy") || filter.equalsIgnoreCase("male") || filter.equalsIgnoreCase("man")){
            this.filter = 'M';
        }else if(filter.equalsIgnoreCase("girl") || filter.equalsIgnoreCase("woman") || filter.equalsIgnoreCase("female")){
            this.filter = 'F';
        }else if(filter.equalsIgnoreCase("neutral")){
            this.filter = 'N';
        }
    }

    //@Override
    public void setChecked(List<Character> checkedFilters){
        checkedGenderFilters.addAll(checkedFilters);
    }
    public void resetFilter(){
        checkedGenderFilters.clear();
        filter = 'z';
    }
}
