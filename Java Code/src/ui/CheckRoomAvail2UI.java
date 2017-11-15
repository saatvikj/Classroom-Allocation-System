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
	 * 
	 * @param event
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
	 * 
	 * @param event
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
	 * 
	 * @param event
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
	 * 
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

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Confirm Message");
			alert.setHeaderText(null);
			alert.setContentText(
					"Your room " + relevantRooms.get(relevantRoomsList.getSelectionModel().getSelectedIndex())
							.getRoomNumber().toUpperCase() + " has been booked!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("You're not allowed to perform this action!");
			alert.showAndWait();
		}
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public void deserializeUsers() throws IOException, ClassNotFoundException, FileNotFoundException {

		/*
		 * Deserializes the list of registered users into the ArrayList so that
		 * checking can be done.
		 * 
		 */
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

}
