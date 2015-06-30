package aufgabe6;

import java.util.ArrayList;
import java.util.List;

public class Clause {

	List<ClauseVar> vars;
	
	public Clause() {
		vars = new ArrayList<ClauseVar>();
	}
	
	public void add(ClauseVar var) {
		vars.add(var);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(ClauseVar var : vars) {
			sb.append(var);
			sb.append(", ");
		}
		return sb.toString();
	}
	
	public boolean isTrue() {
		for(ClauseVar var : vars) {
			if(var.isTrue()) {
				return true;
			}
		}
		return false;
	}
	
	public double getExpectationValue() {
		double result = 1 - (1/Math.pow(2, vars.size()));
		System.out.println(result);
		return result;
	}
}
