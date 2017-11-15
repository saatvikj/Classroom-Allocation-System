package ui;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import backend.Admin;
import backend.ClassRoom;
import backend.Faculty;
import backend.Slot;
import backend.Student;
import backend.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class BookedRoomsUI {

	public User currUser;

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private Label roomName;

	@FXML
	private Label roomCapacity;

	@FXML
	private Label roomSlot;

	@FXML
	private GridPane roomDetails;

	@FXML
	private ListView<String> roomRecordsList;

	/**
	 * This is handler for mouse click of home button
	 * @param event: The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void homeButtonClicked(MouseEvent event) throws ClassNotFoundException {

		try {

			String path;
			if (currUser.getTypeOfUser().equals("Admin")) {
				path = "/fxml/AdminHome.fxml";
			} else if (currUser.getTypeOfUser().equals("Faculty")) {
				path = "/fxml/FacultyHome.fxml";
			} else {
				path = "/fxml/StudentHome.fxml";
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
			} else {

				Student student = (Student) currUser;
				StudentHomeUI controller = loader.<StudentHomeUI>getController();
				controller.currStudent = student;
				controller.populate();
			}

			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function sets the name, email ID and title of the
	 * current logged in user and populates it's list with list
	 * of booked rooms of the user.
	 */
	public void populate() {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());

		if (currUser.getBookedRooms().isEmpty()) {
			roomRecordsList.getItems().add("No booking!");
			roomDetails.setVisible(false);
		} else {

			for (Map.Entry<Slot, ClassRoom> entry : currUser.getBookedRooms().entrySet()) {

				Slot key = entry.getKey();
				ClassRoom value = entry.getValue();

				roomRecordsList.getItems().add(value.getRoomNumber().toUpperCase() + ", " + key.displayFormattedDate());

			}

		}

		roomRecordsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub

				try {

					if (!(newValue.equalsIgnoreCase("No booking!"))) {

						roomDetails.setVisible(true);
						String[] details = newValue.split(",");
						roomName.setText(details[0]);
						try {
							roomCapacity.setText(
									Integer.toString(currUser.getCorrespondingRoom(roomName.getText()).getCapacity()));
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						roomSlot.setText(details[1] + details[2]);

					}

				} catch (NullPointerException e) {
					roomDetails.setVisible(false);
					roomRecordsList.getItems().add("No booked rooms!");
				}

			}
		});

	}

	/**
	 * This is handler for mouse click of check availibility of room
	 * @param event: The mouse event
	 */
	@FXML
	private void checkAvail(MouseEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckRoomAvail1.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene((Parent) loader.load(), 800, 600));
			CheckRoomAvail1UI controller = loader.<CheckRoomAvail1UI>getController();
			controller.currUser = currUser;
			controller.onlyAvailability = true;
			controller.populate();

			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of back button
	 * @param event: The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) throws ClassNotFoundException {

		try {

			String path;
			if (currUser.getTypeOfUser().equals("Admin")) {
				path = "/fxml/AdminHome.fxml";
			} else if (currUser.getTypeOfUser().equals("Faculty")) {
				path = "/fxml/FacultyHome.fxml";
			} else {
				path = "/fxml/StudentHome.fxml";
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
			} else {

				Student student = (Student) currUser;
				StudentHomeUI controller = loader.<StudentHomeUI>getController();
				controller.currStudent = student;
				controller.populate();
			}

			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of logout
	 * @param event: The mouse event
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

}
