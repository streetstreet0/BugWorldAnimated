package bugworld;

import java.util.*;

public abstract class Bug extends Entity implements Comparable<Bug> {
	/* class variables */
	private String name;
	protected String species;
	private int energy;
	private boolean dead = false;

	/* constructors */
	/*//default constructor
	public Bug() {
		this.id = nextId++;
		this.name = "Bugg";
		this.species = "species1";
		this.symbol = '#';
		this.xPos = 0;
		this.yPos = 0;
		this.energy = 50;
		System.out.println("Details:-" + " Id: " + id + " Name: " + name + " Species: " + species + " Symbol: " + symbol
				+ " x position: " + xPos + " y position: " + yPos + " Energy: " + energy);
	}*/
	/*// parameterized constructor
	public Bug(String name, String species, char symbol, int xPos, int yPos, int energy) {
		this.id = nextId ++;
		this.name = name;
		this.species = species;
		this.symbol = symbol;
		this.xPos = xPos;
		this.yPos = yPos;
		this.energy = energy;
		System.out.println("Details:-" + " Id: " + id + " Name: " + name + " Species: " + species + " Symbol: " + symbol
				+ " x position: " + xPos + " y position: " + yPos + " Energy: " + energy);
	}*/
	// main constructor
	public Bug(String name, char symbol, int xPos, int yPos, int energy) {
		super(symbol, xPos, yPos);
		this.name = name;
		this.energy = energy;
	}
	public Bug(int xPos, int yPos, int energy) {
		super(xPos, yPos);
		this.energy = energy;
	}

	

	/* getters */
	public String getName() {
		return this.species + ": " + this.getId();
	}
	public String getSpecies() {
		return species;
	}
	public int getEnergy() {
		return energy;
	}



	/* setters */
	/*public void setId(int id) {
		this.id = id;
	}*/
	public void setName(String name) {
		this.name = name;
	}
	public void die() {
		this.dead = true;
	}
	/*public void setSpecies(String species) {
		this.species = species;
	}*/
	/*public void setEnergy(int energy) {
		this.energy = energy;
	}*/
	
