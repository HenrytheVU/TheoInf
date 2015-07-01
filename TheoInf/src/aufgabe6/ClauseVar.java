package aufgabe6;

public class ClauseVar {

	boolean isPositive;
	Variable var;
	
	public ClauseVar(boolean isPositive, Variable var) {
		this.isPositive = isPositive;
		this.var = var;
	}
	
	public boolean isTrue() {
		if(isPositive && (var.getValue() == 1)) {
			return true;
		} 
		if(!isPositive && (var.getValue() == 0)) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return isPositive + ":" + var;
	}
	
}
