package aufgabe1;

public class Vertex {
	
	private int color;
	private String name;
	private String data;
	
	public Vertex(){
	};
	
	public Vertex(String name) {
		setName(name);
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
