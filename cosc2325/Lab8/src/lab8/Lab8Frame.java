package lab8;
/* *
 * [Lab8Frame.java]
 * Author: Kristin Hamilton
 * Desc: GUI for Lab8
 * Date created:  01-Apr-2014 for Lab8
 * Date last modified: 04-Apr-2014  
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.Character;

@SuppressWarnings("serial")
public class Lab8Frame extends JFrame implements InfixToPostfixConversion
{
    private boolean errorFlag;
    public static final int FRAMEHEIGHT = 325;
    public static final int FRAMEWIDTH = 675;
    //JPanel textPanel1;
    JLabel textFieldLabel;
    JTextField textField;
    JPanel buttonPanel1;
    JButton convertButton;
    JButton clearButton;
    JTextArea textArea;
    StackList<Character> stackA;
    
    /* *
     * main method, calls buildAndDisplayGUI() to construct the GUI frame.
     */
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                buildAndDisplayGUI();
            }
            
        } );
        
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
        Lab8Frame newFrame = new Lab8Frame();
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        newFrame.setVisible(true);
        
    }//end buildAndDisplayGUI()
    
    /* *
     * Pre:  (nothing)
     *       default constructor for Lab8Frame
     * Post: (nothing)
     *       results in new instance of Lab8Frame; method contains actionListeners for
     *       two buttons: convertButton and clearButton.
     *       convertButton: when clicked, convertButton calls getInfixFromUser(), then
     *         does a length check to make sure infixString is not null (if it is null,
     *         calls displayNullInfixMsg() to show error msg).
     *         calls convertToPostFix() to get value of postfixString. checks
     *         this.errorFlag, a private boolean attribute for the Lab8Frame class.
     *         if errorFlag is "on" (== true), calls displayErrorMsg(); otherwise calls
     *         displayResults().
     *       clearButton: resets text of both textField and textArea to empty Strings.
     */
    public Lab8Frame()
    {
        this.errorFlag = false;
        stackA = new StackAImpl<Character>();
        
        textFieldLabel = new JLabel("Please enter an infix expression in the space below:");
        textField = new JTextField(50);
        textArea = new JTextArea(10,50);
        textArea.setEditable(false);
        
        convertButton = new JButton("Convert to Postfix");
        clearButton = new JButton("Clear Text");
               
        //textPanel1 = new JPanel();
        buttonPanel1 = new JPanel();
        
        this.getContentPane().setLayout(new FlowLayout());
        //this.getContentPane().add(textPanel1);
        this.getContentPane().add(textFieldLabel);
        this.getContentPane().add(textField);
        this.getContentPane().add(buttonPanel1);
        this.getContentPane().add(textArea);
        
        //textPanel1.add(textFieldLabel);
        //textPanel1.add(textField);
        buttonPanel1.add(convertButton);
        buttonPanel1.add(clearButton);       
        
        /* actionListener: convertButton */
        convertButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String infixString = getInfixFromUser();
                
                /* infixString must contain at least one character, since in
                 * convertToPostFix(), it is assumed that length > 0 when beginning loop
                 * through infixString */
                if(infixString.length() > 0)
                {                
                    String postfixString = convertToPostFix(infixString);
                    
                    if(errorFlag == true)
                    {
                        displayErrorMsg(infixString);
                    }
                    
                    else
                    {
                        displayResults(infixString, postfixString);
                    }
                    
                }//end if(infixString.length() > 0)
                
                else  //if(infixString.length() <= 0)
                {
                    displayNullInfixMsg();
                }
                                
            }//end actionPerformed()
            
        } );//end addActionListener()
    
        /* actionListener: clearButton */
        clearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                textField.setText("");
                textArea.setText("");
            }
            
        } );//end addActionListener()
    
    }//end default constructor
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       gets user-provided text from textField, assigning the text to String
     *       variable infixString.
     *       returns infixString. 
     */
    public String getInfixFromUser()
    {
        String infixString = textField.getText();        
        return infixString;
        
    }//end getInfixFromUser()
    
    /* *
     * Pre:  (String)
     *       expects to receive String infixString; length of infixString must be > 0
     *       (length is expected to be checked in caller method)
     * Post: (String)
     *       converts infix expression infixString to postfix expression postfixString.
     *       please see method below for additional comments.
     *       returns String postfixString.
     */
    public String convertToPostFix(String infixString)
    {
        Character ch = ' ';
        int precedence = 0;
        String postfixString = "";
        
        /* iterate through infixString */
        for(int i = 0; i < infixString.length(); i++)
        {
            /* cast char returned by charAt() method into Character since stackA is an
             * ArrayList implementation of StackList, and an ArrayList requires Character
             * wrapper class since it operates on objects, and not primitive data types */
            ch = (Character) infixString.charAt(i);
            
            /* if ch is an operand (is alphanumeric), append to postfixString */
            if(isOperand(ch))
            {
                postfixString += ch;
            }
            
            /* if ch is an operator (^, *, /, +, -, =), get the precedence value of ch;
             * if stackA isnt empty, peek at it and get the precedence of the operator
             * on the top of the stack. pop operators from stackA, appending each onto
             * postfixString, until either the stack is empty, or the precedence of ch
             * exceeds the precedence of the operator on top of the stack. then, as long
             * as stack isnt already full, push ch onto the stack. if the stack is full,
             * set errorFlag to true and break out of for loop.
             */
            else if(isOperator(ch))
            {
                precedence = getPrecedence(ch);
                
                if(!stackA.isEmpty() && precedence <= getPrecedence(stackA.peek()))
                {
                    while(!stackA.isEmpty() && precedence <= getPrecedence(stackA.peek()))
                    {
                        postfixString += stackA.pop();
                        
                    }//end while
                    
                    stackA.push(ch);
                }
                
                else
                {
                    if(!stackA.isFull())
                    {
                        stackA.push(ch);
                    }
                    
                    /* set errorFlag if stackA is full */
                    else
                    {
                        this.errorFlag = true;
                        break;
                    }
                    
                }//end else
                
            }//end else if(isOperator(ch))
            
            /* if ch is a left, or opening, parenthesis, push ch to stack */
            else if(ch == '(')
            {
                stackA.push(ch);
            }
            
            /* if ch is a right, or closing, parenthesis, pop stackA until reaching either
             * left, or opening, parenthesis, or until reaching bottom of the stack,
             * appending each operator to postfixString as it is popped from the stack.
             * if bottom of stack is reached, and no left paren has been found, set
             * errorFlag to true, and break out of for loop. if left paren is found, do
             * one additional pop to "throw away" the paren. do nothing with the right
             * paren: throw it away as well.
             */
            else if(ch == ')')
            {
                while(!stackA.isEmpty() && stackA.peek() != '(')
                {
                    postfixString += stackA.pop();
                    
                }//end while
                
                /* determine condition of exiting while loop */
                if(!stackA.isEmpty() && stackA.peek() == '(')
                {
                    stackA.pop();  //pop once more to get rid of left paren
                }
                
                /* set errorFlag if reached bottom of stack and no left paren found */
                else
                {
                    this.errorFlag = true;  
                    break;
                }
                
            }//end else if(ch == ')')
            
            /* if ch is a whitespace character, no action required */
            else if(Character.isWhitespace(ch))
            {
                /* do nothing, but dont want to trigger error msg */
            }
            
            /* set errorFlag to true if invalid character found */
            else  //if invalid character
            {
                this.errorFlag = true;
                break;
            }
            
        }//end for
        
        /* pop whatever is left on the stack (if anything) after infixString is exhausted,
         * and append each operator to postfixString as it is popped from the stack */
        while(!stackA.isEmpty())
        {
            postfixString += stackA.pop();
            
        }//end while
        
        return postfixString;
        
    }//end convertToPostFix()
   
    /* *
     * Pre:  (Character)
     *       expects to receive Character someCharacter
     * Post: (boolean)
     *       converts someCharacter to primitive type char ch. if ch is alphanumeric,
     *       returns true; otherwise, returns false.
     */
    public static boolean isOperand(Character someCharacter)
    {
        char ch = someCharacter.charValue();
        return Character.isLetterOrDigit(ch);
        
    }//end isOperand()
    
    /* *
     * Pre:  (Character)
     *       expects to receive Character someCharacter
     * Post: (boolean)
     *       converts someCharacter to primitive type char, ch; if ch is one of the
     *       following, returns true: ^, *, /, +, -, =; otherwise, returns false.
     */
    public static boolean isOperator(Character someCharacter)
    {
        char ch = someCharacter.charValue();
        
        switch(ch)
        {
            case '^':
            case '*':
            case '/':
            case '+':
            case '-':
            case '=':
                return true;
            default:
                return false;
        
        }//end switch(ch)
        
    }//end isOperator()
    
    /* *
     * Pre:  (Character)
     *       expects to receive Character someOperator; Character is expected to be one
     *       of the following: ^, *, /, +, -, or =.
     * Post: (int)
     *       converts someOperator to char ch; determines value of int precedence based on
     *       value of ch.
     *       returns precedence.
     */
    public static int getPrecedence(Character someOperator)
    {
        char ch = someOperator.charValue();
        int precedence = 0;
        
        switch(ch)
        {
            case '^':
                precedence = 5;
                break;
                
            case '*':
            case '/':
                precedence = 4;
                break;
                
            case '+':
            case '-':
                precedence = 3;
                break;
                
            case '=':
                precedence = 2;
                break;
        
        }//end switch(ch)
        
        return precedence;
        
    }//end getPrecedence()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       displays error message in the event that user clicks "convert to postfix"
     *       button prior to entering any expression in the text field.
     *       returns nothing.
     */
    public void displayNullInfixMsg()
    {
        textArea.setText("Error: unable to convert null String to postfix.");
        return;
        
    }//end displayNullInfixMsg()
    
    /* *
     * Pre:  (String)
     *       expects to receive non-null String infixString, the original user-provided
     *       String
     * Post: (nothing)
     *       displays error message to user informing them that they entered an invalid
     *       expression; error message provides their original String entry for their
     *       review.
     *       returns nothing.
     */
    public void displayErrorMsg(String infixString)
    {
        textArea.setText("Error: invalid infix expression.\n" +
            "You entered: " + infixString);
        return;
        
    }//end displayErrorMsg()
    
    /* *
     * Pre:  (String, String)
     *       expects to receive String infixString and String postfixString, both of
     *       which are expected to be not null
     * Post: (nothing)
     *       displays text to user revealing result of infix-to-postfix conversion:
     *       displays both the original user-provided infix expression, and the postfix
     *       expression result of the conversion.
     *       returns nothing.
     */
    public void displayResults(String infixString, String postfixString)
    {
        textArea.setText("Infix expression: " + infixString + "\n\n" +
                         "Postfix expression: " + postfixString);
        return;
        
    }//end displayResults()
    
}//end class Lab8GUI