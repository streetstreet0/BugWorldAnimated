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
	public void moveBug(int direction) {
		super.moveBug(direction);
		// can move twice when flying
		if (flying) {
			super.moveBug(direction);
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
			Plant plant = world.getPlantsIndex(i);
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
	public int smellFood(World world) {
		DistanceStorer<Plant> distStore = new DistanceStorer<Plant>();
		for (int i=0; i<world.getPlantsSize(); i++) {
			Plant plant = world.getPlantsIndex(i);
			int distance = Math.abs(plant.getxPos() - this.getxPos()) + Math.abs(plant.getyPos() - this.getyPos());
			distStore.addDistance(plant, distance);
		}
		if (distStore.getMinDistance() > 8) {
			return -1;
		}
		
		Plant closest = distStore.getClosest();
		// check blocked positions
		ArrayList<Integer> nonBlockedPositions = getBlockedAdjacentPositions(world);
		// for tomorrow need to check each nonBlockedPosition
		// return which ever results in least distance to the target
		// if all positions blocked, return -1
		
		// in future could just replace with a pathfinding algorithm
		// and just return the first direction in that path
		
		int xDirection;
		if (closest.getxPos() > this.getxPos() ) {
			xDirection = 1;
		}
		else {
			xDirection = 3;
		}
		
		int yDirection;
		if (closest.getyPos() > this.getyPos()) {
			yDirection = 0;
		}
		else {
			yDirection = 2;
		}
		
		boolean closerX = Math.abs(closest.getxPos() - this.getxPos()) >= Math.abs(closest.getyPos() - this.getyPos());
		
	}
}
