package aufgabe1;

import org.jgrapht.graph.DefaultWeightedEdge;

public class WeightedEdge extends DefaultWeightedEdge{

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return super.toString() + " " + Double.toString(getWeight());
	}

}
