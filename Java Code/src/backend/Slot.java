package backend;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class Slot implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String[] TYPES = { "Lecture", "Lab", "Tutorial", "Other" };
	private Date date;
	private String day;
	private String purpose;
	private Time startTime;
	private Time endTime;

	public Slot() {

	}

	/**
	 * 
	 * @param date
	 * @param day
	 * @param purpose
	 * @param startTime
	 * @param endTime
	 */
	public Slot(Date date, String day, String purpose, Time startTime, Time endTime) {
		super();
		this.date = date;
		this.day = day;
		this.purpose = purpose;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		Slot newSlot = (Slot) arg0;
		Date dt = new Date(0000, 00, 00);
		if (newSlot.date.equals(dt)) {
			return false;
		} else {
			return (this.day.equals(newSlot.day) && this.startTime.getTime() == newSlot.startTime.getTime()
					&& this.endTime.getTime() == newSlot.endTime.getTime());
		}

	}

	/**
	 * 
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @return day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * 
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * 
	 * @return startTime
	 */
	public Time getStartTime() {
		return startTime;
	}

	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	/**
	 * 
	 * @return endTime
	 */
	public Time getEndTime() {
		return endTime;
	}

	/**
	 * 
	 * @param endTime
	 */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
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
	 */
	@Override
	public String toString() {
		return "Slot [date=" + date + ", day=" + day + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	/**
	 * 
	 * @return string
	 */
	public String displayFormattedDate() {

		String startMinutes;
		String endMinutes;
		if (this.startTime.getMinutes() < 10) {
			startMinutes = "0" + this.startTime.getMinutes();
		} else {
			startMinutes = Integer.toString(this.startTime.getMinutes());
		}

		if (this.endTime.getMinutes() < 10) {
			endMinutes = "0" + this.endTime.getMinutes();
		} else {
			endMinutes = Integer.toString(this.endTime.getMinutes());
		}

		return this.date.getDate() + "/" + (this.date.getMonth() + 1) + "/" + (this.date.getYear() + 1900) + ", "
				+ this.startTime.getHours() + ":" + startMinutes + "-" + this.endTime.getHours() + ":" + endMinutes;

	}

}
