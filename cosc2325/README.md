cosc2325 Computing Sciences Concepts II.<br />
Class projects completed during the Spring 2014 semester.<br />

I have added a .jar (executable) for each of the projects in this course. Please see descriptions given below for each of the labs.<br />

<ul>
<li><strong>Lab6: Vet Visits for House Pets (does not include a GUI)</strong><br />
Program menu allows user to choose among the following actions:<br />
Populate list with HousePet data from a file (hpvv.txt included in .zip), add/remove a HousePet to/from the list, remove all HousePets from list, view current list of HousePets, find out whether a HousePet is in the current list, view the number of HousePets in current list, search for a HousePet by name, or modify the age of a HousePet in the list.<br />
The abstract VetVisit class is extended by two types of VetVisits: urgent and standard.<br />
User may also add or remove a VetVisit for a given HousePet; if a VetVisit is removed, it is written to a 'cancellations' file.<br />
Program also keeps track of changes made to the data: upon selecting option to exit program, if any data has been modified, will ask user if they would like to save the changes before exiting. If user elects to 'save', any changes will be written to file (user-provided filename).
  <ul>
    <li><em><strong>Topics:</strong> Abstract classes, object-oriented design/programming, binary search, Comparable interface</em>
  </ul>
<li><strong>Lab7: Stack Testing (with GUI)</strong><br />
Two separate stacks are maintained, one implemented using an ArrayList, the other implemented using a LinkedList.<br />
User may push, pop, peek, clear, get size of stack, etc., as well as switch between the two stacks.<br />
  <ul>
    <li><em><strong>Topics:</strong> Stacks, abstract data types</em>
  </ul>
<li><strong>Lab8. Infix-to-Postfix Conversion (with GUI)</strong><br />
Converts user-provided infix expression to the corresponding postfix equivalent.<br />
Uses ArrayList implementation of a stack.<br />
  <ul>
    <li><em><strong>Topics:</strong> Stacks, infix and postfix expressions, abstract data types</em>
  </ul>
<li><strong>Lab9: Array Sorting (with GUI)</strong><br />
Sorting randomly-generated array of 400 ints; array is reshuffled each time user chooses to begin a new sort method.<br />
Three sort methods to choose from (insertion sort, quick sort, and merge sort), with choice of three speed settings and ability to pause/resume the sort. If user selects a different speed while a sort is already in progress, the change in speed will take effect immediately. Duration of pause is currently set to 5000 ms.<br />
Includes thread management and a GUI so user may watch the sorts-in-progress.<br />
  <ul>
    <li><em><strong>Topics:</strong> Sorts, recursion, threads</em>
  </ul>
<li><strong>Lab10: Queue Testing (with GUI)</strong><br />
QueueList (LinkedList analogue) implementation of a queue with a GUI<br />
User may add an item to the queue, remove an item, clear the queue, and see the first and last items in the current queue.<br />
The current size of the queue and the amount of space available are updated as appropriate and displayed continuously in non-editable text fields. User may change the max size of the queue at any time.<br />
  <ul>
    <li><em><strong>Topics:</strong> Queues</em>
  </ul>
</ul>
