package bugworld;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;

public class AnimatedEntityCircle extends Circle implements AnimatedEntity {
	Entity entity;
	float size;
	World world;

	public AnimatedEntityCircle(World world, Entity entity, float size, Paint colour) {
		super((entity.getxPos() * size) + (size/2), (entity.getyPos() * size) + (size/2), size/2);
		setFill(colour);
		this.world = world;
		this.size = size;
		this.entity = entity;
	}
	
	public boolean update() {
		if (!world.contains(entity)) {
			// set the colour to be white and hide at the back
			setFill(Color.WHITE);
			this.toBack();
			return true;
		}
		// center should be offset by radius (i.e size/2) so it doesn't go over the edge
		// x and y positions should be adjusted by the size of the image
		this.setCenterX((entity.getxPos() * size) + (size/2));
		this.setCenterY((entity.getyPos() * size) + (size/2));
		return false;
	}
	
	public int getxPos() {
		return this.entity.getxPos();
	}
	
	public int getyPos() {
		return this.entity.getyPos();
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof AnimatedEntityCircle && ((AnimatedEntityCircle)object).getEntity().equals(this.getEntity())) {
			return true;
		}
		return false;
	}
}
