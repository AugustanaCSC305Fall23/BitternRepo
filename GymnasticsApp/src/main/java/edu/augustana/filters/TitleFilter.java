package edu.augustana.filters;

import edu.augustana.Card;

public class TitleFilter implements CardFilter{
    private String filter = "";
    public boolean match(Card card){
        if(card.getTitle().equalsIgnoreCase(filter) || card.getTitle().toLowerCase().contains(filter.toLowerCase()) || filter.isEmpty()) return true;
        return false;
    }
    public void setFilter(String filter){
        this.filter = filter;
    }
    public void resetFilter(){
        filter = "";
    }
}
