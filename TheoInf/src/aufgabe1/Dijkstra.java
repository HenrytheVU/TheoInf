package aufgabe1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;

public class Dijkstra  {

	public static <V, E> void findShortestPath(Graph<V, E> graph, V startVertex, V endVertex) {
		List<String> allVertexes = new ArrayList<>();
		List<String> visitedVertexes = new ArrayList<>();
		for(V v : graph.vertexSet()){
			System.out.println(v);
			allVertexes.add(v.toString());
		}

	}

}
