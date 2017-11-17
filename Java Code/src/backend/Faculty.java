package backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Faculty class that extends the User class
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class Faculty extends User {

	private ArrayList<Course> coursesTaught;
	private String[] week = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	/**
	 * Parametrized constructor for faculty object
	 * 
	 * @param name
	 * @param emailID
	 * @param encryptedPassword
	 * @param typeOfUser
	 * @param securityQuestion
	 * @param securityAnswer
	 */
	public Faculty(String name, String emailID, String encryptedPassword, String typeOfUser, String securityQuestion, String securityAnswer) {
		super(name, emailID, encryptedPassword, typeOfUser, securityQuestion, securityAnswer);
	}

	/**
	 * Getter method that returns the coursesTaught arraylist
	 * 
	 * @return coursesTaught
	 */
	public ArrayList<Course> getCoursesTaught() {
		return coursesTaught;
	}

	/**
	 * Setter method that sets the coursesTaught
	 * 
	 * @param coursesTaught
	 */
	public void setCoursesTaught(ArrayList<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}

	/**
	 * This function populates the notifications of Faculty and informs him
	 * which all of his class are scheduled, where and at what time
	 * 
	 * @return notifications
	 */
	@Override
	public ArrayList<String> populateNotifications() {

		ArrayList<String> notifications = super.populateNotifications();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < coursesTaught.size(); i++) {
			Map<Slot, List<ClassRoom>> courseMap = coursesTaught.get(i).getCourseTimeTable();
			for (Map.Entry<Slot, List<ClassRoom>> it : courseMap.entrySet()) {
				Slot key = it.getKey();
				List<ClassRoom> val = it.getValue();
				String currday = key.getDay();
				if (currday.equals(week[day - 1]) && key.getPurpose().equals(Slot.TYPES[0])) {
					notifications.add("You have a " + coursesTaught.get(i).getAcronym().toUpperCase() + " class in "
							+ val.get(0).getRoomNumber().toUpperCase() + " at " + key.getStartTime());
				}
			}

		}

		return notifications;

	}

}
