package ui;

import java.io.IOException;
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

public class HandleRequestsUI {
	
	public Admin currAdmin;

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
	
	public void populate() throws ClassNotFoundException, IOException {
		name.setText(currAdmin.getName());
		email.setText(currAdmin.getEmailID());
		title.setText(currAdmin.getTypeOfUser().toUpperCase());
		currAdmin.deserializeRequests();
		ArrayList<Request> reqList = currAdmin.getListOfRequests();
		
		if(reqList.size() == 0)
		{
			requestsList.getItems().add("No pending requests!");
			
		}
		else
		{
			for(int i = 0; i < reqList.size(); i++)
			{
				requestsList.getItems().add(reqList.get(i).getPreferredRoom().getRoomNumber().toUpperCase() + "  Dated : " + reqList.get(i).getTimeSlot().displayFormattedDate());
			}
		}
		
		requestsList.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				requestPane.setVisible(true);

				int index = requestsList.getSelectionModel().getSelectedIndex();
				roomNumber.setText(reqList.get(index).getPreferredRoom().getRoomNumber().toUpperCase());
				roomPurpose.setText(reqList.get(index).getPurpose());
				roomCap.setText(Integer.toString(reqList.get(index).getRequiredCapacity()));
				roomSlot.setText(reqList.get(index).getTimeSlot().displayFormattedDate());
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
