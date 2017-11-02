package ui;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import backend.Slot;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CheckRoomAvail1UI {

	public String[] daysOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	public User currUser;

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private TextField preferredRoom;

	@FXML
	private TextField reqCapacity;

	@FXML
	private TextField slot;

	@FXML
	private DatePicker date;

	@FXML
	private void confirmBooking(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/CheckRoomAvail2.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			Date dt;
			dt = new Date(date.getValue().getYear(), date.getValue().getMonthValue(), date.getValue().getDayOfMonth());
			String day = daysOfWeek[dt.getDay()];
			String timeSlot = slot.getText();

			String startTime = timeSlot.split("\\-")[0];
			int startTimeHour = Integer.parseInt(startTime.split("\\:")[0]);
			int startTimeMinute = Integer.parseInt(startTime.split("\\:")[1]);
			Time sTime = new Time(startTimeHour, startTimeMinute, 0);
			String endTime = timeSlot.split("\\-")[1];
			int endTimeHour = Integer.parseInt(endTime.split("\\:")[0]);
			int endTimeMinute = Integer.parseInt(endTime.split("\\:")[1]);
			Time eTime = new Time(endTimeHour, endTimeMinute, 0);

			Slot userSlot = new Slot(dt, day, Slot.TYPES[3], sTime, eTime);
			
			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void populate() {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());
	}

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
