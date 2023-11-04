package edu.augustana.filters;

import edu.augustana.Card;

public class EquipmentFilter extends CardFilter{
    public boolean match(Card card){
        for(String eq : card.getEquipment()){
            if(eq.equalsIgnoreCase(filter) || eq.toLowerCase().contains(filter.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
