package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SignUp {

	private String name;
	private String emailID;
	private String typeOfUser;
	private String password;
	private String confirmPassword;
	private ArrayList<User> listOfUsers;
	private ArrayList<Course> allCourses;

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

	public String getPassword() {
		return password;
	}

	public void setPasswword(String password) {
		this.password = password;
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

	public boolean validateEmail() throws InvalidEmailException {
		/*
		 * It checks whether the email ID is a valid IIIT Delhi email ID or not.
		 * Check after @ it is iiitd.ac.in or not (Substring) Returns true if it
		 * is a valid email ID, else returns false meghna16056@iiitd.ac.in
		 */

		if (this.getEmailID().split("\\@")[2].split("\\.")[0].equals("iiitd")) {
			return true;
		} else {
			throw new InvalidEmailException("Not a valid IIITD emailID");

		}
	}

	public boolean checkStrongPassword() throws WeakPasswordException {
		/*
		 * It checks whether the entered password is strong enough or not.
		 * Length >= 8 At Least one digit and at least one alphabet Returns true
		 * when a password is strong, false when it is not
		 */
		
		String pass = this.password;
		if(pass.length() >= 8)
		{
			boolean flag = false;
			for(int i = 0; i < pass.length(); i++)
			{
				if(Character.isDigit(pass.charAt(i)))
				{
					flag = true;
					break;
				}
			}
			if(flag == true)
			{
				boolean check = false;
				for(int i = 0; i < pass.length(); i++)
				{
					if(Character.isUpperCase(pass.charAt(i)) || Character.isLowerCase(pass.charAt(i)))
					{
						check = true;
						break;
					}
				}
				if(check == true)
				{
					return true;
				}
			}
				
		}
		
		return false;
	}

	public String encryptPassword() {
		/*
		 * It encrypts the user’s password (safety feature) Encryption function
		 * (to be thought of) Returns the encrypted password
		 */
		
		return md5(this.password);
	}
	
	public static String md5(String input) {
			
			String md5 = null;
			if(null == input) return null;
			try {
			//Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
	
			//Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
	
			//Converts message digest value in base 16 (hex) 
			md5 = new BigInteger(1, digest.digest()).toString(16);
	
			} catch (NoSuchAlgorithmException e) {
	
				e.printStackTrace();
			}
			return md5;
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
		return (this.getPassword().equals(this.getConfirmPassword()));
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
