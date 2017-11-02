package backend;

import java.util.ArrayList;

public class Admin extends User {
	
	private ArrayList<Request> listOfRequests;
	
	public Admin(String name, String emailID, String encryptedPassword, String typeOfUser) {
		super(name, emailID, encryptedPassword, typeOfUser);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Request> getListOfRequests() {
		return listOfRequests;
	}

	public void setListOfRequests(ArrayList<Request> listOfRequests) {
		this.listOfRequests = listOfRequests;
	}

	@Override
	public void makeBooking(ClassRoom reqRoom, Slot reqSlot)
	{
		
	}
	
	public void handleRequests(int indexOfRequest, boolean choice)
	{
		
	}
	
	public void addToStudent()
	{
		
	}
	
	public void addToRoom()
	{
		
	}
	
	public void populateNotifications()
	{
		
	}
	

	

}
