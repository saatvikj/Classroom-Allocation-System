package ui;

import java.io.IOException;
import java.util.ArrayList;

import backend.ClassRoom;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CheckRoomAvail2UI {
	
	public User currUser;
	public ArrayList<ClassRoom> relevantRooms;
	
	@FXML
	private ListView<String> relevantRoomsList;
	
	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;
	
	
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
	
	@FXML
	private void backButtonClicked(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/CheckRoomAvail1.fxml"));
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
	
	public void populate() {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());
		if(relevantRooms.size() == 0)
		{
			relevantRoomsList.getItems().add("No Rooms found!");
		}
		else
		{
			for(int i = 0; i < relevantRooms.size(); i++)
			{
				relevantRoomsList.getItems().add(relevantRooms.get(i).getRoomNumber().toUpperCase() + "     Capacity : " + relevantRooms.get(i).getCapacity());
			}
		}
		
	}
	
	@FXML
	public void bookRoom(){
		
		System.out.println(relevantRoomsList.getSelectionModel().getSelectedIndex());
	}

}
