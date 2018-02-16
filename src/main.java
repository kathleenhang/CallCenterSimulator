import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class main {

	public static void main(String[] args)  throws Exception
	{
		int l=0;	// number of lines
		int n=0;	// call arrival rate
		int t=0;	// total simulation time
		int m=0;	// customer service rate
		int callArrival;	// time in relation to t that a customer call arrives
		int serviceTime = 0;	// will generate random customer service rates and behave as decrementing counter
		int customerID = 0;
		int totalCustomersServed = 0;	// keeps track of total customers served
		int longestLine = 0;	// keeps track of longest call line of simulation
		int longestWaitTime = 0;	// keeps track of longest wait time of simulation
		int totalWaitTime = 0;	// keeps track of total wait time of all customers combined
		String fileName = "testcase0.txt";
		int menuSelection;
		boolean repAvail = true;	// is a customer representative available or not
		
		Customer currentCustomer = new Customer();
		//new number generator
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please select one of the following options:\n"
				+ "1. File input\n"
				+ "2. Manual input\n");
		
		menuSelection = scan.nextInt();
		
		if(menuSelection == 1) 
		{
			System.out.println("Please type file name: ");
			fileName = scan.next();
			
			// scan this text file for simulation input
			Scanner fileScanner = new Scanner(new File(fileName));
			
			//get simulation input from file
			l = fileScanner.nextInt(); // number of lines
			n = fileScanner.nextInt(); // customer call arrival rate
			m = fileScanner.nextInt(); // customer service rate
			t = fileScanner.nextInt(); // total simulation time
		}
		else if(menuSelection == 2)
		{
			System.out.println("Please type the number of call lines, customer's call arrival rate, "
					+ "customer service time rate, and total simulation time.");
			//get simulation input from file
			l = scan.nextInt(); // number of lines
			n = scan.nextInt(); // customer call arrival rate
			m = scan.nextInt(); // customer service rate
			t = scan.nextInt(); // total simulation time
		}
		
	
		//set callArrival using random values from 1 - n
		callArrival = (rand.nextInt(n) + 1);
		
		//create our call line queue
		LinkedQueue<Customer> callLine = new LinkedQueue();
		
		//loop through total simulation time. Minute 1, 2, 3...t
		for(int i = 1; i <= t; i++)
		{
			//output current minute
			System.out.println("Minute: " + i);
						
			//if the customer called during current time iteration
			if(i == callArrival)
			{		
				//create a new customer & add the following customer information: ID # & call arrival time
				Customer customer = new Customer(++customerID, callArrival);
				
				//store the customer into the end of the queue
				callLine.enqueue(customer);
				
				//Estimate the customer wait time
				int estimatedWaitTime = callLine.size() * m/2;
				
				//output that the customer has called
				System.out.println("[ --- Customer " + customer.getCustomerID() + " has called! --- ]"
									+ "\nAverage wait time: " + estimatedWaitTime + " mins");
	
				//schedule next customer call arrival time
				callArrival += rand.nextInt(n) + 1;
			}
			
			// once customer service has been completed
			if(serviceTime == 0 && !repAvail)
			{	
				System.out.println("Customer " + currentCustomer.getCustomerID() + " Completed***");
				repAvail = true;
			}
			
			
			
			//if no customer is being served and there is at least one customer in the queue
			if(repAvail && !callLine.isEmpty())
			{
				// calculate wait time of first customer in queue
				int startTime = callLine.first().getStartTime();
				int waitTime = i - startTime;
				
				// accumulate first customer in queue wait time into totalWaitTime 
				totalWaitTime += waitTime;
				
				//keep track of longest wait time of a single customer of the simulation
				if(waitTime > longestWaitTime)
					longestWaitTime = waitTime;
				
				//dequeue next customer
				currentCustomer = callLine.dequeue();
				
				//increment totalCustomersServed counter
				totalCustomersServed++;
				
				//simulate customer service time
				serviceTime = (rand.nextInt(m) + 1);
				
				//change representative to busy mode
				repAvail = false;
				
			}
			
			// keep track of longest call line of simulation
			if(callLine.size() > longestLine)
				longestLine = callLine.size();
			
			// while representative is not available, continue to decrement service time counter
			if(!repAvail)
				serviceTime--;
		}
		
		// calculate average wait time of all customers served
		float averageWaitTime = (float)totalWaitTime/totalCustomersServed;
		
		// output all results
		System.out.println("---------------------------");
		System.out.print("# of customers served: " + totalCustomersServed
				+ "\n# of customers left in queue: " + callLine.size()
				+ "\nMax # of customers in line at any time:  " + longestLine
				+ "\nLongest wait time: " + longestWaitTime
				+ "\nAverage wait time: " + averageWaitTime );
	}
	
	
}
