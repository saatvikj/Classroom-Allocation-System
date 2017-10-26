package backend;

import java.util.ArrayList;

public class SignUp {

	private String name;
	private String emailID;
	private String typeOfUser;
	private String passwword;
	private String confirmPassword;
	private ArrayList<User> listOfUsers;
	private ArrayList<Course> allCourses;

	public boolean validateEmail() throws InvalidEmailException {
		/*
		 * It checks whether the email ID is a valid IIIT Delhi email ID or not.
		 * Check after @ it is iiitd.ac.in or not (Substring) Returns true if it
		 * is a valid email ID, else returns false
		 */
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	public String getPasswword() {
		return passwword;
	}

	public void setPasswword(String passwword) {
		this.passwword = passwword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public ArrayList<User> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(ArrayList<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	public ArrayList<Course> getAllCourses() {
		return allCourses;
	}

	public void setAllCourses(ArrayList<Course> allCourses) {
		this.allCourses = allCourses;
	}

	public boolean checkStrongPassword() throws WeakPasswordException {
		/*
		 * It checks whether the entered password is strong enough or not.
		 * Length >= 8 At Least one digit and at least one alphabet Returns true
		 * when a password is strong, false when it is not
		 */
		return true;
	}

	public String encryptPassword() {
		/*
		 * It encrypts the user’s password (safety feature) Encryption function
		 * (to be thought of) Returns the encrypted password
		 */
	}

	public void addCoursesToFaculty() {
		/*
		 * If the user is a faculty, iterate over the entire courses objects and
		 * see if that faculty has any courses taught, if yes then append the
		 * course objects to the list of courses in the faculty.
		 */
	}

	public void addUserToDatabase(User newUser) {
		/*
		 * First deserializes the file to populate array list,adds newUser to it
		 * and then serializes it back again.
		 */
	}

	public boolean passwordMatch() throws PasswordNotMatchException {
		/*
		 * If confirmed and entered password match, it returns true, else
		 * returns false
		 */
	}

	public void deserializeUsers() {
		/*
		 * Deserialize the file listofusers.txt into the arraylist
		 */
	}

	public void serializeUsers() {
		/*
		 * Serialize the arraylist into the file listofusers.txt.
		 */
	}

	public void deserializeCourses() {
		/*
		 * Deserialize the file listofcourses.txt into the arraylist
		 */
	}

}
