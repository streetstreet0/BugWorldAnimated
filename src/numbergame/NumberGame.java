package numbergame;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class NumberGame {
	private Scene previousScene;
	private Stage primaryStage;
	private VBox pane;
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
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
	}
	
	public void setText(String str) {
		text.setText(str);
	}
	
	public void restartButton(Button button) {
		button.setText("Play Again");
		button.setOnAction(new RestartEventHandler(this));
	}
	
	public void imageBackground(String url) {
		ArrayList<BackgroundFill> fill = new ArrayList<BackgroundFill>();
		fill.add(new BackgroundFill(Color.CHARTREUSE, null, null));
		ArrayList<BackgroundImage> image = new ArrayList<BackgroundImage>();
		image.add(new BackgroundImage(new Image(url), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null));
		pane.setBackground(new Background(fill, image));
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
	}
	
	public void checkGuess(String guess, Button button) {
		int guessNum = Integer.parseInt(guess);
		guessesRemaining--;
		if (guessNum == answer) {
			imageBackground("https://www.asiaone.com/sites/default/files/original_images/Apr2021/20210401_harold_fb.jpg");
			header.setText("Congratulations!");
			text.setText("Correct! (in " + (defaultGuesses - guessesRemaining) + " guesses)");
			restartButton(button);
		}
		else if (guessesRemaining == 0) {
			imageBackground("https://media.istockphoto.com/photos/laughing-man-pointing-at-you-picture-id525966115");
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
		header.setFont(new Font(25));
		text = new Text();
		text.setFont(new Font(18));
		
		TextField input = new TextField();
		input.setMaxWidth(150);
		input.setPromptText("Enter Guess Here");
		
		
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
		
		pane = new VBox();
		pane.getChildren().add(header);
		pane.getChildren().add(text);
		pane.getChildren().add(input);
		pane.getChildren().add(button);
		pane.getChildren().add(quit);
		pane.setAlignment(Pos.CENTER);
		pane.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
		pane.setSpacing(5);
		
		Scene scene = new Scene(pane);
		
		primaryStage.setTitle("NOBODY READS THIS ANYWAYS");
		primaryStage.setScene(scene);
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
	}

}
