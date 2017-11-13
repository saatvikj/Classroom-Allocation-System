package ui;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import backend.Course;
import backend.Slot;
import backend.Student;
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

public class ViewTimeTableUI {

	public Student currStudent;

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private ListView<String> timetableList;

	@FXML
	private MenuButton dayPicker;

	boolean flag = false;
	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DAY_OF_WEEK);
	String currDay = null;

	public void populate() {
		name.setText(currStudent.getName());
		email.setText(currStudent.getEmailID());
		title.setText(currStudent.getTypeOfUser());
		if (day == 1) {
			currDay = "Sunday";
		} else if (day == 2) {
			currDay = "Monday";
		} else if (day == 2) {
			currDay = "Tuesday";
		} else if (day == 2) {
			currDay = "Wednesday";
		} else if (day == 2) {
			currDay = "Thursday";
		} else if (day == 2) {
			currDay = "Friday";
		} else if (day == 2) {
			currDay = "Saturday";
		}

		
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			
			displayDayTimeTable(c, s, currDay);

		}

	}

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
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Monday");
		}

	}

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
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Tuesday");
		}

	}

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
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Wednesday");
		}

	}

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
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Thursday");
		}

	}

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
		for (Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()) {
			Slot s = mp.getKey();
			Course c = mp.getValue();
			displayDayTimeTable(c, s, "Friday");
		}

	}

	@FXML
	private void homeButtonClicked(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/StudentHome.fxml"));
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
			root = FXMLLoader.load(getClass().getResource("/fxml/StudentHome.fxml"));
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

	
	public void displayDayTimeTable(Course c, Slot s,String currDay) {
		Map<Course, Integer> labPreferences = null;
		Map<Course, Integer> tutPreferences = null;

		if (s.getPurpose().equals(Slot.TYPES[1])) {
			if (c.getCourseTimeTable().get(s).size() >= 1) {
				labPreferences = currStudent.getLabPref();
			}
		} else if (s.getPurpose().equals(Slot.TYPES[2])) {
			if (c.getCourseTimeTable().get(s).size() >= 1) {
				tutPreferences = currStudent.getLabPref();
			}

		}

		if (s.getDay().equals(currDay) && labPreferences != null) {
			timetableList.getItems().add(c.getAcronym() + " " + s.getStartTime() + "-" + s.getEndTime() + " "
					+ s.getPurpose() + " "
					+ c.getCourseTimeTable().get(s).get(labPreferences.get(c)).getRoomNumber().toUpperCase());

		} else if (s.getDay().equals(currDay) && tutPreferences != null) {

			timetableList.getItems().add(c.getAcronym() + " " + s.getStartTime() + "-" + s.getEndTime() + " "
					+ s.getPurpose() + " "
					+ c.getCourseTimeTable().get(s).get(tutPreferences.get(c)).getRoomNumber().toUpperCase());

		} else if(s.getDay().equals(currDay)){
			timetableList.getItems().add(c.getAcronym() + " " + s.getStartTime() + "-" + s.getEndTime() + " "
					+ s.getPurpose() + " " + c.getCourseTimeTable().get(s).get(0).getRoomNumber().toUpperCase());

		}
	}
	
}
