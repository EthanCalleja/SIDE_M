// The "SIDE_M" class.
import hsa.Console;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SIDE_M
{
    static Console c;           // The output console
    public static void main (String[] args) throws IOException
    {

	//Decloration
	c = new Console (35, 140);

	Budget budget = new Budget ("Budget", c);

	//Random Acsess Files
	RandomAccessFile userinfo = new RandomAccessFile ("userinfo.bin", "rw");
	RandomAccessFile sraf = new RandomAccessFile ("savings.bin", "rw");
	RandomAccessFile iraf = new RandomAccessFile ("income.bin", "rw");
	RandomAccessFile draf = new RandomAccessFile ("debt.bin", "rw");
	RandomAccessFile eraf = new RandomAccessFile ("expenses.bin", "rw");
	byte[] nameBytes = new byte [15];
	byte[] nameBytes2 = new byte [100];


	//Date Class to Keep Track of Time
	Date start = new Date ();
	int month;

	//Linked Lists
	SLink sList = new SLink (c);
	ILink iList = new ILink (c);
	DLink dList = new DLink (c);
	ELink eList = new ELink (c);

	//Initialize Counters Values (To be Incremented Upon First Instantiation of each Clas)
	int a, b, e, d;

	String uName;

	//Promt User for Personal Information to Customize Program (Only if they have not yet acsessed the program)
	if (userinfo.length () == 0)
	{
	    c.println ("Hello! Welcome to S.I.D.E. M., the one program you need to manage your finances! Let's start off with your name.");
	    uName = c.readLine ();

	    c.println ("Awesome! Please enter in the current dollar balance of your bank account.");
	    budget.setBalance (c.readDouble ());
	    month = start.getMonth ();

	    //Write to Binary File
	    nameBytes = new byte [15];
	    uName.getBytes (0, uName.length (), nameBytes, 0);
	    userinfo.write (nameBytes);
	    userinfo.writeDouble (budget.getBalance ());
	    userinfo.writeInt (month);

	    //Write Counters to Binary File
	    for (int p = 0 ; p < 4 ; p++)
		userinfo.writeInt (0);
	    userinfo.close ();
	}

	//Retrieve Value of Counters and Month from Binary File
	userinfo = new RandomAccessFile ("userinfo.bin", "rw");
	nameBytes = new byte [15];
	userinfo.read (nameBytes);
	uName = new String (nameBytes, 0);
	budget.setBalance (userinfo.readDouble ());
	month = userinfo.readInt ();
	a = userinfo.readInt ();                        //The Value of this counter is stored at the 27th position of the Random Acsess File (Savings Class)
	b = userinfo.readInt ();                        //The Value of this counter is stored at the 31st position of the Random Acsess File (Income Class)
	e = userinfo.readInt ();                       //The Value of this counter is stored at the 35th position of the Random Acsess File (Debt Class)
	d = userinfo.readInt ();                        //The Value of this counter is stored at the 39th position of the Random Acsess File (Expenses Class)

	if (userinfo.length () != 0)
	    c.println ("Hello " + uName + ", welcome back!.");


	//Update Month and Make Any Necessary Compensations

	if (start.getMonth () - month != 0)
	{
	    int difference = start.getMonth () - month;

	    if (month != 0 && start.getMonth () == 0)
	    {
		difference = start.getMonth () - month + 11;
	    }


	    if (iList.getTop () != null)
	    {
		Income current = iList.getTop ();
		for (int p = 0 ; p < b ; p++)
		{
		    budget.setBalance (budget.getBalance () + (current.getIncome ()) * (difference));
		    current = current.getNext ();
		}
	    }


	    if (dList.getTop () != null)
	    {
		Debt current = dList.getTop ();
		for (int i = 0 ; i < e ; i++)
		{
		    budget.setBalance (budget.getBalance () - (current.getMFee ()) * (difference));
		    //Add in total debt remaining
		    current = current.getNext ();
		}
	    }

	    //Add reset Monthly Savings remaining (may have to add in another savings variable for remaining Monthly)

	    //Update Month of the Year in Binary File
	    month = start.getMonth ();
	    userinfo.seek (23);
	    userinfo.writeInt (month);

	    //Write Balance to Binary File

	    userinfo.close ();
	}



	//Write In All Previously Entered Information

	//Savings
	if (a != 0)
	{
	    sraf = new RandomAccessFile ("savings.bin", "rw");
	    for (int p = 0 ; p < a ; p++)
	    {
		sraf.seek (p * 131);
		nameBytes = new byte [15];
		sraf.read (nameBytes);
		String n = new String (nameBytes, 0);
		double g = sraf.readDouble ();
		double m = sraf.readDouble ();
		nameBytes2 = new byte [100];
		sraf.read (nameBytes2);
		String r = new String (nameBytes2, 0);
		Savings sp = new Savings (n, g, m, r, c);
		sList.addNode (sp);
	    }
	    sraf.close ();
	}

	//Income
	if (b != 0)
	{
	    iraf = new RandomAccessFile ("income.bin", "rw");
	    for (int p = 0 ; p < b ; p++)
	    {
		iraf.seek (p * 23);
		nameBytes = new byte [15];
		iraf.read (nameBytes);
		String n = new String (nameBytes, 0);
		Income ip = new Income (n, iraf.readDouble (), c);
		iList.addNode (ip);
	    }
	    iraf.close ();
	}

	//Debt
	if (e != 0)
	{
	    draf = new RandomAccessFile ("debt.bin", "rw");
	    for (int p = 0 ; p < e ; p++)
	    {
		draf.seek (p * 39);
		nameBytes = new byte [15];
		draf.read (nameBytes);
		String n = new String (nameBytes, 0);
		Debt dp = new Debt (n, draf.readDouble (), draf.readDouble (), draf.readDouble (), c);
		dList.addNode (dp);
	    }
	    draf.close ();
	}

	//Expenses
	if (d != 0)
	{
	    eraf = new RandomAccessFile ("expenses.bin", "rw");
	    for (int p = 0 ; p < d ; p++)
	    {
		eraf.seek (p * 2400);
		nameBytes2 = new byte [100];
		eraf.read (nameBytes2);
		String n = new String (nameBytes2, 0);
		nameBytes = new byte [15];
		eraf.read (nameBytes);
		String pn = new String (nameBytes, 0);
		Expenses ep = new Expenses (n, pn, eraf.readDouble (), c);

		for (int t = 0 ; t < 100 ; t++)
		{
		    nameBytes = new byte [15];
		    eraf.read (nameBytes);
		    String pni = new String (nameBytes, 0);
		    ep.setPurchaseName (t, pni);
		    ep.setPurchase (t, eraf.readDouble ());
		}

		eList.addNode (ep);
	    }
	    eraf.close ();
	}


	//Delete any Savings Goals and Debt Streams that are no longer relevant              PROGRAM MUST BE RESET AFTER NODES ARE DELETED

	//Savings
	for (int i = 0 ; i < a ; i++)
	{
	    Savings current = sList.getTop ();

	    if (current.getRemaining () == 0)
	    {
		sList.deleteNode (current.getName ());            //Deletes Node from linked list


		//Compensate in Binary File
		for (int j = i ; j < a ; j++)
		{
		    sraf = new RandomAccessFile ("savings.bin", "rw");
		    nameBytes = new byte [15];
		    sraf.seek ((j + 1) * 131);                              //Copies Information from Next Part of File to Temporary Variables
		    sraf.read (nameBytes);
		    String n = new String (nameBytes, 0);
		    double g = sraf.readDouble ();
		    double m = sraf.readDouble ();
		    nameBytes2 = new byte [100];
		    sraf.read (nameBytes2);
		    String r = new String (nameBytes2, 0);

		    c.println ("The savings goal " + n + " was deleted.");

		    sraf.seek (j * 131);               //Replaces Part of File Storing Deleted Information with Data Stored in Temporary Variables
		    n.getBytes (0, n.length (), nameBytes, 0);
		    sraf.write (nameBytes);
		    sraf.writeDouble (g);
		    sraf.writeDouble (m);
		    n.getBytes (0, n.length (), nameBytes2, 0);
		    sraf.write (nameBytes2);
		    sraf.close ();

		    userinfo = new RandomAccessFile ("userinfo.bin", "rw");
		    a--;                                                   //Compensate for change in number of objects represented by the counters
		    userinfo.seek (27);
		    userinfo.writeInt (a);
		    userinfo.close ();
		}
	    }
	    current = current.getNext ();
	}

	//Debt
	for (int i = 0 ; i < e ; i++)
	{
	    Debt current = dList.getTop ();

	    if (current.getRemaining () == 0 || current.getMFee () == 0)
	    {
		dList.deleteNode (current.getName ());            //Deletes Node from linked list


		//Compensate in Binary File
		for (int j = i ; j < e ; j++)
		{
		    draf.seek ((j + 1) * 39);                              //Copies Information from Next Part of File to Temporary Variables
		    nameBytes = new byte [15];
		    draf.read (nameBytes);
		    String n = new String (nameBytes, 0);
		    double q = sraf.readDouble ();
		    double mF = sraf.readDouble ();
		    double mP = sraf.readDouble ();

		    c.println ("The debt source " + n + " was deleted.");

		    draf.seek (j * 39);                                   //Replaces Part of File Storing Deleted Information with Data Stored in Temporary Variables
		    n.getBytes (0, n.length (), nameBytes, 0);
		    draf.write (nameBytes);
		    draf.writeDouble (q);
		    draf.writeDouble (mF);
		    draf.writeDouble (mP);

		    e--;                                                   //Compensate for change in number of objects represented by the counters
		    userinfo.seek (35);
		    userinfo.writeInt (e);
		    userinfo.close ();
		}
	    }
	    current = current.getNext ();
	}


	//Initial Notification to User of Commands to Use

	c.println ("Listed below are a few commands to help you to personalize and use S.I.D.E. M.");
	c.println ("\n-Create a new Savings Goal by enetering \"s\"\n-List a new Income Stream by enetering \"i\" \n-List a new Source of Debt by enetering \"d\"\n-Create a new Category of Expenses by enetering \"e\"");
	c.println ("Type \"done\" to exit the program.");
	c.println ("Just type \"help\" for a list of different commands and their functions.");
	String command;

	do
	{
	    command = c.readLine ();

	    //Comands


	    //The "Help" Command (Lists all commands)
	    if (command.compareTo ("help") == 0)
	    {
		c.println ("\n-Acsess a list various commands and their functions by entering \"help\"");
		c.println ("-Exit the program by entering \"done\".");
		c.println ("-Create a new Savings Goal by enetering \"s\"\n-List a new Income Stream by enetering \"i\" \n-List a new Source of Debt by enetering \"d\"\n-Create a new Category of Expenses by enetering \"e\"");
		c.println ("-Withdrawal money by entering \"withdrawal\"\n-Deposit money by entering \"deposit\"");
		c.println ("-Delete a savings goal, income stream, source of debt, purchase, or expense catgeory by entering \"delete\"");
		c.println ("-Acsess the information to various accounts by entering \"acsess\"");
		c.println ("Change your user name by entering \"changeUName\"");
	    }



	    //Create New Savings Goal
	    if (command.compareTo ("s") == 0 || command.compareTo ("S") == 0)
	    {
		c.println ("Please enter the name your savings goal, enter the amount you would like to save, then please enter in the months to achieve this goal,and \nthe reason you are starting this goal.");
		Savings sa = new Savings (c.readLine (), c.readDouble (), c.readDouble (), c.readLine (), c);
		sa.getName ().getBytes (0, sa.getName ().length (), nameBytes, 0);

		//Write to Binary File
		sa.getReason ().getBytes (0, sa.getReason ().length (), nameBytes2, 0);
		sraf = new RandomAccessFile ("savings.bin", "rw");
		sraf.seek (a * 131);
		nameBytes = new byte [15];
		sraf.write (nameBytes);
		sraf.writeDouble (sa.getGoal ());
		sraf.writeDouble (sa.getMonths ());
		nameBytes2 = new byte [100];
		sraf.write (nameBytes2);
		sraf.close ();

		sList.addNode (sa);
		a++;

		//Update Counter in User Info Binary File
		userinfo = new RandomAccessFile ("userinfo.bin", "rw");
		userinfo.seek (27);
		userinfo.writeInt (a);
		userinfo.close ();
	    }


	    //Create New Income Stream
	    if (command.compareTo ("i") == 0 || command.compareTo ("I") == 0)
	    {
		c.println ("Please enter the name your income stream, amd your monthly income.");
		Income ib = new Income (c.readLine (), c.readDouble (), c);

		//Write to Binary File
		ib.getName ().getBytes (0, ib.getName ().length (), nameBytes, 0);
		iraf = new RandomAccessFile ("income.bin", "rw");
		iraf.seek (b * 23);
		nameBytes = new byte [15];
		iraf.write (nameBytes);
		iraf.writeDouble (ib.getIncome ());
		iraf.close ();

		iList.addNode (ib);
		b++;


		//Update Counter in User Info Binary File
		userinfo = new RandomAccessFile ("userinfo.bin", "rw");
		userinfo.seek (31);
		userinfo.writeInt (b);
		userinfo.close ();
	    }



	    //Create New Source of Debt
	    if (command.compareTo ("d") == 0 || command.compareTo ("D") == 0)
	    {
		c.println ("Please enter the name your source of debt, enter in your current debt form this source, then please enter the monthly fee you owe, and \nhow much money you intend to pay per month.");
		c.println ("It is highly reccomended that your monthly payment is a greater dollar value than the monthly fee.");
		Debt de = new Debt (c.readLine (), c.readDouble (), c.readDouble (), c.readDouble (), c);

		//Write to Binary File
		de.getName ().getBytes (0, de.getName ().length (), nameBytes, 0);
		draf = new RandomAccessFile ("debt.bin", "rw");
		draf.seek (e * 39);
		nameBytes = new byte [15];
		draf.write (nameBytes);
		draf.writeDouble (de.getInitial ());
		draf.writeDouble (de.getMPay ());
		draf.writeDouble (de.getMFee ());
		draf.close ();

		dList.addNode (de);
		e++;

		//Update Counter in User Info Binary File
		userinfo = new RandomAccessFile ("userinfo.bin", "rw");
		userinfo.seek (35);
		userinfo.writeInt (e);
		userinfo.close ();
	    }



	    //Create New Expenses Category
	    if (command.compareTo ("e") == 0 || command.compareTo ("E") == 0)
	    {
		char check;
		do
		{
		    c.println ("Would you like to add in an initial purchase (Y/N)?");
		    check = c.readLine ().charAt (0);

		    if (check == 'Y' || check == 'y')
		    {
			c.println ("Please enter the name of the new category of expenses, an initial purchase name, and value of the purchase.");
			Expenses ed = new Expenses (c.readLine (), c.readLine (), c.readDouble (), c);

			//Write to Binary File
			ed.getCategory ().getBytes (0, ed.getCategory ().length (), nameBytes2, 0);
			ed.getPurchaseName (0).getBytes (0, ed.getCategory ().length (), nameBytes, 0);
			eraf = new RandomAccessFile ("debt.bin", "rw");
			eraf.seek (d * 2400);
			nameBytes2 = new byte [100];
			eraf.write (nameBytes2);
			nameBytes = new byte [15];
			eraf.write (nameBytes);
			eraf.writeDouble (ed.getPurchase (0));
			eraf.close ();

			eList.addNode (ed);
			d++;

			//Update Counter in User Info Binary File
			userinfo = new RandomAccessFile ("userinfo.bin", "rw");
			userinfo.seek (39);
			userinfo.writeInt (d);
			userinfo.close ();
		    }
		    else if (check == 'N' || check == 'n')
		    {
			c.println ("Please enter the name of the new category of expenses.");
			Expenses ed = new Expenses (c.readLine ());

			//Write to Binary File
			ed.getCategory ().getBytes (0, ed.getCategory ().length (), nameBytes2, 0);
			eraf = new RandomAccessFile ("debt.bin", "rw");
			eraf.seek (d * 2400);
			nameBytes2 = new byte [100];
			eraf.write (nameBytes2);
			eraf.close ();

			eList.addNode (ed);
			d++;

			//Update Counter in User Info Binary File
			userinfo = new RandomAccessFile ("userinfo.bin", "rw");
			userinfo.seek (39);
			userinfo.writeInt (d);
			userinfo.close ();
		    }
		}
		while (check != 'Y' && check != 'y' && check != 'N' && check != 'n');
	    }


	    //Delete Method
	    if (command.compareTo ("delete") == 0)
	    {
		c.println ("Would you like to delete a savings goal (s), debt source (d), income stream (i), or an expense category(e)/purchase?");
		char choice = 'q';
		while (choice != 's' && choice != 'd' && choice != 'i' && choice != 'e')
		{
		    choice = c.readChar ();
		    c.println ("What is the name of the object you would like to delete?");
		    String acc = c.readLine ();

		    if (choice == 's')
		    {
			int j = -2;

			sList.deleteNode (acc);

			//Compensate in Binary File

			sraf = new RandomAccessFile ("savings.bin", "rw");

			for (int u = 0 ; u < a ; u++)
			{
			    nameBytes = new byte [15];
			    sraf.seek ((u) * 131);                              //Copies Information from Next Part of File to Temporary Variables
			    sraf.read (nameBytes);
			    String n = new String (nameBytes, 0);

			    if (n.compareTo (acc) == 0)
				j = u;
			}

			nameBytes = new byte [15];
			sraf.seek ((j + 1) * 131);                              //Copies Information from Next Part of File to Temporary Variables
			sraf.read (nameBytes);
			String n = new String (nameBytes, 0);
			double g = sraf.readDouble ();
			double m = sraf.readDouble ();
			nameBytes2 = new byte [100];
			sraf.read (nameBytes2);
			String r = new String (nameBytes2, 0);

			c.println ("The savings goal " + n + " was deleted.");

			sraf.seek (j * 131);               //Replaces Part of File Storing Deleted Information with Data Stored in Temporary Variables
			n.getBytes (0, n.length (), nameBytes, 0);
			sraf.write (nameBytes);
			sraf.writeDouble (g);
			sraf.writeDouble (m);
			n.getBytes (0, n.length (), nameBytes2, 0);
			sraf.write (nameBytes2);
			sraf.close ();

			userinfo = new RandomAccessFile ("userinfo.bin", "rw");
			a--;                                                   //Compensate for change in number of objects represented by the counters
			userinfo.seek (27);
			userinfo.writeInt (a);
			userinfo.close ();

		    }

		    else if (choice == 'i')
		    {
			iList.deleteNode (acc);

			//Compensate in Binary File
			iraf = new RandomAccessFile ("income.bin", "rw");
			int j = -2;

			for (int u = 0 ; u < b ; u++)
			{
			    nameBytes = new byte [15];
			    iraf.seek ((u) * 23);                              //Copies Information from Next Part of File to Temporary Variables
			    iraf.read (nameBytes);
			    String n = new String (nameBytes, 0);

			    if (n.compareTo (acc) == 0)
				j = u;
			}

			nameBytes = new byte [15];
			iraf.seek ((j + 1) * 23);                              //Copies Information from Next Part of File to Temporary Variables
			iraf.read (nameBytes);
			String n = new String (nameBytes, 0);
			double g = iraf.readDouble ();
			double m = iraf.readDouble ();
			nameBytes2 = new byte [100];
			iraf.read (nameBytes2);
			String r = new String (nameBytes2, 0);

			c.println ("The income stream " + n + " was deleted.");

			iraf.seek (j * 23);               //Replaces Part of File Storing Deleted Information with Data Stored in Temporary Variables
			n.getBytes (0, n.length (), nameBytes, 0);
			iraf.write (nameBytes);
			iraf.writeDouble (g);
			iraf.writeDouble (m);
			n.getBytes (0, n.length (), nameBytes2, 0);
			iraf.write (nameBytes2);
			iraf.close ();

			userinfo = new RandomAccessFile ("userinfo.bin", "rw");
			b--;                                                   //Compensate for change in number of objects represented by the counters
			userinfo.seek (31);
			userinfo.writeInt (b);
			userinfo.close ();
		    }

		    else if (choice == 'd')
		    {
			dList.deleteNode (acc);

			//Compensate in Binary File
			draf = new RandomAccessFile ("debt.bin", "rw");

			int j = -2;

			for (int u = 0 ; u < e ; u++)
			{
			    nameBytes = new byte [15];
			    draf.seek ((u) * 39);                              //Copies Information from Next Part of File to Temporary Variables
			    draf.read (nameBytes);
			    String n = new String (nameBytes, 0);

			    if (n.compareTo (acc) == 0)
				j = u;
			}

			nameBytes = new byte [15];
			draf.seek ((j + 1) * 39);                              //Copies Information from Next Part of File to Temporary Variables
			draf.read (nameBytes);
			String n = new String (nameBytes, 0);
			double g = draf.readDouble ();
			double m = draf.readDouble ();
			nameBytes2 = new byte [100];
			draf.read (nameBytes2);
			String r = new String (nameBytes2, 0);

			c.println ("The source of debt " + n + " was deleted.");

			draf.seek (j * 39);               //Replaces Part of File Storing Deleted Information with Data Stored in Temporary Variables
			n.getBytes (0, n.length (), nameBytes, 0);
			draf.write (nameBytes);
			draf.writeDouble (g);
			draf.writeDouble (m);
			n.getBytes (0, n.length (), nameBytes2, 0);
			draf.write (nameBytes2);
			draf.close ();

			userinfo = new RandomAccessFile ("userinfo.bin", "rw");
			e--;                                                   //Compensate for change in number of objects represented by the counters
			userinfo.seek (35);
			userinfo.writeInt (e);
			userinfo.close ();
		    }


		    else if (choice == 'e')
		    {
			c.println ("Press 'l' to delete the entire list or 's' to delete a specific purchase");
			while (choice != 'l' && choice != 's')
			{
			    choice = c.readChar ();

			    if (choice == 'l')
			    {
				eList.deleteNode (acc);

				//Compensate in Binary File
				eraf = new RandomAccessFile ("expenses.bin", "rw");

				int j = -2;

				for (int u = 0 ; u < d ; u++)
				{
				    nameBytes = new byte [15];
				    eraf.seek ((u) * 2400);                              //Copies Information from Next Part of File to Temporary Variables
				    eraf.read (nameBytes);
				    String n = new String (nameBytes, 0);

				    if (n.compareTo (acc) == 0)
					j = u;
				}

				nameBytes = new byte [15];
				eraf.seek ((j + 1) * 2400);                              //Copies Information from Next Part of File to Temporary Variables
				eraf.read (nameBytes);
				String n = new String (nameBytes, 0);
				double g = eraf.readDouble ();
				double m = eraf.readDouble ();
				nameBytes2 = new byte [100];
				eraf.read (nameBytes2);
				String r = new String (nameBytes2, 0);

				c.println ("The expense category " + n + " was deleted.");

				eraf.seek (j * 2400);               //Replaces Part of File Storing Deleted Information with Data Stored in Temporary Variables
				n.getBytes (0, n.length (), nameBytes, 0);
				eraf.write (nameBytes);
				eraf.writeDouble (g);
				eraf.writeDouble (m);
				n.getBytes (0, n.length (), nameBytes2, 0);
				eraf.write (nameBytes2);
				eraf.close ();

				userinfo = new RandomAccessFile ("userinfo.bin", "rw");
				d--;                                                   //Compensate for change in number of objects represented by the counters
				userinfo.seek (39);
				userinfo.writeInt (d);
				userinfo.close ();
			    }

			    else if (choice == 's')
			    {
				c.println ("What is the name of the purchase?");
				eList.getNode (acc).deletePurchase (c.readLine ());
			    }
			}
		    }
		}
	    }

	    //Acsess Command
	    if (command.compareTo ("acsess") == 0)
	    {
		c.println ("Would you like to acsess a savings goal (s), debt source (d), income stream (i), or an expense category/purchase(e)?");
		char choice = 'q';
		while (choice != 's' && choice != 'd' && choice != 'i' && choice != 'e')
		{
		    choice = c.readChar ();

		    c.println ("What is the name of the object you would like to delete?");
		    String acc = c.readLine ();

		    if (choice == 's')
		    {
			c.println (sList.getNode (acc).toString ());
			//PERCENT SAVED CIC:E
		    }

		    else if (choice == 'i')
		    {
			c.println (iList.getNode (acc).toString ());
		    }

		    else if (choice == 'd')
		    {
			c.println (dList.getNode (acc).toString ());
		    }

		    else if (choice == 'e')
		    {
			c.println ("Press 'l' to acsess the entire list or 's' to acsess a specific purchase");
			while (choice != 'l' && choice != 's')
			{
			    choice = c.readChar ();

			    if (choice == 'l')
				eList.getNode (acc).printList ();
			    else if (choice == 's')
			    {
				c.println ("What is the name of the purchase?");
				c.println (eList.getNode (acc).getPurchase (c.readLine ()));
			    }
			}
		    }
		}
	    }


	    //Withdrawal Command
	    if (command.compareTo ("withdrawal") == 0)
	    {
		c.println ("How much money would you like to withdrawal from your balance?");
		budget.withdrawal (c.readDouble ());
		//Write to binary file
		userinfo.seek (15);
		userinfo.writeDouble (budget.getBalance ());
	    }

	    //Deposit Command
	    if (command.compareTo ("deposit") == 0)
	    {
		c.println ("Would you like to deposit to savings (s), debt (d), or income (i)?");
		char choice = 'q';
		while (choice != 's' && choice != 'd' && choice != 'i')
		{
		    choice = c.readChar ();

		    c.println ("What is the name of the goal/source/stream, and how much money would you like to deposit?");
		    String acc = c.readLine ();
		    double amount = c.readDouble ();

		    if (choice == 's')
			sList.getNode (acc).deposit (amount);

		    else if (choice == 'i')
			iList.getNode (acc).deposit (amount);

		    else if (choice == 'd')
			dList.getNode (acc).deposit (amount);
		}
		//Write to binary file
		userinfo = new RandomAccessFile ("userinfo.bin", "rw");
		userinfo.seek (15);
		userinfo.writeDouble (budget.getBalance ());
	    }

	    if (command.compareTo ("changeUName") == 0)
	    {
		c.println ("What would you like to change your username to?");
		uName = c.readLine ();

		//Compensate in Binary File

		uName.getBytes (0, uName.length (), nameBytes, 0);
		userinfo = new RandomAccessFile ("userinfo.bin", "rw");
		userinfo.write (nameBytes);
		userinfo.close ();
	    }

	    if (command.compareTo("sinfo") == 0)
	    {
	    c.println("Which savings goal would you like specific information on?");
	    String sName = c.readLine();
	    c.print("The savings goal "+sName+" requires a monthly contribution of $");
	    c.print(sList.getNode(sName).getMContribution(),0,2);
	    c.print(".\n The precent of the goal saved is ");
	    c.print(sList.getNode(sName).getPercSaved(),0,2);
	    c.print("%, and the remaining percent is ");
	    c.print(sList.getNode(sName).getPercLeft(),0,2);
	    c.println("%.");
	    }
	    
	    if (command.compareTo ("done") != 0)
		c.println ("Please enter in another command\n");
	}
	while (command.compareTo ("done") != 0);






	//Close all Random Acsess Files Upon Program Being Closed
	userinfo.close ();
	sraf.close ();
	iraf.close ();
	draf.close ();
	eraf.close ();


    } // main method
} // SIDE_M class


