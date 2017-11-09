package ui;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import backend.ClassRoom;
import backend.Request;
import backend.Slot;
import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MakeRequestUI {

	public String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	public Student currUser;

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
	private TextField purpose;

	@FXML
	private DatePicker date;

	@FXML
	private TextField startTime;

	@FXML
	private TextField endTime;

	public void populate() {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser());
	}

	@FXML
	public void submitRequest(MouseEvent event) throws ClassNotFoundException, IOException {

		boolean validity = checkEmptiness(preferredRoom.getText(), reqCapacity.getText(), purpose.getText(),
				startTime.getText(), endTime.getText(), date.getValue() == null);
		if (validity) {

			String prefRoom = preferredRoom.getText().toString();
			int reqCap = Integer.parseInt(reqCapacity.getText().toString());
			String purposeRoom = purpose.getText().toString();
			Date dt;
			String dat[] = date.getValue().toString().split("\\-");
			dt = new Date(Integer.parseInt(dat[0]) - 1900, Integer.parseInt(dat[1]) - 1, Integer.parseInt(dat[2]));
			String day = daysOfWeek[date.getValue().getDayOfWeek().getValue() - 1];
			String startT = startTime.getText().toString();
			String endT = endTime.getText().toString();

			int startTimeHour = Integer.parseInt(startT.split("\\:")[0]);
			int startTimeMinute = Integer.parseInt(startT.split("\\:")[1]);
			Time sTime = new Time(startTimeHour, startTimeMinute, 0);
			int endTimeHour = Integer.parseInt(endT.split("\\:")[0]);
			int endTimeMinute = Integer.parseInt(endT.split("\\:")[1]);
			Time eTime = new Time(endTimeHour, endTimeMinute, 0);
			Slot userSlot = new Slot(dt, day, purposeRoom, sTime, eTime);
			ClassRoom userRoom = currUser.getCorrespondingRoom(prefRoom);

			ArrayList<ClassRoom> rooms = currUser.checkRoomAvailability(userRoom, userSlot, reqCap);
			boolean check = false;
			for (int i = 0; i < rooms.size(); i++) {
				if (rooms.get(i).getRoomNumber().equalsIgnoreCase(userRoom.getRoomNumber())) {
					check = true;
				}
			}
			if (check) {
				currUser.makeBooking(userRoom, userSlot, reqCap);
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setContentText("The room is already booked, please make another request.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("At least one of the fields is empty, try again.");
			alert.showAndWait();
		}

	}

	public boolean checkEmptiness(String text, String text2, String text3, String text4, String text5, boolean b) {
		// TODO Auto-generated method stub
		if (text.length() == 0 || text2.length() == 0 || text3.length() == 0 || text4.length() == 0
				|| text5.length() == 0 || b) {
			return false;
		} else {
			return true;
		}
	}

	@FXML
	private void homeButtonClicked(MouseEvent event) throws ClassNotFoundException {

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

	@FXML
	private void backButtonClicked(MouseEvent event) throws ClassNotFoundException {

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
