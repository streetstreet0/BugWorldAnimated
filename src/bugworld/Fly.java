package bugworld;

import java.util.ArrayList;

public class Fly extends Bug {
	/* class variables */
	boolean flying;

	/* constructors */
	public Fly(String name, char symbol, int xPos, int yPos, int energy, boolean flying) {
		super(name, symbol, xPos, yPos, energy);
		this.species = "Fly";
		this.flying = flying;
	}
	public Fly(int xPos, int yPos, int energy) {
		super(xPos, yPos, energy);
		this.species = "Fly";
		this.flying = true;
	}
	/*public Fly() {
		super();
		flying = false;
	}*/
	
	
	/* getter */
	public boolean isFlying() {
		return flying;
	}
	/* setter */
	public void setFlying(boolean flying) {
		this.flying = flying;
	}
	
	/* other methods */
	@Override
	public void moveBug(World world) {
		super.moveBug(smellFood(world));
		// can move twice when flying
		if (flying) {
			super.moveBug(smellFood(world));
		}
		// if energy drops below 10, can no longer fly
		if (this.getEnergy() < 10) {
			this.flying = false;
		}
		// if energy goes back above 20, can fly again
		else if (this.getEnergy() > 20) {
			this.flying = true;
		}
	}
	
	@Override
	public boolean inedibleEntity(Entity entity) {
		if (entity == null) {
			return false;
		}
		return !(entity instanceof Plant);
	}
	
	
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
	// returns the direction that gets it closest to the closest plant 
	// or -1 if it cannot smell anything
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
		else if (distStore.getMinDistance() == 0) {
			return Direction.STATIONARY;
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
