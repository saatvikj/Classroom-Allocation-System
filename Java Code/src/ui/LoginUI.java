package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import backend.Login;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginUI {

	@FXML
	private TextField userEmail;

	@FXML
	private TextField userPassword;

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
					
					Parent root;
					try {
						
						System.out.println("Login successful");
						root = FXMLLoader.load(getClass().getResource("/fxml/StudentHome.fxml"));
						Stage stage = new Stage();
						stage.setTitle("IIIT Delhi");
						stage.setScene(new Scene(root, 800, 600));
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

	public boolean checkEmptiness(String enteredEmail, String enteredPassword) {

		if (enteredEmail.length() == 0 || enteredPassword.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

}
