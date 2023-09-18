package bugworld;

public class Plant extends Entity {
	/* class variables */
	private int size;
	private int turnsGrowing = 0;
	private static final int turnsToGrow = 3;
	
	/* constructor */
	public Plant(int size, int xPos, int yPos) {
		super(("" + size).charAt(0), xPos, yPos);
		this.size = size;
	}

	
	/* getters */
	public int getSize() {
		return size;
	}
	
	/* setters */
	// set size will be removed after testing
	public void setSize(int size) {
		this.size = size;
		String temp = "" + this.size;
		this.setSymbol(temp.charAt(0));
	}
	
	/* other methods */
	public void grow(int growth) {
		this.size += growth;
		if (size > 9) {
			size = 9;
		}
	}
	
	public void grow() {
		if (turnsGrowing < turnsToGrow) {
			turnsGrowing++;
			return;
		}
		turnsGrowing = 0;
		int tempSize = this.size + 1;
		if (tempSize > 9) {
			tempSize = 9;
		}
		this.setSize(tempSize);
	}
	
	@Override
	public String toString() {
		return "x: " + this.getxPos() + ", y: " + this.getyPos() + ", plant: " + this.getId() + ", size: " + this.size;
	}
	
	public void print() {
		System.out.println(this.toString());
	}
}