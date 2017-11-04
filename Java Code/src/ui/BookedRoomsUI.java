package ui;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import backend.ClassRoom;
import backend.Slot;
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

	@FXML
	private void homeButtonClicked(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/AdminHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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

				roomRecordsList.getItems().add(value.getRoomNumber().toUpperCase() + ", "
						+ key.displayFormattedDate());

			}

		}
		
		roomRecordsList.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				roomDetails.setVisible(true);
				String[] details = newValue.split(",");
				roomName.setText(details[0]);
				try {
					roomCapacity.setText(Integer.toString(currUser.getCorrespondingRoom(roomName.getText()).getCapacity()));
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				roomSlot.setText(details[1] + details[2]);	
				
			}
		});

	}

	@FXML
	private void backButtonClicked(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/AdminHome.fxml"));
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
