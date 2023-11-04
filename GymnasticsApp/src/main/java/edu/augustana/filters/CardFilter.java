package edu.augustana.filters;

import edu.augustana.Card;

import java.util.ArrayList;
import java.util.List;

public class CardFilter implements Filters{
    String filter = "";
    private List<String> checkedFilters = new ArrayList<>();
    public boolean match(Card card){
        return false;
    }
    public void setCheckedFilters(List<String> checkedFilters){
        this.checkedFilters.addAll(checkedFilters);

    }
    public void setFilter(String filter){
        this.filter = filter;
    }
    public void resetFilter(){
        filter = "";
    }
}
