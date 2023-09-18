package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import numbergame.*;
import bugworld.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Text mainText = new Text("Select a game");
			Text secondaryText = new Text();
			Button numberGame = new Button("Play guessing game");
			numberGame.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					NumberGame game = new NumberGame(primaryStage);
					game.play();
				}
			});
			
			Button bugWorld = new Button("Look at a bug world");
			bugWorld.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					//bugworld.Main bugWorld = new bugworld.Main(primaryStage);
					secondaryText.setText("Bug world has not been implemented yet");
				}
			});
			
			VBox pane = new VBox();
			pane.getChildren().add(mainText);
			pane.getChildren().add(secondaryText);
			pane.getChildren().add(numberGame);
			pane.getChildren().add(bugWorld);
			pane.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			
			primaryStage.setWidth(600);
			primaryStage.setHeight(600);
			primaryStage.show();
			
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
