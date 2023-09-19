package bugworld;

import java.util.ArrayList;
import java.util.Scanner;

public class World {
	/* class variables */
	ArrayList<Bug> bugs;
	ArrayList<Plant> plants = new ArrayList<Plant>();
	ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	// bugs and plants must be between 0 and (height-1) or 0 and (width-1) for x and y positions
	// i.e x=width is not valid and y=height is not valid
	int height;
	int width;
	int numPlants;
	//ArrayList<int[]> availablePositions = new ArrayList<int[]>();
	//ArrayList<int[]> takenPositions = new ArrayList<int[]>();
	
	
	/* constructors */
	// hybrid contructor
	public World(ArrayList<Bug> bugs, int numPlants, int numObstacles, int width, int height) {
		this.bugs = bugs;
		this.width = width;
		this.height = height;
		this.numPlants = numPlants;
		generateWorld(numObstacles);
	}
	// user input constructor
	public World(int numPlants, int width, int height) {
		this.width = width;
		this.height = height;
		this.numPlants = numPlants;
		generateWorldUserInput();
	}
	// testing constructor
	public World(ArrayList<Bug> bugs, ArrayList<Plant> plants, int width, int height) {
		this.bugs = bugs;
		this.plants = plants;
		this.width = width;
		this.height = height;
	}
	// empty world constructor
	public World(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	
	

	/* getters */
	public int getBugsSize() {
		return bugs.size();
	}
	public int getPlantsSize() {
		return plants.size();
	}
	public int getObstaclesSize() {
		return obstacles.size();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Bug getBugAtIndex(int index) {
		return bugs.get(index);
	}
	public Plant getPlantAtIndex(int index) {
		return plants.get(index);
	}
	public Obstacle getObstacleAtIndex(int index) {
		return obstacles.get(index);
	}
	public ArrayList<int[]> getAllPositions() {
		ArrayList<int[]> allPositions = new ArrayList<int[]>();
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				int[] coord = {x, y};
				allPositions.add(coord);
			}
		}
		return allPositions;
	}
	public ArrayList<int[]> getAvailablePositions() {
		ArrayList<int[]> availablePositions = new ArrayList<int[]>();
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				if (entityInPosition(x,y) == null) {
					int[] coord = {x, y};
					availablePositions.add(coord);
				}
			}
		}
		
		return availablePositions;
	}
	/* setters */
	public void setBug(int index, Bug bug) {
		bugs.set(index, bug);
	}
	public void setPlant(int index, Plant plant) {
		plants.set(index, plant);
	}
	/* deleters */
	public void deletePlant(int index) {
		plants.remove(index);
	}
	public void deleteBug(int index) {
		bugs.remove(index);
	}
	/* adders */
	public void addRandomPlant() {
		int size = (int)(1 + Math.random() * 9);
		int xPos = (int)(Math.random() * width);
		int yPos = (int)(Math.random() * height);
		plants.add(new Plant(size, xPos, yPos));
	}
	public void addPlant() {
		int size = 1;
		ArrayList<int[]> availablePositions = getAvailablePositions();
		int randomLocationIndex = (int)(Math.random() * availablePositions.size());
		int[] randomLocation = availablePositions.get(randomLocationIndex);
		plants.add(new Plant(size, randomLocation[0], randomLocation[1]));
	}
	
	
	
	
	
	/* other methods */
	public void drawWorld() {
		// note: draws - symbol at top and bottom,
		for (int y =-1; y <= height;  y++) {
			String line = "|";
			for (int x=0; x < width; x++) {
				if (y == -1 || y == height) {
					line += "-";
				}
				else {
					Entity entity = entityInPosition(x,y);
					if (entity != null) {
						String entityChar = "" + entity.getSymbol();
						line += entityChar;
					}
					else {
						line += " ";
					}
				}
			}
			line += "|";
			System.out.println(line);
		}
	}
	
	public void updateWorld() {
		bugs.sort(null);
		ArrayList<Bug> deadBugs = new ArrayList<Bug>();
		for (Bug bug : bugs) {
			bug.moveBugRandom(this);
			bug.eat(this);
			if (bug.getEnergy() <= 0) {
				deadBugs.add(bug);
			}
		}
		for (Bug bug: deadBugs) {
			bugs.remove(bug);
		}
		plants.sort(new PlantComparator());
		for (Plant plant : plants) {
			plant.grow();
		}
		for (int i=0; i < (this.numPlants - plants.size()); i++) {
			addPlant();
		}
	}
	
	public Entity entityInPosition(int x, int y) {
		for (Bug bug : bugs) {
			if (bug.getxPos() == x && bug.getyPos() == y) {
				return bug;
			}
		}
		for (Plant plant : plants) {
			if (plant.getxPos() == x && plant.getyPos() == y) {
				return plant;
			}
		}
		for (Obstacle obstacle : obstacles) {
			if (obstacle.getxPos() == x && obstacle.getyPos() == y) {
				return obstacle;
			}
		}
		// returns null if there is nothing in this x,y position
		return null;
	}
	
	public void generateWorld(int numObstacles) {
		generateObstacles(numObstacles);
		generatePlants();
	}
	
	public void generateWorldUserInput() {
		generateBugs();
		generatePlants();
	}
	
	// user chooses the bugs
	public void generateBugs() {
		bugs = new ArrayList<Bug>();
		Scanner scanner = new Scanner(System.in);
		Boolean addMore = true;
		while (addMore) {
			/*String name = "bug" + i;
			// generates a random species
			int species = (int)(Math.random()*2);
			int xPos = (int)(Math.random() * width);
			int yPos = (int)(Math.random() * height);
			bugs.add(new Bug());*/
			
			
			System.out.print("Enter bug's name: ");
			String name = scanner.nextLine();
			
			System.out.print("Select Species: ");
			String species = scanner.nextLine();
			// currently bee and fly are the only 2 valid species, 
			// as more are created, these lines will need to be changes
			while (!(species.equalsIgnoreCase("Bee")) && !(species.equalsIgnoreCase("Fly"))) {
				System.out.println("the species must be Bee or Fly");
				System.out.print("Select Species: ");
				species = scanner.nextLine();
			}
			
			System.out.print("Enter Symbol: ");
			char symbol = scanner.next().charAt(0);
			// numbers are reserved for plants
			while (symbol == 0 || symbol == 1 || symbol == 2 || symbol == 3 || symbol == 4 || symbol == 5
					 || symbol == 6 || symbol == 7 || symbol == 8 || symbol == 9) {
				System.out.println("numbers are reserved for plants");
				System.out.print("Enter Symbol: ");
				species = scanner.nextLine();
			}
			
			System.out.print("Enter x pos: ");
			int xPos = scanner.nextInt();
			while (xPos < 0 || xPos >= width) {
				System.out.println("x pos must be in range 0-" + width);
				System.out.print("Enter x pos: ");
				xPos = scanner.nextInt();
			}
			
			System.out.print("Enter y pos: ");
			int yPos = scanner.nextInt();
			while (yPos < 0 || yPos >= height) {
				System.out.println("y pos must be in range 0-" + height);
				System.out.print("Enter y pos: ");
				yPos = scanner.nextInt();
			}
			
			System.out.print("Enter Energy: ");
			int energy = scanner.nextInt();
			while (energy < 0) {
				System.out.println("energy cannot be less than 0");
				System.out.print("Enter Energy: ");
				energy = scanner.nextInt();
			}
			
			if (species.equalsIgnoreCase("Bee")) {
				System.out.print("Enter Wingspan: ");
				int wingspan = scanner.nextInt();
				while (wingspan <= 0) {
					System.out.println("wingspan must be greater than 0");
					System.out.print("Enter wingspan: ");
					wingspan = scanner.nextInt();
				}
				
				bugs.add(new Bee(name, symbol, xPos, yPos, energy, wingspan));
			}
			else if (species.equalsIgnoreCase("Fly")) {
				bugs.add(new Fly(name, symbol, xPos, yPos, energy, true));
			}
			
			System.out.println("Add another bug? (true/false): ");
			addMore = scanner.nextBoolean();
			scanner.nextLine();
		}
		scanner.close();
	}
	
	public void generatePlants() {
		plants = new ArrayList<Plant>();
		for (int i=0; i<numPlants; i++) {
			//addRandomPlant();
			addPlant();
		}
		/*for (Plant plant: plants) {
			plant.print();
		}*/
	}
	
	public void generateObstacles(int numObstacles) {
		obstacles = new ArrayList<Obstacle>();
		for (int i=0; i<numObstacles; i++) {
			ArrayList<int[]> availablePositions = getAvailablePositions();
			int randomLocationIndex = (int)(Math.random() * availablePositions.size());
			int[] randomLocation = availablePositions.get(randomLocationIndex);
			obstacles.add(new Obstacle(randomLocation[0], randomLocation[1]));
		}
	}
}
