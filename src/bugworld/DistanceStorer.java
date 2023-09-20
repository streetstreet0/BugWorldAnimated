package bugworld;

import java.util.*;

public class DistanceStorer<T> {
	/* class variables */
	List<T> objectList = new ArrayList<T>();
	List<Integer> distanceList = new ArrayList<Integer>();
	
	DistanceStorer() {
		super();
	}
	
	public void addDistance(T object, int distance) {
		objectList.add(object);
		distanceList.add(distance);
	}
	
	public T getClosest() {
		int minDist = distanceList.get(0);
		T closest = objectList.get(0);
		for (int i=1; i<distanceList.size(); i++) {
			if (distanceList.get(i) < minDist) {
				minDist = distanceList.get(i);
				closest = objectList.get(i);
			}
		}
		return closest;
	}
	
	public int getMinDistance() {
		if (distanceList.size() == 0) {
			return 1000000000;
		}
		int minDist = distanceList.get(0);
		for (int i=1; i<distanceList.size(); i++) {
			if (distanceList.get(i) < minDist) {
				minDist = distanceList.get(i);
			}
		}
		return minDist;
	}
}
