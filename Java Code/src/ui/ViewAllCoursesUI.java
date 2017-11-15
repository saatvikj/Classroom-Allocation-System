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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class ViewAllCoursesUI {

	public User currUser;

	public boolean myCourses = false;

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
	private Button postConditionButton;

	@FXML
	private Label coursePreReqs;

	@FXML
	private GridPane courseDetails;

	@FXML
	private ListView<String> courseRecordsList;

	private ArrayList<Course> allCourses;

	/**
	 * Handler for mouse click of home button
	 * 
	 * @param event:
	 *            The mouse event
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
	 * This sets the name, email ID and title of the current logged in user and
	 * populates the list of courses based on choice.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void populate() throws ClassNotFoundException, IOException {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());
		deserializeCourses();

		for (int i = 0; i < allCourses.size(); i++) {
			if (myCourses) {
				if (currUser.getName().equals(allCourses.get(i).getInstructor())) {
					courseRecordsList.getItems().add(allCourses.get(i).getCourseName());
				}
			} else {
				courseRecordsList.getItems().add(allCourses.get(i).getCourseName());
			}
		}

		courseRecordsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				postConditionButton.setVisible(true);
				for (int i = 0; i < allCourses.size(); i++) {
					if (allCourses.get(i).getCourseName().equals(newValue)) {
						Course c = allCourses.get(i);
						courseDetails.setVisible(true);
						courseName.setText(c.getCourseName());
						courseAcronym.setText(c.getAcronym().toUpperCase());
						courseInstructor.setText(c.getInstructor());
						courseCredits.setText(Integer.toString(c.getCredits()));
						coursePreReqs.setText(Arrays.toString(c.getPreReqs()));
					}
				}

			}
		});

	}

	/**
	 * Handler for mouse click of view post conditions
	 * 
	 * @param event:
	 *            The mouse event
	 */
	@FXML
	private void viewPostConds(MouseEvent event) {
		int i = courseRecordsList.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Post conditions");
		alert.setHeaderText(null);
		String post = "";
		for (int j = 0; j < allCourses.get(i).getPostConditions().length; j++) {
			post = post.concat(Integer.toString(j + 1) + ") " + allCourses.get(i).getPostConditions()[j] + "\n");
		}
		alert.setContentText(post);
		alert.showAndWait();
	}

	/**
	 * Handler for mouse click of back button
	 * 
	 * @param event:
	 *            The mouse event
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
	 * Handler for mouse click of logout button
	 * 
	 * @param event:
	 *            The mouse event
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
	 * This deserializes the courses into the class
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void deserializeCourses() throws IOException, ClassNotFoundException {

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
