package backend;

import java.util.ArrayList;

public class Faculty extends User {

	private ArrayList<Course> coursesTaught;

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
	public void populateNotifications() {

	}


}
