package aufgabe6;

public class ClauseVar {

	boolean isPositive;
	Variable var;
	
	public ClauseVar(boolean isPositive, Variable var) {
		this.isPositive = isPositive;
		this.var = var;
	}
	
	public boolean isTrue() {
		if(isPositive && var.getValue()) {
			return true;
		} 
		if(!isPositive && !var.getValue()) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return isPositive + "-" + var;
	}
	
}
