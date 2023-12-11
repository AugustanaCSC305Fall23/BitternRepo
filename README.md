# BitternRepo
Team Bittern's Gymnastics Coach Lesson Planner

Version 1.0 Release Notes

About Gymnastics Planner Pro:
Gymnastics Planner Pro allows users to create and customize gymnastics lesson plans 
and courses using card packs provided by Samantha Keehn.

Special Features:
-Software can handle at least 1000 cards
-Extensible to new card packs by adding to card pack folder
	-All data files must be .csv 
	-Every card pack must contain folder titled "thumbs" with .jpg thumbnails

Known Bugs/Issues:
-Course and Lesson Plan titles are not undoable/redoable
-A file path can be saved to recent files multiple times
-When moving a card from one event to another, sometimes it throws an exception
-If trying to print a lesson without opening the lesson plan editor first from a saved file on your computer, an exception occurs.