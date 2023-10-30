package edu.augustana.filters;

import edu.augustana.Card;

public class EquipmentFilter implements CardFilter{
    private String filter;
    public boolean match(Card card){
        for(String eq : card.getEquipment()){
            if(eq.equalsIgnoreCase(filter) || eq.toLowerCase().contains(filter.toLowerCase())){
                return true;
            }
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
