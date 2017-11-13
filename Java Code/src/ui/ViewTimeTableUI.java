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
		System.out.println(day);
		if (day == 1) {
			currDay = "Sunday";
		} else if (day == 2) {
			currDay = "Monday";
		}else if (day == 2) {
			currDay = "Tuesday";
		}else if (day == 2) {
			currDay = "Wednesday";
		}else if (day == 2) {
			currDay = "Thursday";
		}else if (day == 2) {
			currDay = "Friday";
		}else if (day == 2) {
			currDay = "Saturday";
		}
		
		for(Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()){
			Slot s = mp.getKey();
			Course c = mp.getValue();
			if(s.getDay().equals(currDay)){
				timetableList.getItems().add(c.getAcronym() + " "  + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose());
			}
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
		for(Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()){
			Slot s = mp.getKey();
			Course c = mp.getValue();
			if(s.getDay().equals("Monday")){
				timetableList.getItems().add(c.getAcronym() + " "  + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose());
			}
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
		for(Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()){
			Slot s = mp.getKey();
			Course c = mp.getValue();
			if(s.getDay().equals("Tuesday")){
				timetableList.getItems().add(c.getAcronym() + " "  + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose());
			}
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
		for(Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()){
			Slot s = mp.getKey();
			Course c = mp.getValue();
			if(s.getDay().equals("Wednesday")){
				timetableList.getItems().add(c.getAcronym() + " "  + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose());
			}
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
		for(Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()){
			Slot s = mp.getKey();
			Course c = mp.getValue();
			if(s.getDay().equals("Thursday")){
				timetableList.getItems().add(c.getAcronym() + " "  + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose());
			}
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
		for(Map.Entry<Slot, Course> mp : currStudent.getTimetable().entrySet()){
			Slot s = mp.getKey();
			Course c = mp.getValue();
			if(s.getDay().equals("Friday")){
				timetableList.getItems().add(c.getAcronym() + " "  + s.getStartTime() + "-" + s.getEndTime() + " " + s.getPurpose());
			}
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

}
