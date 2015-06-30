package aufgabe6;

public class Variable {

	boolean value;
	
	public Variable() {
	}
	
	public Variable(boolean value) {
		this.value = value;
	}
	
	public void setValue(boolean b) {
		this.value = b;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public String toString() {
		return String.valueOf(value);
	}
	
}
