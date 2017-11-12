package ui;

import backend.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreateTT3UI {

	public Student currStudent;
	
	@FXML
	private Label name;
	
	@FXML
	private Label title;
	
	@FXML
	private Label email;
	
	
	public void populate() {
		
		name.setText(currStudent.getName());
		title.setText("STUDENT");
		email.setText(currStudent.getEmailID());
		
		
	}
	
	
	
	
}
