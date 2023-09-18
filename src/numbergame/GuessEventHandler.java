package numbergame;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class GuessEventHandler implements EventHandler<ActionEvent> {
	NumberGame game;
	TextField input;
	Button button;
	
	public GuessEventHandler(NumberGame game, TextField input, Button button) {
		this.game = game;
		this.input = input;
		this.button = button;
	}

	@Override
	public void handle(ActionEvent event) {
		String guess = input.getText();
		try {
			game.checkGuess(guess, button);
		}
		catch (NumberFormatException error) {
			game.setText("Guesses must be integers");
		}
	}

}
