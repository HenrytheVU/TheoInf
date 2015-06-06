package aufgabe1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

public class GreedyColVar<V, E> {

	public void colorGraph(Graph<Vertex, E> graph) {
		for (Vertex v : graph.vertexSet()) {
			System.out.println(v);
			v.setColor(Integer.MAX_VALUE);
		}
		for (Vertex vertex : graph.vertexSet()) {
			colorVertex(graph, vertex);
		}
	}

	public void colorVertex(Graph<Vertex, E> graph, Vertex currentVertex) {
		int min = 1;
		List<Integer> takenColors = new ArrayList<Integer>();
		TreeSet<Integer> neighborColors = new TreeSet<Integer>();
		for (Vertex neighborVertex : Graphs
				.neighborListOf(graph, currentVertex)) {
			// System.out.println("Neighbor of " + v + " is " + neighborVertex);
			neighborColors.add(neighborVertex.getColor());

		}
	}

	public int findSmallestAvailableColor(TreeSet<Integer> colors) {
		int minColor = colors.first();
		if (minColor > 1) {
			return 1;
		} else {
			int currentColor = minColor;
			for (int i = 0; i < colors.size(); i++) {

			}
		}

		return 0;

	}
}
