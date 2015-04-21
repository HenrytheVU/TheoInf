package aufgabe1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;

public class Util {
	static final Random rand = new Random();

	public static String[] getEdge(String line) {
		String[] lineSplit = line.split(" ");
		String fromVertex = lineSplit[1];
		String toVertex = lineSplit[2];
		String weight = null;
		if (lineSplit.length == 4) {
			weight = lineSplit[3];
		}
		String[] result = { fromVertex, toVertex, weight };
		return result;
	}

	public static String getVertexName(String line) {
		return line.split(" ")[1];
	}

	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public static boolean isWeightedGraph(List<String[]> edge) {
		if (edge.get(0)[2] == null) {
			return false;
		}
		return true;
	}


	public static <V, E> boolean isEulerianPath(UndirectedGraph<V, E> g) {
		// If the graph is not connected, then no Eulerian circuit exists
		if (!(new ConnectivityInspector<V, E>(g)).isGraphConnected()) {
			return false;
		}

		List<V> vertexWithOddDegree = new ArrayList<V>();

		// A graph is Eulerian-path if only 2 vertices or no vertices have
		// odd degree
		// So, this code will check for that
		Iterator<V> iter = g.vertexSet().iterator();
		while (iter.hasNext()) {
			V v = iter.next();
			if ((g.degreeOf(v) % 2) == 1) {
				vertexWithOddDegree.add(v);
			}
		}
		if(vertexWithOddDegree.isEmpty() || vertexWithOddDegree.size() == 2) {
			return true;
		} else {
			return false;
		}
	}
}
