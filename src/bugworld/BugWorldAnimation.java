package bugworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class BugWorldAnimation {
	private Stage primaryStage;
	private Scene previousScene;
	private static final float size = 20;
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
		world = new World(15,20,5,25,25);
	}
	
	public void drawWorld() {
		float rectWidth = size * world.getWidth();
		float rectHeight = size * world.getHeight();
		
		HashSet<AnimatedEntity> entities = new HashSet<AnimatedEntity>();
		for (int i=0; i<world.getBugsSize(); i++) {
			String url;
			if (world.getBugAtIndex(i) instanceof Fly) {
				url = "images/fly.png";
			}
			else if (world.getBugAtIndex(i) instanceof Bee) {
				url = "images/bee.png";
			}
			else if (world.getBugAtIndex(i) instanceof Wasp) {
				url = "images/wasp.png";
			}
			else {
				url = "images/dragonfly.png";
			}
			try {
				entities.add(new AnimatedEntityImage(url, world, world.getBugAtIndex(i), size));
			}
			catch (FileNotFoundException error) {
				entities.add(new AnimatedEntityCircle(world, world.getBugAtIndex(i), size, Color.RED));
			}
		}
		for (int i=0; i<world.getPlantsSize(); i++) {
			String url;
			int plantSize = world.getPlantAtIndex(i).getSize();
			if (plantSize < 11) {
				url = "images/grass.png";
			}
			else if (plantSize < 21) {
				url = "images/small tree.png";
			}
			else {
				url = "images/large tree.png";
			}
			try {
				entities.add(new AnimatedEntityImage(url, world, world.getPlantAtIndex(i), size));
			}
			catch (FileNotFoundException error) {
				entities.add(new AnimatedEntityCircle(world, world.getPlantAtIndex(i), size, Color.GREEN));
			}
		}
		for (int i=0; i<world.getObstaclesSize(); i++) {
			String url = "images/obstacle.png";
			try {
				entities.add(new AnimatedEntityImage(url, world, world.getObstacleAtIndex(i), size));
			}
			catch (FileNotFoundException error) {
				entities.add(new AnimatedEntityCircle(world, world.getObstacleAtIndex(i), size, Color.BLACK));
			}
		}
		Group animation = new Group();
		for (AnimatedEntity entity : entities) {
			if (entity instanceof Node) {
				animation.getChildren().add((Node)entity);
			}
		}
		animation.maxWidth(rectWidth+1);
		animation.minWidth(rectWidth+1);
		animation.maxHeight(rectHeight+1);
		animation.minHeight(rectHeight+1);
		animation.setAutoSizeChildren(false);
		
		for (int i=0; i<4; i++) {
			// corners are empty pngs
			ImageView corner;
			try {
				corner = new ImageView(new Image(new FileInputStream(new File("images/empty.png"))));
			}
			catch (FileNotFoundException error){
				corner = new ImageView("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/6a840049-1580-4152-9ec2-39f96c4a5a8b/d4y5rky-3edd8db4-4f42-432d-ac44-e169d5dbd598.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwic3ViIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsImF1ZCI6WyJ1cm46c2VydmljZTpmaWxlLmRvd25sb2FkIl0sIm9iaiI6W1t7InBhdGgiOiIvZi82YTg0MDA0OS0xNTgwLTQxNTItOWVjMi0zOWY5NmM0YTVhOGIvZDR5NXJreS0zZWRkOGRiNC00ZjQyLTQzMmQtYWM0NC1lMTY5ZDVkYmQ1OTgucG5nIn1dXX0.oLlz0FYqiHCb-uNvw3DsXdjeAn_bndHVE3-eYmr4BiE");
			}
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
		
		Button quit = new Button();
		quit.setText("Quit");
		
		
		
		Button pausePlay = new Button();
		pausePlay.setText("Play");
		
		
		Slider speedSlider = new Slider(1, 3, 0.1);
		speedSlider.setShowTickMarks(true);
		speedSlider.setShowTickLabels(true);
		speedSlider.setMajorTickUnit(0.25f);
		speedSlider.setBlockIncrement(0.1f);
		
		
		GridPane buttonPane = new GridPane();
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().add(pausePlay);
		buttonPane.getChildren().add(speedSlider);
		GridPane.setColumnIndex(pausePlay, 1);
		GridPane.setColumnIndex(speedSlider, 0);
		
		
		VBox worldBox = new VBox();
		worldBox.setMaxSize(rectWidth+1, rectHeight+1);
		worldBox.setMinSize(rectWidth+1, rectHeight+1);
		//world.getChildren().add(rect);
		worldBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		worldBox.getChildren().add(animation);
		
		
		
		VBox pane = new VBox();
		pane.getChildren().add(worldBox);
		pane.getChildren().add(buttonPane);
		pane.getChildren().add(quit);
		pane.setSpacing(5);
		pane.setAlignment(Pos.CENTER);
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setWidth(primaryStage.getWidth());
		primaryStage.setHeight(primaryStage.getHeight());
		
		KeyFrame frame = new KeyFrame(Duration.millis(800), new WorldEventHandler(world, animation, entities, size));
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		
		// set control buttons actions
		quit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				timeline.stop();
				endWorld();
			}
		});
		
		pausePlay.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (pausePlay.getText().equals("Play")) {
					timeline.play();
					pausePlay.setText("Pause");
				}
				else {
					timeline.pause();
					pausePlay.setText("Play");
				}
			}
		});
		
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			
			@Override
            public void changed(ObservableValue<? extends Number> observed, Number oldValue, Number newValue) {
					double rate = (double) newValue;
                    timeline.setRate(rate);
                }
            });
	}

}
