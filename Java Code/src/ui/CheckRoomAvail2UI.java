package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import backend.Admin;
import backend.ClassRoom;
import backend.Faculty;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class CheckRoomAvail2UI {

	public User currUser;
	public ArrayList<ClassRoom> relevantRooms;
	public Slot requiredSlot;
	public int requiredCapacity;
	public ArrayList<User> listOfUsers;

	@FXML
	private ListView<String> relevantRoomsList;

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private Button book;

	/**
	 * This is handler for mouse click of home button
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void homeButtonClicked(MouseEvent event) {

		try {

			String path;
			if (currUser.getTypeOfUser().equals("Admin")) {
				path = "/fxml/AdminHome.fxml";
			} else {
				path = "/fxml/FacultyHome.fxml";
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));

			if (currUser.getTypeOfUser().equals("Admin")) {
				Admin admin = (Admin) currUser;
				AdminHomeUI controller = loader.<AdminHomeUI>getController();
				controller.currAdmin = admin;
				controller.populate();
			} else if (currUser.getTypeOfUser().equals("Faculty")) {
				Faculty faculty = (Faculty) currUser;
				FacultyHomeUI controller = loader.<FacultyHomeUI>getController();
				controller.currFaculty = faculty;
				controller.populate();
			}

			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is handler for mouse click of back button
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckRoomAvail1.fxml"));

			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			CheckRoomAvail1UI controller = loader.<CheckRoomAvail1UI>getController();
			controller.currUser = currUser;
			controller.onlyAvailability = false;
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
	 * This function sets the name, email ID and title of the current logged in
	 * user and populate its list with the list of relevant rooms as generated
	 * by previous page.
	 */
	public void populate() {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());
		if (relevantRooms.size() == 0) {
			relevantRoomsList.getItems().add("No Rooms found!");

		} else {
			for (int i = 0; i < relevantRooms.size(); i++) {
				relevantRoomsList.getItems().add(relevantRooms.get(i).getRoomNumber().toUpperCase() + "     Capacity : "
						+ relevantRooms.get(i).getCapacity());
			}
		}

	}

	/**
	 * This is handler for mouse click of book room button, it first goes to the
	 * bookings of the current user, adds the selected room and serializes the
	 * users back again.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void bookRoom() throws ClassNotFoundException, IOException {

		if (relevantRooms.size() != 0) {
			currUser.makeBooking(relevantRooms.get(relevantRoomsList.getSelectionModel().getSelectedIndex()),
					requiredSlot, requiredCapacity);
			if (title.getText().equalsIgnoreCase("Admin")) {
				Admin user = (Admin) currUser;
				deserializeUsers();
				for (int i = 0; i < listOfUsers.size(); i++) {
					if (listOfUsers.get(i).getEmailID().equals(user.getEmailID())) {
						listOfUsers.remove(i);
						listOfUsers.add(i, user);
					}
				}
			} else if (title.getText().equalsIgnoreCase("Faculty")) {
				Faculty user = (Faculty) currUser;
				deserializeUsers();
				for (int i = 0; i < listOfUsers.size(); i++) {
					if (listOfUsers.get(i).getEmailID().equals(user.getEmailID())) {
						listOfUsers.remove(i);
						listOfUsers.add(i, user);
					}
				}
			}

			serializeUsers();

			String message = "Your room " + relevantRooms.get(relevantRoomsList.getSelectionModel().getSelectedIndex())
					.getRoomNumber().toUpperCase() + " has been booked!";
			generateAlert("Confirm message", message);
		} else {
			generateAlert("Error!", "You're not allowed to perform this action!");
		}
	}

	/**
	 * This deserializes the list of users into the class
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
			listOfUsers = new ArrayList<User>();
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
				User newUser = listOfUsers.get(i);
				out.writeObject(newUser);
			}

		} finally {

			out.close();

		}

	}

	/**
	 * This function is used to display an alert with the given specifications.
	 * 
	 * @param title:
	 *            The title of the alert
	 * @param message:
	 *            The content of the alert
	 */
	public void generateAlert(String title, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();

	}

}