	/* energy manipulation methods */
	public void gainEnergy(int gain) {
		this.energy += gain;
	}
	public void loseEnergy(int loss) {
		this.energy -= loss;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	
	
	
	/* other methods */
	public void moveBugRandom(World world) {
		// generates a random direction
		// each number 0-3 corresponds to a direction
		// 0=up, 1=right, 2=down, 3=left
		ArrayList<Direction> illegalDirections = getIllegalDirections(world);
		if (illegalDirections.size() == 4) {
			return;
		}
		ArrayList<Direction> legalDirections = new ArrayList<Direction>();
		Direction[] allDirections = {Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN};
		for (Direction direction : allDirections) {
			if (!illegalDirections.contains(direction)) {
				legalDirections.add(direction);
			}
		}
		int directionIndex = (int)(Math.random()*legalDirections.size());
		Direction direction = legalDirections.get(directionIndex);
		/*int gennedDirection = (int)(Math.random() * (4 - illegalDirections.size()));
		int direction = gennedDirection;
		for (int illegalDirection : illegalDirections) {
			if (gennedDirection >= illegalDirection) {
				direction++;
			}
		}*/
		moveBug(direction);
	}
	
	public void moveBug(World world) {
		Direction direction = smellFood(world);
		if (direction == Direction.STATIONARY) {
			return;
		}
		else if (direction == Direction.RANDOM) {
			moveBugRandom(world);
		}
		else {
			moveBug(direction);
		}
	}
	
	public void moveBug(Direction direction) {
		switch (direction) {
			case UP: 
				this.setyPos(this.getyPos() - 1);
				break;
			case RIGHT: 
				this.setxPos(this.getxPos() + 1);
				break;
			case DOWN: 
				this.setyPos(this.getyPos() + 1);
				break;
			case LEFT: 
				this.setxPos(this.getxPos() - 1);
				break;
		}
		// moving costs energy now
		this.energy -= 1;
	}
	
	public void moveRandomUntilDeath() {
		while (this.energy > 0) {
			// each step is only a distance of 1
			moveBugRandom(new World(100, 100));
		}
	}
	
	public abstract void eat(World world);
	
	
	public ArrayList<Direction> getIllegalDirections(World world) {
		ArrayList<Direction> illegalDirections = new ArrayList<Direction>();
		//cannot move left
		if (this.getxPos() == 0 || this.inedibleEntity(world.entityInPosition(getxPos()-1, getyPos())) ) {
			illegalDirections.add(Direction.LEFT);
		}
		
		//cannot move right
		if (this.getxPos() >= world.getWidth()-1 || this.inedibleEntity(world.entityInPosition(getxPos()+1, getyPos())) ) {
			illegalDirections.add(Direction.RIGHT);
		}
		
		// cannot move up
		if (this.getyPos() == 0 || this.inedibleEntity(world.entityInPosition(getxPos(), getyPos()-1)) ) {
			illegalDirections.add(Direction.UP);
		}
		
		// cannot move down
		if (this.getyPos() >= world.getHeight()-1 || this.inedibleEntity(world.entityInPosition(getxPos(), getyPos()+1)) ) {
			illegalDirections.add(Direction.DOWN);
		}
		
		// note: takes into account bug being in world that is too small
		// or bug already being off screen
		return illegalDirections;
	}
	
	public ArrayList<Integer> getBlockedAdjacentPositions(World world) {
		ArrayList<Integer> nonBlockedPositions = new ArrayList<Integer>();
		// check X first
		for (int i=-1; i<2; i+=2) {
			// checks right (i=-1) then left (i=1)
			// done this way so 2+i = their normal number
			if (!inedibleEntity(world.entityInPosition(getxPos()-i, getyPos()))) {
				nonBlockedPositions.add(2+i);
			}
		}
		// check Y next
		for (int i=-1; i<2; i+=2) {
			// checks top (i=-1) then bottom (i=1)
			// done this way so 1+i = their normal number
			if (!inedibleEntity(world.entityInPosition(getxPos(), getyPos()-i))) {
				nonBlockedPositions.add(1+i);
			}
		}
		return nonBlockedPositions;
	}
	
	public abstract boolean inedibleEntity(Entity entity);
	
	public abstract Direction smellFood(World world);
	
	public int compareTo(Bug bug) {
		int comparison = this.getEnergy() - bug.getEnergy();
		if (comparison != 0) {
			return comparison;
		}
		
		Comparator<Bug> comparator = new BugSpeciesComparator();
		comparison = comparator.compare(this, bug);
		if (comparison != 0) {
			return comparison;
		}
		
		comparator = new BugNameComparator();
		comparison = comparator.compare(this, bug);
		if (comparison != 0) {
			return comparison;
		}
		
		comparison = this.getId() - bug.getId();
		return comparison;
	}
	
	/*@Override
	public String toString() {
	    return String.format("Bug[id=%d, name='%s', symbol='%c', x=%d, y=%d, energy=%d]",
	            id, name, symbol, xPos, yPos, energy);
	}*/
	/*public String toText() {
	    return String.format("Bug[id=%d, species='%s', name='%s', symbol='%c', x=%d, y=%d, energy=%d]",
	            id, species, name, symbol, xPos, yPos, energy);
	}*/
	/*public void askUserInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Name: ");
		name = scanner.nextLine();
		System.out.print("Enter Species: ");
		species = scanner.nextLine();
		System.out.print("Enter Symbol: ");
		symbol = scanner.next().charAt(0);
		System.out.print("Enter x pos: ");
		xPos = scanner.nextInt();
		System.out.print("Enter y pos: ");
		yPos = scanner.nextInt();
		System.out.print("Enter Energy: ");
		yPos = scanner.nextInt();
		scanner.close();
	}*/
}
