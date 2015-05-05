package aufgabe1;

import org.jgrapht.Graph;

public class Dijkstra  {

	public static <V, E> void findShortestPath(Graph<V, E> graph, V startVertex, V endVertex) {
		for(V v : graph.vertexSet()){
			System.out.println(v);
		}

	}

}
