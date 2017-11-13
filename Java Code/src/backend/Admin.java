package backend;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public void handleRequests(int indexOfRequest, boolean choice) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		if(choice == false) {
			listOfRequests.get(indexOfRequest).setCurrentStatus(-1);
		} else {
			deserializeRooms();
			Request currRequest = listOfRequests.get(indexOfRequest);
			Slot requestSlot = currRequest.getTimeSlot();
			String day = requestSlot.getDay();
			listOfRequests.get(indexOfRequest).setCurrentStatus(1);
			ClassRoom room = getCorrespondingRoom(listOfRequests.get(indexOfRequest).getPreferredRoom().getRoomNumber());
			
			if(room.getBookedSlots().containsKey(day)){
				Map<Slot, Object> requestDayMap = room.getBookedSlots().get(day);
				requestDayMap.put(requestSlot, currRequest.getSourceStudent());
			} else {
				Map<Slot, Object> newMap = new HashMap<>();
				newMap.put(requestSlot, currRequest.getSourceStudent());
				room.getBookedSlots().put(day, newMap);
			}
			
			System.out.println(listOfRequests.get(indexOfRequest).getCurrentStatus());
			serializeRooms();
		}
		
		System.out.println("serialize done");
		serializeRequests();
	}
	
	public void addToStudent()
	{
		
	}
	
	public void addToRoom()
	{
		
	}
	
	public ArrayList<String> populateNotifications()
	{
		
		ArrayList<String> listNotif = super.populateNotifications();
		int count = 0;
		for(int i = 0; i< listOfRequests.size(); i++){
			if(listOfRequests.get(i).getCurrentStatus() == 0){
				count = count + 1;
			}
		}
		listNotif.add("You have " + count + " requests pending!");
		
		return listNotif;
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
					if(request.getCurrentStatus() == 0) {
						listOfRequests.add(request);
					}
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
