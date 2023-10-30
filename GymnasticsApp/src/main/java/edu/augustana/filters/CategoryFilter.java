package edu.augustana.filters;

import edu.augustana.Card;

public class CategoryFilter implements CardFilter{
    private String filter;
    public boolean match(Card card){
        if(card.getCategory().equalsIgnoreCase(filter) || card.getCategory().toLowerCase().contains(filter.toLowerCase())){
            return true;
        }
        return false;
    }
    public void setFilter(String filter){
        this.filter = filter;
    }
    public void resetFilter(){
        filter = "";
    }
}
