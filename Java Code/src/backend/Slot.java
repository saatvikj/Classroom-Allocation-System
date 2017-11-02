package backend;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Slot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String TYPE_LECTURE = "Lecture";
	public static final String TYPE_TUTORIAL = "Tutorial";
	public static final String TYPE_LAB = "Lab";
	public static final String TYPE_OTHER = "Other";
	private Date date;
	private String day;
	private String purpose;
	private Time startTime;
	private Time endTime;

	public Slot() {

	}
	


	public Slot(Date date, String day, String purpose, Time startTime, Time endTime) {
		super();
		this.date = date;
		this.day = day;
		this.purpose = purpose;
		this.startTime = startTime;
		this.endTime = endTime;
	}





	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		Slot newSlot = (Slot) arg0;

		return ((this.date.compareTo(newSlot.date) == 0) && this.day.equals(newSlot.day)
				&& this.startTime.getTime() == newSlot.startTime.getTime()
				&& this.endTime.getTime() == newSlot.endTime.getTime()
				&& this.purpose.equals(newSlot.purpose));

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}



	@Override
	public String toString() {
		return "Slot [date=" + date + ", day=" + day + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
