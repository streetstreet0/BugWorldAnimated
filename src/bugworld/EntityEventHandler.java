package bugworld;

import javafx.event.*;

public class EntityEventHandler implements EventHandler<ActionEvent> {
	private AnimatedEntity animatedEntity;
	private boolean shouldRemove = false;
	
	public EntityEventHandler(AnimatedEntity entity) {
		this.animatedEntity = entity;
	}
	
	public Entity getEntity() {
		return animatedEntity.getEntity();
	}
	
	public AnimatedEntity getAnimatedEntity() {
		return animatedEntity;
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}

	@Override
	public void handle(ActionEvent event) {
		shouldRemove = animatedEntity.update();
	}

}
