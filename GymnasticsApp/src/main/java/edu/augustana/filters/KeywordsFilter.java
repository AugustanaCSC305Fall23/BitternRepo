package edu.augustana.filters;


import edu.augustana.Card;

public class KeywordsFilter extends CardFilter{
    public boolean match(Card card){
        for(String keyword : card.getKeywords()){
            if(keyword.equalsIgnoreCase(filter) || keyword.toLowerCase().contains(filter.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
