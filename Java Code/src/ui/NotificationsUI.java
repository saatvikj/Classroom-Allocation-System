package ui;

import java.io.IOException;
import java.util.ArrayList;

import backend.Admin;
import backend.Faculty;
import backend.Student;
import backend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NotificationsUI {

	public User currUser;
	public ArrayList<String> notifs = new ArrayList<String>();
	@FXML
	private Label name;

	@FXML
	private Label email;

	@FXML
	private Label title;
	
	@FXML
	private ListView<String> notificationsListView;

	
	public void populate() throws ClassNotFoundException, IOException {
		name.setText(currUser.getName());
		email.setText(currUser.getEmailID());
		title.setText(currUser.getTypeOfUser().toUpperCase());
		
		if(currUser.getTypeOfUser().equals("Admin"))
		{
			Admin currAdmin = (Admin) currUser;
			currAdmin.deserializeRequests();
		}
		notifs = currUser.populateNotifications();
		if(notifs.size() == 0)
		{
			notificationsListView.getItems().add("You have no notifications!");
		}
		else
		{
			for(int i = 0; i < notifs.size(); i++)
			{
				notificationsListView.getItems().add(notifs.get(i));
			}
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

}
