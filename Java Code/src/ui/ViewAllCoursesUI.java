package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import backend.Admin;
import backend.ClassRoom;
import backend.Course;
import backend.Faculty;
import backend.Slot;
import backend.Student;
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

public class ViewAllCoursesUI {

	public User currUser;

	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;

	@FXML
	private Label courseName;

	@FXML
	private Label courseAcronym;

	@FXML
	private Label courseInstructor;

	@FXML
	private Label courseCredits;

	@FXML
	private Label coursePreReqs;

	@FXML
	private GridPane courseDetails;

	@FXML
	private ListView<String> courseRecordsList;
	
	private ArrayList<Course> allCourses;

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

	public void populate() throws ClassNotFoundException, IOException {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());
		deserializeCourses();

		for(int i = 0; i < allCourses.size(); i++){
			courseRecordsList.getItems().add(allCourses.get(i).getCourseName());

		}

		courseRecordsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub

				int selectedindex = courseRecordsList.getSelectionModel().getSelectedIndex();
				Course c = allCourses.get(selectedindex);
				courseDetails.setVisible(true);
				courseName.setText(c.getCourseName());
				courseAcronym.setText(c.getAcronym().toUpperCase());
				courseInstructor.setText(c.getInstructor());
				courseCredits.setText(Integer.toString(c.getCredits()));
				coursePreReqs.setText(Arrays.toString(c.getPreReqs()));

			}
		});

	}

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
	
	public void deserializeCourses() throws IOException, ClassNotFoundException {
		/*
		 * Deserialize the file listofcourses.txt into the arraylist
		 */

		ObjectInputStream in = null;
		allCourses = new ArrayList<Course>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/courses.txt"));
			Course course;

			try {

				while (true) {
					course = (Course) in.readObject();
					allCourses.add(course);
				}

			} catch (EOFException e) {

			}

		} finally {

			in.close();

		}

	}

}