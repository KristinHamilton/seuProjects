package lab10;
/* *
 * [QueueFrame.java]
 * Author: Kristin Hamilton
 * Desc: GUI for Lab10
 * Date created: 29-Apr-2014 for Lab10
 * Date last modified: 04-May-2014 
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;  

@SuppressWarnings("serial")
public class QueueFrame extends JFrame
{
    QueueList<String> queueList;
    int currentSpace;       /* amt of empty space remaining in queueList */
    public static final int FRAMEHEIGHT = 400;
    public static final int FRAMEWIDTH = 700;
   
    JPanel buttonPanel1;    /* buttons for displaying all or part of queueList */
    JButton viewAllButton;
    JButton peekFrontButton;
    JButton peekBackButton;

    JPanel buttonPanel2;    /* buttons for queueList modification */
    JButton addButton;
    JButton removeButton;
    JButton clearButton;
        
    JTextArea textArea;     /* display area for success/failure msgs, etc. */
    
    JPanel queueSizePanel;
    JTextField queueCurrentSizeField;     /* non-editable; show #elements in queueList */
    JTextField queueSpaceRemainingField;  /* non-editable; show #empty slots in queueList */
    JButton changeMaxSizeButton;

    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       main method, calls buildAndDisplayGUI() to construct the GUI frame.
     */
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                buildAndDisplayGUI();
            }  
        });

    }//end main()

    /* *
     * Pre:  (nothing)
     *       called by main().
     * Post: (nothing)
     *       see method name.
     */
    public static void buildAndDisplayGUI()
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        QueueFrame newFrame = new QueueFrame();
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        newFrame.setVisible(true);

    }//end buildAndDisplayGUI()

    /* *
     * Pre:  (nothing)
     *       default constructor for QueueFrame
     * Post: (nothing)
     *       results in new instance of QueueFrame, and initialization of its attributes;
     *       method contains actionListeners for the following seven buttons:
     *         viewAllButton, peekFrontButton, peekBackButton;
     *         addButton, removeButton, clearButton;
     *         changeMaxSizeButton.
     *       see actionListeners for desc of a given button's function once selected
     */
    public QueueFrame()
    {
        queueList = new QueueLImpl<String>();
        currentSpace = queueList.getMaxSize();
        
        /* instantiate and assemble default JButtons, JPanels, JTextFields, JTextArea */
        viewAllButton = new JButton("View queue");
        peekFrontButton = new JButton("Peek at front of queue");
        peekBackButton = new JButton("Peek at back of queue");
        buttonPanel1 = new JPanel();
        buttonPanel1.add(viewAllButton);
        buttonPanel1.add(peekFrontButton);
        buttonPanel1.add(peekBackButton);
        
        addButton = new JButton("Add an item to the queue");
        removeButton = new JButton("Remove an item from the queue");
        clearButton = new JButton("Clear the queue");
        buttonPanel2 = new JPanel();
        buttonPanel2.add(addButton);
        buttonPanel2.add(removeButton);
        buttonPanel2.add(clearButton);
        
        textArea = new JTextArea(10, 50);
        textArea.setText("Queue contents displayed here");
        textArea.setEditable(false);
        
        queueCurrentSizeField = new JTextField(15);
        updateSetSizeField();
        queueSpaceRemainingField = new JTextField(15);
        updateSetSpaceField();
        changeMaxSizeButton = new JButton("Change max size of queue");
        queueSizePanel = new JPanel();
        queueSizePanel.add(queueCurrentSizeField);
        queueSizePanel.add(queueSpaceRemainingField);
        queueSizePanel.add(changeMaxSizeButton);        

        /* add panels to frame */
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(buttonPanel1);
        this.getContentPane().add(buttonPanel2);
        this.getContentPane().add(textArea);
        this.getContentPane().add(queueSizePanel);
        
        this.setTitle("Queue Testing");

        /* actionListener: viewAllButton */
        viewAllButton.addActionListener(new ActionListener(){
            /* *
             * sets textArea to display all items in queueList.
             */
            public void actionPerformed(ActionEvent e)
            {
                textArea.setText("Current queue contents:\n" + queueList);
                return;
            }

        });//end viewAllButton.addActionListener()

        /* actionListener: peekFrontButton */
        peekFrontButton.addActionListener(new ActionListener(){
            /* *
             * checks whether queueList is empty: if not, displays first queue item in
             * textArea; otherwise, displays error msg.
             */
            public void actionPerformed(ActionEvent e)
            {
                if(!(queueList.isEmpty()))
                {
                    textArea.setText("Item at front of queue: " + queueList.front());
                }

                else  //if(queueList.isEmpty())
                {
                    textArea.setText("Unable to get item: Queue is empty.");
                }
                
                return;
            }

        });//end peekFrontButton.addActionListener()
        
        /* actionListener: peekBackButton */
        peekBackButton.addActionListener(new ActionListener(){
            /* *
             * checks whether queueList is empty: if not, displays last queue item in
             * textArea; otherwise, displays error msg.
             */
            public void actionPerformed(ActionEvent e)
            {
                if(!(queueList.isEmpty()))
                {
                    textArea.setText("Item at back of queue: " + queueList.last());
                }

                else  //if(queueList.isEmpty())
                {
                    textArea.setText("Unable to get item: Queue is empty.");
                }
                
                return;
            }

        });//end peekBackButton.addActionListener()
        
        /* actionListener: addButton */
        addButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e)
            {
                doAdd();                
                return;
            }

        });//end addButton.addActionListener()
        
        /* actionListener: removeButton */
        removeButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e)
            {
                doRemove();
                return;
            }

        });//end removeButton.addActionListener()
        
        /* actionListener: clearButton */
        clearButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e)
            {
                doClear();                
                return;
            }

        });//end clearButton.addActionListener()
        
        /* actionListener: changeMaxSizeButton */
        changeMaxSizeButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e)
            {
                doSetNewMaxSize();
                return;
            }

        });//end changeMaxSizeButton.addActionListener()
        
    }//end default constructor
    
    /* *
     * Pre:  (nothing)
     *       called from actionListener for addButton.
     * Post: (nothing)
     *       prompts user to provide an item to add to queue via JOptionPane.
     *       first checks that item attempting to be added isn't null or an empty String.
     *         if item is invalid, sets textArea to display failure msg and exits.
     *       checks whether queueList is already full:
     *       if not, attempts to add item.
     *         if add() returns true ("item added"), sets textArea to display success msg,
     *         then updates size and space JTextFields.
     *         otherwise, sets textArea to display failure msg.
     *       if queueList already full, sets textArea to display failure msg.
     *       returns nothing.       
     */
    public void doAdd()
    {
        String item = JOptionPane.showInputDialog(this, "Please enter item to add to queue.", "");
        boolean itemAdded = false;

        if(item == null || item.equals(""))
        {
            textArea.setText("Unable to add item to queue: Item must not be an empty String.");
            return;
        }
        
        if(!(queueList.isFull()))
        {
            itemAdded = queueList.add(item);

            if(itemAdded)
            {
                textArea.setText("The following item was successfully added to the " +
                    "queue: " + item + "\n\n" + 
                    "Current queue contents:\n" + queueList);
                
                updateSetSizeField();
                updateSetSpaceField();
            }
            
            else
            {
                textArea.setText("Unable to add the following item to queue: " + item + 
                    "\n\n" +
                    "Current queue contents:\n" + queueList);
            }
        }
        
        else  //if(queueList.isFull())
        {
            textArea.setText("Unable to add item to queue: Queue is full.");
        }
        
        return;
            
    }//end doAdd()
    
    /* *
     * Pre:  (nothing)
     *       called from actionListener for removeButton.
     * Post: (nothing)
     *       checks whether queueList is empty. if not empty, does the following:
     *         verifies that item was removed as expected by checking the resultant size
     *         of queueList and checking that item returned from remove() is not null.
     *         if both the item removed from queueList is not null, and queueList
     *         size following remove() is as expected, sets textArea to display success
     *         msg, then updates size and space JTextFields.
     *         otherwise, sets textArea to display failure msg.
     *       if queueList is already empty, sets textArea to display errorMsg, then exits.
     *       returns nothing.
     */
    public void doRemove()
    {
        String removedItem = "";
        int previousSize = queueList.size();
        int expectedSize = previousSize - 1;
        
        if(!(queueList.isEmpty()))
        {
            removedItem = queueList.remove();
            
            if(removedItem != null && (queueList.size() == expectedSize))
            {
                textArea.setText("The following item was successfully removed " +
                    "from queue: " + removedItem + "\n\n" + 
                    "Current queue contents:\n" + queueList);
                
                updateSetSizeField();
                updateSetSpaceField();
            }
            
            else  
            {
                textArea.setText("Unable to remove item from queue.\n" +
                    "Current queue contents:\n" + queueList);
            }
        }
        
        else  //if(queueList.isEmpty())
        {
            textArea.setText("Unable to remove item from queue: Queue is empty.");
        }
        
        return;
        
    }//end doRemove
    
    /* *
     * Pre:  (nothing)
     *       Called from actionListener for clearButton.
     * Post: (nothing)
     *       calls queueList.clear().
     *       obviously, if successfully cleared, the queueList should then be empty:
     *       if indeed empty following clear(), sets textArea to display success msg,
     *       then updates size and space JTextFields;
     *       otherwise, sets textArea to display failure msg.
     *       returns nothing.
     */
    public void doClear()
    {
        queueList.clear();
        
        if(queueList.isEmpty())
        {
            textArea.setText("Queue cleared successfully!\n\n" +
                "Current queue contents:\n" + queueList);
            
            updateSetSizeField();
            updateSetSpaceField();
        }
        
        else  //if(!(queueList.isEmpty()))
        {
            textArea.setText("Unable to clear queue.\n\n" +
                "Current queue contents:\n" + queueList);
        }
        
        return;
        
    }//end doClear()
    
    /* *
     * Pre:  (nothing)
     *       Called from actionListener for changeMaxSizeButton.
     * Post: (nothing)
     *       Attempts to get valid String input from user via JOptionPane.
     *       Input is valid only if String can be parsed as an int.
     *       if input invalid, sets textArea to error msg and exits method;
     *       otherwise, checks whether int newMaxSize is valid (>= 0).
     *         if newMaxSize is not valid, sets textArea to display error msg and exits;
     *         otherwise, sets queueList's maxSize to newMaxSize, updates the size and
     *         space JTextFields, and displays success msg in textArea.
     *       returns nothing.
     *       
     *       Note: other statements/methods which may work in addition to the one used
     *       for parsing String input to get Integer object include the following:
     *       newMaxSize = decode(input); and newMaxSize = Integer.valueOf(input).intValue();
     *       there's also another method in the Integer class called parseInt or similar.
     */
    public void doSetNewMaxSize()
    {
        String input = JOptionPane.showInputDialog(this, "Please enter new maxSize for queue", "");
        int newMaxSize = 0;
                
        try
        {
            newMaxSize = (int) Integer.valueOf(input);
        }
        catch(NumberFormatException e)
        {
            textArea.setText("Unable to modify maxSize: New maxSize must be an int.\n" +
                "You entered: " + input);
            return;
        }

        if(newMaxSize < 0)
        {
            textArea.setText("Unable to modify maxSize: New maxSize must be a nonnegative int\n" +
                "You entered: " + input);
            return;
        }
        
        else  //if(newMaxSize >= 0)
        {
            queueList.setMaxSize(newMaxSize);
            updateSetSizeField();
            updateSetSpaceField();
            textArea.setText("maxSize modification successful!");
        }
        
        return;
        
    }//end doSetMaxSize()
    
    /* *
     * Pre:  (nothing)
     *       Caller methods include the following (ctrl+alt+H):
     *       doAdd(), doClear(), doRemove(), doSetMaxSize(), and QueueFrame().
     * Post: (nothing)
     *       Sets text in queueCurrentSizeField to reflect current queue size, then
     *       resets field to non-editable.
     *       returns nothing.
     */
    public void updateSetSizeField()
    {
        queueCurrentSizeField.setText("# items currently in queue: " + queueList.size());
        queueCurrentSizeField.setEditable(false);
        return;
    }
    
    /* *
     * Pre:  (nothing)
     *       Caller methods include the following (ctrl+alt+H):
     *       doAdd(), doClear(), doRemove(), doSetMaxSize(), and QueueFrame().
     * Post: (nothing)
     *       Calculates current space available in queueList, then sets the text in
     *       queueSpaceRemainingField to reflect calculated value and resets field to
     *       non-editable.
     *       returns nothing.
     */
    public void updateSetSpaceField()
    {
        currentSpace = queueList.getMaxSize() - queueList.size();
        queueSpaceRemainingField.setText("space remaining in queue: " + currentSpace);
        queueSpaceRemainingField.setEditable(false);
        return;
    }

}//end class QueueFrame
