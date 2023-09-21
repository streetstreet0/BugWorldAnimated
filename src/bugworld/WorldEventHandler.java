package bugworld;

import java.io.FileNotFoundException;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class WorldEventHandler implements EventHandler<ActionEvent> {
	private HashSet<EntityEventHandler> entityHandlers;
	private World world;
	private float size;
	private Group group;
	
	public WorldEventHandler(World world, Group group, HashSet<AnimatedEntity> entities, float size) {
		this.world = world;
		this.size = size;
		this.group = group;
		entityHandlers = new HashSet<EntityEventHandler>();
		for (AnimatedEntity entity : entities) {
			entityHandlers.add(new EntityEventHandler(entity));
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		// update the world internall
		world.updateWorld();
		
		
		// updates the visuals
		HashSet<EntityEventHandler> toRemove = new HashSet<EntityEventHandler>();
		for (EntityEventHandler entityHandler : entityHandlers) {
			entityHandler.handle(event);
			if (entityHandler.shouldRemove()) {
				toRemove.add(entityHandler);
			}
		}
		// removes any dead bugs
		for (EntityEventHandler entityHandler : toRemove) {
			entityHandlers.remove(entityHandler);
			AnimatedEntity entity = (AnimatedEntity) entityHandler.getAnimatedEntity();
			if (entity instanceof Node) {
				group.getChildren().remove((Node)entity);
			}
		}
		
		// any new plants that have spawned need to be added
		for (int i=0; i<world.getPlantsSize(); i++) {
			Plant plant = world.getPlantAtIndex(i);
			if (plant.isNew()) {
				String url = "images/grass.png";
				AnimatedEntity newPlant;
				try {
					newPlant = new AnimatedEntityImage(url, world, (Entity)world.getPlantAtIndex(i), size);
				}
				catch (FileNotFoundException erorr) {
					newPlant = new AnimatedEntityCircle(world, (Entity)world.getPlantAtIndex(i), size, Color.GREEN);
				}
				entityHandlers.add(new EntityEventHandler(newPlant));
				// it is always a node
				if (newPlant instanceof Node) {
					group.getChildren().add((Node)newPlant);
				}
			}
		}
		
	}
}
