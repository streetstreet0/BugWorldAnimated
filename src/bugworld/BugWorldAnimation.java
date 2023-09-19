package bugworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class BugWorldAnimation {
	private Stage primaryStage;
	private Scene previousScene;
	private static final int size = 10;
	World world;
	
	public BugWorldAnimation(Stage primaryStage) {
		this.primaryStage = primaryStage;
		previousScene = primaryStage.getScene();
	}
	
	public void endWorld() {
		primaryStage.setScene(previousScene);
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
	}
	
	public void begin() {
		generateWorld();
		drawWorld();
	}
	
	public void generateWorld() {
		Bug bug1 = new Fly("Fly", '*', 0, 0, 50, true);
		Bug bug2 = new Bee("Bee", '%', 40, 7, 50, 5);
		Bug bug3 = new CarnivoreBug("Carnivore", '&', 68, 0, 50);
		ArrayList<Bug> bugs = new ArrayList<Bug>();
		bugs.add(bug1);
		bugs.add(bug2);
		bugs.add(bug3);
		world = new World(bugs,10,8,55,50);
	}
	
	public void drawWorld() {
		int rectWidth = size * world.getWidth();
		int rectHeight = size * world.getHeight();
		
		Rectangle rect = new Rectangle(rectWidth+1, rectHeight+1, Color.WHITE);
		rect.setStroke(Color.BLACK);
		
		Button button = new Button();
		button.setText("quit");
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				endWorld();
			}
		});
		
		VBox pane = new VBox();
		pane.getChildren().add(rect);
		pane.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
	}
	
	public void animateBugWorld(int frameTime) {
		
		
		KeyFrame frame = new KeyFrame(Duration.millis(frameTime), new WorldEventHandler(world));
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

}
