package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUpUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			
			Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
			Scene s = new Scene(root, 800,600);
			primaryStage.setTitle("IIIT Delhi");
			primaryStage.setScene(s);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
