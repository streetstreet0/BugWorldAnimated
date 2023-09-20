package bugworld;

public class CarnivoreBug extends Bug {
	/* constructor */
	public CarnivoreBug(String name, char symbol, int xPos, int yPos, int energy) {
		super(name, symbol, xPos, yPos, energy);
		this.species = "Carnivore";
	}
	public CarnivoreBug(int xPos, int yPos, int energy) {
		super(xPos, yPos, energy);
		this.species = "Carnivore";
	}
	
	/* other methods */
	@Override
	public void eat(World world) {
		for (int i=0; i<world.getBugsSize(); i++) {
			Bug bug= world.getBugAtIndex(i);
			if (bug.atPosition(this.getxPos(), this.getyPos()) && this != bug) {
				this.gainEnergy(bug.getEnergy());
				world.killBug(i);
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
	// returns the direction of the closest bug or -1 if it cannot smell anything
	public Direction smellFood(World world) {
		DistanceStorer<Bug> distStore = new DistanceStorer<Bug>();
		for (int i=0; i<world.getBugsSize(); i++) {
			Bug bug = world.getBugAtIndex(i);
			if (!bug.equals(this)) {
				int distance = Math.abs(bug.getxPos() - this.getxPos()) + Math.abs(bug.getyPos() - this.getyPos());
				distStore.addDistance(bug, distance);
			}
		}
		
		if (distStore.getMinDistance() > 24) {
			return Direction.RANDOM;
		}
		
		Bug closest = distStore.getClosest();
		boolean closerX = (Math.abs(closest.getxPos() - this.getxPos()) >= Math.abs(closest.getyPos() - this.getyPos()) && closest.getxPos() - this.getxPos() != 0);
		
		
		Direction direction;
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
