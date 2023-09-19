package bugworld;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class WorldEventHandler implements EventHandler<ActionEvent> {
	public World world;
	
	public WorldEventHandler(World world) {
		this.world = world;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		
		
	}
}
