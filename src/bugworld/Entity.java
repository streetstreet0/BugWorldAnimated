package bugworld;

public abstract class Entity {
	/* class variables */
	private static int nextId = 1;
	private int id;
	private char symbol;
	private int xPos;
	private int yPos;
	
	/* constructor */
	public Entity(char symbol, int xPos, int yPos) {
		this.id = nextId;
		nextId++;
		this.symbol = symbol;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/* getters */
	public int getId() {
		return this.id;
	}
	public char getSymbol() {
		return this.symbol;
	}
	public int getxPos() {
		return this.xPos;
	}
	public int getyPos() {
		return this.yPos;
	}
	
	
	/* setters */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	/* other methods */
	public boolean atPosition(int xPos, int yPos) {
		if (this.xPos == xPos && this.yPos == yPos) {
			return true;
		}
		return false;
	}
}
