package bugworld;

import java.util.*;

public class BugEnergyComparator implements Comparator<Bug> {
		
	public int compare(Bug bug1, Bug bug2) {
		return bug1.getEnergy() - bug2.getEnergy();
	}
}