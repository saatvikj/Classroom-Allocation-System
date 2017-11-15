package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import backend.ClassRoom;
import backend.Course;
import backend.Slot;
import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class StudentHomeUI {

	public Student currStudent;
	private ArrayList<User> listOfUsers = new ArrayList<User>();

	@FXML
	private Label nameStudent;

	@FXML
	private Label emailStudent;

	/**
	 * This sets the name and email ID of the current logged in student.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void populate() throws FileNotFoundException, IOException, ClassNotFoundException {
		deserializeUsers();
		currStudent.deserializeRequests();
		serializeUsers();
		nameStudent.setText(currStudent.getName());
		emailStudent.setText(currStudent.getEmailID());

	}

	/**
	 * Handler for mouse click of notifications tab.
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
			controller.currUser = currStudent;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handler for mouse click of view booked rooms
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
			controller.currUser = currStudent;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handler for mouse click of cancel booking
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
			controller.currUser = currStudent;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handler for mouse click of make request
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void makeRequest(MouseEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MakeRequest.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			MakeRequestUI controller = loader.<MakeRequestUI>getController();
			controller.currUser = currStudent;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handler for mouse click of make time table
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void createTimeTable(MouseEvent event) throws ClassNotFoundException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTT1.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			MakeTimeTableUI controller = loader.<MakeTimeTableUI>getController();
			controller.currStudent = currStudent;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handler for mouse click of view time table
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void viewTimeTable(MouseEvent event) throws ClassNotFoundException {

		if (currStudent.getTimetable() != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimeTable.fxml"));
				Stage stage = new Stage();
				stage.setTitle("IIIT Delhi");
				stage.setScene(new Scene(loader.load(), 800, 600));
				ViewTimeTableUI controller = loader.<ViewTimeTableUI>getController();
				controller.currStudent = currStudent;
				controller.populate();
				stage.show();

				((Node) (event.getSource())).getScene().getWindow().hide();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("Your time-table is empty!");
			alert.showAndWait();
		}

	}

	/**
	 * Handler for mouse click of cancel request
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void cancelRequest(MouseEvent event) throws ClassNotFoundException {

		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CancelRequest.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			CancelRequestUI controller = loader.<CancelRequestUI>getController();
			controller.currUser = currStudent;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handler for mouse click of logout button
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
	 * This serializes the list of users back into the database
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeUsers() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/users.txt"));

			for (int i = 0; i < listOfUsers.size(); i++) {

				if (listOfUsers.get(i).getEmailID().equals(currStudent.getEmailID())) {
					out.writeObject(currStudent);
				} else {

					User newUser = listOfUsers.get(i);
					out.writeObject(newUser);
				}
			}

		} finally {

			out.close();

		}

	}

	/**
	 * This deserializes the user into the class
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public void deserializeUsers() throws IOException, ClassNotFoundException, FileNotFoundException {

		ObjectInputStream in = null;

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
	 * Handler for mouse click of view all course
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
			controller.currUser = currStudent;
			controller.myCourses = false;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
