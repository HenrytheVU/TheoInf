package aufgabe1;


public class MyEdge extends WeightedEdge {

	private Vertex sourceVertex;
	private Vertex targetVertex;
	private double weight;
	
	public MyEdge(Vertex sourceVertex, Vertex targetVertex, Double weight) {
		setSourceVertex(sourceVertex);
		setTargetVertex(targetVertex);
		setWeight(weight);
	}
	
	public MyEdge() {
		
	}

	public Vertex getSourceVertex() {
		return sourceVertex;
	}

	public void setSourceVertex(Vertex sourceVertex) {
		this.sourceVertex = sourceVertex;
	}

	public Vertex getTargetVertex() {
		return targetVertex;
	}

	public void setTargetVertex(Vertex targetVertex) {
		this.targetVertex = targetVertex;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
