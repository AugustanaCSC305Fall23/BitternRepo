package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Card;
import edu.augustana.model.CardDatabase;
import edu.augustana.model.LessonPlan;
import edu.augustana.structures.EventSubcategory;
import javafx.scene.control.TreeItem;

import java.util.ListIterator;

/*
    I used https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm and
    https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm to help with the tree view
    throughout this class
     */

public class TreeViewManager {
    private LessonPlan lessonPlan;
    public TreeViewManager(LessonPlan lessonPlan){
        this.lessonPlan = lessonPlan;
    }
    /**
     * Sets up the TreeView of a lesson plan by adding a new TreeItem for each category
     * and a new TreeItem for each card under that category
     * @param root the root of the treeView
     */
    public void displayTreeView(TreeItem<String> root){
        if(!lessonPlan.isLessonPlanEmpty()){
            Card card;
            for (ListIterator<EventSubcategory> it = lessonPlan.getLessonPlanIndexedMap().listIterator(); it.hasNext();) {

                EventSubcategory event = it.next();
                TreeItem<String> newEvent = new TreeItem<>(event.getEventHeading());
                for(String cardID : event.getCardIDList()){
                    card = CardDatabase.getFullCardCollection().getCardByID(cardID);
                    newEvent.getChildren().add(new TreeItem<>(card.getCode() + ", " + card.getTitle()));
                }
                root.getChildren().add(newEvent);

            }
            expandTreeItem(root);
        }
    }

    private void expandTreeItem(TreeItem<String> root){
        for(TreeItem<String> heading : root.getChildren()){
            heading.setExpanded(true);
        }
    }

    /**
     * Adds a card to the TreeView unless it is already there
     * and the event of the card if it is not already there
     * @param card to be displayed in the TreeView
     * @param root of the TreeView
     */
    public void addToTreeView(Card card, TreeItem<String> root){
        if (!App.getCurrentLessonPlan().isEventInPlanList(card)){
            App.getCurrentLessonPlan().addEventToPlanList(card);
            TreeItem<String> newEvent = new TreeItem<>(card.getEvent());
            newEvent.getChildren().add(new TreeItem<>(card.getCode() + ", " + card.getTitle()));
            root.getChildren().add(newEvent);
        } else{
            if (!App.getCurrentLessonPlan().cardInPlanList(card)){
                App.getCurrentLessonPlan().addCardToEvent(card);
                TreeItem<String> treeItem = new TreeItem<String>(card.getCode() + ", " + card.getTitle());
                root.getChildren().get(App.getCurrentLessonPlan().getLessonPlanIndexedMap().getDirection(card.getEvent())).getChildren().add(treeItem);
            }
        }
        expandTreeItem(root);
    }

    /**
     * Clears and constructs the TreeView based on the lesson plan
     * @param root of the TreeView
     */
    public void redrawTreeView(TreeItem<String> root){
        root.getChildren().clear();
        displayTreeView(root);
    }

    /**
     * Changes the heading displayed in the TreeView to what the user entered and makes that change in the
     * lesson plan itself
     * @param newHeading String of the user-entered heading
     * @param headingToEdit Category containing the heading to be changed
     * @param root of the treeview
     */
    public void setHeadingInTreeView(String newHeading, EventSubcategory headingToEdit, TreeItem<String> root){
        headingToEdit.setEventHeading(newHeading);
        redrawTreeView(root);
    }

    /**
     * Adds changes the order of the categories from the controller
     * in the controller and the lesson plan's indexed map
     * @param selectedEvent Category to be moved
     * @param direction int to move the event (-1 for up and 1 for down)
     * @param root of tree view
     */
    public TreeItem<String> moveEvent(EventSubcategory selectedEvent, int direction, TreeItem<String> root){
        int index = lessonPlan.getLessonPlanIndexedMap().getDirection(selectedEvent.getEventHeading());
        lessonPlan.getLessonPlanIndexedMap().moveEventByOne(direction, index);
        redrawTreeView(root);
        return root;
    }

    public TreeItem<String> moveCard(EventSubcategory event, String selectedCardID, int direction, TreeItem<String> root){
        int index = event.getCardIDList().indexOf(selectedCardID);
        lessonPlan.getLessonPlanIndexedMap().moveCardByOne(direction, index, event);
        redrawTreeView(root);
        return root;
    }

}
