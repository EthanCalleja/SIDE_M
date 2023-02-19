/*Ethan Calleja
May 21 2020
This is the savings class, each instance will represent a savings goal.
Some methods and variables of this class are inherited by the budget class.
This class will be used in a linked list.*/
import hsa.Console;
public class Savings extends Budget
{
    //Declare Instance Variables
    protected double goal;
    protected double months;
    protected double remaining;
    protected String reason;
    private Savings nextSavings;

    //Constructor
    public Savings (String n, double g, double m, String r, Console c)
    {
	super (n, c);                          //Name of savings goal and console class
	goal = g;
	remaining = goal;                      //Set remaining funds until goal equal to the goal
	months = m;
	reason = r;                            //Reason for starting savings goal
	nextSavings = null;
    }


    //Methods that pertain specifically to the Income class

    //Getter Methods
    public double getGoal ()
    {
	return goal;
    }


    public double getMonths ()
    {
	return months;
    }


    public double getRemaining ()
    {
	return remaining;
    }


    public String getReason ()
    {
	return reason;
    }


    public double getMContribution ()
    {
	return (remaining / months);                                //Returns Monthly Contribution until Goal
    }


    public double getPercSaved ()
    {
	return Math.abs (((remaining - goal) / goal) * 100);
    }


    public double getPercLeft ()
    {
	return ((remaining) / goal) * 100;
    }



    //Setter Methods
    public void setGoal (double g)
    {
	goal = g;
    }


    public void setMonths (double m)
    {
	months = m;
    }


    public void setRemaining (double r)
    {
	remaining = r;
    }


    public void setReason (String r)
    {
	reason = r;
    }


    //Other Methods

    //Overwrite the Withdrawal and Deposit Methods

    public void withdrawal (double x)                    //This method evaluates if the user has enough funds to withdrawal and subtracts the funds if they do
    {
	if (super.getBalance () - x >= 0)
	{
	    super.setBalance (super.getBalance () - x);
	    remaining = remaining + x;
	    c.println ("Your current balance is $" + balance + ".");
	    c.println ("The remaining funds to complete the goal is $" + remaining + ".");
	}

	else
	    c.println ("Insufficient Funds.\nPlease enter a command.");
    }


    public void deposit (double x)                       //This method adds moeny to the balance
    {
	super.setBalance (super.getBalance () + x);
	remaining = remaining - x;
	c.println("You have $"+remaining+" left to reach your savings goal!");
    }


    //Linked List Mehods

    public Savings getNext ()
    {
	return nextSavings;
    }


    public void setNext (Savings nextSavings)
    {
	this.nextSavings = nextSavings;
    }





    //toString Method
    public String toString ()
    {
	return "Your savings goal " + name + " is for $" + goal + " in " + months + " months. You have $" + remaining + " remaining. \nThe reason you started this goal: " + reason;
    }
} //The "Savings" Class
