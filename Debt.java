/*Ethan Calleja
May 25 2020
This is the debt class, each instance will represent a debt-relief goal.
Some methods and variables of this class are inherited by the budget class.
This class will be used in a linked list.*/
import hsa.Console;
public class Debt extends Budget
{
    //Declare Instance Variables
    protected double initial;                           //Initial Debt
    protected double mPay;                              //Monthly Payment to Relieve Debt
    protected double mFee;                              //Monthly Fee of Debt
    protected double remaining;                         //Remaining Debt
    private Debt nextDebt;

    //Constructor
    public Debt (String n, double i, double mf, double mp, Console c)
    {
	super (n,c);                                                     //Name of source of debt and console class
	initial = i;            
	mPay = mp; 
	mFee = mf;
	remaining = initial;                                             //Set remaining debt to initial debt
	nextDebt = null;
    }


    //Methods that pertain specifically to the Income class

    //Getter Methods
    public double getInitial ()
    {
	return initial;
    }


    public double getMPay ()
    {
	return mPay;
    }


    public double getMFee ()
    {
	return mFee;
    }


    public double getRemaining ()
    {
	return remaining;
    }




    //Setter Methods
    public void setInitial (double i)
    {
	initial = i;
    }


    public void setMPay (double mp)
    {
	if (mPay > mFee)                                           //Explain w comment and make it so mpay must be greater than mFee
	    mPay = mp;

	else if (mPay == mFee && remaining != 0)
	    System.out.println ("You are paying exactly what you owe but you are still in debt");

	else
	    System.out.println ("You are not paying your entire monthly fee");
    }


    public void setFee (double mf)
    {
	mFee = mf;
    }


    public void setRemaining (double r)
    {
	remaining = r;
    }



    //Linked List Methods

    public Debt getNext ()
    {
	return nextDebt;
    }


    public void setNext (Debt nextDebt)
    {
	this.nextDebt = nextDebt;
    }


    //Other Methods

    //Overwrite the Withdrawal and Deposit Methods

      //Overwrite the Withdrawal and Deposit Methods

    public void withdrawal (double x)                    //This method evaluates if the user has enough funds to withdrawal and subtracts the funds if they do
    {
	if (super.getBalance() - x >= 0)
	{
	    super.setBalance(super.getBalance() - x);
	    remaining = remaining + x;
	    c.println ("Your current balance is $" + balance + ".");
	    c.println ("The remaing funds to relieve debt is $" + remaining + ".");
	}

	else
	    c.println ("Insufficient Funds.\nPlease enter a command.");
    }


    public void deposit (double x)                       //This method adds moeny to the balance
    {
	super.setBalance(super.getBalance() + x);
	remaining = remaining - x;
	c.println("You have $"+remaining+" of remaining debt.");
    }



    //toString Method
    public String toString ()
    {
	return "You initially had $"+initial+" of debt. You owe $"+mFee+"/month and intend to pay $"+mPay+"/month. You have $"+remaining+" of debt remaining from "+name+".";
    }
    
} //The "Debt" Class


