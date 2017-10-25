package backend;

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

	}

	public boolean checkIfRegistered() throws UnregisteredUserExcpetion {

		/*
		 * It checks if that emailID is of a registered user or not. Returns
		 * true if it is, else returns false.
		 */
	}

	public boolean checkPassword() throws WrongPasswordException {

		/*
		 * It checks if the entered password is correct or not. Returns true if
		 * it is correct, else returns false.
		 */

	}
	
	public void deserializeUsers() {
		
		/*
		 * Deserializes the list of registered users into the ArrayList so that checking can be done.
		 * 
		 */
		
	}

}
