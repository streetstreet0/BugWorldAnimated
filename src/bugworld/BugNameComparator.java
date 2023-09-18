package bugworld;

import java.util.*;

public class BugNameComparator implements Comparator<Bug> {
	
	public int compare(Bug bug1, Bug bug2) {
		return bug1.getName().compareTo(bug2.getName());
	}
}
