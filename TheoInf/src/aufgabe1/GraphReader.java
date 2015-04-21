package aufgabe1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

public class GraphReader extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	private JGraphModelAdapter jGraphModelAdapter;
	private List<String> vertexList = new ArrayList<String>();
	private List<String[]> edgeList = new ArrayList<String[]>();

	public void init(String filePath) {
		
		// read a graph file and build it
		readGraph(filePath);
		ListenableGraph graph = buildGraph();

		// check for euler shit
		System.out.println("Graph is a eulerian circuit: "
				+ EulerianCircuit.isEulerian((UndirectedGraph) graph));
		System.out.println("Graph is a eulerian path: "
				+ Util.isEulerianPath((UndirectedGraph) graph));
		
		if (EulerianCircuit.isEulerian((UndirectedGraph) graph)) {
			System.out
					.println("List of Vertices: "
							+ EulerianCircuit
									.getEulerianCircuitVertices((UndirectedGraph) graph));
		}

		System.out.println(deepFirstSearch(graph));
		// create a visualization using JGraph, via an model adapter
		jGraphModelAdapter = new JGraphModelAdapter(graph);

		JGraph jgraph = new JGraph(jGraphModelAdapter);

		adjustDisplaySettings(jgraph);
		adjustGraphLayout(vertexList);

		// Create and set up the window.
		JFrame frame = new JFrame("Übungsblatt 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(jgraph);

		frame.pack();
		frame.setVisible(true);
	}

	private boolean deepFirstSearch(ListenableGraph graph) {
		DepthFirstIterator<Integer, DefaultEdge> iterator = 
                new DepthFirstIterator<Integer, DefaultEdge>(graph);
        while (iterator.hasNext()) {
            System.out.println( iterator.next());
        }
		return false;
	}
	
	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);
		jg.setBackground(DEFAULT_BG_COLOR);
	}

	private ListenableGraph buildGraph() {
		if (Util.isWeightedGraph(edgeList)) {
			System.out.println("Graph is a weighted G.");
			ListenableUndirectedWeightedGraph<String, MyWeightedEdge> graph = new ListenableUndirectedWeightedGraph<String, MyWeightedEdge>(
					MyWeightedEdge.class);

			for (String vName : vertexList) {
				graph.addVertex(vName);
			}

			for (String[] edge : edgeList) {
				MyWeightedEdge currentEdge = graph.addEdge(edge[0], edge[1]);
				graph.setEdgeWeight(currentEdge, Double.parseDouble(edge[2]));
			}
			return graph;
		} else {
			System.out.println("Graph is a unweighted G.");
			ListenableGraph graph = new ListenableUndirectedGraph(
					DefaultEdge.class);
			for (String vName : vertexList) {
				graph.addVertex(vName);
			}
			for (String[] edge : edgeList) {
				graph.addEdge(edge[0], edge[1]);
			}
			return graph;
		}
	}

	private void readGraph(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String currentLine;

			while ((currentLine = br.readLine()) != null) {
				System.out.println(currentLine);
				if (!currentLine.startsWith("#")) {
					if (currentLine.startsWith("knoten")) {
						vertexList.add(Util.getVertexName(currentLine));
					}
					if (currentLine.startsWith("kante")) {
						edgeList.add(Util.getEdge(currentLine));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void adjustGraphLayout(List<String> vertexList) {
		int x = 50;
		int y = 50;
		// for(String vertex : vertexList) {
		// x += randInt(100, 200);
		// if(x > DEFAULT_SIZE.getWidth()) {
		// x = randInt(100, 200);
		// y += randInt(100, 200);
		// }
		// positionVertexAt(vertex, x, y);
		// }

		int max = ((int) DEFAULT_SIZE.getWidth() - 50);
		for (String vertex : vertexList) {
			x = Util.randInt(50, max);
			y = Util.randInt(50, max);
			positionVertexAt(vertex, x, y);
		}
	}

	private void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = jGraphModelAdapter.getVertexCell(vertex);
		Map attr = cell.getAttributes();
		Rectangle2D b = GraphConstants.getBounds(attr);

		int width = (int) b.getWidth();
		int height = (int) b.getHeight();

		GraphConstants.setBounds(attr, new Rectangle(x, y, width, height));

		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		jGraphModelAdapter.edit(cellAttr, null, null, null);
	}

	public static void main(String[] args) {
		String filePath = "C:\\Euler1.txt";
		GraphReader demo = new GraphReader();
		demo.init(filePath);
	}
}