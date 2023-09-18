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
	private int guessesRemaining;
	private int min;
	private int max;
	private int answer;
	private Text header;
	private Text text;
	private static final String defaultText = "Guess a number between 1 and 100 (inclusive)";
	private static final int defaultMin = 1;
	private static final int defaultMax = 100;
	private static final int defaultGuesses = 10;

	public NumberGame(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.previousScene = primaryStage.getScene();
	}
	
	public void endGame() {
		primaryStage.setScene(previousScene);
	}
	
	public void setText(String str) {
		text.setText(str);
	}
	
	public void restartButton(Button button) {
		button.setText("Play Again");
		button.setOnAction(new RestartEventHandler(this));
	}
	
	public void checkGuess(String guess, Button button) {
		int guessNum = Integer.parseInt(guess);
		guessesRemaining--;
		if (guessNum == answer) {
			header.setText("Congratulations!");
			text.setText("Correct! (in " + (defaultGuesses - guessesRemaining) + "guesses");
			restartButton(button);
		}
		else if (guessesRemaining == 0) {
			header.setText("Game Over!");
			text.setText("The answer was " + answer);
			restartButton(button);
		}
		else if (guessNum < answer) {
			text.setText("Guess higher!");
			min = Math.max(min, guessNum);
			button.setText("Enter Guess (" + guessesRemaining + " guesses remaining)");
		}
		else {
			text.setText("Guess lower!");
			max = Math.min(max, guessNum);
			button.setText("Enter Guess (" + guessesRemaining + " guesses remaining)");
		}
	}
	
	public void play() {
		begin();
	}
	
	public void begin() {
		min = defaultMin;
		max = defaultMax;
		guessesRemaining = defaultGuesses;
		answer = (int)(Math.random() * (defaultMax-defaultMin + 1)) + defaultMin;
		header = new Text(defaultText);
		text = new Text();
		
		TextField input = new TextField();
		input.maxWidth(10);
		
		
		Button button = new Button();
		button.setText("Enter Guess (" + guessesRemaining + " guesses remaining)");
		GuessEventHandler guessEvent = new GuessEventHandler(this, input, button);
		button.setOnAction(guessEvent);
		
		Button quit = new Button("Quit");
		quit.setOnAction(new EventHandler<ActionEvent>() {
			
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
