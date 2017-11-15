package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassRoom class that stores the properties of a room
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class ClassRoom implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roomNumber;
	private int capacity;
	private Map<String, Map<Slot, Object>> bookedSlots = new HashMap<String, Map<Slot, Object>>();

	/**
	 *
	 * Default Constructor of ClassRoom Class.
	 */
	public ClassRoom() {

	}

	/**
	 * Getter method to return the RoomNumber.
	 * 
	 * @return roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Setter method to set the room number of the corresponding room.
	 * 
	 * @param roomNumber
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * Getter method to return the Map of BookedSlots.
	 * 
	 * @return bookedSlots
	 */
	public Map<String, Map<Slot, Object>> getBookedSlots() {
		return bookedSlots;
	}

	/**
	 * Setter method to set the value of bookedSlots map
	 * 
	 * @param bookedSlots
	 */
	public void setBookedSlots(Map<String, Map<Slot, Object>> bookedSlots) {
		this.bookedSlots = bookedSlots;
	}

	/**
	 * Getter method to return the seating capacity of a room
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Setter method to set the capacity of a classroom
	 * 
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * toString method for the ClassRoom class
	 * 
	 * @return string
	 */
	@Override
	public String toString() {
		return "ClassRoom [roomNumber=" + roomNumber + ", capacity=" + capacity + "]";
	}

}
