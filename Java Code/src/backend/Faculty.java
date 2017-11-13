package backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		for(int i = 0; i < coursesTaught.size(); i++){
			Map<Slot, List<ClassRoom>> courseMap = coursesTaught.get(i).getCourseTimeTable();
			for(Map.Entry<Slot, List<ClassRoom>> it : courseMap.entrySet()){
				Slot key = it.getKey();
				List<ClassRoom> val = it.getValue();
				String currday = key.getDay();
				if(currday.equals(week[day-1]) && key.getPurpose().equals(Slot.TYPES[0]))
				{
					notifications.add("You have a "  + coursesTaught.get(i).getAcronym().toUpperCase() + " class in " + val.get(0).getRoomNumber().toUpperCase() + " at " + key.getStartTime());
				}
			}
				
		}
			
		
		return notifications;

	}


}
