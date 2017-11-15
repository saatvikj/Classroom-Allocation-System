package backend;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;
	private String purpose;
	private ClassRoom preferredRoom;
	private int requiredCapacity;
	private Slot timeSlot;
	private Date timeStamp;
	private int currentStatus;
	private Student sourceStudent;

	/**
	 * Default constructor of the class Request
	 */
	Request() {

	}

	/**
	 * 
	 * Parameterized Constructor of the class Request
	 * 
	 * @param purpose
	 * @param preferredRoom
	 * @param requiredCapacity
	 * @param timeSlot
	 * @param timeStamp
	 * @param currentStatus
	 * @param sourceStudent
	 */
	Request(String purpose, ClassRoom preferredRoom, int requiredCapacity, Slot timeSlot, Date timeStamp,
			int currentStatus, Student sourceStudent) {
		super();
		this.purpose = purpose;
		this.preferredRoom = preferredRoom;
		this.requiredCapacity = requiredCapacity;
		this.timeSlot = timeSlot;
		this.timeStamp = timeStamp;
		this.currentStatus = currentStatus;
		this.sourceStudent = sourceStudent;
	}

	/**
	 * Getter method to return the purpose of a request
	 * 
	 * @return purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * Setter method to set the purpose of a request
	 * 
	 * @param purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * Getter method to get the preferredRoom
	 * 
	 * @return preferredRoom
	 */
	public ClassRoom getPreferredRoom() {
		return preferredRoom;
	}

	/**
	 * Setter method to set the preferredRoom
	 * 
	 * @param preferredRoom
	 */
	public void setPreferredRoom(ClassRoom preferredRoom) {
		this.preferredRoom = preferredRoom;
	}

	/**
	 * Getter method to return the required capacity
	 * 
	 * @return requiredCapacity
	 */
	public int getRequiredCapacity() {
		return requiredCapacity;
	}

	/**
	 * Setter method that sets the required capacity
	 * 
	 * @param requiredCapacity
	 */
	public void setRequiredCapacity(int requiredCapacity) {
		this.requiredCapacity = requiredCapacity;
	}

	/**
	 * Getter function to return the timeslot
	 * 
	 * @return timeSlot
	 */
	public Slot getTimeSlot() {
		return timeSlot;
	}

	/**
	 * Setter function to set the value of timeSlot
	 * 
	 * @param timeSlot
	 */
	public void setTimeSlot(Slot timeSlot) {
		this.timeSlot = timeSlot;
	}

	/**
	 * Getter function to get the TimeStamp of the request
	 * 
	 * @return timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Setter function to set the timeStamp of the request
	 * 
	 * @param timeStamp
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Getter function to return the current status of the request
	 * 
	 * @return currentStatus
	 */
	public int getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * Setter function to set the current status of the request
	 * 
	 * @param currentStatus
	 */
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * Getter function to get the source Student of the request.
	 * 
	 * @return sourceStudent
	 */
	public Student getSourceStudent() {
		return sourceStudent;
	}

	/**
	 * Setter function to set the source student of the request
	 * 
	 * @param sourceStudent
	 */
	public void setSourceStudent(Student sourceStudent) {
		this.sourceStudent = sourceStudent;
	}

	/**
	 * This function checks if the request has expired or not.
	 * 
	 * @return boolean
	 */
	public boolean checkExpiry() {
		/*
		 * Checks whether the current request has been there for more than 5
		 * days or not Returns true if it is expired (more than 5 days)
		 * otherwise returns false. Will be called to check if the request in
		 * arraylist is to be cancelled or not. If yes, replace it with null.
		 */

		Date today = new Date();
		if (today.getDate() - this.timeStamp.getDate() == 5) {
			if (today.getTime() >= this.timeStamp.getTime()) {
				return true;
			} else {
				return false;
			}
		} else if (today.getDate() - this.timeStamp.getDate() > 5) {
			return true;
		}
		return false;
	}

}
