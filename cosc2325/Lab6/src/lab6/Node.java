package lab6;

public class Node<T>
{

    public T data; 
    public Node<T> link;

    //default constructor 
    public Node()
    {
        this.data = null;
        this.link = null;
    }//end of constructor

    public Node(T theData)
    {
        this.data = theData;
        this.link = null;
    }

    public Node(Node<T> theLink)
    {
        this.data = null;
        this.link = theLink;
    }

    public Node(T theData, Node<T> theLink)
    {
        this.data = theData;
        this.link = theLink;
    }

}//End of Node<T> public class. 