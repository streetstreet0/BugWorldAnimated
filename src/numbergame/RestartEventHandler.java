package numbergame;

import javafx.event.*;

public class RestartEventHandler implements EventHandler<ActionEvent> {
	NumberGame game;
	
	public RestartEventHandler(NumberGame game) {
		this.game = game;
	}

	@Override
	public void handle(ActionEvent event) {
		game.begin();
	}

}
