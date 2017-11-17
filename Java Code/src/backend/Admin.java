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

/**
 * Admin Class that extends User Class
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class Admin extends User {

	private transient ArrayList<Request> listOfRequests;

	/**
	 * Parameterized constructor of the class Admin. Instantiates an aboject of
	 * type Admin.
	 * 
	 * @param name
	 * @param emailID
	 * @param encryptedPassword
	 * @param typeOfUser
	 * @param securityQuestion
	 * @param securityAnswer
	 */
	public Admin(String name, String emailID, String encryptedPassword, String typeOfUser, String securityQuestion, String securityAnswer) {
		super(name, emailID, encryptedPassword, typeOfUser, securityQuestion, securityAnswer);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Getter function that returns the listOfRequests of Admin.
	 * 
	 * @return listOfRequests
	 */
	public ArrayList<Request> getListOfRequests() {
		return listOfRequests;
	}

	/**
	 * Setter function that sets the value of listOfRequests
	 * 
	 * @param listOfRequests
	 */
	public void setListOfRequests(ArrayList<Request> listOfRequests) {
		this.listOfRequests = listOfRequests;
	}

	/**
	 * This function takes the index of request selected by user, and according
	 * to the choice passed as parameter, handles it and then serializes the
	 * requests list.
	 * 
	 * @param indexOfRequest
	 * @param choice
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void handleRequests(int indexOfRequest, boolean choice)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		if (choice == false) {
			listOfRequests.get(indexOfRequest).setCurrentStatus(-1);
		} else {
			deserializeRooms();
			Request currRequest = listOfRequests.get(indexOfRequest);
			Slot requestSlot = currRequest.getTimeSlot();
			String day = requestSlot.getDay();
			listOfRequests.get(indexOfRequest).setCurrentStatus(1);
			ClassRoom room = getCorrespondingRoom(
					listOfRequests.get(indexOfRequest).getPreferredRoom().getRoomNumber());

			if (room.getBookedSlots().containsKey(day)) {
				Map<Slot, Object> requestDayMap = room.getBookedSlots().get(day);
				requestDayMap.put(requestSlot, currRequest.getSourceStudent());
			} else {
				Map<Slot, Object> newMap = new HashMap<>();
				newMap.put(requestSlot, currRequest.getSourceStudent());
				room.getBookedSlots().put(day, newMap);
			}

			serializeRooms();
		}

		serializeRequests();
	}

	/**
	 * The function populates the notifications list of the admin, and gives the
	 * number of requests pending. It also calls the superclass method.
	 * 
	 * @return ArrayList<String> - listNotif
	 */
	public ArrayList<String> populateNotifications() {

		ArrayList<String> listNotif = super.populateNotifications();
		int count = 0;
		for (int i = 0; i < listOfRequests.size(); i++) {
			if (listOfRequests.get(i).getCurrentStatus() == 0) {
				count = count + 1;
			}
		}
		listNotif.add("You have " + count + " requests pending!");

		return listNotif;
	}

	/**
	 * This function deserializes the text file to obtain the list of requests.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void deserializeRequests() throws ClassNotFoundException, IOException {

		ObjectInputStream in = null;
		listOfRequests = new ArrayList<Request>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/requests.txt"));
			Request request;

			try {

				while (true) {
					request = (Request) in.readObject();
					if (request.getCurrentStatus() == 0) {
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

	/**
	 * This function serializes the updated listOfRequests into the requests.txt
	 * file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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
