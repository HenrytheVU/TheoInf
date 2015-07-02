package aufgabe6;

import java.util.List;

public class SATSolver {
	
	int maxSastified = 0;

	public SATSolver() {
	}
	
	public boolean isTermTrue(List<Clause> term) {
		for(Clause clause : term) {
			if(!clause.isTrue()) {
				return false;
			}
		}
		return true;
	}
	
	
	public int getMax(List<Clause> term) {
		int counter = 0;
		for(Clause clause : term) {
			if(clause.isTrue()) {
				counter++;
				if(maxSastified <= counter) {
					maxSastified = counter;
				}
			}
		}
		return counter;
	}
	
	public int getMaxSastified() {
		return maxSastified;
	}
	
	
}
