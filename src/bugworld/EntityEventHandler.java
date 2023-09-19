package bugworld;

import javafx.event.*;

public class EntityEventHandler implements EventHandler<ActionEvent> {
	private AnimatedEntity entity;
	
	public EntityEventHandler(AnimatedEntity entity) {
		this.entity = entity;
	}

	@Override
	public void handle(ActionEvent event) {
		entity.update();
	}

}
