package ui;

import java.io.IOException;

import backend.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class AdminHomeUI {

	public Admin currAdmin;

	@FXML
	private Label nameAdmin;

	@FXML
	private Label emailAdmin;

	/**
	 * This function sets the name and the email ID of the current logged in
	 * admin.
	 */
	public void populate() {

		nameAdmin.setText(currAdmin.getName());
		emailAdmin.setText(currAdmin.getEmailID());
	}

	/**
	 * This is handler for mouse click of view booked rooms
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void viewBookedRooms(MouseEvent event) {

		Parent root;
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookedRoomRecords.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			BookedRoomsUI controller = loader.<BookedRoomsUI>getController();
			controller.currUser = currAdmin;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of cancel room booking
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void cancelBooking(MouseEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CancelBooking.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			CancelBooking controller = loader.<CancelBooking>getController();
			controller.currUser = currAdmin;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of make booking
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void makeBooking(MouseEvent event) {

		Parent root;
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckRoomAvail1.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			CheckRoomAvail1UI controller = loader.<CheckRoomAvail1UI>getController();
			controller.currUser = currAdmin;
			controller.onlyAvailability = false;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of handle reqests
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException:
	 *             Can arise while deserializing requests
	 */
	@FXML
	private void handleRequests(MouseEvent event) throws ClassNotFoundException {

		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HandleRequests.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			HandleRequestsUI controller = loader.<HandleRequestsUI>getController();
			controller.currAdmin = currAdmin;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of notifications tab
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException:
	 *             Can arise while deserializing requests.
	 */
	@FXML
	private void showNotifications(MouseEvent event) throws ClassNotFoundException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NotificationsUI.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			NotificationsUI controller = loader.<NotificationsUI>getController();
			controller.currUser = currAdmin;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of logout
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void logout(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/HomePage.fxml"));
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
	 * This is handler for mouse click of show all courses.
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException:
	 *             Can arise while deserializing courses.
	 */
	@FXML
	private void showCourses(MouseEvent event) throws ClassNotFoundException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewAllCourses.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			ViewAllCoursesUI controller = loader.<ViewAllCoursesUI>getController();
			controller.currUser = currAdmin;
			controller.myCourses = false;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
