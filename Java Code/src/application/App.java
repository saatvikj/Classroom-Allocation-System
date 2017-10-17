package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			
			Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
			Scene s = new Scene(root, 800,600);
			primaryStage.setTitle("IIIT Delhi");
			Button signup = new Button();
			signup.setOnAction(new	SignUpEvent());
			primaryStage.setScene(s);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	class	SignUpEvent	implements	EventHandler<ActionEvent>	{		
		@Override	
		public void	handle(ActionEvent	event)	{	
				
		      try{
		            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		            Parent root1 = (Parent) fxmlLoader.load();
		            Stage stage = new Stage();
		            stage.initModality(Modality.APPLICATION_MODAL);
		            stage.initStyle(StageStyle.UNDECORATED);
		            stage.setTitle("IIIT Delhi");
		            stage.setScene(new Scene(root1));  
		            stage.show();
		          }
		}
	}
}
