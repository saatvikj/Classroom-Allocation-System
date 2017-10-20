package application;

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

public class HomePageController {
	

	@FXML
	private void openSignUp(ActionEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openLogin(ActionEvent event) {

		Parent root;
		try {
			
			FXMLLoader ldr = new FXMLLoader(getClass().getResource("Login.fxml"));
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



	@FXML
	private void showNotifications(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("NotificationsUI.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void viewBookedRooms(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("BookedRoomRecords.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void cancelBooking(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("CancelBooking.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void makeBooking(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("CheckRoomAvail1.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void makeRequest(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("MakeRequest.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void handleRequests(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("HandleRequests.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void confirmBooking(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("CheckRoomAvail2.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	@FXML
	private void createTimeTable(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("CreateTT1.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void viewTimeTable(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("TimeTable.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void cancelRequest(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("CancelRequest.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void searchResults(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("CreateTT2.fxml"));
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

