package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import backend.AutocompletionlTextField;
import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
	
	private ArrayList<String> rel = new ArrayList<String>();

	public void populate() throws ClassNotFoundException, FileNotFoundException, IOException {
		name.setText(currStudent.getName());
		email.setText(currStudent.getEmailID());
		title.setText(currStudent.getTypeOfUser());
		deserializeAutoCompleteText();
		searchKeyword.getEntries().addAll(rel);
	}

	@FXML
	private void searchResults(MouseEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/CreateTT2.fxml"));
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
	
	
	public void deserializeAutoCompleteText() throws IOException, ClassNotFoundException, FileNotFoundException {

		/*
		 * Deserializes the list of registered users into the ArrayList so that
		 * checking can be done.
		 * 
		 */
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

}
