package aufgabe1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		List<Integer> neighborColors = new ArrayList<Integer>();
		List<Integer> takenColors = new ArrayList<Integer>();
		for (Vertex neighborVertex : Graphs
				.neighborListOf(graph, currentVertex)) {
			// System.out.println("Neighbor of " + v + " is " + neighborVertex);
			neighborColors.add(neighborVertex.getColor());
			int minIndex = neighborColors.indexOf(Collections
					.min(neighborColors));
			int minColor = neighborColors.get(minIndex);
			if (minColor == Integer.MAX_VALUE) {
				minColor = min;
			} else {
				minColor = Integer.min(min, minColor);
			}
		}
	}
}
