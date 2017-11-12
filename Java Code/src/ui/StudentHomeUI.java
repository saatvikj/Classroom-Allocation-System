package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StudentHomeUI {

	public Student currStudent;
	private ArrayList<User> listOfUsers = new ArrayList<User>();

	@FXML
	private Label nameStudent;

	@FXML
	private Label emailStudent;


	public void populate() throws FileNotFoundException, IOException, ClassNotFoundException {
		deserializeUsers();
		currStudent.deserializeRequests();
		serializeUsers();
		nameStudent.setText(currStudent.getName());
		emailStudent.setText(currStudent.getEmailID());
		
	}

	@FXML
	private void showNotifications(MouseEvent event) {

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

	@FXML
	private void viewTimeTable(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/TimeTable.fxml"));
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
	
}
