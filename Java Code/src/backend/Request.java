package backend;

import java.util.Date;

public class Request {

	private String purpose;
	private ClassRoom preferredRoom;
	private int requiredCapacity;
	private Slot timeSlot;
	private Date timeStamp;
	private boolean currentStatus;
	private Student sourceStudent;

	Request() {

	}

	Request(String purpose, ClassRoom preferredRoom, int requiredCapacity, Slot timeSlot, Date timeStamp,
			boolean currentStatus, Student sourceStudent) {
		super();
		this.purpose = purpose;
		this.preferredRoom = preferredRoom;
		this.requiredCapacity = requiredCapacity;
		this.timeSlot = timeSlot;
		this.timeStamp = timeStamp;
		this.currentStatus = currentStatus;
		this.sourceStudent = sourceStudent;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public ClassRoom getPreferredRoom() {
		return preferredRoom;
	}

	public void setPreferredRoom(ClassRoom preferredRoom) {
		this.preferredRoom = preferredRoom;
	}

	public int getRequiredCapacity() {
		return requiredCapacity;
	}

	public void setRequiredCapacity(int requiredCapacity) {
		this.requiredCapacity = requiredCapacity;
	}

	public Slot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(Slot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(boolean currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Student getSourceStudent() {
		return sourceStudent;
	}

	public void setSourceStudent(Student sourceStudent) {
		this.sourceStudent = sourceStudent;
	}

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
