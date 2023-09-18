package bugworld;

import java.util.*;

public abstract class Bug extends Entity implements Comparable<Bug> {
	/* class variables */
	private String name;
	protected String species;
	private int energy;

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

	

	/* getters */
	public String getName() {
		return name;
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
	
	
	
	
	/* other methods */
	public void moveBugRandom(World world) {
		// generates a random direction
		// each number 0-3 corresponds to a direction
		// 0=up, 1=right, 2=down, 3=left
		ArrayList<Integer> illegalDirections = getIllegalDirections(world);
		if (illegalDirections.size() == 4) {
			return;
		}
		ArrayList<Integer> legalDirections = new ArrayList<Integer>();
		for (int i=0; i<4; i++) {
			if (!illegalDirections.contains(i)) {
				legalDirections.add(i);
			}
		}
		int directionIndex = (int)(Math.random()*legalDirections.size());
		int direction = legalDirections.get(directionIndex);
		/*int gennedDirection = (int)(Math.random() * (4 - illegalDirections.size()));
		int direction = gennedDirection;
		for (int illegalDirection : illegalDirections) {
			if (gennedDirection >= illegalDirection) {
				direction++;
			}
		}*/
		moveBug(direction);
	}
	
	public void moveBug(int direction) {
		// each number 0-3 corresponds to a direction
		// 0=up, 1=right, 2=down, 3=left
		switch (direction) {
			// 0 is up
			case 0: 
				this.setyPos(this.getyPos() - 1);
				break;
			// 1 is right
			case 1: 
				this.setxPos(this.getxPos() + 1);
				break;
			// 2 is down
			case 2: 
				this.setyPos(this.getyPos() + 1);
				break;
			// 3 is left
			case 3: 
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
	
	
	public ArrayList<Integer> getIllegalDirections(World world) {
		ArrayList<Integer> illegalDirections = new ArrayList<Integer>();
		//cannot move left
		if (this.getxPos() == 0 || this.inedibleEntity(world.entityInPosition(getxPos()-1, getyPos())) ) {
			illegalDirections.add(3);
		}
		
		//cannot move right
		if (this.getxPos() >= world.getWidth()-1 || this.inedibleEntity(world.entityInPosition(getxPos()+1, getyPos())) ) {
			illegalDirections.add(1);
		}
		
		// cannot move up
		if (this.getyPos() == 0 || this.inedibleEntity(world.entityInPosition(getxPos(), getyPos()-1)) ) {
			illegalDirections.add(0);
		}
		
		// cannot move down
		if (this.getyPos() >= world.getHeight()-1 || this.inedibleEntity(world.entityInPosition(getxPos(), getyPos()+1)) ) {
			illegalDirections.add(2);
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
	
	public abstract int smellFood(World world);
	
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
