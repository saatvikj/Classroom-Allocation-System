package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import backend.ClassRoom;
import backend.Course;
import backend.Slot;
import backend.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreateTT3UI {

	public Student currStudent;
	public Course selectedCourse;
	
	@FXML
	private Label name;
	
	@FXML
	private Label title;
	
	@FXML
	private Label email;
	
	@FXML
	private ListView<String> labRecordsList;
	
	@FXML
	private ListView<String> tutorialRecordsList;
	
	
	
	
	public void populate() {
		
		name.setText(currStudent.getName());
		title.setText("STUDENT");
		email.setText(currStudent.getEmailID());
		
		for(Map.Entry<Slot, ClassRoom> map : selectedCourse.getCourseTimeTable().entrySet()){
			Slot slt = map.getKey();
			ClassRoom room = map.getValue();
			System.out.println(slt.getPurpose());
			if(slt.getPurpose().equals(Slot.TYPES[1])){
				labRecordsList.getItems().add(slt.getDay() + " " + slt.getStartTime() + "-" + slt.getEndTime() + " , " + room.getRoomNumber());
			}
			else if(slt.getPurpose().equals(Slot.TYPES[2])){
				tutorialRecordsList.getItems().add(slt.getDay() + " " + slt.getStartTime() + "-" + slt.getEndTime() + " , " + room.getRoomNumber());
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

	
}
