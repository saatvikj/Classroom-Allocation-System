package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreateTT3UI {

	public Student currStudent;
	public Course selectedCourse;
	
	private ArrayList<Slot> slotList = new ArrayList<Slot>();
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
	private Button addTutorialButton;
	
	@FXML
	private ListView<String> labRecordsList;
	
	@FXML
	private ListView<String> tutorialRecordsList;
	
	
	
	
	public void populate() {
		
		name.setText(currStudent.getName());
		title.setText("STUDENT");
		email.setText(currStudent.getEmailID());
		
		for(Map.Entry<Slot, List<ClassRoom>> map : selectedCourse.getCourseTimeTable().entrySet()){
			Slot slt = map.getKey();
			List<ClassRoom> room = map.getValue();
			
			if(slt.getPurpose().equals(Slot.TYPES[1])){
				for(int i = 0; i <room.size(); i++){
					labRecordsList.setVisible(true);
					addLabButton.setVisible(true);
					slotList.add(slt);
					rooomsList.add(room.get(i));
					labRecordsList.getItems().add(slt.getDay() + " " + slt.getStartTime() + "-" + slt.getEndTime() + ", " + room.get(i).getRoomNumber());
			
				}
			}
			else if(slt.getPurpose().equals(Slot.TYPES[2])){
				for(int i = 0; i < room.size(); i++){
					slotList.add(slt);
					rooomsList.add(room.get(i));
					tutorialRecordsList.setVisible(true);
					addTutorialButton.setVisible(true);
					tutorialRecordsList.getItems().add(slt.getDay() + " " + slt.getStartTime() + "-" + slt.getEndTime() + ", " + room.get(i).getRoomNumber());
			
				}
			}
		}
		
	}
	
	@FXML
	private void addLabs(MouseEvent event) throws ClassNotFoundException, FileNotFoundException, IOException{
		deserializeUsers();
		int index = labRecordsList.getSelectionModel().getSelectedIndex();
		currStudent.addToTimeTableLabs(slotList.get(index), selectedCourse);
		serializeUsers();		
	}
	
	@FXML
	private void addTutorials(MouseEvent event) throws ClassNotFoundException, FileNotFoundException, IOException{
		deserializeUsers();
		int index = tutorialRecordsList.getSelectionModel().getSelectedIndex();
		currStudent.addToTimeTableTutorials(slotList.get(index), selectedCourse);
		serializeUsers();
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
	
	
}
