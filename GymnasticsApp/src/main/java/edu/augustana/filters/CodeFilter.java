package edu.augustana.filters;

import edu.augustana.Card;

public class CodeFilter extends CardFilter {
    private String filter;
    public boolean match(Card card){
        if(card.getCode().equalsIgnoreCase(filter)){
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
