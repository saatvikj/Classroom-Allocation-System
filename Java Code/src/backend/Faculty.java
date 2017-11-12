package backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Faculty extends User {

	private ArrayList<Course> coursesTaught;
	private String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	public Faculty(String name, String emailID, String encryptedPassword, String typeOfUser) {
		super(name, emailID, encryptedPassword, typeOfUser);
	}

	public ArrayList<Course> getCoursesTaught() {
		return coursesTaught;
	}

	public void setCoursesTaught(ArrayList<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}

	@Override
	public ArrayList<String> populateNotifications() {
		
		ArrayList<String> notifications =  super.populateNotifications();
//		Calendar cal = Calendar.getInstance();
//		int day = cal.get(Calendar.DAY_OF_WEEK);
//		for(int i = 0; i < coursesTaught.size(); i++){
//			Map<Slot, ClassRoom> courseMap = coursesTaught.get(i).getCourseTimeTable();
//			for(Map.Entry<Slot, ClassRoom> it : courseMap.entrySet()){
//				Slot key = it.getKey();
//				ClassRoom val = it.getValue();
//				String currday = key.getDay();
//				if(currday.equals(week[day-1]))
//				{
//					notifications.add("You have a "  + coursesTaught.get(i).getAcronym().toUpperCase() + " class in " + val.getRoomNumber().toUpperCase() + " at " + key.getStartTime());
//				}
//			}
//				
//		}
//			
		
		return notifications;

	}


}
