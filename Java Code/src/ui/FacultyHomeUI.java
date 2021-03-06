package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import backend.Admin;
import backend.Faculty;
import backend.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class FacultyHomeUI {

	public Faculty currFaculty;

	@FXML
	private Label nameFaculty;

	@FXML
	private Label emailFaculty;

	/**
	 * This sets the name and email ID of the current logged in faculty.
	 */
	public void populate() {
		nameFaculty.setText(currFaculty.getName());
		emailFaculty.setText(currFaculty.getEmailID());
	}

	/**
	 * The handler for mouse click of notifications tab
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void showNotifications(MouseEvent event) throws ClassNotFoundException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NotificationsUI.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			NotificationsUI controller = loader.<NotificationsUI>getController();
			controller.currUser = currFaculty;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The handler for mouse click of view booked rooms
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
			controller.currUser = currFaculty;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The handler for mouse click of cancel booking
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
			controller.currUser = currFaculty;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The handler of the mouse click of make booking
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
			controller.currUser = currFaculty;
			controller.onlyAvailability = false;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The handler for mouse click of logout button
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
	 * The handler of mouse click of show all courses button
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void showCourses(MouseEvent event) throws ClassNotFoundException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewAllCourses.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			ViewAllCoursesUI controller = loader.<ViewAllCoursesUI>getController();
			controller.currUser = currFaculty;
			controller.myCourses = false;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The handler of mouse click of show my courses button
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void showMyCourses(MouseEvent event) throws ClassNotFoundException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewAllCourses.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			ViewAllCoursesUI controller = loader.<ViewAllCoursesUI>getController();
			controller.currUser = currFaculty;
			controller.myCourses = true;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}