package bugworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class BugWorldAnimation {
	private Stage primaryStage;
	private Scene previousScene;
	private static final float size = 10;
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
		primaryStage.setTitle("In de begninging");
		generateWorld();
		drawWorld();
	}
	
	public void generateWorld() {
		//Bug bug1 = new Fly("Fly", '*', 0, 0, 50, true);
		Bug bug2 = new Bee("Bee", '%', 40, 7, 50, 5);
		Bug bug3 = new CarnivoreBug("Carnivore", '&', 33, 0, 50);
		ArrayList<Bug> bugs = new ArrayList<Bug>();
		//bugs.add(bug1);
		bugs.add(bug2);
		bugs.add(bug3);
		world = new World(bugs,10,8,55,50);
	}
	
	public void drawWorld() {
		float rectWidth = size * world.getWidth();
		float rectHeight = size * world.getHeight();
		
		Rectangle rect = new Rectangle(rectWidth+1, rectHeight+1, Color.WHITE);
		rect.setStroke(Color.BLACK);
		
		ArrayList<AnimatedEntity> entities = new ArrayList<AnimatedEntity>();
		for (int i=0; i<world.getBugsSize(); i++) {
			entities.add(new AnimatedEntity(world, world.getBugAtIndex(i), size, Color.RED));
		}
		for (int i=0; i<world.getPlantsSize(); i++) {
			entities.add(new AnimatedEntity(world, world.getPlantAtIndex(i), size, Color.GREEN));
		}
		for (int i=0; i<world.getObstaclesSize(); i++) {
			entities.add(new AnimatedEntity(world, world.getObstacleAtIndex(i), size, Color.BROWN));
		}
		Group animation = new Group();
		for (AnimatedEntity entity : entities) {
			animation.getChildren().add(entity);
			entity.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					System.out.println(entity.getX() + ", " + entity.getY());
				}
			});
		}
		animation.maxWidth(rectWidth+1);
		animation.minWidth(rectWidth+1);
		animation.maxHeight(rectHeight+1);
		animation.minHeight(rectHeight+1);

		
		Button button = new Button();
		button.setText("Quit");
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				endWorld();
			}
		});
		
		VBox worldBox = new VBox();
		worldBox.setMaxSize(rectWidth+1, rectHeight+1);
		worldBox.setMinSize(rectWidth+1, rectHeight+1);
		//world.getChildren().add(rect);
		worldBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		worldBox.getChildren().add(animation);
		
		VBox pane = new VBox();
		pane.getChildren().add(worldBox);
		pane.getChildren().add(button);
		pane.setSpacing(5);
		pane.setAlignment(Pos.CENTER);
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
		
		KeyFrame frame = new KeyFrame(Duration.millis(100), new WorldEventHandler(world, entities));
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

}
