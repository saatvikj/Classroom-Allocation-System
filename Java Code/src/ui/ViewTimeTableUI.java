package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import backend.Course;
import backend.Slot;
import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class ViewTimeTableUI {

	public Student currStudent;
	private ArrayList<User> listOfUsers;
	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private Label dayHeader;

	@FXML
	private ListView<String> timetableList;

	@FXML
	private MenuButton dayPicker;

	boolean flag = false;
	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DAY_OF_WEEK);
	String currDay = null;

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void populate() throws ClassNotFoundException, FileNotFoundException, IOException {
		name.setText(currStudent.getName());
		email.setText(currStudent.getEmailID());
		title.setText(currStudent.getTypeOfUser());
		deserializeUsers();
		if (day == 1) {
			currDay = "Sunday";
		} else if (day == 2) {
			currDay = "Monday";
		} else if (day == 3) {
			currDay = "Tuesday";
		} else if (day == 4) {
			currDay = "Wednesday";
		} else if (day == 5) {
			currDay = "Thursday";
		} else if (day == 6) {
			currDay = "Friday";
		} else if (day == 7) {
			currDay = "Saturday";
		}

		System.out.println(day);
		if (currStudent.getTimetable() != null) {
			for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
				Slot s = mp.getKey();
				Course c = mp.getValue();

				displayDayTimeTable(c, s, currDay);

			}
			if (timetableList.getItems().size() == 0) {
				timetableList.getItems().add("You have no classes today!");
			}

		}

	}

	/**
	 * 
	 */
	@FXML
	private void monday() {

		for (MenuItem item : dayPicker.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Monday")) {
				chk.setSelected(true);
				flag = true;
				dayPicker.setText("Monday");
			}

		}
		for (MenuItem it : dayPicker.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Monday")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}

		timetableList.getItems().clear();
		dayHeader.setText("Monday's Time-Table");
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Monday");

		}
		if (timetableList.getItems().size() == 0) {
			timetableList.getItems().add("You have no classes today!");
		}

	}

	/**
	 * 
	 */
	@FXML
	private void tuesday() {

		for (MenuItem item : dayPicker.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Tuesday")) {
				chk.setSelected(true);
				flag = true;
				dayPicker.setText("Tuesday");
			}

		}
		for (MenuItem it : dayPicker.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Tuesday")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}
		timetableList.getItems().clear();
		dayHeader.setText("Tuesday's Time-Table");
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Tuesday");
		}
		if (timetableList.getItems().size() == 0) {
			timetableList.getItems().add("You have no classes today!");
		}

	}

	/**
	 * 
	 */
	@FXML
	private void wednesday() {

		for (MenuItem item : dayPicker.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Wednesday")) {
				chk.setSelected(true);
				flag = true;
				dayPicker.setText("Wednesday");
			}

		}
		for (MenuItem it : dayPicker.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Wednesday")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}
		timetableList.getItems().clear();
		dayHeader.setText("Wednesday's Time-Table");
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Wednesday");
		}
		if (timetableList.getItems().size() == 0) {
			timetableList.getItems().add("You have no classes today!");
		}

	}

	/**
	 * 
	 */
	@FXML
	private void thursday() {

		for (MenuItem item : dayPicker.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Thursday")) {
				chk.setSelected(true);
				flag = true;
				dayPicker.setText("Thursday");
			}

		}
		for (MenuItem it : dayPicker.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Thursday")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}
		timetableList.getItems().clear();
		dayHeader.setText("Thursday's Time-Table");
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Thursday");
		}
		if (timetableList.getItems().size() == 0) {
			timetableList.getItems().add("You have no classes today!");
		}

	}

	/**
	 * 
	 */
	@FXML
	private void friday() {

		for (MenuItem item : dayPicker.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Friday")) {
				chk.setSelected(true);
				flag = true;
				dayPicker.setText("Friday");
			}

		}
		for (MenuItem it : dayPicker.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Friday")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}
		timetableList.getItems().clear();
		dayHeader.setText("Friday's Time-Table");
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Friday");
		}
		if (timetableList.getItems().size() == 0) {
			timetableList.getItems().add("You have no classes today!");
		}

	}

	/**
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void homeButtonClicked(MouseEvent event) throws ClassNotFoundException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			StudentHomeUI controller = loader.<StudentHomeUI>getController();
			controller.currStudent = currStudent;
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
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) throws ClassNotFoundException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentHome.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			StudentHomeUI controller = loader.<StudentHomeUI>getController();
			controller.currStudent = currStudent;
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
	 * @param c
	 * @param s
	 * @param currDay
	 */
	public void displayDayTimeTable(Course c, Slot s, String currDay) {
		Map<Course, Integer> labPreferences = null;
		Map<Course, Integer> tutPreferences = null;

		if (s.getPurpose().equals(Slot.TYPES[1])) {
			if (c.getCourseTimeTable().get(s).size() >= 1) {
				labPreferences = currStudent.getLabPref();
			}
		} else if (s.getPurpose().equals(Slot.TYPES[2])) {
			if (c.getCourseTimeTable().get(s).size() >= 1) {
				tutPreferences = currStudent.getTutPref();
			}

		}

		if (s.getDay().equals(currDay) && labPreferences != null) {
			timetableList.getItems()
					.add(c.getAcronym() + " " + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose() + " "
							+ c.getCourseTimeTable().get(s).get(labPreferences.get(c)).getRoomNumber().toUpperCase());

		} else if (s.getDay().equals(currDay) && tutPreferences != null) {

			timetableList.getItems()
					.add(c.getAcronym() + " " + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose() + " "
							+ c.getCourseTimeTable().get(s).get(tutPreferences.get(c)).getRoomNumber().toUpperCase());

		} else if (s.getDay().equals(currDay)) {
			timetableList.getItems().add(c.getAcronym() + " " + s.getStartTime() + "-" + s.getEndTime() + " "
					+ s.getPurpose() + " " + c.getCourseTimeTable().get(s).get(0).getRoomNumber().toUpperCase());

		}
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public void deserializeUsers() throws IOException, ClassNotFoundException, FileNotFoundException {

		ObjectInputStream in = null;
		listOfUsers = new ArrayList<User>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/users.txt"));
			User user;

			try {

				while (true) {
					user = (User) in.readObject();
					listOfUsers.add(user);
					if (user.getEmailID().equals(currStudent.getEmailID())) {
						currStudent = (Student) user;
					}
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
