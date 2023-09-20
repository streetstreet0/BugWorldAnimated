package bugworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

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
		world = new World(25,30,8,55,50);
	}
	
	public void drawWorld() {
		float rectWidth = size * world.getWidth();
		float rectHeight = size * world.getHeight();
		
		/*
		 * Image urls
		 * Fly: https://webstockreview.net/images/fly-clipart-4.png
		 */
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
		animation.setAutoSizeChildren(false);
		
		for (int i=0; i<4; i++) {
			ImageView corner = new ImageView("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/6a840049-1580-4152-9ec2-39f96c4a5a8b/d4y5rky-3edd8db4-4f42-432d-ac44-e169d5dbd598.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwic3ViIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsImF1ZCI6WyJ1cm46c2VydmljZTpmaWxlLmRvd25sb2FkIl0sIm9iaiI6W1t7InBhdGgiOiIvZi82YTg0MDA0OS0xNTgwLTQxNTItOWVjMi0zOWY5NmM0YTVhOGIvZDR5NXJreS0zZWRkOGRiNC00ZjQyLTQzMmQtYWM0NC1lMTY5ZDVkYmQ1OTgucG5nIn1dXX0.oLlz0FYqiHCb-uNvw3DsXdjeAn_bndHVE3-eYmr4BiE");
			animation.getChildren().add(corner);
			corner.setFitWidth(1);
			corner.setFitHeight(1);
			if (i % 2 == 0) {
				corner.setX(0);
			}
			else {
				corner.setX(rectWidth);
			}
			if (i < 2) {
				corner.setY(0);
			}
			else {
				corner.setY(rectHeight);
			}
		}
		
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
