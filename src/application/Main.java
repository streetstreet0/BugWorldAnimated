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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane background = new BorderPane();
			Pane controls = new Pane();
			Pane main = new Pane();
			
			controls.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
			main.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
			
			Text mainText = new Text("Select a game");
			mainText.setFont(new Font(25));
			Text secondaryText = new Text();
			Button numberGameButton = new Button("Play guessing game");
			numberGameButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					NumberGame numberGame = new NumberGame(primaryStage);
					numberGame.play();
				}
			});
			
			Button bugWorldButton = new Button("Look at a bug world");
			bugWorldButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					BugWorldAnimation bugWorld = new BugWorldAnimation(primaryStage);
					bugWorld.begin();
				}
			});
			
			VBox controlPane = new VBox();
			controlPane.getChildren().add(numberGameButton);
			controlPane.getChildren().add(bugWorldButton);
			controlPane.setAlignment(Pos.TOP_LEFT);
			controls.getChildren().add(controlPane);
			controlPane.setSpacing(5);
			numberGameButton.setMaxWidth(Double.MAX_VALUE);
			bugWorldButton.setMaxWidth(Double.MAX_VALUE);
			
			VBox centrePane = new VBox();
			centrePane.getChildren().add(mainText);
			centrePane.getChildren().add(secondaryText);
			main.getChildren().add(centrePane);
			
			background.setLeft(controls);
			background.setCenter(main);
			
			Scene scene = new Scene(background);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Nkdajf");
			
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
