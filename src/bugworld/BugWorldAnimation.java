package bugworld;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.*;

public class BugWorldAnimation {
	private Stage primaryStage;
	private Scene previousScene;
	World world;
	
	public BugWorldAnimation(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void generateWorld() {
		Bug bug1 = new Fly("Fly", '*', 0, 0, 50, true);
		Bug bug2 = new Bee("Bee", '%', 40, 7, 50, 5);
		Bug bug3 = new CarnivoreBug("Carnivore", '&', 68, 0, 50);
		ArrayList<Bug> bugs = new ArrayList<Bug>();
		bugs.add(bug1);
		bugs.add(bug2);
		bugs.add(bug3);
		world = new World(bugs,10,8,70,20);
	}
	
	public void drawWorld() {
		Rectangle rectangle = new Rectangle();
		rectangle.setWidth(world.getWidth());
		rectangle.setHeight(world.getHeight());
	}

}
