package backend;

import java.util.ArrayList;
import java.util.Map;

public class User {
	
	protected String name;
	protected String emailID;
	protected String encryptedPassword;
	protected Map<Slot, ClassRoom> bookedRooms;
	public ArrayList<String> listOfNotifications;
	
	public void viewRoomBooked()
	{
		/*
		 * Iterates through the hashmap and prints all the rooms booked by that User, else prints a fancy message of no bookings.
		 */
	}
	
	public boolean checkRoomAvailability(ClassRoom reqRoom, Slot reqSlot)
	{
		/*
		 * It directly calls the method checkIfEmptyInSlot of the reqRoom object with reqSlot as the parameter.
		 */
		return true;
	}
	
	public void makeBooking(ClassRoom reqRoom, Slot reqSlot)
	{
		/*
		 * It calls the method makeBooking of the reqRoom object with reqSlot as the first parameter rest is handled by inheritance (comment).
		 */
	}
	
	public void cancelBooking(ClassRoom bookedRoom, Slot bookedSlot)
	{
		/*
		 * It calls the method cancelBooking from bookedRoom object with bookedSlot as parameter and then makes this entry in its own hashmap(bookedRooms) as null.
		 */
	}
	
	public void populateNotifications()
	{
		/*
		 * It goes through the required things to give notification about and refreshes the listOfNotifications attribute. (New requests for admin, request status for student etc etc)
		 */
	}

}
