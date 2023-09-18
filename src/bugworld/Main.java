package bugworld;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;

public class Main {
	private Stage primaryStage;
	
	public Main(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/* main method */
	public static void main(String[] args) {
		Bug bug1 = new Fly("Fly", '*', 0, 0, 50, true);
		Bug bug2 = new Bee("Bee", '%', 40, 7, 50, 5);
		Bug bug3 = new CarnivoreBug("Carnivore", '&', 68, 0, 50);
		Plant plant1 = new Plant(5,3,7);
		ArrayList<Bug> bugs = new ArrayList<Bug>();
		bugs.add(bug1);
		bugs.add(bug2);
		bugs.add(bug3);
		ArrayList<Plant> plants = new ArrayList<Plant>();
		plants.add(plant1);
		//World world = new World(bugs, plants, 70, 10);
		World world = new World(bugs,10,8,70,20);
		world.drawWorld();
		for (int i=0; i<23; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			}
			catch (InterruptedException error) {
				System.out.println("error: " + error);
			}
			world.updateWorld();
			world.drawWorld();
		}
	}

}
