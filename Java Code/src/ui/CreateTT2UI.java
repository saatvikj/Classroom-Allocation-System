package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import backend.ClassRoom;
import backend.Course;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class CreateTT2UI {

	public Student currStudent;
	public ArrayList<Course> relevantCourses;
	public boolean audit;
	private ArrayList<User> listOfUsers = new ArrayList<>();

	@FXML
	private Label name;

	@FXML
	private Label title;

	@FXML
	private Label email;

	@FXML
	private Label courseCode;

	@FXML
	private Label abbreviation;

	@FXML
	private Label prereqs;

	@FXML
	private Label credits;

	@FXML
	private Label instructor;

	@FXML
	private Button addToTimeTable;

	@FXML
	private ListView<String> relevantCoursesList;

	@FXML
	private GridPane relevantPane;

	/**
	 * This function sets the name, email ID and title of the 
	 * current logged in user, and then populates its list 
	 * with the list of relevant courses generated from previous page.
	 */
	public void populate() {
		name.setText(currStudent.getName());
		email.setText(currStudent.getEmailID());
		title.setText(currStudent.getTypeOfUser());

		if (relevantCourses.isEmpty()) {
			relevantCoursesList.getItems().add("No courses!");
			relevantPane.setVisible(false);
			addToTimeTable.setVisible(false);
		} else {

			for (int i = 0; i < relevantCourses.size(); i++) {
				relevantCoursesList.getItems().add(relevantCourses.get(i).getCourseCode());
			}
		}

		relevantCoursesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub

				try {

					if (!(newValue.equalsIgnoreCase("No courses!"))) {

						relevantPane.setVisible(true);
						addToTimeTable.setVisible(true);
						int index = relevantCoursesList.getSelectionModel().getSelectedIndex();
						courseCode.setText(relevantCourses.get(index).getCourseName());
						abbreviation.setText(relevantCourses.get(index).getAcronym().toUpperCase());
						credits.setText(Integer.toString(relevantCourses.get(index).getCredits()));
						prereqs.setText(Arrays.toString(relevantCourses.get(index).getPreReqs()));
						instructor.setText(relevantCourses.get(index).getInstructor());
					}

				} catch (NullPointerException e) {
					relevantPane.setVisible(false);
					addToTimeTable.setVisible(false);
					relevantCoursesList.getItems().add("No courses!");
				}

			}
		});

	}

	/**
	 * It first checks the selected course type, if it is audit then directly adds
	 * to the time table and displays a warning with the pre-requisites, but if the 
	 * course is credit then it checks for clashes, if no clashes are there, it adds the
	 * course to the time table and in case the course has tutorials/labs then gives an option
	 * to add those as well and then serializes the user.
	 * @param event: The mouse event
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void addCourse(MouseEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {

		deserializeUsers();
		int selectedCourseIndex = relevantCoursesList.getSelectionModel().getSelectedIndex();
		Course selectedCourse = relevantCourses.get(selectedCourseIndex);

		boolean auditedCourse = false;
		if (audit) {

			if (!selectedCourse.getPreReqs()[0].equalsIgnoreCase("None")) {

				String message = "You have selected to audit the course, kindly ensure that you have the following pre-requisites covered:\n"
						+ Arrays.toString(selectedCourse.getPreReqs());
				generateAlert("Warning", message);

			}

			currStudent.addToTimeTable(selectedCourse);
			auditedCourse = true;
			generateAlert("Success", "The audit course has been added to your time-table");
			return;
		}

		int countLab = 0;
		int countTutorial = 0;
		for (Map.Entry<Slot, List<ClassRoom>> map : selectedCourse.getCourseTimeTable().entrySet()) {
			Slot slt = map.getKey();
			List<ClassRoom> room = map.getValue();

			if (slt.getPurpose().equals(Slot.TYPES[1])) {
				countLab++;
			}
		}

		for (Map.Entry<Slot, List<ClassRoom>> map : selectedCourse.getCourseTimeTable().entrySet()) {
			Slot slt = map.getKey();
			List<ClassRoom> room = map.getValue();

			if (slt.getPurpose().equals(Slot.TYPES[2])) {
				countTutorial++;
			}
		}

		boolean existance = false;
		boolean clashes = false;
		boolean labExistance = false;
		boolean tutExistance = false;
		if (currStudent.getTimetable() != null) {
			for (Map.Entry<Slot, Course> map : currStudent.getTimetable().entrySet()) {
				Course c = map.getValue();
				Slot s = map.getKey();
				boolean flag = true;
				for (Map.Entry<Slot, List<ClassRoom>> mp : selectedCourse.getCourseTimeTable().entrySet()) {
					Slot selSlot = mp.getKey();
					if (s.getDay().equals(selSlot.getDay())) {
						flag = flag && currStudent.checkIfValidSlot(s, selSlot);

					}
				}
				if (!flag) {
					clashes = true;
				}
				if (c.getCourseName().equals(selectedCourse.getCourseName())) {
					existance = true;
					if (countLab != 0 && s.getPurpose().equals(Slot.TYPES[1])) {
						labExistance = true;
					} else if (countTutorial != 0 && s.getPurpose().equals(Slot.TYPES[2])) {
						tutExistance = true;
					}
				}
			}
		}

		if (!existance) {

			if (!clashes) {

				currStudent.addToTimeTable(selectedCourse);
				serializeUsers();
				if (countLab == 0 && countTutorial == 0) {

					currStudent.addToTimeTable(selectedCourse);
					generateAlert("Success", "This course has been added to your time table");

				} else {

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Confirm!");
					alert.setHeaderText("The course has been added.");
					alert.setResizable(false);
					alert.setContentText("Do you want to add tutorials and labs as well?");
					Optional<ButtonType> result = alert.showAndWait();
					if (!result.isPresent()) {

					} else if (result.get() == ButtonType.OK) {

						relevantCoursesList.getItems().remove(selectedCourseIndex);

						try {

							FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTT3.fxml"));
							Stage stage = new Stage();
							stage.setTitle("IIIT Delhi");
							stage.setScene(new Scene(loader.load(), 800, 600));
							CreateTT3UI controller = loader.<CreateTT3UI>getController();
							controller.currStudent = currStudent;
							controller.selectedCourse = relevantCourses.get(selectedCourseIndex);
							controller.populate();
							stage.show();

							((Node) (event.getSource())).getScene().getWindow().hide();

						} catch (IOException e) {
							e.printStackTrace();
						}

					} else if (result.get() == ButtonType.CANCEL) {

					}

				}

			} else {

				generateAlert("Error", "This course conflicts with an already existing course in your time table");
			}

		} else if ((!tutExistance && countTutorial != 0) || (!labExistance && countLab != 0)) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText("The lectures are already added in your time table.");
			alert.setContentText("Do you want to add tutorials/labs as well?");
			Optional<ButtonType> result = alert.showAndWait();
			if (!result.isPresent()) {

			} else if (result.get() == ButtonType.OK) {

				relevantCoursesList.getItems().remove(selectedCourseIndex);

				try {

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTT3.fxml"));
					Stage stage = new Stage();
					stage.setTitle("IIIT Delhi");
					stage.setScene(new Scene(loader.load(), 800, 600));
					CreateTT3UI controller = loader.<CreateTT3UI>getController();
					controller.currStudent = currStudent;
					controller.selectedCourse = relevantCourses.get(selectedCourseIndex);
					controller.populate();
					stage.show();

					((Node) (event.getSource())).getScene().getWindow().hide();

				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (result.get() == ButtonType.CANCEL) {

			}

		} else {
			generateAlert("Warning", "This course already exists in your time table");

		}

	}

	/**
	 * This is handler for mouse click of home button
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
	 * This is handler for mouse click of back button
	 * @param event: The mouse event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void backButtonClicked(MouseEvent event) throws ClassNotFoundException {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTT1.fxml"));
			Stage stage = new Stage();
			stage.setTitle("IIIT Delhi");
			stage.setScene(new Scene(loader.load(), 800, 600));
			MakeTimeTableUI controller = loader.<MakeTimeTableUI>getController();
			controller.currStudent = currStudent;
			controller.populate();
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is handler for mouse click of logout
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
	 * This serializes the list of users back into the database
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
	 * This deserializes the list of users into the class
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
	public void generateAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title + "!");
		alert.setHeaderText(null);
		alert.setContentText(message + "!");
		alert.showAndWait();
	}

}
