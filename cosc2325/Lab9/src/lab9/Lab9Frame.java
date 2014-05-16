package lab9;
/* *
 * [Lab9Frame.java]
 * Author: Kristin Hamilton
 * Desc: GUI for Lab9
 * Date created:  08-Apr-2014 for Lab9
 * Date last modified: 22-Apr-2014  
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("serial")
public class Lab9Frame extends JFrame
{
    int[] myList;
    MyPanel sortPanel;
    Thread sortThread;
    boolean sortThreadIsActive;
    boolean stopFlag;
    boolean pauseFlag;
    int msSleepDuration;
    
    public static final int FRAMEHEIGHT = 700;
    public static final int FRAMEWIDTH = 950;
    public static SwingConstants LEADING;
    
    ButtonGroup radioButtonGroup1;
    JPanel radioButtonPanel1;
    JRadioButton insertionSortRadioButton;
    JRadioButton quickSortRadioButton;
    JRadioButton mergeSortRadioButton;
    
    JPanel buttonPanel1;
    JButton startButton;
    JButton resetButton;
    JButton stopButton;
    JButton pauseResumeButton;
    
    ButtonGroup radioButtonGroup2;
    JPanel radioButtonPanel2;
    JRadioButton lowSpeedRadioButton;
    JRadioButton normalSpeedRadioButton;
    JRadioButton highSpeedRadioButton;
    
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
        Lab9Frame newFrame = new Lab9Frame();
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        newFrame.setVisible(true);
        
    }//end buildAndDisplayGUI()
    
    /* *
     * Pre:  (nothing)
     *       default constructor for Lab9Frame
     * Post: (nothing)
     *       results in new instance of Lab9Frame, and initialization of its attributes;
     *       method contains actionListeners for the following four buttons:
     *         startButton, resetButton, stopButton, and pauseResumeButton.
     *       see actionListeners for description of a given button's function once selected
     */
    public Lab9Frame()
    {
        /* create int[]; create new instance of MyPanel sortPanel with myList attribute */
        myList = buildList();
        sortPanel = new MyPanel(myList);
        
        /* thread, flags, sleep duration */
        sortThread = new Thread(new Runnable(){
            public void run()
            {
                return;
            }
        });
        sortThreadIsActive = (sortThread.isAlive()) && (!(sortThread.isInterrupted()));
        stopFlag = true;
        pauseFlag = false;
        msSleepDuration = 0;
         
        /* SORT BUTTONS: radioButtonPanel 1 of 2 */
        insertionSortRadioButton = new JRadioButton("insertion sort", true);  /* default sort */
        quickSortRadioButton = new JRadioButton("quick sort", false);
        mergeSortRadioButton = new JRadioButton("merge sort", false);
        radioButtonGroup1 = new ButtonGroup();
        radioButtonGroup1.add(insertionSortRadioButton);
        radioButtonGroup1.add(quickSortRadioButton);
        radioButtonGroup1.add(mergeSortRadioButton);
        radioButtonPanel1 = new JPanel();
        radioButtonPanel1.add(insertionSortRadioButton);
        radioButtonPanel1.add(quickSortRadioButton);
        radioButtonPanel1.add(mergeSortRadioButton);
        
        /* CONTROL BUTTONS */
        startButton = new JButton("Start");
        resetButton = new JButton("Reset");
        stopButton = new JButton("Stop");
        pauseResumeButton = new JButton("Pause");
        buttonPanel1 = new JPanel();
        buttonPanel1.add(startButton);
        buttonPanel1.add(resetButton);
        buttonPanel1.add(stopButton);
        buttonPanel1.add(pauseResumeButton);
        
        /* SPEED BUTTONS: radioButtonPanel 2 of 2 */
        lowSpeedRadioButton = new JRadioButton("slow", false);
        normalSpeedRadioButton = new JRadioButton("normal", true);  /* default speed */
        highSpeedRadioButton = new JRadioButton("fast", false);
        radioButtonGroup2 = new ButtonGroup();
        radioButtonGroup2.add(lowSpeedRadioButton);
        radioButtonGroup2.add(normalSpeedRadioButton);
        radioButtonGroup2.add(highSpeedRadioButton);
        radioButtonPanel2 = new JPanel();
        radioButtonPanel2.add(lowSpeedRadioButton);
        radioButtonPanel2.add(normalSpeedRadioButton);
        radioButtonPanel2.add(highSpeedRadioButton);
        
        /* add panels to frame */
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(radioButtonPanel1);
        this.getContentPane().add(buttonPanel1);
        this.getContentPane().add(radioButtonPanel2);
        this.getContentPane().add(sortPanel);
        
        
        /* actionListener: startButton */
        startButton.addActionListener(new ActionListener(){
            /* checks whether sortThread is currently alive/running, or if has been set
             *   to false. if so, stopFlag is set to true.
             * calls removeAll() on sortPanel, shuffles myList, and repaints the
             *   randomized array onto sortPanel.
             * checks which sortButton has been selected; creates a new instance of
             *   Runnable Thread sortThread, calls the appropriate sort method within
             *   run(), then starts sortThread, afterward setting both stopFlag and
             *   pauseFlag to false */
            public void actionPerformed(ActionEvent e)
            {
                if(sortThreadIsActive || stopFlag == false)
                {
                    stopFlag = true;
                }
                
                sortPanel.removeAll();
                shuffleList(myList);
                repaint();                               
                
                /* SORT radio buttons */
                if(insertionSortRadioButton.isSelected())
                {
                    sortThread = new Thread(new Runnable(){
                        public void run()
                        {
                            insertionSort(myList);
                        }
                    });
                    
                    sortThread.start();
                    stopFlag = false;
                    pauseFlag = false;
                }
                
                else if(quickSortRadioButton.isSelected())
                {
                    sortThread = new Thread(new Runnable(){
                        public void run()
                        {
                            quickSort(myList, 0, myList.length - 1);
                        }
                    });
                    
                    sortThread.start();
                    stopFlag = false;
                    pauseFlag = false;
                }
                
                else  //if(mergeSortRadioButton.isSelected())
                {
                    sortThread = new Thread(new Runnable(){
                        public void run()
                        {
                            mergeSort(myList, 0, myList.length - 1);
                        }
                    });
                    
                    sortThread.start();
                    stopFlag = false;
                    pauseFlag = false;
                }
                                
            }//end actionPerformed()
            
        });//end startButton.addActionListener()
    
        /* actionListener: resetButton */
        resetButton.addActionListener(new ActionListener(){
            /* if resetButton has been selected, stopFlag is set to true, removeAll() is
             * called on sortPanel, and myList is randomized and then repainted onto
             * sortPanel */
            public void actionPerformed(ActionEvent e)
            {
                stopFlag = true;
                sortPanel.removeAll();
                shuffleList(myList);
                repaint();
                return;
            }
            
        });//end ResetButton.addActionListener()
    
        /* action listener: stopButton */
        stopButton.addActionListener(new ActionListener(){
            /* if stopButton has been selected, stopFlag is set to true */
            public void actionPerformed(ActionEvent e)
            {
                stopFlag = true;
                return;
            }
            
        });//end stopButton.addActionListener()
        
        /* action listener: stopButton */
        pauseResumeButton.addActionListener(new ActionListener(){
            /* if button has been selected and the button text says 'Pause,' pauseFlag
             * is set to true, and the button text is set to 'Resume.'
             * otherwise, if the button has been selected and the button text says 'Resume,'
             * pauseFlag is set to false, and the button text is set back to 'Pause.' */
            public void actionPerformed(ActionEvent e)
            {
                if(pauseResumeButton.getText() == "Pause")
                {
                    pauseResumeButton.setText("Resume");
                    pauseFlag = true;                    
                }
                
                else if(pauseResumeButton.getText() == "Resume")
                {
                    pauseResumeButton.setText("Pause");
                    pauseFlag = false;
                }

                return;
            }
            
        });//end pauseResumeButton.addActionListener()
        
    }//end default constructor
    
    /* *
     * Pre:  (nothing)
     *       called once, in default constructor for Lab9Frame.
     * Post: (int[])
     *       builds array of 400 ints, each element of which is a randomly-generated int
     *       between 1 and 600, inclusive. returns myList, the int array.
     */
    public int[] buildList()
    {
        int maxInts = 400;
        int[] myList = new int[maxInts];
        Random randmInt = new Random();        
        
        for(int i = 0; i < maxInts; i++)
        {
            myList[i] = (int) randmInt.nextInt(599) + 1;  /* 400 ints, range = 1 to 600 */
        }
        
        return myList;
        
    }//end buildList()
    
    /* *
     * Pre:  (int[])
     *       called two times: once, inside the actionListener for startButton, and the
     *       second time, inside the actionListener for ResetButton.
     *       expects to receive int array with length of 400.
     * Post: (nothing)
     *       myList is "shuffled" by the following process:
     *       two different integers between 0 and 399 are randomly-generated, providing
     *       the value for two separate indices. the value of myList at the first random
     *       index is swapped with the value of myList at the second random index and
     *       vice-versa. this swap is repeated 999 times.
     *       returns nothing.
     */
    public void shuffleList(int[] myList)
    {
        Random rand1 = new Random();
        Random rand2 = new Random();
        int randomIndex1 = 0;
        int randomIndex2 = 0;
        int temp = 0;
        
        for(int i = 0; i < 999; i++)
        {
            randomIndex1 = rand1.nextInt(400);  /* randomIndex range: 0 to 399 */
            randomIndex2 = rand2.nextInt(400);

            temp = myList[randomIndex1];
            myList[randomIndex1] = myList[randomIndex2];
            myList[randomIndex2] = temp;
            
        }//end for loop
        
    }//end shuffleList()
    
    /* *
     * Pre:  (int[])
     *       called once, inside run() method inside of startButton actionListener.
     *       expects to receive array of ints.
     * Post: (nothing)
     *       myList is sorted by way of insertionSort.
     *       repaint() and sleep() are called inside of while loop following swap procedure
     */
    public void insertionSort(int[] myList)
    {
        for(int i = 1; i < myList.length; i++)
        {
            /* check if stopButton has been selected */
            if(stopFlag == true)
            {
                return;
            }
            
            int k = i;
            
            while(k > 0)
            {
                if(myList[k] < myList[k - 1])
                {
                    int temp = myList[k];
                    myList[k] = myList[k - 1];
                    myList[k - 1] = temp;
                    k--;
                    
                    try
                    {
                        sortThread.sleep(getMsSleepDuration());
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println("Error: sleep interrupted" + e);
                    }
                    
                    repaint();
                }
                
                else
                {
                    k = 0;
                }
                
            }//end while loop

        }//end for loop
        
    }//end insertionSort()
    
    /* *
     * Pre:  (int[], int, int)
     *       called once, inside of startButton actionListener.
     *       expects to receive the following:
     *         an array of ints, int lowest index in current half of array, and int
     *         highest index in current half of array.
     * Post: (nothing)
     *       performs recursive portion of quickSort method. method result is that myList
     *       is sorted by way of the quick sort. returns nothing.
     */
    public void quickSort(int[] myList, int lowIndex, int highIndex)
    {
        /* check if stopButton has been selected */
        if(stopFlag == true)
        {
            return;
        }
        
        int pivotLocation = 0;
        
        /* as long as lowIndex is lower than highIndex, keep partitioning the current
         * portion of array, determining new pivotLocation each time. then call quickSort
         * on lower half of newly-partitioned portion of array, and finally call quickSort
         * again, this time on the higher half of newly-partitioned portion of array */
        if(lowIndex < highIndex)
        {
            pivotLocation = partition(myList, lowIndex, highIndex);
            quickSort(myList, lowIndex, pivotLocation - 1);
            quickSort(myList, pivotLocation + 1, highIndex);
        }
        
    }//end quickSort()
    
    /* *
     * Pre:  (int[], int, int)
     *       called only by quickSort() if low index is still lower than high index for
     *       current partition/portion of array.
     *       expects to receive the following:
     *         an array of ints, int low which is an index, and int high, which is also
     *         an index.
     * Post: (int)
     *       performs non-recursive portion of quick sort method.
     *       first, checks whether stopFlag has been set; if so, returns 0 to quickSort()
     *       caller method. otherwise, goes through array values, and moves all elements
     *       smaller than pivotValue towards front of array, and moves all element larger
     *       than pivotValue towards back of array.
     *       calls sleep() and repaint() for sortThread and sortPanel, respectively,
     *       inside of both instances of if blocks.
     *       returns int pivotValue which is the value of the element at array[high] once
     *       high and low reach each other; the values of both high and low are modified
     *       within this method: high and low are moved closer together as method progresses
     */
    public int partition(int[] myList, int low, int high)
    {        
        int pivotValue = myList[low];
        
        /* check if stopButton has been selected. if so, return int from method */
        if(stopFlag == true)
        {
            return 0;  /* the 0 is arbitrary. decide on a more meaningful return value */
        }
        
        while(low < high)
        {
            while(low < high && pivotValue < myList[high])
            {
                high--;
            }
            
            if(low != high)
            {
                myList[low] = myList[high];
                low++;
                repaint();
                
                try
                {
                    sortThread.sleep(getMsSleepDuration());
                }
                catch(InterruptedException e)
                {
                    System.out.println("Error: sleep interrupted" + e);
                }
                                
            }//end while(low < high && pivotValue < myList[high])
            
            while(low < high && pivotValue > myList[low])
            {
                low++;
            }
            
            if(low != high)
            {
                myList[high] = myList[low];
                high--;
                repaint();
                
                try
                {
                    sortThread.sleep(getMsSleepDuration());
                }
                catch(InterruptedException e)
                {
                    System.out.println("Error: sleep interrupted" + e);
                }
                                
            }//end while(low < high && pivotValue > myList[low])
            
        }//end while(low < high)
        
        myList[high] = pivotValue;
        return high;
        
    }//end partition()
    
    /* *
     * Pre:  (int[], int, int)
     *       called once, inside of startButton actionListener, if mergeSortRadioButton
     *       is selected.
     *       expects to receive the following:
     *         an array of ints, int lowIndex which is the lowest index of the current
     *         portion of array, and int highIndex which is the highest index of current
     *         portion of array.
     * Post: (nothing)
     *       performs recursive portion of mergeSort. myList is sorted via merge sort
     *       technique. first checks whether stopFlag has been set; if so, exits method.
     *       otherwise, if lowIndex is lower than highIndex, calculates int midIndex which
     *       is the average of lowIndex and highIndex, then calls itself (mergeSort())
     *       twice: the first time, calls itself on the lower half of current portion of
     *       array, and the second time, calls itself on the upper half of current portion
     *       of array. then calls merge() to copy the elements of each half into tempArray.
     *       returns nothing.
     */
    public void mergeSort(int[] myList, int lowIndex, int highIndex)
    {
        /* check if stopButton has been selected */
        if(stopFlag == true)
        {
            return;
        }
        
        if(lowIndex < highIndex)
        {
            int midIndex = (lowIndex + highIndex) / 2;
            
            mergeSort(myList, lowIndex, midIndex);
            mergeSort(myList, midIndex + 1, highIndex);
            merge(myList, lowIndex, midIndex, midIndex + 1, highIndex);      
        }
        
    }//end mergeSort()
    
    /* *
     * Pre:  (int[], int, int, int, int)
     *       called only by mergeSort(). called only if lowest index for current portion
     *       of array is smaller than the highest index for the same array portion.
     *       expects to receive the following:
     *         an array of ints, int low1 and int mid, which are the lowest and highest
     *         indices, respectively, for the lower half of current portion of array, and
     *         int low2 and int high, which are analogous to low1 and mid, except the
     *         former pair are the corresponding indices for the upper half of current
     *         portion of array. low2 = mid + 1.
     * Post: (nothing)
     *       performs non-recursive portion of merge sort technique on myList.
     *       first checks whether stopFlag has been set. if so, exits method; otherwise,
     *       creates a tempArray to hold the sorted and merged portions of the array, and
     *       goes through the upper and lower halves of the current portion of array,
     *       comparing one element from each half with one element from the other half,
     *       copying the value of the smaller int over to tempArray. at the very end of
     *       method, copies merge-sorted tempArray over to myList. please see method body
     *       for additional comments.
     *       returns nothing.
     */
    public void merge(int[] myList, int low1, int mid, int low2, int high)
    {
        /* check if stopButton has been selected */
        if(stopFlag == true)
        {
            return;
        }
            
        /* create a temp array to hold the sorted ints, initialize iterators etc. */
        int[] tempArray = new int[(mid - low1) + (high - low2) + 2];
        int s1 = low1;  /* starting point for lower half */
        int s2 = low2;  /* starting point for upper half */
        int d = 0;      /* index for tempArray */
        int k = 0;
        
        while(s1 <= mid && s2 <= high)  /* while elements remaining in BOTH halves */
        {
            /* compare one element from lower half with one element from upper half;
             * put the smaller of the two ints into tempArray */
            if(myList[s1] < myList[s2])
            {
                tempArray[d++] = myList[s1++];   
            }
            
            else
            {
                tempArray[d++] = myList[s2++];
            }
            
        }//end while(s1 <= mid && s2 <= high)
        
        /* at this point, either s1 has reached mid or s2 has reached high (ie one of the
         * two halves is exhausted), so copy whatever is left of the array half (that
         * hasn't been exhausted) into tempArray */
        
        /* copy over remaining elements in lower half of array if s1 isnt already at mid */
        while(s1 <= mid)
        {
            tempArray[d++] = myList[s1++];
        }
        
        /* copy over remaining elements in upper half of array if s2 isnt already at high */
        while(s2 <= high)
        {
            tempArray[d++] = myList[s2++];
        }
        
        /* copy merge-sorted tempArray over to myList */
        for(k = 0, s1 = low1; s1 <= high; s1++, k++)
        {
            myList[s1] = tempArray[k];
            repaint();
            
            try
            {
                sortThread.sleep(getMsSleepDuration());
            }
            catch(InterruptedException e)
            {
                System.out.println("Error: sleep interrupted" + e);
            }
            
        }//end for loop
                        
    }//end merge()
    
    /* *
     * Pre:  (nothing)
     *       called by insertionSort() inside while loop that is inside of for loop.
     *       called by partition() (non-recursive portion of quickSort) inside of main
     *         while loop in both if(low != high) blocks.
     *       called by merge() (non-recursive portion of mergeSort) inside of for loop
     *         at end of method where the tempArray is being copied over to myList.
     * Post: (int)
     *       if the pauseFlag is set, returns 5000 as the ms sleep duration.
     *       otherwise, checks which speed radio button is selected, and sets
     *       msSleepDuration to its corresponding value. returns int msSleepDuration.
     */
    public int getMsSleepDuration()
    {
        /* check if pauseResumeButton has been selected */
        if(pauseFlag == true)
        {
            return 5000;
        }
        
        /* SPEED radio buttons */
        if(lowSpeedRadioButton.isSelected())
        {
            msSleepDuration = 250;
        }
        
        else if(highSpeedRadioButton.isSelected())
        {
            msSleepDuration = 10;
        }
                        
        else  //if(normalSpeedRadioButton.isSelected())
        {
            msSleepDuration = 50;
        }
        
        return msSleepDuration;
        
    }//end getMsSleepDuration()
    
}//end class Lab9Frame