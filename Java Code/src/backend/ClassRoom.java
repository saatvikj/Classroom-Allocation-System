package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
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
	 */
	public ClassRoom() {

	}

	/**
	 * 
	 * @return roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * 
	 * @param roomNumber
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * 
	 * @return bookedSlots
	 */
	public Map<String, Map<Slot, Object>> getBookedSlots() {
		return bookedSlots;
	}

	/**
	 * 
	 * @param bookedSlots
	 */
	public void setBookedSlots(Map<String, Map<Slot, Object>> bookedSlots) {
		this.bookedSlots = bookedSlots;
	}

	/**
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * 
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return string
	 */
	@Override
	public String toString() {
		return "ClassRoom [roomNumber=" + roomNumber + ", capacity=" + capacity + "]";
	}

}
