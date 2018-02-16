
public class Customer 
{
	private int waitTime;
	private int customerID;
	private int startTime;
		
	Customer()
	{
		
	}
	
	Customer(int customerID, int startTime)
	{
		setStartTime(startTime);
		setCustomerID(customerID);
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
}
