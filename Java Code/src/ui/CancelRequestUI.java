package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import backend.Request;
import backend.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class CancelRequestUI {

	private ArrayList<Request> listOfRequests;

	public Student currUser;

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private GridPane requestPane;

	@FXML
	private ListView<String> requestList;

	@FXML
	private Label roomNumber;

	@FXML
	private Label roomCap;

	@FXML
	private Label reqCap;

	@FXML
	private Label roomSlot;

	@FXML
	private Button cancelButton;

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void populate() throws ClassNotFoundException, IOException {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		deserializeRequests();

		if (listOfRequests.size() == 0) {
			requestList.getItems().add("No requests!");
		}

		for (int i = 0; i < listOfRequests.size(); i++) {
			requestList.getItems().add(
					"Request : " + (i + 1) + " Dated : " + listOfRequests.get(i).getTimeSlot().displayFormattedDate());
		}

		requestList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub

				try {

					if (!(newValue.equalsIgnoreCase("No requests!"))) {

						requestPane.setVisible(true);
						int index = requestList.getSelectionModel().getSelectedIndex();
						roomNumber.setText(listOfRequests.get(index).getPreferredRoom().getRoomNumber().toUpperCase());
						reqCap.setText(Integer.toString(listOfRequests.get(index).getRequiredCapacity()));
						roomCap.setText(Integer.toString(listOfRequests.get(index).getPreferredRoom().getCapacity()));
						roomSlot.setText(listOfRequests.get(index).getTimeSlot().displayFormattedDate());
						cancelButton.setVisible(true);

					}

				} catch (NullPointerException e) {

					requestList.getItems().add("No requests!");
					requestPane.setVisible(false);
					cancelButton.setVisible(false);

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
	private void homeButtonClicked(MouseEvent event) throws ClassNotFoundException {

		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			StudentHomeUI controller = loader.<StudentHomeUI>getController();
			controller.currStudent = currUser;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void cancelRequest() throws FileNotFoundException, IOException {

		int index = requestList.getSelectionModel().getSelectedIndex();
		requestList.getItems().remove(index);
		listOfRequests.remove(index);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert!");
		alert.setHeaderText(null);
		alert.setContentText("Your request has been cancelled!");
		alert.showAndWait();
		serializeRequests();

	}

	/**
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) throws ClassNotFoundException {

		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			StudentHomeUI controller = loader.<StudentHomeUI>getController();
			controller.currStudent = currUser;
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
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void deserializeRequests() throws ClassNotFoundException, IOException {

		ObjectInputStream in = null;
		listOfRequests = new ArrayList<Request>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/requests.txt"));
			Request request;

			try {

				while (true) {
					request = (Request) in.readObject();
					if (request.getCurrentStatus() == 0) {
						listOfRequests.add(request);
					}
				}

			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {

		} finally {

			if (in != null) {
				in.close();
			} else {
				listOfRequests = new ArrayList<Request>();
			}
		}

	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeRequests() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/requests.txt"));

			for (int i = 0; i < listOfRequests.size(); i++) {
				Request request = listOfRequests.get(i);
				if (!request.checkExpiry()) {
					out.writeObject(request);
				}
			}

		} finally {

			out.close();

		}

	}

}
