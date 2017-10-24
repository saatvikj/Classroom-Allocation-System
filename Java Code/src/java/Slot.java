package java;

import java.sql.Time;
import java.util.Date;

public class Slot {

	private Date date;
	private String day;
	private Time startTime;
	private Time endTime;

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		Slot newSlot = (Slot) arg0;

		return ((this.date.compareTo(newSlot.date) == 0) && this.day.equals(newSlot.day)
				&& this.startTime.getTime() == newSlot.startTime.getTime()
				&& this.endTime.getTime() == newSlot.endTime.getTime());

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

}
