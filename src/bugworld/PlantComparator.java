package bugworld;

import java.util.*;

public class PlantComparator implements Comparator<Plant> {
	
	public int compare(Plant plant1, Plant plant2) {
		int plant1Size = plant1.getSize();
		int plant2Size = plant2.getSize();
		if (plant1Size == plant2Size) {
			return 0;
		}
		else if (plant1Size > plant2Size) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
