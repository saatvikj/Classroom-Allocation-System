package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;

import backend.Admin;
import backend.ClassRoom;
import backend.Faculty;
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
import javafx.scene.control.DateCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class CheckRoomAvail1UI {

	public String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	public User currUser;

	public boolean onlyAvailability = false;

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

	/**
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void confirmBooking(MouseEvent event) throws ClassNotFoundException {

		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckRoomAvail2.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));

			boolean validity = checkEmptiness(preferredRoom.getText(), slot.getText(), reqCapacity.getText(),
					date.getValue() == null);

			System.out.println(date.toString());

			if (validity) {
				Date dt;
				String dat[] = date.getValue().toString().split("\\-");
				dt = new Date(Integer.parseInt(dat[0]) - 1900, Integer.parseInt(dat[1]) - 1, Integer.parseInt(dat[2]));
				String day = daysOfWeek[date.getValue().getDayOfWeek().getValue() - 1];
				String timeSlot = slot.getText();
				String requiredCap = reqCapacity.getText();
				int reqcap = Integer.parseInt(requiredCap);
				String startTime = timeSlot.split("\\-")[0];
				int startTimeHour = Integer.parseInt(startTime.split("\\:")[0]);
				int startTimeMinute = Integer.parseInt(startTime.split("\\:")[1]);
				Time sTime = new Time(startTimeHour, startTimeMinute, 0);
				String endTime = timeSlot.split("\\-")[1];
				int endTimeHour = Integer.parseInt(endTime.split("\\:")[0]);
				int endTimeMinute = Integer.parseInt(endTime.split("\\:")[1]);
				Time eTime = new Time(endTimeHour, endTimeMinute, 0);

				Slot userSlot = new Slot(dt, day, Slot.TYPES[3], sTime, eTime);
				ClassRoom userRoom = currUser.getCorrespondingRoom(preferredRoom.getText());

				ArrayList<ClassRoom> eligibleRooms = currUser.checkRoomAvailability(userRoom, userSlot, reqcap);
				for (int c = 0; c < eligibleRooms.size(); c++) {
					if (eligibleRooms.get(c).getRoomNumber().equals(userRoom.getRoomNumber())) {
						ClassRoom temp = eligibleRooms.get(c);
						ClassRoom pref = eligibleRooms.get(0);
						eligibleRooms.remove(0);
						eligibleRooms.add(0, temp);
						eligibleRooms.remove(c);
						eligibleRooms.add(c, pref);

					}
				}

				if (!onlyAvailability) {
					CheckRoomAvail2UI controller = loader.<CheckRoomAvail2UI>getController();
					controller.currUser = currUser;
					controller.relevantRooms = eligibleRooms;
					controller.requiredSlot = userSlot;
					controller.requiredCapacity = reqcap;
					controller.populate();
					stage.show();

					((Node) (event.getSource())).getScene().getWindow().hide();
				} else {
					if (eligibleRooms.size() == 0) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error!");
						alert.setHeaderText(null);
						alert.setContentText("Room isn't available!");
						alert.showAndWait();

					} else {
						if (eligibleRooms.get(0).getRoomNumber().equalsIgnoreCase(preferredRoom.getText())) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Success!");
							alert.setHeaderText(null);
							alert.setContentText("The room is available!");
							alert.showAndWait();

						} else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error!");
							alert.setHeaderText(null);
							alert.setContentText("Room isn't available!");
							alert.showAndWait();
						}
					}
				}
			} else {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setContentText("At least one of the fields is empty, try again.");
				alert.showAndWait();

			}
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
		date.setDayCellFactory(this.getDayCellFactory());

	}

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
	 * @param roomNo
	 * @param slot
	 * @param capacity
	 * @param date
	 * @return boolean
	 */
	public boolean checkEmptiness(String roomNo, String slot, String capacity, boolean date) {

		if (roomNo.length() == 0 || slot.length() == 0 || capacity.length() == 0 || date) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return dayCellFactory
	 */
	private Callback<DatePicker, DateCell> getDayCellFactory() {

		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						// Disable Monday, Tueday, Wednesday.
						if (item.isBefore(LocalDate.now())) {
							setDisable(true);
							setStyle("-fx-background-color: #f0f0f0");
						}
					}
				};
			}
		};
		return dayCellFactory;
	}

}
