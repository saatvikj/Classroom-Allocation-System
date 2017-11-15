package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import backend.Admin;
import backend.Faculty;
import backend.Login;
import backend.Student;
import exceptions.InvalidEmailException;
import exceptions.PasswordNotMatchException;
import exceptions.UnregisteredUserExcpetion;
import exceptions.WrongPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class LoginUI {

	@FXML
	private TextField userEmail;

	@FXML
	private PasswordField userPassword;

	/**
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void login(ActionEvent event) throws ClassNotFoundException, FileNotFoundException, IOException {

		String email = userEmail.getText();
		String password = userPassword.getText();

		boolean validity = checkEmptiness(email, password);
		if (!validity) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("At least one of the fields is empty, try again.");
			alert.showAndWait();
		}

		if (validity) {
			Login page = new Login();
			page.setEmailID(email);
			page.setPassword(password);

			boolean allResults = false;
			try {

				boolean emailResult = page.validateEmail();
				try {
					boolean registeredResult = page.checkIfRegistered();
					try {
						boolean passwordResult = page.checkPassword();
						allResults = true;
					} catch (WrongPasswordException e) {
						// TODO: handle exception
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error!");
						alert.setHeaderText(null);
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}

				} catch (UnregisteredUserExcpetion e) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error!");
					alert.setHeaderText(null);
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}

			} catch (InvalidEmailException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}

			if (allResults) {

				try {
					String typeOfUser = page.getLoggedInUser().getTypeOfUser();
					String path;

					if (typeOfUser.equals("Admin")) {
						path = "/fxml/AdminHome.fxml";
					} else if (typeOfUser.equals("Faculty")) {
						path = "/fxml/FacultyHome.fxml";
					} else {
						path = "/fxml/StudentHome.fxml";
					}
					Parent root;
					try {

						FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
						Stage stage = new Stage();
						stage.setTitle("IIIT Delhi");
						stage.setScene(new Scene((Parent) loader.load(), 800, 600));
						if (typeOfUser.equals("Admin")) {
							Admin admin = (Admin) page.getLoggedInUser();
							AdminHomeUI controller = loader.<AdminHomeUI>getController();
							controller.currAdmin = admin;
							controller.populate();
						} else if (typeOfUser.equals("Faculty")) {
							Faculty faculty = (Faculty) page.getLoggedInUser();
							FacultyHomeUI controller = loader.<FacultyHomeUI>getController();
							controller.currFaculty = faculty;
							controller.populate();
						} else {
							Student student = (Student) page.getLoggedInUser();
							StudentHomeUI controller = loader.<StudentHomeUI>getController();
							controller.currStudent = student;
							controller.populate();
						}
						stage.show();
						((Node) (event.getSource())).getScene().getWindow().hide();

					} catch (IOException e) {
						e.printStackTrace();
					}

				} catch (UnregisteredUserExcpetion e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error!");
					alert.setHeaderText(null);
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}

			}

		}

	}

	/**
	 * 
	 * @param enteredEmail
	 * @param enteredPassword
	 * @return boolean
	 */
	public boolean checkEmptiness(String enteredEmail, String enteredPassword) {

		if (enteredEmail.length() == 0 || enteredPassword.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @param event
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage.fxml"));

			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
