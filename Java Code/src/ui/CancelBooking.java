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
import javafx.scene.control.Button;
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
public class CancelBooking {

	public User currUser;
	public ArrayList<User> listOfUsers;

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

	@FXML
	private Button cancelBookingButton;

	/**
	 * 
	 * @param event
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
	 * 
	 */
	public void populate() {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());

		if (currUser.getBookedRooms().isEmpty()) {
			roomRecordsList.getItems().add("No booking!");
			roomDetails.setVisible(false);
			cancelBookingButton.setVisible(false);

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
					if (!(newValue.equalsIgnoreCase("No booked rooms!"))) {
						roomDetails.setVisible(true);
						cancelBookingButton.setVisible(true);
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
					cancelBookingButton.setVisible(false);
					roomRecordsList.getItems().add("No booked rooms!");
				}

			}
		});

	}

	/**
	 * 
	 * @param event
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
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void cancelBooking(MouseEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
		String selectedItem = roomRecordsList.getSelectionModel().getSelectedItem();
		int index = roomRecordsList.getSelectionModel().getSelectedIndex();
		String[] bookingdetails = selectedItem.split(",");
		for (Map.Entry<Slot, ClassRoom> mp : currUser.getBookedRooms().entrySet()) {
			Slot key = mp.getKey();
			ClassRoom value = mp.getValue();
			String[] slot = key.displayFormattedDate().split(",");
			bookingdetails[1] = bookingdetails[1].replaceAll(" ", "");
			if (value.getRoomNumber().equalsIgnoreCase(bookingdetails[0]) && slot[0].equalsIgnoreCase(bookingdetails[1])
					&& slot[1].equalsIgnoreCase(bookingdetails[2])) {

				try {
					String removed = roomRecordsList.getItems().remove(index);
				} catch (NullPointerException e) {

					roomDetails.setVisible(false);
					cancelBookingButton.setVisible(false);
					roomRecordsList.getItems().add("No booked rooms");
				}

				currUser.cancelBooking(value, key);
				currUser.getBookedRooms().remove(key);
			}
		}

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
		} else {

			Student user = (Student) currUser;
			deserializeUsers();
			for (int i = 0; i < listOfUsers.size(); i++) {
				if (listOfUsers.get(i).getEmailID().equals(user.getEmailID())) {
					listOfUsers.remove(i);
					listOfUsers.add(i, user);
				}
			}
		}

		serializeUsers();
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
