package bugworld;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class AnimatedEntity extends Circle {
	Entity entity;
	float size; 

	public AnimatedEntity(Entity entity, float size) {
		super((entity.getxPos() * size) + (size/2), (entity.getyPos() * size) + (size/2), size/2);
		this.size = size;
		this.entity = entity;
	}
	
	public AnimatedEntity(Entity entity, float size, Paint color) {
		super((entity.getxPos() * size) + (size/2), (entity.getyPos() * size) + (size/2), size/2, color);
		this.size = size;
		this.entity = entity;
	}
	
	public void update() {
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
