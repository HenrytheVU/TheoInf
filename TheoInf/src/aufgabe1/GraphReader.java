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

public class GraphReader extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	private JGraphModelAdapter jGraphModelAdapter;
	private List<String> vertexList = new ArrayList<String>();
	private List<String[]> edgeList = new ArrayList<String[]>();

	public void init() {
		// create a JGraphT graph after read a graph file
		ListenableGraph graph = buildGraph();

		System.out.println("is a eulerian circuit "
				+ EulerianCircuit.isEulerian((UndirectedGraph) graph));
		if (EulerianCircuit.isEulerian((UndirectedGraph) graph)) {
			System.out
					.println("List of Vertices: "
							+ EulerianCircuit
									.getEulerianCircuitVertices((UndirectedGraph) graph));
		}

		// create a visualization using JGraph, via an adapter
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

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);
		jg.setBackground(DEFAULT_BG_COLOR);
	}

	private ListenableGraph buildGraph() {

		if (isWeightedGraph(edgeList)) {
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
			ListenableGraph graph = new ListenableUndirectedGraph(
					DefaultEdge.class);
			for(String vName : vertexList) {
				graph.addVertex(vName);
			}
			for(String[] edge : edgeList) {
				graph.addEdge(edge[0], edge[1]);
			}
			return graph;
		}

	}

	private boolean isWeightedGraph(List<String[]> edge) {
		if (edge.get(0)[2] == null) {
			return false;
		}
		return true;
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

		int max = ((int) DEFAULT_SIZE.getWidth() - 150);
		for (String vertex : vertexList) {
			x = Util.randInt(0, max);
			y = Util.randInt(0, max);
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
		String filePath = "C:\\Euler2.txt";
		GraphReader demo = new GraphReader();
		demo.readGraph(filePath);
		demo.init();
	}
}