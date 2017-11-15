package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class App extends Application {

	/**
	 * The main function that runs the GUI.
	 * 
	 * @param primaryStage:
	 *            The main stage to run the GUI
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/fxml/HomePage.fxml"));
			Scene s = new Scene(root, 800, 600);
			primaryStage.setTitle("IIIT Delhi");
			s.setFill(Color.WHITE);
			primaryStage.setScene(s);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main function that launches the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
