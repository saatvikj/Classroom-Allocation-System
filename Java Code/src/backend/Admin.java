package backend;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class Admin extends User {
	
	private transient ArrayList<Request> listOfRequests;
	
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
	
	public void handleRequests(int indexOfRequest, boolean choice)
	{
		
		if(choice == false) {
			listOfRequests.get(indexOfRequest).setCurrentStatus(false);
		} else {
			
			Request currRequest = listOfRequests.get(indexOfRequest);
			Slot requestSlot = currRequest.getTimeSlot();
			String day = requestSlot.getDay();
			
			if(currRequest.getPreferredRoom().getBookedSlots().containsKey(day)){
				Map<Slot, Object> requestDayMap = currRequest.getPreferredRoom().getBookedSlots().get(day);
				
			}
			
		
		}
		
	}
	
	public void addToStudent()
	{
		
	}
	
	public void addToRoom()
	{
		
	}
	
	public ArrayList<String> populateNotifications()
	{
		return super.populateNotifications();
	}
	
	public void deserializeRequests() throws ClassNotFoundException, IOException {

		ObjectInputStream in = null;
		listOfRequests = new ArrayList<Request>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/requests.txt"));
			Request request;

			try {

				while (true) {
					request = (Request) in.readObject();
					listOfRequests.add(request);
				}

			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {

		} finally {

			if (in != null) {
				in.close();
			} else {
				listOfRequests = new ArrayList<Request>();
			}
		}

	}

	public void serializeRequests() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/requests.txt"));

			for (int i = 0; i < listOfRequests.size(); i++) {
				Request request = listOfRequests.get(i);
				if (!request.checkExpiry()) {
					out.writeObject(request);
				}
			}

		} finally {

			out.close();

		}

	}

	

	

}
