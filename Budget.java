/*Ethan Calleja
May 19 2020
This is the budget class, the superclass for the savings, income, and debt class.
This class contains fundamental methods and variables that are inherited by
the listed subclasses like name, withrawal() , toString(), etc. */
import hsa.Console;
public class Budget
{
    //Declare Instance Variables
    protected String name;
    protected double balance;
    protected Console c;


    //Constructor
    public Budget (String n, Console c)
    {
	name = n;                             //Name of User
	balance = 0;                          //Initial Balance of User
	this.c = c;
    }


    //Methods

    //Getter Methods
    public String getName ()
    {
	return name;
    }


    public double getBalance ()
    {
	return balance;
    }


    //Setter Methods
    public void setName (String n)
    {
	name = n;
    }


    public void setBalance (double b)
    {
	balance = b;
    }


    //Other Methods
    public void withdrawal (double x)                    //This method evaluates if the user has enough funds to withdrawal and subtracts the funds if they do
    {
	if (balance - x >= 0)
	{
	    balance = balance - x;
	    c.println ("Your current balance is $" + balance + ".");
	}
	else
	    c.println ("Insufficient Funds.\nPlease enter a command.");
    }


    public void deposit (double x)                       //This method adds moeny to the balance
    {
	balance = balance + x;
	c.println ("Your current balance is $" + balance + ".");
    }


    //toString Method
    public String toString ()
    {
	return name + ", your balance is $" + balance + ".";
    }
} //The "Budget" Class
