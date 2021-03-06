package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class HomePageUI {

	/**
	 * The handler for mouse click of sign up button
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void openSignUp(ActionEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The handler for mouse click of sign up button
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void openLogin(ActionEvent event) {

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
