package backend;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import exceptions.AlreadyRegisteredUserException;
import exceptions.InvalidEmailException;
import exceptions.PasswordNotMatchException;
import exceptions.WeakPasswordException;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class SignUp {

	private String name;
	private String emailID;
	private String typeOfUser;
	private String password;
	private String confirmPassword;
	private ArrayList<User> listOfUsers = new ArrayList<User>();
	private ArrayList<Course> allCourses = new ArrayList<Course>();

	/**
	 * Getter function that returns the name of the user.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter function that sets the name of the user
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter function that returns the email id of a user
	 * 
	 * @return emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * 
	 * Setter function to set the email id
	 * 
	 * @param emailID
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * Getter function that returns the type of user
	 * 
	 * @return typeOfUser
	 */
	public String getTypeOfUser() {
		return typeOfUser;
	}

	/**
	 * Setter function that sets the type of user
	 * 
	 * @param typeOfUser
	 */
	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	/**
	 * Getter method that returns the password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method that sets the value of password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter method to get the confirmedPassword
	 * 
	 * @return confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Setter method to set the confirmedPassword
	 * 
	 * @param confirmPassword
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Getter method that returns the list of users
	 * 
	 * @return listOfUsers
	 */
	public ArrayList<User> getListOfUsers() {
		return listOfUsers;
	}

	/**
	 * Setter method that sets the value of listof users
	 * 
	 * @param listOfUsers
	 */
	public void setListOfUsers(ArrayList<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	/**
	 * 
	 * Getter method to get the list of all courses
	 * 
	 * @return allCourses
	 */
	public ArrayList<Course> getAllCourses() {
		return allCourses;
	}

	/**
	 * Setter method that sets the value of allCourses
	 * 
	 * @param allCourses
	 */
	public void setAllCourses(ArrayList<Course> allCourses) {
		this.allCourses = allCourses;
	}

	/**
	 * It checks whether the email ID is a valid IIIT Delhi email ID or not.
	 * Check after @ it is iiitd.ac.in or not (Substring) Returns true if it is
	 * a valid email ID, else returns false meghna16056@iiitd.ac.in
	 * 
	 * @return boolean
	 * @throws InvalidEmailException
	 */
	public boolean validateEmail() throws InvalidEmailException {

		if (this.getEmailID().contains("@")) {

			if (this.getEmailID().split("\\@")[1].split("\\.")[0].equals("iiitd")) {
				return true;
			} else {
				throw new InvalidEmailException("Not a valid IIITD Email ID");

			}
		}
		throw new InvalidEmailException("Not a valid IIITD Email ID");

	}

	/**
	 * 
	 * It checks whether the entered password is strong enough or not. Length >=
	 * 8 At Least one digit and at least one alphabet Returns true when a
	 * password is strong, false when it is not
	 * 
	 * @return boolean
	 * @throws WeakPasswordException
	 */
	public boolean checkStrongPassword() throws WeakPasswordException {

		String pass = this.password;
		if (pass.length() >= 8) {
			boolean flag = false;
			for (int i = 0; i < pass.length(); i++) {
				if (Character.isDigit(pass.charAt(i))) {
					flag = true;
					break;
				}
			}
			if (flag == true) {
				boolean check = false;
				for (int i = 0; i < pass.length(); i++) {
					if (Character.isUpperCase(pass.charAt(i)) || Character.isLowerCase(pass.charAt(i))) {
						check = true;
						break;
					}
				}
				if (check == true) {
					return true;
				}
			}

		}

		throw new WeakPasswordException(
				"Password is weak. It must contain at least 8 characters, at least one digit and one alphabet.");
	}

	/**
	 * 
	 * 
	 * It encrypts the user’s password (safety feature) Encryption function (to
	 * be thought of) Returns the encrypted password
	 * 
	 * @return
	 */
	public String encryptPassword() {

		return md5(this.password);
	}

	/**
	 * 
	 * md5 encryption method.
	 * 
	 * @param input
	 * @return string
	 */
	public static String md5(String input) {

		String md5 = null;
		if (null == input)
			return null;
		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");

			// Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());

			// Converts message digest value in base 16 (hex)
			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}

	/**
	 *
	 * If the user is a faculty, iterate over the entire courses objects and see
	 * if that faculty has any courses taught, if yes then append the course
	 * objects to the list of courses in the faculty.
	 * 
	 * @param facultyName
	 * @return facCourses
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ArrayList<Course> addCoursesToFaculty(String facultyName) throws ClassNotFoundException, IOException {

		deserializeCourses();
		ArrayList<Course> facCourses = new ArrayList<Course>();
		for (int i = 0; i < allCourses.size(); i++) {
			if (allCourses.get(i).getInstructor().equalsIgnoreCase(facultyName)) {
				facCourses.add(allCourses.get(i));
			}
		}

		return facCourses;
	}

	/**
	 * 
	 * 
	 * First deserializes the file to populate array list,adds newUser to it and
	 * then serializes it back again.
	 * 
	 * @param newUser
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void addUserToDatabase(User newUser) throws FileNotFoundException, ClassNotFoundException, IOException {

		deserializeUsers();
		listOfUsers.add(newUser);
		serializeUsers();
	}

	/**
	 * If confirmed and entered password match, it returns true, else returns
	 * false
	 * 
	 * @return boolean
	 * @throws PasswordNotMatchException
	 */
	public boolean passwordMatch() throws PasswordNotMatchException {

		if (this.getPassword().equals(this.getConfirmPassword())) {
			return true;
		} else {
			throw new PasswordNotMatchException("Passwords don't match");
		}
	}

	/**
	 * This method checks if the user is already registered or not
	 * 
	 * @return boolean
	 * @throws AlreadyRegisteredUserException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public boolean alreadyRegistered()
			throws AlreadyRegisteredUserException, FileNotFoundException, ClassNotFoundException, IOException {

		deserializeUsers();
		for (int i = 0; i < listOfUsers.size(); i++) {
			if (listOfUsers.get(i).getEmailID().equals(emailID)) {
				throw new AlreadyRegisteredUserException("User is already registered!");
			}
		}

		return false;

	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void deserializeUsers() throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream in = null;

		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/users.txt"));
			User user;

			try {

				while (true) {
					user = (User) in.readObject();
					listOfUsers.add(user);
				}

			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {

		} finally {

			if (in != null) {
				in.close();
			} else {
				listOfUsers = new ArrayList<User>();
			}

		}

	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeUsers() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/users.txt"));

			for (int i = 0; i < listOfUsers.size(); i++) {
				User newUser = listOfUsers.get(i);
				out.writeObject(newUser);
			}

		} finally {

			out.close();

		}

	}

	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void deserializeCourses() throws IOException, ClassNotFoundException {
		/*
		 * Deserialize the file listofcourses.txt into the arraylist
		 */

		ObjectInputStream in = null;

		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/courses.txt"));
			Course course;

			try {

				while (true) {
					course = (Course) in.readObject();
					allCourses.add(course);
				}

			} catch (EOFException e) {

			}

		} finally {

			in.close();

		}

	}

}
