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
		// create a JGraphT graph
		ListenableGraph graph = buildGraph();

		// create a visualization using JGraph, via an adapter
		jGraphModelAdapter = new JGraphModelAdapter(graph);

		JGraph jgraph = new JGraph(jGraphModelAdapter);

		adjustDisplaySettings(jgraph);
		adjustGraphLayout(vertexList);
		
		// Create and set up the window.
		JFrame frame = new JFrame("Übungsblatt 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(jgraph);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	


	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);
		jg.setBackground(DEFAULT_BG_COLOR);
	}

	private String getVertexName(String line) {
		return line.split(" ")[1];
	}
	
	private String[] getEdge(String line) {
		String[] lineSplit = line.split(" ");
		String fromVertex = lineSplit[1];
		String toVertex = lineSplit[2];
		String weight = null;
		if(lineSplit.length == 4) {
			weight = lineSplit[3];
		}
		String[] result = {fromVertex, toVertex, weight};
		return result;
	}
	
	private ListenableGraph buildGraph() {
		
		if(isWeightedGraph(edgeList)) {
			ListenableUndirectedWeightedGraph<String, MyWeightedEdge> graph = new ListenableUndirectedWeightedGraph<String, MyWeightedEdge>(
					MyWeightedEdge.class);
			
			for(String vName : vertexList) {
				graph.addVertex(vName);
			}
			
			for(String[] edge : edgeList) {
				MyWeightedEdge currentEdge = graph.addEdge(edge[0], edge[1]);
				graph.setEdgeWeight(currentEdge, Double.parseDouble(edge[2]));
			}
			return graph;
		} else {
			ListenableGraph graph = new ListenableUndirectedGraph(DefaultEdge.class);
			return graph;
		}
		

	}
	
	private boolean isWeightedGraph(List<String[]> edge) {
		if(edge.get(0)[2] == null) {
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
					if(currentLine.startsWith("knoten")){
						vertexList.add(getVertexName(currentLine));
					}
					if(currentLine.startsWith("kante")) {
						edgeList.add(getEdge(currentLine));
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
//		for(String vertex : vertexList) {
//			x += randInt(100, 200);
//			if(x > DEFAULT_SIZE.getWidth()) {
//				x = randInt(100, 200);
//				y += randInt(100, 200);
//			}
//			positionVertexAt(vertex, x, y);
//		}
		
		int max = ((int) DEFAULT_SIZE.getWidth() - 150);
		for(String vertex : vertexList) {
			x = randInt(0, max);
			y = randInt(0, max);
			positionVertexAt(vertex, x, y);
		}
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
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
		String filePath = "C:\\Dijkstra.txt";
		GraphReader demo = new GraphReader();
		demo.readGraph(filePath);
		demo.init();
	}
}