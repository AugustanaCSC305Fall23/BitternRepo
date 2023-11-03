package edu.augustana.filters;

import edu.augustana.Card;

import java.util.ArrayList;
import java.util.List;

public class CardFilter {
    String filter = "";
    private List<String> checkedFilter = new ArrayList();
    public boolean match(Card card){
        return false;
    }
    public void setChecked(List<String> checkedFilters){
        this.checkedFilter.addAll(checkedFilters);

    }
    public void setFilter(String filter){
        this.filter = filter;
    }
    public void resetFilter(){
        filter = "";
    }
}
