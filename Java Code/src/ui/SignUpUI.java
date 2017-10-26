package ui;

import java.io.IOException;

import backend.InvalidEmailException;
import backend.PasswordNotMatchException;
import backend.SignUp;
import backend.WeakPasswordException;
import javafx.application.Application;
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
		
	@FXML
	public void admin(ActionEvent event) {
		
		for(MenuItem item: userType.getItems()) {
			
			CheckMenuItem chk = (CheckMenuItem) item;
			if(chk.getText().equals("Admin")) {
				chk.setSelected(true);
				userType.setText("Admin");
			}
			
		}
		
	}
	
	@FXML
	public void faculty(ActionEvent event) {
		
		for(MenuItem item: userType.getItems()) {
			
			CheckMenuItem chk = (CheckMenuItem) item;
			if(chk.getText().equals("Faculty")) {
				chk.setSelected(true);
			}
			
		}
		
	}
	
	@FXML
	public void student(ActionEvent event) {
		
		for(MenuItem item: userType.getItems()) {
			
			CheckMenuItem chk = (CheckMenuItem) item;
			if(chk.getText().equals("Student")) {
				chk.setSelected(true);
			}
			
		}
		
	}
	
	@FXML
	public void openLogin(ActionEvent event) {

		String name = userName.getText();
		String email = userEmail.getText();
		String password = userPass.getText();
		String confirmPass = userConfirmPass.getText();

		for(MenuItem item: userType.getItems()) {
		
			CheckMenuItem chk = (CheckMenuItem) item;
			if(chk.isSelected()) {
				System.out.println(chk.getText());
			}
			
		}
		
		boolean validity = checkEmptiness(name, email, password, confirmPass);
		if (!validity) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("At least one of the fields is empty, try again.");
			alert.showAndWait();
		}

		if (validity) {
			SignUp page = new SignUp();
			page.setName(name);
			page.setEmailID(email);
			page.setPassword(password);
			page.setConfirmPassword(confirmPass);
			boolean allResults = false;

			try {
				boolean emailResult = page.validateEmail();

				try {
					boolean matchResult = page.passwordMatch();

					try {
						boolean strongResult = page.checkStrongPassword();
						allResults = true;

					} catch (WeakPasswordException e) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error!");
						alert.setHeaderText(null);
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}

				} catch (PasswordNotMatchException e) {

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

				
				
				 Parent root;
				 try {
				
				 FXMLLoader ldr = new
				 FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
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

	public boolean checkEmptiness(String enteredName, String enteredEmail, String enteredPassword,
			String enteredConfirmPass) {

		if (enteredName.length() == 0 || enteredEmail.length() == 0 || enteredPassword.length() == 0
				|| enteredConfirmPass.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

}
