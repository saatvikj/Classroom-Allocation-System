package backend;

import java.util.ArrayList;
import java.util.Map;

import exceptions.NoResultFoundException;

public class Student extends User{
	
	private Map<Slot, Course> timetable;
	private ArrayList<Request> allRequests;
	
	public Student(String name, String emailID, String encryptedPassword, String typeOfUser) {
		super(name, emailID, encryptedPassword, typeOfUser);
		// TODO Auto-generated constructor stub
	}

	public Map<Slot, Course> getTimetable() {
		return timetable;
	}

	public void setTimetable(Map<Slot, Course> timetable) {
		this.timetable = timetable;
	}

	public ArrayList<Request> getAllRequests() {
		return allRequests;
	}

	public void setAllRequests(ArrayList<Request> allRequests) {
		this.allRequests = allRequests;
	}

	@Override
	public void makeBooking(ClassRoom reqRoom, Slot reqSlot)
	{
		
	}
	
	public void addToTimeTable(Slot _slot, Course _course)
	{
		
	}
	
	public ArrayList<Course> giveRelevantCourses(String[] keywords, boolean audit) throws NoResultFoundException
	{
		return null;
	}
	
	public void viewTimeTable()
	{
		
	}
	
	public void cancelRequest(Slot timeSlot)
	{
		
	}
	
	public void populateNotifications()
	{
		
	}
	


}
