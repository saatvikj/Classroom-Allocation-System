package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import backend.AutocompletionlTextField;
import backend.Course;
import backend.Student;
import backend.User;
import exceptions.NoResultFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class MakeTimeTableUI {

	public Student currStudent;

	@FXML
	private Label name;

	@FXML
	private Label title;

	@FXML
	private Label email;

	@FXML
	private AutocompletionlTextField searchKeyword;

	@FXML
	private MenuButton courseType;

	boolean flag = false;

	/**
	 * Handler for clicking of credit in drop down list
	 * 
	 * @param event:
	 *            The event
	 */
	@FXML
	public void credit(ActionEvent event) {

		for (MenuItem item : courseType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Credit")) {
				chk.setSelected(true);
				flag = true;
				courseType.setText("Credit");
			}

		}
		for (MenuItem it : courseType.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Credit")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}

	}

	/**
	 * Handler for clicking of audit on drop down list
	 * 
	 * @param event:
	 *            the event
	 */
	@FXML
	public void audit(ActionEvent event) {

		for (MenuItem item : courseType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.getText().equals("Audit")) {
				chk.setSelected(true);
				courseType.setText("Audit");
			}

		}
		for (MenuItem it : courseType.getItems()) {
			CheckMenuItem ch = (CheckMenuItem) it;
			if (!ch.getText().equals("Audit")) {
				if (ch.isSelected()) {
					ch.setSelected(false);
				}
			}
		}

	}

	private ArrayList<String> rel = new ArrayList<String>();

	/**
	 * This sets the name and email ID of current logged in student and
	 * populates the auto complete textfield suggestions
	 * 
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void populate() throws ClassNotFoundException, FileNotFoundException, IOException {
		name.setText(currStudent.getName());
		email.setText(currStudent.getEmailID());
		title.setText(currStudent.getTypeOfUser());
		deserializeAutoCompleteText();
		searchKeyword.getEntries().addAll(rel);
	}

	/**
	 * Handler for mouse click of search button, it sets the course type and
	 * relevant courses list for the next page.
	 * 
	 * @param event:
	 *            The mouse event
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void searchResults(MouseEvent event) throws ClassNotFoundException, FileNotFoundException, IOException {

		String keywords = searchKeyword.getText().toString();
		String[] keywordsArray = keywords.split(",");
		String type = "";

		for (MenuItem item : courseType.getItems()) {

			CheckMenuItem chk = (CheckMenuItem) item;
			if (chk.isSelected()) {
				type = chk.getText();
			}

		}
		boolean audit = false;
		if (type.equalsIgnoreCase("Audit")) {
			audit = true;
		}

		boolean validity = checkEmptiness(keywords, type);
		if (validity) {
			try {
				ArrayList<Course> relevantCourses = currStudent.giveRelevantCourses(keywordsArray, audit);
				try {

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTT2.fxml"));
					Stage stage = new Stage();
					stage.setTitle("IIIT Delhi");
					stage.setScene(new Scene(loader.load(), 800, 600));
					CreateTT2UI controller = loader.<CreateTT2UI>getController();
					controller.currStudent = currStudent;
					controller.relevantCourses = relevantCourses;
					controller.populate();
					controller.audit = audit;
					stage.show();

					((Node) (event.getSource())).getScene().getWindow().hide();

				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (NoResultFoundException e) {
				generateAlert("Error!", e.getMessage());
			}

		} else {
			generateAlert("Error!", "At least one of the fields is empty, try again.!");
		}
	}

	/**
	 * This checks whether all entered fields are filled or not
	 * 
	 * @param enteredName
	 * @param enteredEmail
	 * @return boolean: returns true if all fields are filled, else returns
	 *         false
	 */
	public boolean checkEmptiness(String enteredName, String enteredEmail) {

		if (enteredName.length() == 0 || enteredEmail.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

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
	 * Handler for mouse click of back button
	 * 
	 * @param event:
	 *            The mouse event
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
	 * Handler for mouse click of logout page
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
	 * This deserializes the auto complete text suggestions
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public void deserializeAutoCompleteText() throws IOException, ClassNotFoundException, FileNotFoundException {

		ObjectInputStream in = null;

		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/autocomplete.txt"));
			String str;

			try {

				while (true) {
					str = (String) in.readObject();
					rel.add(str);
				}

			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {

		} finally {

			if (in != null) {
				in.close();
			} else {
				rel = new ArrayList<String>();
			}

		}

	}

	/**
	 * This function is used to display an alert with the given specifications.
	 * 
	 * @param title:
	 *            The title of the alert
	 * @param message:
	 *            The content of the alert
	 */
	public void generateAlert(String title, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();

	}

}
