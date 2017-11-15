package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.ClassRoom;
import backend.Course;
import backend.Slot;
import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class CreateTT3UI {

	public Student currStudent;
	public Course selectedCourse;

	private ArrayList<Slot> labSlotList = new ArrayList<Slot>();
	private ArrayList<Slot> tutSlotList = new ArrayList<Slot>();
	private ArrayList<ClassRoom> rooomsList = new ArrayList<ClassRoom>();
	private ArrayList<User> listOfUsers = new ArrayList<>();

	@FXML
	private Label name;

	@FXML
	private Label title;

	@FXML
	private Label email;

	@FXML
	private Button addLabButton;

	@FXML
	private Label labLabel;

	@FXML
	private Label tutorialLabel;

	@FXML
	private Button addTutorialButton;

	@FXML
	private ListView<String> labRecordsList;

	@FXML
	private ListView<String> tutorialRecordsList;

	/**
	 * This sets the name, email ID and title of the current
	 * logged in student and populates both the lists of labs
	 * and tutorials with all available labs and tutorials if
	 * any. 
	 */
	public void populate() {

		name.setText(currStudent.getName());
		title.setText("STUDENT");
		email.setText(currStudent.getEmailID());

		for (Map.Entry<Slot, List<ClassRoom>> map : selectedCourse.getCourseTimeTable().entrySet()) {
			Slot slt = map.getKey();
			List<ClassRoom> room = map.getValue();

			if (slt.getPurpose().equals(Slot.TYPES[1])) {
				for (int i = 0; i < room.size(); i++) {
					labLabel.setVisible(true);
					labRecordsList.setVisible(true);
					addLabButton.setVisible(true);
					labSlotList.add(slt);
					rooomsList.add(room.get(i));
					labRecordsList.getItems().add(slt.getDay() + " " + slt.getStartTime() + "-" + slt.getEndTime()
							+ ", " + room.get(i).getRoomNumber());

				}
			} else if (slt.getPurpose().equals(Slot.TYPES[2])) {
				for (int i = 0; i < room.size(); i++) {
					tutSlotList.add(slt);
					rooomsList.add(room.get(i));
					tutorialLabel.setVisible(true);
					tutorialRecordsList.setVisible(true);
					addTutorialButton.setVisible(true);
					tutorialRecordsList.getItems().add(slt.getDay() + " " + slt.getStartTime() + "-" + slt.getEndTime()
							+ ", " + room.get(i).getRoomNumber());

				}
			}
		}

	}

	/**
	 * It is handler of mouse click on add lab button. It adds the selected
	 * lab to the user's object and serializes the user.
	 * @param event: The mouse event
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void addLabs(MouseEvent event) throws ClassNotFoundException, FileNotFoundException, IOException {
		deserializeUsers();
		int index = labRecordsList.getSelectionModel().getSelectedIndex();
		currStudent.addToTimeTableLabs(labSlotList.get(index), selectedCourse);
		generateAlert("Success!","This lab has been added to your time table!");

		if (currStudent.getLabPref() == null) {
			Map<Course, Integer> labPref = new HashMap<Course, Integer>();
			labPref.put(selectedCourse, index);
			currStudent.setLabPref(labPref);
		} else {
			currStudent.getLabPref().put(selectedCourse, index);
		}

		serializeUsers();
	}

	/**
	 * It is handler of mouse click on add tutorial button. It adds the selected
	 * tutorial to the user's object and serializes the user.
	 * @param event: The mouse event
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void addTutorials(MouseEvent event) throws ClassNotFoundException, FileNotFoundException, IOException {
		deserializeUsers();
		int index = tutorialRecordsList.getSelectionModel().getSelectedIndex();
		currStudent.addToTimeTableTutorials(tutSlotList.get(index), selectedCourse);
		generateAlert("Success!","This tutorial has been added to your time table!");
		if (currStudent.getTutPref() == null) {
			Map<Course, Integer> tutPref = new HashMap<Course, Integer>();
			tutPref.put(selectedCourse, index);
			currStudent.setTutPref(tutPref);
		} else {
			currStudent.getTutPref().put(selectedCourse, index);
		}

		serializeUsers();
	}

	/**
	 * The handler of mouse click on home button
	 * @param event: The mouse event
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
	 * The handler for mouse click of back button
	 * @param event: The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) throws ClassNotFoundException {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTT2.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			CreateTT2UI controller = loader.<CreateTT2UI>getController();
			controller.currStudent = currStudent;
			controller.relevantCourses = new ArrayList<Course>();
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The handler for mouse event of logout button
	 * @param event: The mouse event
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
	 * It serializes the list of users back into the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeUsers() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/users.txt"));

			for (int i = 0; i < listOfUsers.size(); i++) {

				if (listOfUsers.get(i).getEmailID().equals(currStudent.getEmailID())) {
					out.writeObject(currStudent);
				} else {

					User newUser = listOfUsers.get(i);
					out.writeObject(newUser);
				}
			}

		} finally {

			out.close();

		}

	}

	/**
	 * It deserializes the list of users into the class
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public void deserializeUsers() throws IOException, ClassNotFoundException, FileNotFoundException {

		/*
		 * Deserializes the list of registered users into the ArrayList so that
		 * checking can be done.
		 * 
		 */
		ObjectInputStream in = null;

		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/users.txt"));
			User user;

			try {

				while (true) {
					user = (User) in.readObject();
					listOfUsers.add(user);
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

	/**
	 * This function is used to display an alert with the given 
	 * specifications.
	 * @param title: The title of the alert
	 * @param message: The content of the alert
	 */
	public generateAlert(String title, String message) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(title);
				alert.setHeaderText(null);
				alert.setContentText(message);
				alert.showAndWait();

	}	

}
