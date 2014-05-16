package lab8;
/* *
 * [InfixToPostfixConversion.java]
 * Author: Kristin Hamilton
 * Desc: InfixToPostfixConversion interface implemented by Lab8Frame class
 * Date created:  01-Apr-2014 for Lab8
 * Date last modified: 04-Apr-2014  
 */
public interface InfixToPostfixConversion
{
    public String getInfixFromUser();
    public String convertToPostFix(String infixString);
    public void displayResults(String infixString, String postfixString);
    
}//end interface InfixToPostfixConversion