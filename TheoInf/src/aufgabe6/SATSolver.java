package aufgabe6;

import java.util.List;

public class SATSolver {

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
}
