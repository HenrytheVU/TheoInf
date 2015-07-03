package aufgabe6;

import java.io.Serializable;

public class ClauseVar implements Serializable{

	private static final long serialVersionUID = 1L;
	boolean isPositive;
	Variable var;
	
	public ClauseVar(boolean isPositive, Variable var) {
		this.isPositive = isPositive;
		this.var = var;
	}
	
	public ClauseVar() {
	}
	
	public static ClauseVar copy(ClauseVar clauseVar){
		return new ClauseVar(clauseVar.isPositive, clauseVar.var);
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
	
	public Variable getVar(){
		return var;
	}
	
	public String toString() {
		return isPositive + ":" + var;
	}
	
}
