package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
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
	public void openLogin(ActionEvent event) {

		String name = userName.getText();
		String email = userEmail.getText();
		String password = userPass.getText();
		String confirmPass = userConfirmPass.getText();

		boolean validity = checkEmptiness(name, email, password, confirmPass);
		if(!validity) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("At least one of the fields is empty, try again.");
			alert.showAndWait();
		}

		
//		Parent root;
//		try {
//
//			FXMLLoader ldr = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
//			root = ldr.load();
//			Stage stage = new Stage();
//			stage.setTitle("IIIT Delhi");
//			stage.setScene(new Scene(root, 800, 600));
//			stage.show();
//
//			((Node) (event.getSource())).getScene().getWindow().hide();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
