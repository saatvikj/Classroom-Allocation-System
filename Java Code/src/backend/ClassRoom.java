package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ClassRoom implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roomNumber;
	private int capacity;
	private ArrayList<Map<Slot, Object>> bookedSlots;

	public ClassRoom() {

	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public ArrayList<Map<Slot, Object>> getBookedSlots() {
		return bookedSlots;
	}

	public void setBookedSlots(ArrayList<Map<Slot, Object>> bookedSlots) {
		this.bookedSlots = bookedSlots;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean checkIfEmptyInSlot(Slot timeslot) {
		/*
		 * Goes through the hashmap and checks if the slot is available in that
		 * day’s hashmap Returns true if available, else returns false
		 */
		return true;
	}

	public void makeBooking(Slot timeslot, Object source) {
		/*
		 * Iterate to the hashmap to that corresponding day, and then assigns
		 * that object according to the slot passed
		 */
	}

	public void cancelBooking(Slot timeslot) {
		/*
		 * User iterates over the hashmap to go to the classroom value by
		 * looking for the key timeSlot. Sets the key, value pair to null if
		 * found else do nothing
		 */
	}

	@Override
	public String toString() {
		return "ClassRoom [roomNumber=" + roomNumber + ", capacity=" + capacity + ", bookedSlots=" + bookedSlots + "]";
	}
	
	

}
