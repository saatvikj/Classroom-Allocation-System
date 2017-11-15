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
import backend.Request;
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
public class HandleRequestsUI {

	public Admin currAdmin;
	private ArrayList<User> listOfUsers = new ArrayList<User>();

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private ListView<String> requestsList;

	@FXML
	private Label roomNumber;

	@FXML
	private Label roomPurpose;

	@FXML
	private Label roomCap;

	@FXML
	private Label roomSlot;

	@FXML
	private Label studentName;

	@FXML
	private GridPane requestPane;

	/**
	 * The handler for mouse click of home button
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void homeButtonClicked(MouseEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));

			AdminHomeUI controller = loader.<AdminHomeUI>getController();
			controller.currAdmin = currAdmin;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This sets the name and email ID of the current logged in admin and
	 * populates the list with the current list of requests.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void populate() throws ClassNotFoundException, IOException {
		currAdmin.deserializeRequests();
		name.setText(currAdmin.getName());
		email.setText(currAdmin.getEmailID());
		title.setText(currAdmin.getTypeOfUser().toUpperCase());
		ArrayList<Request> reqList = currAdmin.getListOfRequests();

		if (reqList.size() == 0) {
			requestsList.getItems().add("No pending requests!");

		} else {
			for (int i = 0; i < reqList.size(); i++) {
				requestsList.getItems().add(reqList.get(i).getPreferredRoom().getRoomNumber().toUpperCase()
						+ "  Dated : " + reqList.get(i).getTimeSlot().displayFormattedDate());
			}
		}

		requestsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub

				try {

					if (!(newValue.equalsIgnoreCase("No pending requests!"))) {

						requestPane.setVisible(true);
						int index = requestsList.getSelectionModel().getSelectedIndex();
						roomNumber.setText(reqList.get(index).getPreferredRoom().getRoomNumber().toUpperCase());
						roomPurpose.setText(reqList.get(index).getPurpose());
						roomCap.setText(Integer.toString(reqList.get(index).getRequiredCapacity()));
						roomSlot.setText(reqList.get(index).getTimeSlot().displayFormattedDate());
						studentName.setText(reqList.get(index).getSourceStudent().getName());

					}

				} catch (NullPointerException e) {

					requestsList.getItems().add("No pending requests!");
					requestPane.setVisible(false);

				}

			}
		});
	}

	/**
	 * The handler for mouse click of back button
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));

			AdminHomeUI controller = loader.<AdminHomeUI>getController();
			controller.currAdmin = currAdmin;
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
	 * The handler for mouse click of accept request button, it then changes the
	 * status of the request and books the room for the user and serializes the
	 * rooms and requests.
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void acceptRequest(MouseEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {

		deserializeUsers();
		int requestIndex = requestsList.getSelectionModel().getSelectedIndex();
		currAdmin.handleRequests(requestIndex, true);
		requestsList.getItems().remove(requestIndex);
		serializeUsers();
	}

	/**
	 * The handler for mouse click of reject request button, it then changes the
	 * status of the request and serializes the request.
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void rejectRequest(MouseEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {

		deserializeUsers();
		int requestIndex = requestsList.getSelectionModel().getSelectedIndex();
		currAdmin.handleRequests(requestIndex, false);
		requestsList.getItems().remove(requestIndex);
		serializeUsers();
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

				if (listOfUsers.get(i).getEmailID().equals(currAdmin.getEmailID())) {
					out.writeObject(currAdmin);
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
	 * This deserializes the list of users into the class
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
