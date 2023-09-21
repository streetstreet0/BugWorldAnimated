package bugworld;

public class Plant extends Entity {
	/* class variables */
	private int size;
	private int turnsGrowing = 0;
	private static final int turnsToGrow = 1;
	private boolean newPlant;
	
	/* constructor */
	public Plant(int size, int xPos, int yPos) {
		super(("" + size).charAt(0), xPos, yPos);
		this.size = size;
		newPlant = true;
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
	public boolean isNew() {
		return newPlant;
	}
	
	/* other methods */
	public void grow(int growth) {
		this.size += growth;
		if (size > 30) {
			size = 30;
		}
	}
	
	public void grow() {
		this.newPlant = false;
		if (turnsGrowing < turnsToGrow) {
			turnsGrowing++;
			return;
		}
		turnsGrowing = 0;
		int tempSize = this.size + 1;
		if (tempSize > 30) {
			tempSize = 30;
		}
		this.setSize(tempSize);
	}
}