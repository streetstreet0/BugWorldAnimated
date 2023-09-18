package numbergame;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class NumberGame {
	private Scene previousScene;
	private Stage primaryStage;
	int min;
	int max;
	int answer;
	private static final String defaultText = "Guess a number between 1 and 100 (inclusive)";	

	public NumberGame(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void endGame() {
		primaryStage.setScene(previousScene);
	}
	
	public void checkGuess(String guess, Text text) {
		int guessNum = Integer.parseInt(guess);
		if (guessNum == answer) {
			text.setText("Correct!");
		}
		else if (guessNum < answer) {
			text.setText("Guess higher!");
			min = Math.max(min, guessNum);
		}
		else {
			text.setText("Guess lower!");
			max = Math.min(max, guessNum);
		}
	}
	
	public void play() {
		begin();
	}
	
	public void begin() {
		min = 1;
		max = 100;
		answer = (int)(Math.random() * 100) + 1;
		Text header = new Text(defaultText);
		Text text = new Text();
		
		TextField input = new TextField();
		input.maxWidth(10);
		
		
		Button button = new Button();
		button.setText("Enter Guess");
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String guess = input.getText();
				try {
					checkGuess(guess, text);
				}
				catch (NumberFormatException error) {
					text.setText("Guesses must be integers");
				}
			}
		});
		
		Button quit = new Button("Quit");
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				endGame();
			}
		});
		
		VBox pane = new VBox();
		pane.getChildren().add(header);
		pane.getChildren().add(text);
		pane.getChildren().add(input);
		pane.getChildren().add(button);
		pane.getChildren().add(quit);
		pane.setAlignment(Pos.CENTER);
		pane.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
		//pane.setBackground(new Background(new BackgroundImage(new Image("../images/laughing at.jpg"), null, null, null, null)));
		
		Scene scene = new Scene(pane);
		
		primaryStage.setTitle("NOBODY READS THIS ANYWAYS");
		primaryStage.setScene(scene);
	}

}
