package bugworld;

import javafx.event.*;

public class EntityEventHandler implements EventHandler<ActionEvent> {
	private AnimatedEntity entity;
	private boolean shouldRemove = false;
	
	public EntityEventHandler(AnimatedEntity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity.getEntity();
	}
	
	public AnimatedEntity getAnimatedEntity() {
		return entity;
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}

	@Override
	public void handle(ActionEvent event) {
		shouldRemove = entity.update();
	}

}
