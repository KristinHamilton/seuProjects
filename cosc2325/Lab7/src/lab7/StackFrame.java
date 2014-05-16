package lab7;
/**
 * @author Laura Baker
 * @version 1.1
 * program to demonstrate use of GUI 
 *  create a simple demo of a few stack operations
 *  
 * edited by Kristin Hamilton for Lab7.
 * added a second buttonPanel.
 * added buttons: popButton, peekButton, sizeButton, and spaceButton, and created
 * actionListeners for each; edited area.text for all actionListeners.
 * modified jframe dimensions. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class StackFrame extends JFrame 
{
    /** these are all of the widgets (parts) that go on the Frame
     *  Buttons for execution control, TextFields for input and output
     */
    public static final int FRAMEHEIGHT=450;
    public static final int FRAMEWIDTH=700;
    JButton stackButton;
    JButton pushButton;
    JButton popButton;
    JButton peekButton;
    JButton showButton;
    JButton sizeButton;
    JButton spaceButton;
    JButton clearButton;

    JTextArea area;
    JPanel buttonPanel1;
    JPanel buttonPanel2;
    JTextField field;
    StackList <String> stackA;
    StackList<String> stackL;
    boolean isStackA;  // flag to tell us which stack we are using

    /* Default Frame constructor, initializes the frame's components */
    public StackFrame()
    {
        // first create the buttons and widgets to put on the frame

        pushButton = new JButton("Push Item");
        popButton = new JButton("Pop Item");
        peekButton = new JButton("Peek At Stack");
        showButton = new JButton("Show Stack");
        sizeButton = new JButton("Show Stack Size");
        spaceButton = new JButton("Show Stack Space");
        stackButton = new JButton("Switch Stacks");
        clearButton = new JButton("Clear Stack");

        field = new JTextField(50);
        area = new JTextArea(10,50);
        field.setText("Current stack: stackA");
        stackA = new StackAImpl<String>();
        stackL = new StackLImpl<String>();
        isStackA=true;

        buttonPanel1 = new JPanel();  // used to keep the buttons together
        buttonPanel2 = new JPanel();
        // 
        this.setTitle("Stack Testing");
        
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(buttonPanel1);
        this.getContentPane().add(buttonPanel2);
        this.getContentPane().add(field);
        this.getContentPane().add(area);
        
        buttonPanel1.add(pushButton);
        buttonPanel1.add(popButton);
        buttonPanel1.add(peekButton);
        buttonPanel1.add(showButton);
        
        buttonPanel2.add(sizeButton);
        buttonPanel2.add(spaceButton);
        buttonPanel2.add(stackButton);
        buttonPanel2.add(clearButton);

        area.setEditable(false); // its now UNchangeable
        
        
        /* action listener: push button */
        pushButton.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                doPush();
            }
            
        } );

        
        /* action listener: pop button */
        popButton.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {             
                String poppedItem = "";
                
                if(isStackA)
                {
                    poppedItem = stackA.pop();
                    area.setText("pop successful!\n" +
                        "Here's what was popped from stackA: " + poppedItem + "\n\n" +
                        "Here are the current contents of stackA:\n" + stackL); 
                }
                else
                {
                    poppedItem = stackL.pop();
                    area.setText("pop successful!\n" +
                        "Here's what was popped from stackL: " + poppedItem + "\n\n" +
                        "Here are the current contents of stackL:\n" + stackL);                  
                }               
                
            }
            
        } );
        
        
        /* action listener: peek button */
        peekButton.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                String peekItem = "";
                
                if(isStackA)
                {
                    peekItem = stackA.peek();
                    area.setText("Here's the item on the top of stackA: " + peekItem);
                }
                else
                {
                    peekItem = stackL.peek();
                    area.setText("Here's the item on the top of stackL: " + peekItem);
                }               
                
            }
            
        } );
        
        
        /* action listener: stack button */
        stackButton.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                isStackA = !isStackA;
                
                if(isStackA)
                {
                    field.setText("Current stack: stackA");
                    area.setText("Here are the current contents of stackA:\n" + stackA);
                }
                else
                {
                    field.setText("Current stack: stackL");
                    area.setText("Here are the current contents of stackL:\n" + stackL);
                }

            }
            
        } );
        
        /* action listener: show button */
        showButton.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                if(isStackA)
                    area.setText("Here are the current contents of stackA:\n" + stackA);
                
                else
                    area.setText("Here are the current contents of stackL:\n" + stackL);
            }
            
        } );

        
        /* action listener: size button */
        sizeButton.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                if(isStackA)
                    area.setText("Here's the current size of stackA:  " + stackA.size() +
                        " items");
                
                else
                    area.setText("Here's the current size of stackL:  " + stackL.size() +
                        " items");
            }
            
        } );
        
        
        /* action listener: space button */
        spaceButton.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                if(isStackA)
                {
                    if(!stackA.isFull())
                        area.setText("stackA has room for " + (5 - stackA.size()) +
                            " more items.");
                
                    else
                        area.setText("stackA is already full.");
                }
                
                else
                {
                    if(!stackL.isFull())
                        area.setText("stackL has room for " + (5 - stackL.size()) +
                            " more items.");
            
                    else
                        area.setText("stackL is already full.");
                }
            }
            
        } );
        
        
        /* action listener: clear button */
        clearButton.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                if(isStackA)
                {
                    stackA.clear();
                    area.setText("clear successful!\n" + stackA);
                }
                else
                {
                    stackL.clear();
                    area.setText("clear successful!\n" + stackL);
                }
            }
            
        } );


    }// end of constructor

    // method to manage push onto a stack
    // pre: none
    // post: if not full, the currently used stack has a new value pushed onto it
    public void doPush()
    {
        String pushedItem = JOptionPane.showInputDialog(this, "Please enter item to push to stack.", "");
        
        if(isStackA)
        {
            if(!stackA.isFull())
            {
                stackA.push(pushedItem);
                area.setText("push successful!\n" +
                        "Here's what was pushed onto stackA: " + pushedItem + "\n\n" +
                        "Here are the current contents of stackA:\n" + stackA);
            }
            
            else
            {
                area.setText("Unable to push item to stackA: stack is full");
            }
        }
        
        else
        {   
            if(!stackL.isFull())
            {
                stackL.push(pushedItem);
                area.setText("push successful!\n" +
                    "Here's what was pushed onto stackL: " + pushedItem + "\n\n" +
                    "Here are the current contents of stackL:\n" + stackL);
            }
            else
            {
                area.setText("Unable to push item to stackL: stack is full");
            }
        }
        
    }//end doPush()
  
    
    /** main method, really just creates the frame (window) and
     *  waits (listens) for user actions (inputs).
     * @param args - command line arguments - none expected
     */ 
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
            
        } );
        
    }//end main()
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Make sure we have nice window decorations so use default for system
        JFrame.setDefaultLookAndFeelDecorated(true);    

        //Create and set up the window.
        StackFrame frame = new StackFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(StackFrame.FRAMEWIDTH, StackFrame.FRAMEHEIGHT);
        
        //Display the window.
        frame.setVisible(true);

    } // end createAndShowGui
    
}// end class StackFrame