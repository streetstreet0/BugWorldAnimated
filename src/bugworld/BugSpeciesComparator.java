package bugworld;

import java.util.*;

public class BugSpeciesComparator implements Comparator<Bug> {
	
	public int compare(Bug bug1, Bug bug2) {
		return bug1.getSpecies().compareTo(bug2.getSpecies());
	}
}
