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
	 * 
	 */
	Request() {

	}

	/**
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
	 * 
	 * @return purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * 
	 * @param purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 
	 * @return preferredRoom
	 */
	public ClassRoom getPreferredRoom() {
		return preferredRoom;
	}

	/**
	 * 
	 * @param preferredRoom
	 */
	public void setPreferredRoom(ClassRoom preferredRoom) {
		this.preferredRoom = preferredRoom;
	}

	/**
	 * 
	 * @return requiredCapacity
	 */
	public int getRequiredCapacity() {
		return requiredCapacity;
	}

	/**
	 * 
	 * @param requiredCapacity
	 */
	public void setRequiredCapacity(int requiredCapacity) {
		this.requiredCapacity = requiredCapacity;
	}

	/**
	 * 
	 * @return timeSlot
	 */
	public Slot getTimeSlot() {
		return timeSlot;
	}

	/**
	 * 
	 * @param timeSlot
	 */
	public void setTimeSlot(Slot timeSlot) {
		this.timeSlot = timeSlot;
	}

	/**
	 * 
	 * @return timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * 
	 * @param timeStamp
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * 
	 * @return currentStatus
	 */
	public int getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * 
	 * @param currentStatus
	 */
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * 
	 * @return sourceStudent
	 */
	public Student getSourceStudent() {
		return sourceStudent;
	}

	/**
	 * 
	 * @param sourceStudent
	 */
	public void setSourceStudent(Student sourceStudent) {
		this.sourceStudent = sourceStudent;
	}

	/**
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
