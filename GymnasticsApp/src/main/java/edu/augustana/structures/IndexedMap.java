package edu.augustana.structures;

import java.util.*;

public class IndexedMap implements Cloneable{
    private List<EventSubcategory> eventSubcategoryList = new ArrayList<>();

    public IndexedMap(){

    }

    public List<EventSubcategory> getEventSubcategoryList(){
        return eventSubcategoryList;
    }

    /**
     * Returns the number of elements in the list
     * @return number of elements in the list
     */
    public int size(){
        return eventSubcategoryList.size();
    }

    /**
     * Looks to see if the list is empty
     * @return true if the list is empty and false otherwise
     */
    public boolean isEmpty(){
        return eventSubcategoryList.isEmpty();
    }

    /**
     * Adds an EventSubcategory onto the end of the list of
     * EventSubcategories in this map
     * @param eventSubcategory EventSubcategory to add to the list
     */
    public void addEventSubcategory(EventSubcategory eventSubcategory) {
        eventSubcategoryList.add(eventSubcategory);
    }

    /**
     * Removes a given EventSubcategory if the element is there and
     * if it is not in the list then the list remains how it is
     * @param o EventSubcategory to be removed
     */
    public void remove(EventSubcategory o) {
        eventSubcategoryList.remove(o);
    }

    /**
     * Returns the EventSubcategory at a given position in the list
     * @param index of the element to return
     * @return the EventSubcategory at that index
     */
    public EventSubcategory get(int index) {
        return eventSubcategoryList.get(index);
    }

    /**
     * Checks to see if any of the EventSubcategories in the list
     * have a given heading
     * @param heading to be looked for
     * @return true if one of the EventSubcategories has the heading
     * and false otherwise
     */
    public Boolean contains(String heading){
        for(int i = 0; i < eventSubcategoryList.size(); i++){
            if(eventSubcategoryList.get(i).getEventHeading().equalsIgnoreCase(heading)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the position of an EventSubcategory with a given
     * heading
     * @param category heading of the given EventSubcategory
     * @return the index of a specific EventSubcategory by its heading
     */
    public int get(String category){
        for(int i = 0; i < eventSubcategoryList.size(); i++){
            if(eventSubcategoryList.get(i).getEventHeading().equals(category)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a list iterator over the EventSubcategories in this list
     * @return list iterator over the EventSubcategories in this list
     */
    public ListIterator<EventSubcategory> listIterator() {
        return eventSubcategoryList.listIterator();
    }

    /**
     * Moves the card up or down 1 in the EventSubcategory it currently is in
     * @param direction for the card to be moved (-1 for up and 1 for down)
     * @param index of the card in the EventSubcategory
     * @param subcategory EventSubcategory the card is in
     */
    public void moveCardByOne(int direction, int index, EventSubcategory subcategory){
        subcategory.moveCardByOne(direction, index);
    }

    /**
     * Moves a given EventSubcategory up or down by 1
     * @param direction for the event to be moved (-1 for up and 1 for down)
     * @param index of the EventSubcategory to be moved
     */
    public void moveEventByOne(int direction, int index){
        if (direction + index >= 0 && direction + index < eventSubcategoryList.size()) {
            EventSubcategory temp = eventSubcategoryList.get(direction + index);
            eventSubcategoryList.set(direction + index, eventSubcategoryList.get(index));
            eventSubcategoryList.set(index, temp);
        }
    }
    @Override
    public String toString(){
        String toReturn;
        String header;
        String list;
        toReturn = "{";
        for(EventSubcategory eventSubcategory : eventSubcategoryList){
            header = eventSubcategory.getEventHeading();
            list = eventSubcategory.getCardIDList().toString();
            toReturn = toReturn + header + " : " + list + "\n";
        }
        return toReturn + "}\n";
    }

    @Override
    public IndexedMap clone() {
        try {
            IndexedMap clone = (IndexedMap) super.clone();
            clone.eventSubcategoryList = new ArrayList<>();
            for (EventSubcategory eventSubcategory : eventSubcategoryList) {
                clone.addEventSubcategory(eventSubcategory.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
