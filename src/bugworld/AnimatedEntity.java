package bugworld;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class AnimatedEntity extends Circle {
	Entity entity;
	float size; 
	World world;

	public AnimatedEntity(World world, Entity entity, float size) {
		super((entity.getxPos() * size) + (size/2), (entity.getyPos() * size) + (size/2), size/2);
		this.world = world;
		this.size = size;
		this.entity = entity;
	}
	
	public AnimatedEntity(World world, Entity entity, float size, Paint color) {
		super((entity.getxPos() * size) + (size/2), (entity.getyPos() * size) + (size/2), size/2, color);
		this.world = world;
		this.size = size;
		this.entity = entity;
	}
	
	public void update() {
		if (!world.contains(entity)) {
			this.setFill(Color.WHITE);
			this.toBack();
		}
		// center should be offset by radius (i.e size/2) so it doesn't go over the edge
		// x and y positions should be adjusted by the size of the image
		this.setCenterX((entity.getxPos() * size) + (size/2));
		this.setCenterY((entity.getyPos() * size) + (size/2));
	}
	
	public int getX() {
		return this.entity.getxPos();
	}
	
	public int getY() {
		return this.entity.getyPos();
	}
}
