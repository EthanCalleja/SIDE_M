/*Ethan Calleja
May 21 2020
This is the expenses class, desgined to help one keep track of all expenses.
This class contains to arrays with 100 cells each, one array is for the name
of a purchase and the other is for the value of the purchase. Each instance
of this class is considered a "catgeory", and the name is stored in the
category String type variable. This class will be used in a linked list.*/
import hsa.Console;
public class Expenses
{
    //Instance Variables
    private Console c;
    private String category;
    private String[] pName = new String [100];
    private double[] purchase = new double [100];
    private int i = 0;
    private Expenses nextExpenses;                              //Counter

    //Constructor for initial purchase to enter
    public Expenses (String k, String name, double p, Console c)
    {
	category = k;
	this.c = c;
	pName [0] = name;
	purchase [0] = p;
	i++;
	nextExpenses = null;
    }


    //Constructor for no initial purchase to enter
    public Expenses (String c)
    {
	category = c;
	nextExpenses = null;
    }



    //Methods

    //Getter Methods

    public String getCategory ()
    {
	return category;
    }


    //if name is known
    public double getPurchase (String name)
    {
	for (int a = 0 ; a < pName.length ; a++)
	{
	    if (name.compareTo (pName [a]) == 0)
		return purchase [a];
	}

	return -1;                                                 //Return -1 If Purchase Not found
    }


    //if position is known
    public double getPurchase (int position)
    {
	return purchase [(position)];
    }


    //position must be know
    public String getPurchaseName (int position)
    {
	return pName [(position)];
    }


    public double getTotal ()
    {
	double sum = 0;
	for (int a = 0 ; a < pName.length ; a++)
	    sum += purchase [a];
	return sum;
    }

    
    public double getI()
    {
    return i;
    }


    //Setter Methods

    public void setCategory (String c)
    {
	category = c;
    }


    //if name is known
    public void setPurchase (String name, double p)
    {
	for (int a = 0 ; a < pName.length ; a++)
	{
	    if (name.compareTo (pName [a]) == 0)
		purchase [a] = p;
	}
    }


    //if position is known (all positions start at 1)
    public void setPurchase (int position, double p)
    {
	purchase [position] = p;
    }


    //position must be know (all positions start at 1)
    public void setPurchaseName (int position, String name)
    {
	pName [position] = name;
    }


    //delete purchase method
    public void deletePurchase (String name)
    {
	int b = -1;

	for (int a = 0 ; a < (i + 1) ; a++)
	{
	    if (name.compareTo (pName [a]) == 0)
	    {
		b = a;
		break;
	    }
	}

	if (b == -1)
	    c.println ("Purchase not found.");

	else
	{
	    for (int a = b ; a < i ; a++)
	    {
		purchase [a] = purchase [a + 1];
		pName [a] = pName [a + 1];
	    }
	    i--;
	}
    }

    public void setI(int a)
    {
    i =a;
    }

    //Print List Method
    public void printList ()
    {
	for (int a = 0 ; a < i ; a++)
	    c.println (pName [a] + " $" + purchase [a]);
    }


    //Linked List Mehods

    public Expenses getNext ()
    {
	return nextExpenses;
    }


    public void setNext (Expenses nextExpenses)
    {
	this.nextExpenses = nextExpenses;
    }


    //toString Method
    public String toString ()
    {
	return "This is the " + category + " category of expenses.";
    }
} //The "Expenses" Class


