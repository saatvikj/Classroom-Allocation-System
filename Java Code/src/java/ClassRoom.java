package java;

import java.util.ArrayList;
import java.util.Map;

public class ClassRoom {

	private String roomNumber;
	private int capacity;
	private ArrayList<Map<Slot, Object>> bookedSlots;

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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

}
