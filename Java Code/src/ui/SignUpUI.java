package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import backend.Admin;
import backend.Faculty;
import backend.SignUp;
import backend.Student;
import backend.User;
import exceptions.AlreadyRegisteredUserException;
import exceptions.InvalidEmailException;
import exceptions.PasswordNotMatchException;
import exceptions.WeakPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class SignUpUI {

	@FXML
	private TextField userName;

	@FXML
	private TextField userEmail;

	@FXML
	private TextField userPass;

	@FXML
	private TextField userConfirmPass;

	@FXML
	private MenuButton userType;

	boolean flag = false;

	/**
	 * Handler for selection of admin in drop down
	 * @param event: The event
	 */
	@FXML
	public void admin(ActionEvent event) {

		for (MenuItem item : userType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Admin")) {
				chk.setSelected(true);
				flag = true;
				userType.setText("Admin");
			}

		}
		for (MenuItem it : userType.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Admin")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}

	}

	/**
	 * Handler for selection of faculty in drop down
	 * @param event: The event
	 */
	@FXML
	public void faculty(ActionEvent event) {

		for (MenuItem item : userType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Faculty")) {
				chk.setSelected(true);
				userType.setText("Faculty");
			}

		}
		for (MenuItem it : userType.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Faculty")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}

	}

	/**
	 * Handler for selection of student in the dropdown
	 * @param event: The event
	 */
	@FXML
	public void student(ActionEvent event) {

		for (MenuItem item : userType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Student")) {
				chk.setSelected(true);
				userType.setText("Student");
			}

		}
		for (MenuItem it : userType.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Student")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}

	}

	/**
	 * Handler for mouse click of sign up button
	 * @param event: The mouse event
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void openLogin(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {

		String name = userName.getText();
		String email = userEmail.getText();
		String password = userPass.getText();
		String confirmPass = userConfirmPass.getText();
		String typeUser = "";

		for (MenuItem item : userType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.isSelected()) {
				typeUser = chk.getText();
			}

		}

		boolean validity = checkEmptiness(name, email, password, confirmPass);
		if (!validity) {
			generateAlert("Error!","At least one of the fields is empty, try again.");
		}

		if (validity) {
			SignUp page = new SignUp();
			page.setName(name);
			page.setEmailID(email);
			page.setPassword(password);
			page.setConfirmPassword(confirmPass);
			page.setTypeOfUser(typeUser);
			boolean allResults = false;

			try {
				boolean registeredResult = page.alreadyRegistered();
				boolean emailResult = page.validateEmail();

				try {
					boolean matchResult = page.passwordMatch();

					try {
						boolean strongResult = page.checkStrongPassword();
						allResults = true;

					} catch (WeakPasswordException e) {
						generateAlert("Error!",e.getMessage());
					}

				} catch (PasswordNotMatchException e) {
					generateAlert("Error!",e.getMessage());
				}

			} catch (InvalidEmailException e) {
				generateAlert("Error!",e.getMessage());

			} catch (AlreadyRegisteredUserException e) {
				generateAlert("Error!",e.getMessage());
			}

			if (allResults) {

				String encryptedPass = page.encryptPassword();
				if (typeUser.equals("Admin")) {
					Admin user = new Admin(name, email, encryptedPass, typeUser);
					page.addUserToDatabase((user));

				} else if (typeUser.equals("Faculty")) {
					Faculty user = new Faculty(name, email, encryptedPass, typeUser);
					user.setCoursesTaught(page.addCoursesToFaculty(name));
					page.addUserToDatabase(user);
				} else {
					Student user = new Student(name, email, encryptedPass, typeUser);
					page.addUserToDatabase(user);
				}

				Parent root;
				try {

					FXMLLoader ldr = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
					root = ldr.load();
					Stage stage = new Stage();
					stage.setTitle("IIIT Delhi");
					stage.setScene(new Scene(root, 800, 600));
					stage.show();

					((Node) (event.getSource())).getScene().getWindow().hide();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * This checks whethe all fields are filled or not
	 * @param enteredName
	 * @param enteredEmail
	 * @param enteredPassword
	 * @param enteredConfirmPass
	 * @return boolean: returns true if all fields are filled, else returns false
	 */
	public boolean checkEmptiness(String enteredName, String enteredEmail, String enteredPassword,
			String enteredConfirmPass) {

		if (enteredName.length() == 0 || enteredEmail.length() == 0 || enteredPassword.length() == 0
				|| enteredConfirmPass.length() == 0) {
			return false;
		} else {
			return true;
		}

	}


	/**
	 * This function is used to display an alert with the given 
	 * specifications.
	 * @param title: The title of the alert
	 * @param message: The content of the alert
	 */
	public generateAlert(String title, String message) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(title);
				alert.setHeaderText(null);
				alert.setContentText(message);
				alert.showAndWait();

	}	

}
