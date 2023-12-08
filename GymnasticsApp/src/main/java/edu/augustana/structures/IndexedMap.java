package edu.augustana.structures;

import java.util.*;

public class IndexedMap implements Cloneable{
    private List<EventSubcategory> eventSubcategoryList = new ArrayList<>();

    public IndexedMap(){

    }
    public IndexedMap(EventSubcategory eventSubcategory){
        eventSubcategoryList.add(eventSubcategory);
    }
    public List<EventSubcategory> getEventSubcategoryList(){
        return eventSubcategoryList;
    }
    public int size(){
        return 0;
    }
    public boolean isEmpty(){
        return eventSubcategoryList.isEmpty();
    }
    public void addEventSubcategory(EventSubcategory eventSubcategory) {
        eventSubcategoryList.add(eventSubcategory);
    }

    public void addEventSubcategory(String eventHeading, String cardID){
        for(int i = 0; i < eventSubcategoryList.size(); i++){
            if(eventSubcategoryList.get(i).getEventHeading().equalsIgnoreCase(eventHeading)){
                eventSubcategoryList.get(i).addCardIDToList(cardID);
            }
        }
    }

    public boolean remove(EventSubcategory o) {
        return eventSubcategoryList.remove(o);
    }

    public void clear() {
        eventSubcategoryList.clear();
    }

    public EventSubcategory get(int index) {
        return eventSubcategoryList.get(index);
    }
    public Boolean contains(String heading){
        for(int i = 0; i < eventSubcategoryList.size(); i++){
            if(eventSubcategoryList.get(i).getEventHeading().equalsIgnoreCase(heading)){
                return true;
            }
        }
        return false;
    }
    public int get(String category){
        //TODO:
        for(int i = 0; i < eventSubcategoryList.size(); i++){
            if(eventSubcategoryList.get(i).getEventHeading().equals(category)){
                return i;
            }
        }
        return -1;
    }

    public EventSubcategory set(int index, EventSubcategory element) {
        return eventSubcategoryList.set(index, element);
    }

    public void addEventSubcategory(int index, EventSubcategory element) {
        eventSubcategoryList.add(index, element);
    }

    public int indexOf(EventSubcategory eventSubcategory) {
        for(int i = 0; i < eventSubcategoryList.size(); i++){
            if(eventSubcategoryList.get(i).equals(eventSubcategory)){
                return i;
            }
        }
        return -1;
    }

    public ListIterator<EventSubcategory> listIterator() {
        return eventSubcategoryList.listIterator();
    }

    public ListIterator<EventSubcategory> listIterator(int index) {
        return eventSubcategoryList.listIterator(index);
    }

    public List<EventSubcategory> subList(int fromIndex, int toIndex) {
        return eventSubcategoryList.subList(fromIndex, toIndex);
    }
    public void move(int fromIndex, int toIndex){
        //changes the position in the list that the heading is in along with
        //moving everything before or after
    }
    public void moveByOne(int direction, int index){
        //moves by one index for each call to method
        EventSubcategory temp = eventSubcategoryList.get(direction + index);
        eventSubcategoryList.set(direction + index, eventSubcategoryList.get(index));
        eventSubcategoryList.set(index, temp);
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
            //clone.indexedMap = new ArrayList<>(clone.indexedMap);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
