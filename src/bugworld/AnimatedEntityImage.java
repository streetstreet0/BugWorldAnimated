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

public class AnimatedEntityImage extends ImageView implements AnimatedEntity {
	Entity entity;
	float size;
	World world;

	public AnimatedEntityImage(String url, World world, Entity entity, float size) throws FileNotFoundException {
		super(new Image(new FileInputStream(new File(url))));
		setFitWidth(size);
		setFitHeight(size);
		setX(entity.getxPos()*size);
		setY(entity.getyPos()*size);
		//super((entity.getxPos() * size) + (size/2), (entity.getyPos() * size) + (size/2), size/2);
		this.world = world;
		this.size = size;
		this.entity = entity;
	}
	
	public boolean update() {
		if (!world.contains(entity)) {
			// set image to be an empty png
			try {
				setImage(new Image( new FileInputStream(new File("images/empty.png"))));
			}
			catch (FileNotFoundException error) {
				setImage(new Image("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/6a840049-1580-4152-9ec2-39f96c4a5a8b/d4y5rky-3edd8db4-4f42-432d-ac44-e169d5dbd598.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwic3ViIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsImF1ZCI6WyJ1cm46c2VydmljZTpmaWxlLmRvd25sb2FkIl0sIm9iaiI6W1t7InBhdGgiOiIvZi82YTg0MDA0OS0xNTgwLTQxNTItOWVjMi0zOWY5NmM0YTVhOGIvZDR5NXJreS0zZWRkOGRiNC00ZjQyLTQzMmQtYWM0NC1lMTY5ZDVkYmQ1OTgucG5nIn1dXX0.oLlz0FYqiHCb-uNvw3DsXdjeAn_bndHVE3-eYmr4BiE"));
			}
			this.toBack();
			return true;
		}
		if (entity instanceof Plant) {
			int plantSize = ((Plant) entity).getSize();
			if (plantSize > 10) {
				nextImage(plantSize);
			}
		}
		// center should be offset by radius (i.e size/2) so it doesn't go over the edge
		// x and y positions should be adjusted by the size of the image
		this.setX((entity.getxPos() * size));
		this.setY((entity.getyPos() * size));
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
	
	public void nextImage(int plantSize) {
		try {
			if (plantSize == 11) {
				setImage(new Image(new FileInputStream(new File("images/small tree.png"))));
			}
			else if (plantSize == 21) {
				setImage(new Image(new FileInputStream(new File("images/large tree.png"))));
			}
		}
		catch (FileNotFoundException error) {
			
		}
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof AnimatedEntityImage && ((AnimatedEntityImage)object).getEntity().equals(this.getEntity())) {
			return true;
		}
		return false;
	}
}
