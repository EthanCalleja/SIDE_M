/*Ethan Calleja
May 25, 2020
The Income Link Class (ILink) is used to manage the Income linked list.
All of the required methods to manage the Income linked list are found in
this class.*/
import hsa.Console;
public class ILink
{
    protected Income top;
    private Console c;

    //Start a Null List

    public ILink (Console c)
    {
	top = null;
	this.c = c;
    }


    public Income getTop ()
    {
	return top;
    }


    //Add Debt Object to End of List

    public void addNode (Income i)
    {
	if (top == null)
	    top = i;

	else
	{
	    Income current = top;

	    while (current.getNext () != null)
		current = current.getNext ();

	    current.setNext (i);
	}
    }


    //Display Linked List

    public void showList ()
    {
	if (top == null)
	{
	    c.println ("Empty List.");
	}
	else
	{
	    c.println ("The current list:");

	    Income current;
	    current = top;

	    while (current.getNext () != null)
	    {
		c.println (current.toString ());
		current = current.getNext ();
	    }
	    c.println (current.toString ());
	}
    }


    //Get Node Method

    public Income getNode (String search)
    {
	{
	    Income current = top;

	    while (current != null)
	    {
		//If the searched name is found
		if (current.getName ().compareTo (search) == 0)
		{
		    return current;
		}

		//If current does not contain searched record
		else
		    current = current.getNext ();
	    }

	    System.out.println ("Record not found.");
	    return current.getNext ();
	}
    }


    //Delete Node Method

    public void deleteNode (String search)
    {
	Income current = top;
	int p = 0;

	//If first node must be deleted
	if ((current.getName ().compareTo (search) == 0))
	{
	    top = (current.getNext ());
	    current = top;
	    p++;
	}

	while (current.getNext () != null)
	{

	    //If last node must be deleted
	    if (((current.getNext ().getName ()).compareTo (search) == 0) && current.getNext ().getNext () == null)
	    {
		current.setNext (null);
		p++;
	    }

	    //If any middle nodes must be deleted
	    else if (((current.getNext ().getName ()).compareTo (search) == 0))
	    {
		current.setNext (current.getNext ().getNext ());
		current = current.getNext ();
		p++;
	    }

	    //If current is not supposed to be deleted
	    else
		current = current.getNext ();
	}


	//If record is not found
	if (current.getNext () == null && p == 0)
	    c.println ("Record not found.");
    }
} //The "ILink" Class
