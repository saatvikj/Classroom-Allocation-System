package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import backend.User;
import exceptions.WeakPasswordException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class ForgotPasswordUI {

	public String emailID;
	private User currUser;
	private ArrayList<User> listOfUsers;

	@FXML
	private Label helloLabel;

	@FXML
	private Label securityQuestion;

	@FXML
	private TextField securityAnswer;

	@FXML
	private PasswordField newPassword;

	@FXML
	private PasswordField confirmNewPassword;

	/**
	 * This function sets the security question for the user with the entered
	 * email ID and keeps its details handy for checking.
	 * 
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void populate() throws ClassNotFoundException, FileNotFoundException, IOException {

		deserializeUsers();

		for (int i = 0; i < listOfUsers.size(); i++) {
			if (listOfUsers.get(i).getEmailID().equals(emailID)) {
				helloLabel.setText("Hello, " + listOfUsers.get(i).getName() + "!");
				securityQuestion.setText(listOfUsers.get(i).getSecurityQuestion());
				currUser = listOfUsers.get(i);
			}
		}

	}

	/**
	 * This handler for reset password button, it makes necessary checks and
	 * updates password if all clear.
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void resetPasswordButtonClicked(MouseEvent event) throws FileNotFoundException, IOException {

		String answer = securityAnswer.getText();
		String newPass = newPassword.getText();
		String confPass = confirmNewPassword.getText();
		boolean validity = checkEmptiness(answer, newPass, confPass);

		if (validity) {

			if (answer.equals(currUser.getSecurityAnswer())) {

				if (newPass.equals(confPass)) {

					try {

						boolean strongPass = currUser.checkStrongPassword(newPass);
						currUser.setEncryptedPassword(currUser.encryptPassword(confPass));
						serializeUsers();
						generateAlert("Success!", "Password changed successfully!");
						securityAnswer.setText("");
						newPassword.setText("");
						confirmNewPassword.setText("");
					} catch (WeakPasswordException e) {
						generateAlert("Error!", e.getMessage());
					}
				} else {
					generateAlert("Error!", "Passwords don't match!");
				}

			} else {
				generateAlert("Error!", "Answers don't match, try again!");
			}

		} else

		{
			generateAlert("Error!", "At least one of the fields is empty, try again.");
		}

	}

	/**
	 * This method deserializes the users.txt file into allUsers arraylist
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public void deserializeUsers() throws IOException, ClassNotFoundException, FileNotFoundException {

		/*
		 * Deserializes the list of registered users into the ArrayList so that
		 * checking can be done.
		 * 
		 */
		ObjectInputStream in = null;
		listOfUsers = new ArrayList<User>();
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
	 * The handler for mouse click of back button
	 * 
	 * @param event
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));

			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
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
				if (newUser.getEmailID().equals(currUser.getEmailID())) {
					out.writeObject(currUser);
				} else {
					out.writeObject(newUser);
				}
			}

		} finally {

			out.close();

		}

	}

	/**
	 * This checks whether all the fields are filled or not
	 * 
	 * @param answer
	 * @param newPass
	 * @param confPass
	 * @return boolean: returns true if all fields are filled, else returns
	 *         false
	 */
	private boolean checkEmptiness(String answer, String newPass, String confPass) {
		if (answer.length() == 0 || newPass.length() == 0 || confPass.length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * This function is used to display an alert with the given specifications.
	 * 
	 * @param title:
	 *            The title of the alert
	 * @param message:
	 *            The content of the alert
	 */
	public void generateAlert(String title, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();

	}

}
