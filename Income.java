/*Ethan Calleja
May 19 2020
This is the income class, each instance will represent an income stream.
Some methods and variables of this class are inherited by the budget class.
This class will be used in a linked list.*/
import hsa.Console;
public class Income extends Budget
{
    //Declare Instance Variables
    protected double income;                             //Income is Monthly Income
    private Income nextIncome;

    //Constructor
    public Income (String n,  double i, Console c)
    {
	super (n,c);                          //Name of source of income and console class
	income = i;
	nextIncome = null;
    }


    //Methods that pertain specifically to the Income class

    //Getter Method


    public double getIncome ()
    {
	return income;
    }



    //Setter Method

    public void setIncome (double i)
    {
	income = i;
    }


    //Overwrite the Withdrawal and Deposit Methods

    public void withdrawal (double x)                    //This method evaluates if the user has enough funds to withdrawal and subtracts the funds if they do
    {
	if (super.getBalance () - x >= 0)
	{
	    super.setBalance (super.getBalance () - x);
	     c.println ("Your current balance is $" + balance + ".");
	}

	else
	    c.println ("Insufficient Funds.\nPlease enter a command.");
    }


    public void deposit (double x)                       //This method adds moeny to the balance
    {
	super.setBalance (super.getBalance () + x);
    }


    //Linked List Mehods

    public Income getNext ()
    {
	return nextIncome;
    }


    public void setNext (Income nextIncome)
    {
	this.nextIncome = nextIncome;
    }



    //toString Method
    public String toString ()
    {
	return "Your income from " + name + " is $" + income + " every month.";
    }
} //The "Income" Class
