package aufgabe6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Clause implements Serializable{

	private static final long serialVersionUID = 1L;
	List<ClauseVar> vars;
	ClauseVar undoVar;
	
	public Clause() {
		vars = new ArrayList<ClauseVar>();
	}
	
	public Clause(Clause clone) {
		this.vars = clone.vars;
	}
	
	
	public List<ClauseVar> getVars() {
		return vars;
	}
	
	public void add(ClauseVar var) {
		vars.add(var);
	}
	
	public boolean isTrue() {
		for(ClauseVar var : vars) {
			if(var.isTrue()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasVar(Variable var) {
		for(int i = 0; i < vars.size(); i++) {
			if(vars.get(i).getVar().equals(var)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean removeVar(Variable var) {
		for(int i = 0; i < vars.size(); i++) {
			if(vars.get(i).getVar().equals(var)){
				undoVar = ClauseVar.copy(vars.get(i));
				vars.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void undoRemove() {
//		System.out.println("undoing " + vars.size() + " " + undoVar);
		vars.add(undoVar);
//		System.out.println("after undo: " + vars.size());
	}
	
	public double getExpectationValue() {
		double result = 1 - (1/Math.pow(2, vars.size()));
		return result;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(ClauseVar var : vars) {
			sb.append(var);
			sb.append(", ");
		}
		return sb.toString();
	}

}
