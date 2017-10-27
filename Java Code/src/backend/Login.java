package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import exceptions.InvalidEmailException;
import exceptions.UnregisteredUserExcpetion;
import exceptions.WrongPasswordException;

public class Login {

	private String emailID;
	private String password;
	private String typeOfUser;
	private ArrayList<User> listOfUsers;
	private User currentUser;

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public boolean validateEmail() throws InvalidEmailException {

		/*
		 * It checks whether the email ID is a valid IIIT Delhi email ID or not.
		 * Returns true if it is valid, else returns false.
		 */
		if (this.getEmailID().contains("@")) {

			if (this.getEmailID().split("\\@")[1].split("\\.")[0].equals("iiitd")) {
				return true;
			} else {
				throw new InvalidEmailException("Not a valid IIITD Email ID");

			}
		}
		throw new InvalidEmailException("Not a valid IIITD Email ID");

	}

	public boolean checkIfRegistered() throws UnregisteredUserExcpetion {

		/*
		 * It checks if that emailID is of a registered user or not. Returns
		 * true if it is, else returns false.
		 */
		for(int i = 0; i < listOfUsers.size(); i++)
		{
			if(listOfUsers.get(i).getEmailID().equals(emailID))
			{
				return true;
			}
		}
		throw new UnregisteredUserExcpetion("Email-id is not registered. Please register first!");
	}

	public boolean checkPassword() throws WrongPasswordException {

		/*
		 * It checks if the entered password is correct or not. Returns true if
		 * it is correct, else returns false.
		 */
		
		for(int i = 0; i < listOfUsers.size(); i++)
		{
			if(listOfUsers.get(i).getEmailID().equals(emailID))
			{
				if(listOfUsers.get(i).getEncryptedPassword().equals(encryptPassword()))
				{
					return true;
				}
			}
		}
		throw new WrongPasswordException("Password entered does not match!");

	}
	
	public String encryptPassword() {
		/*
		 * It encrypts the user’s password (safety feature) Encryption
		 * function (to be thought of) Returns the encrypted password
		 */

		return md5(this.password);
	}
	
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

	public void deserializeUsers() {

		/*
		 * Deserializes the list of registered users into the ArrayList so that
		 * checking can be done.
		 * 
		 */
		

	}

	public User getLoggedInUser() throws UnregisteredUserExcpetion {

		/*
		 * It iterates through the list of users and returns the current user
		 * who just entered the details.
		 */
		for(int i = 0; i < listOfUsers.size(); i++)
		{
			if(listOfUsers.get(i).getEmailID().equals(emailID) || listOfUsers.get(i).getEncryptedPassword().equals(encryptPassword()))
			{
				return listOfUsers.get(i);
			}
		}
		throw new UnregisteredUserExcpetion("User not found");

	}

}
