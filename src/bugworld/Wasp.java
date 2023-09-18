package bugworld;

public class Wasp extends Bug {
	/* class variables */
	int wingSize ;
	
	/* constructors */
	public Wasp(String name, char symbol, int xPos, int yPos, int energy, int wingSize) {
		super(name, symbol, xPos, yPos, energy);
		this.species = "Wasp";
		this.wingSize = wingSize;
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
		for (int i=0; i<world.getBugsSize(); i++) {
			Bug bug= world.getBugsIndex(i);
			if (bug.atPosition(this.getxPos(), this.getyPos()) && this != bug) {
				this.gainEnergy(bug.getEnergy() / 2);
				world.deleteBug(i);
				return;
			}
		}
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
	public boolean inedibleEntity(Entity entity) {
		if (entity == null) {
			return false;
		}
		return !(entity instanceof Plant || entity instanceof Bug);
	}
	
	@Override
	public int smellFood(World world) {
		DistanceStorer<Entity> distStore = new DistanceStorer<Entity>();
		for (int i=0; i<world.getBugsSize(); i++) {
			Bug bug = world.getBugsIndex(i);
			if (!bug.equals(this)) {
				int distance = Math.abs(bug.getxPos() - this.getxPos()) + Math.abs(bug.getyPos() - this.getyPos());
				distStore.addDistance(bug, distance);
			}
		}
		for (int i=0; i<world.getPlantsSize(); i++) {
			Plant plant = world.getPlantsIndex(i);
			int distance = Math.abs(plant.getxPos() - this.getxPos()) + Math.abs(plant.getyPos() - this.getyPos());
			distStore.addDistance(plant, distance);
		}
		
		if (distStore.getMinDistance() > 6) {
			return -1;
		}
		
		Entity closest = distStore.getClosest();
		boolean closerX = Math.abs(closest.getxPos() - this.getxPos()) >= Math.abs(closest.getyPos() - this.getyPos());
		if (closerX) {
			if (closest.getxPos() > this.getxPos()) {
				return 1;
			}
			else {
				return 3;
			}
		}
		else {
			if (closest.getyPos() > this.getyPos()) {
				return 0;
			}
			else {
				return 2;
			}
		}
	}
}