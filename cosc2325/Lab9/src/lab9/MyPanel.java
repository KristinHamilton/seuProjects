package lab9;
/* *
 * [MyPanel.java]
 * Author: Kristin Hamilton
 * Desc: class for MyPanel, the JPanel onto which the lines constituting myList will be
 *       drawn by paintComponent(). contains just two methods: a constructor, and 
 *       paintComponent().
 * Date created:  08-Apr-2014 for Lab9
 * Date last modified: 22-Apr-2014  
 */
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import java.awt.Color;

@SuppressWarnings("serial")
public class MyPanel extends JPanel
{
    public int[] myList;
    public static final int MYWIDTH = 600;
    public static final int MYHEIGHT = 610;
    
    /* *
     * Pre:  (int[])
     *       expects to receive array of ints. called from default constructor for
     *       Lab9Frame class.
     * Post: (nothing)
     *       instantiates new MyPanel sortPanel. assigns received int array to myList
     *       attribute, sets size of MyPanel, and sets foreground color (the color that
     *       the lines in paintComponent() will be drawn in).
     *       returns nothing.
     */
    public MyPanel(int[] aList)
    {
        Color myColor = new Color(0, 180, 180);
        this.myList = aList;
        this.setPreferredSize(new Dimension(MYWIDTH, MYHEIGHT));
        setForeground(myColor);
        
    }//end constructor
    
    /* *
     * Pre:  (Graphics)
     *       expects to receive Graphics g.
     * Post: (nothing)
     *       paints one line whose height corresponds to the value of the current element
     *       for each int in myList. moves along horizontally each time a line is drawn.
     *       returns nothing.
     */
    public void paintComponent(Graphics g)
    {
        int x1 = 0;
        int y1 = MYHEIGHT;
        int x2 = 0;
        int y2 = 0;
        
        for(int i = 0; i < myList.length; i++)
        {
            x1 = i + 100;
            x2 = i + 100;
            y2 = MYHEIGHT - myList[i];
            
            g.drawLine(x1, y1, x2, y2);
            
        }//end for loop
        
    }//end paintComponent()
        
}//end MyPanel.java