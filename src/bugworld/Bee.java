package bugworld;

public class Bee extends Bug {
	/* class variables */
	private int wingSize ;
	
	/* constructors */
	public Bee(String name, char symbol, int xPos, int yPos, int energy, int wingSize) {
		super(name, symbol, xPos, yPos, energy);
		this.species = "Bee";
		this.wingSize = wingSize;
	}
	public Bee(int xPos, int yPos, int energy) {
		super(xPos, yPos, energy);
		this.species = "Bee";
	}
	/*
	public Bee(String name, String species, char symbol, int xPos, int yPos, int energy, int wingSize) {
		super(name, species, symbol, xPos, yPos, energy);
		this.wingSize = wingSize;
	}*/
	
	
	/*getter*/
	public int getWingSize() {
		return wingSize;
	}
	/*setter*/
	public void setWingSize(int wingSize) {
		this.wingSize = wingSize;
	}
	 
	
	/* other methods */
	@Override
	public void eat(World world) {
		for (int i=0; i<world.getPlantsSize(); i++) {
			Plant plant = world.getPlantAtIndex(i);
			if (plant.atPosition(this.getxPos(), this.getyPos())) {
				this.gainEnergy(plant.getSize());
				world.deletePlant(i);
				return;
			}
		}
	}
	
	@Override
	public boolean inedibleEntity(Entity entity) {
		if (entity == null) {
			return false;
		}
		return !(entity instanceof Bug);
	}
	
	@Override
	// returns the direction of the closest plant or -1 if it cannot smell anything
	public Direction smellFood(World world) {
		DistanceStorer<Plant> distStore = new DistanceStorer<Plant>();
		for (int i=0; i<world.getPlantsSize(); i++) {
			Plant plant = world.getPlantAtIndex(i);
			int distance = Math.abs(plant.getxPos() - this.getxPos()) + Math.abs(plant.getyPos() - this.getyPos());
			distStore.addDistance(plant, distance);
		}
		
		if (distStore.getMinDistance() > 24) {
			return Direction.RANDOM;
		}
		Plant closest = distStore.getClosest();
		boolean closerX = (Math.abs(closest.getxPos() - this.getxPos()) >= Math.abs(closest.getyPos() - this.getyPos()) && closest.getxPos() - this.getxPos() != 0);
		Direction direction;
		// check the closest axis first
		// if that direction is blocked, check the other axis
		// if that is blocked, go with random
		if (closerX) {
			if (closest.getxPos() > this.getxPos()) {
				direction = Direction.RIGHT;
			}
			else {
				direction = Direction.LEFT;
			}
			
			if (this.getIllegalDirections(world).contains(direction)) {	
				if (closest.getyPos() > this.getyPos()) {
					direction = Direction.DOWN;
				}
				else {
					direction = Direction.UP;
				}
				if (this.getIllegalDirections(world).contains(direction)) {
					direction = Direction.RANDOM;
				}
			}
		}
		else {
			if (closest.getyPos() > this.getyPos()) {
				direction = Direction.DOWN;
			}
			else {
				direction = Direction.UP;
			}
			
			if (this.getIllegalDirections(world).contains(direction)) {	
				if (closest.getxPos() > this.getxPos()) {
					direction = Direction.RIGHT;
				}
				else {
					direction = Direction.LEFT;
				}
				if (this.getIllegalDirections(world).contains(direction)) {
					direction = Direction.RANDOM;
				}
			}
		}
		return direction;
	}
}