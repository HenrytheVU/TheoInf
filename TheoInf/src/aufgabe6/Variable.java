package aufgabe6;

public class Variable {

	// -1 = not occupied
	// 0 = false
	// 1 = true
	int value = -1;
	
	public Variable() {
	}
	
	public Variable(int value) {
		this.value = value;
	}
	
	public void setValue(int value) {
		if(value >= -1 || value <= 1) {
			this.value = value;			
		} else {
			System.err.println("Invalid value input");
		}
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return String.valueOf(value);
	}
	
}
