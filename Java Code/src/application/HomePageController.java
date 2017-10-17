package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController {

	@FXML
	private Button buttonSignUp;
	
	@FXML
	private void signup(ActionEvent event) {
		
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Stage stage = new Stage();
            stage.setTitle("IIIT Delhi");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
