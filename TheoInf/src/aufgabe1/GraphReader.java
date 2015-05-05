package aufgabe1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

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

import com.jgraph.layout.JGraphCompoundLayout;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;

public class GraphReader extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	private JGraphModelAdapter jGraphModelAdapter;
	private JGraphFacade facade;
	private JFrame frame;
	ListenableGraph graph;
	private List<String[]> vertexList;
	private List<String[]> edgeList;
	
	public static void main(String[] args) {
		String filePath = "bin\\Euler1.txt";
		GraphReader demo = new GraphReader();
		demo.readGraph(filePath);
		demo.initVisualization();
	}

	public void initVisualization() {
		
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

		// create a visualization using JGraph, via an model adapter
		jGraphModelAdapter = new JGraphModelAdapter(graph);

		JGraph jgraph = new JGraph(jGraphModelAdapter);
		
		facade = new JGraphFacade(jgraph);
		adjustDisplaySettings(jgraph);
		
		// Create and set up the window.
		frame = new JFrame("Übungsblatt 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setJMenuBar(createTopMenuBar());
		frame.getContentPane().add(createMenuBar(), BorderLayout.LINE_START);
		frame.getContentPane().add(jgraph);

		frame.pack();
		frame.setVisible(true);
	}
	
	public JMenuBar createTopMenuBar() {
		JMenuBar topMenuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem openFile = new JMenuItem("Open");
		openFile.addActionListener(new OpenFileAL());
		fileMenu.add(openFile);
		topMenuBar.add(fileMenu);
		
		return topMenuBar;
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.PAGE_AXIS));
		
		JButton resetLayoutBtn = new JButton("Random Layout");
		resetLayoutBtn.addActionListener(new RandomLayoutAL());
		menuBar.add(resetLayoutBtn);
		
		JButton fastOrganicLayoutBtn = new JButton("Fast Organic Layout");
		fastOrganicLayoutBtn.addActionListener(new FastOrganicLayoutAL());
		menuBar.add(fastOrganicLayoutBtn);
		
		JButton organicLayoutBtn = new JButton("Organic Layout");
		organicLayoutBtn.addActionListener(new OrganicLayoutAL());
		menuBar.add(organicLayoutBtn);
		
		JButton hierarchialLayoutBtn = new JButton("Hierarchial Layout");
		hierarchialLayoutBtn.addActionListener(new HierarchialLayoutAL());
		menuBar.add(hierarchialLayoutBtn);
		
		JButton treeLayoutBtn = new JButton("Tree Layout");
		treeLayoutBtn.addActionListener(new TreeLayoutAL());
		menuBar.add(treeLayoutBtn);
		
		JButton compactTreeLayoutBtn = new JButton("Compact Tree Layout");
		compactTreeLayoutBtn.addActionListener(new CompactTreeLayoutAL());
		menuBar.add(compactTreeLayoutBtn);
		
		menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,
				Color.BLACK));
		return menuBar;
	}

	private boolean deepFirstSearch(ListenableGraph graph) {
		DepthFirstIterator<Integer, DefaultEdge> iterator = new DepthFirstIterator<Integer, DefaultEdge>(
				graph);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		return false;
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);
		jg.setBackground(DEFAULT_BG_COLOR);
	}
	
	public void createGraph() {
		graph = buildGraph();
	}

	private ListenableGraph buildGraph() {
		if (Util.isWeightedGraph(edgeList)) {
			System.out.println("Graph is a weighted G.");
			ListenableUndirectedWeightedGraph<String, MyWeightedEdge> graph = new ListenableUndirectedWeightedGraph<String, MyWeightedEdge>(
					MyWeightedEdge.class);

			for (String[] vertex : vertexList) {
				graph.addVertex(vertex[0]);
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
			for (String[] vertex : vertexList) {
				graph.addVertex(vertex[0]);
			}
			for (String[] edge : edgeList) {
				graph.addEdge(edge[0], edge[1]);
			}
			return graph;
		}
	}

	private void readGraph(String filePath) {
		vertexList = new ArrayList<String[]>();
		edgeList = new ArrayList<String[]>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String currentLine;

			while ((currentLine = br.readLine()) != null) {
				System.out.println(currentLine);
				if (!currentLine.startsWith("#")) {
					if (currentLine.startsWith("knoten")) {
						vertexList.add(Util.getVertex(currentLine));
					}
					if (currentLine.startsWith("kante")) {
						edgeList.add(Util.getEdge(currentLine));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		createGraph();
	}

	public class RandomLayoutAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			adjustGraphLayout(vertexList);
		}
		private void adjustGraphLayout(List<String[]> vertexList) {
			int x = 50;
			int y = 50;
			int max = ((int) DEFAULT_SIZE.getWidth() - 50);
			for (String[] vertex : vertexList) {
				String vertexName = vertex[0];
				x = Util.randInt(50, max);
				y = Util.randInt(50, max);
				positionVertexAt(vertexName, x, y);
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
	}
	
	public class FastOrganicLayoutAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JGraphLayout layout = new JGraphFastOrganicLayout();
			layout.run(facade);
			Map nested = facade.createNestedMap(true, true);
			jGraphModelAdapter.edit(nested, null, null, null);
		}
	}
	
	public class OrganicLayoutAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JGraphLayout layout = new JGraphOrganicLayout();
			layout.run(facade);
			Map nested = facade.createNestedMap(true, true);
			jGraphModelAdapter.edit(nested, null, null, null);
		}
	}
	
	public class HierarchialLayoutAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JGraphLayout layout = new JGraphHierarchicalLayout();
			layout.run(facade);
			Map nested = facade.createNestedMap(true, true);
			jGraphModelAdapter.edit(nested, null, null, null);
		}
	}
	
	public class TreeLayoutAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JGraphLayout layout = new JGraphTreeLayout();
			layout.run(facade);
			Map nested = facade.createNestedMap(true, true);
			jGraphModelAdapter.edit(nested, null, null, null);
		}
	}
	
	public class CompactTreeLayoutAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JGraphLayout layout = new JGraphCompactTreeLayout();
			layout.run(facade);
			Map nested = facade.createNestedMap(true, true);
			jGraphModelAdapter.edit(nested, null, null, null);
		}
	}
	
	public class OpenFileAL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(getLastChosenFilePath());
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph Text Files",
		       "txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(frame.getContentPane());
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	String selectedFilePath = chooser.getSelectedFile().getPath();
		    	rememberLastChosenFile(selectedFilePath);
		    	readGraph(selectedFilePath);
		     }
		}
		
		public void rememberLastChosenFile(String input) {
			File file = new File("bin\\lastChosenFile.txt");
			try {
				if(!file.exists()) {
					file.createNewFile();
				}
				FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fileWriter);
				bw.write(input);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public String getLastChosenFilePath() {
			BufferedReader br = null;
			File file = new File("bin\\lastChosenFile.txt");
			String lastChosenFilePath = null;
			try {
				if(!file.exists()) {
					return null;
				}
				br = new BufferedReader(new FileReader(file));
				while ((lastChosenFilePath = br.readLine()) != null) {
					System.out.println("last chosen: "  +lastChosenFilePath);
					return lastChosenFilePath;
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return lastChosenFilePath;
		}
	}
}