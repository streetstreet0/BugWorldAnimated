package bugworld;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class WorldEventHandler implements EventHandler<ActionEvent> {
	private ArrayList<EntityEventHandler> entityHandlers;
	private World world;
	
	public WorldEventHandler(World world, ArrayList<AnimatedEntity> entities) {
		this.world = world;
		entityHandlers = new ArrayList<EntityEventHandler>();
		for (AnimatedEntity entity : entities) {
			entityHandlers.add(new EntityEventHandler(entity));
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		world.updateWorld();
		for (EntityEventHandler entityHandler : entityHandlers) {
			entityHandler.handle(event);
		}
		
	}
}
