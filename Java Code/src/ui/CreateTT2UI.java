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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateTT2UI {
	
	public Student currStudent;
	public ArrayList<Course> relevantCourses;
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
	
	public void populate(){
		name.setText(currStudent.getName());
		email.setText(currStudent.getEmailID());
		title.setText(currStudent.getTypeOfUser());
		
		if (relevantCourses.isEmpty()) {
			relevantCoursesList.getItems().add("No courses!");
			relevantPane.setVisible(false);
			addToTimeTable.setVisible(false);
		} else {

			for(int i=0;i<relevantCourses.size();i++) {
				relevantCoursesList.getItems().add(relevantCourses.get(i).getCourseCode());
			}
		}
		
		relevantCoursesList.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				
				try {
					
					if(!(newValue.equalsIgnoreCase("No courses!"))) {

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
	
	@FXML
	private void addCourse(MouseEvent event) throws FileNotFoundException, IOException, ClassNotFoundException{
		
		deserializeUsers();
		int selectedCourseIndex = relevantCoursesList.getSelectionModel().getSelectedIndex();
		Course selectedCourse = relevantCourses.get(selectedCourseIndex);
		currStudent.addToTimeTable(selectedCourse);
		serializeUsers();
		int countLab = 0;
		int countTutorial = 0;
		for(Map.Entry<Slot, List<ClassRoom>> map : selectedCourse.getCourseTimeTable().entrySet()){
			Slot slt = map.getKey();
			List<ClassRoom> room = map.getValue();
			
			if(slt.getPurpose().equals(Slot.TYPES[1])){
				countLab++;
			}
		}
		
		for(Map.Entry<Slot, List<ClassRoom>> map : selectedCourse.getCourseTimeTable().entrySet()){
			Slot slt = map.getKey();
			List<ClassRoom> room = map.getValue();
			
			if(slt.getPurpose().equals(Slot.TYPES[2])){
				countTutorial++;
			}
		}
		if(countLab == 0 && countTutorial == 0){
			
			currStudent.addToTimeTable(selectedCourse);
			
		}else{
		
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirm!");
			alert.setHeaderText("The course has been added.");
			alert.setResizable(false);
			alert.setContentText("Do you want to add tutorials and labs as well?");
			Optional<ButtonType> result = alert.showAndWait();
			if(!result.isPresent()) {
				
			} else if(result.get() == ButtonType.OK) {
			
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
				
				
				
			} else if(result.get() == ButtonType.CANCEL) {
	
				
				
			}
			
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
