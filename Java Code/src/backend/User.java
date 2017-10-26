package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class User implements Serializable {


	private static final long serialVersionUID = 1L;
	protected String name;
	protected String emailID;
	protected String encryptedPassword;
	protected Map<Slot, ClassRoom> bookedRooms;
	protected String typeOfUser;
	public ArrayList<String> listOfNotifications;

	public User() {
		
		
	}
	
	
	public User(String name, String emailID, String encryptedPassword, String typeOfUser) {
		super();
		this.name = name;
		this.emailID = emailID;
		this.encryptedPassword = encryptedPassword;
		this.typeOfUser = typeOfUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Map<Slot, ClassRoom> getBookedRooms() {
		return bookedRooms;
	}

	public void setBookedRooms(Map<Slot, ClassRoom> bookedRooms) {
		this.bookedRooms = bookedRooms;
	}

	public ArrayList<String> getListOfNotifications() {
		return listOfNotifications;
	}

	public void setListOfNotifications(ArrayList<String> listOfNotifications) {
		this.listOfNotifications = listOfNotifications;
	}

	public void viewRoomBooked() {
		/*
		 * Iterates through the hashmap and prints all the rooms booked by that
		 * User, else prints a fancy message of no bookings.
		 */
	}

	public boolean checkRoomAvailability(ClassRoom reqRoom, Slot reqSlot) {
		/*
		 * It directly calls the method checkIfEmptyInSlot of the reqRoom object
		 * with reqSlot as the parameter.
		 */
		return true;
	}

	public void makeBooking(ClassRoom reqRoom, Slot reqSlot) {
		/*
		 * It calls the method makeBooking of the reqRoom object with reqSlot as
		 * the first parameter rest is handled by inheritance (comment).
		 */
	}

	public void cancelBooking(ClassRoom bookedRoom, Slot bookedSlot) {
		/*
		 * It calls the method cancelBooking from bookedRoom object with
		 * bookedSlot as parameter and then makes this entry in its own
		 * hashmap(bookedRooms) as null.
		 */
	}

	public void populateNotifications() {
		/*
		 * It goes through the required things to give notification about and
		 * refreshes the listOfNotifications attribute. (New requests for admin,
		 * request status for student etc etc)
		 */
	}

}
