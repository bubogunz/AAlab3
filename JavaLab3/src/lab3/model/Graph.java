package lab3.model;

import java.util.ArrayList;

import lab3.model.Edge.Label;
/**
 * Graph class holds every properties that a graph has
 */
public final class Graph {

	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;

	public Graph() {
		super();
		this.nodes = new ArrayList<Node>();
		this.edges = new ArrayList<Edge>();
	}
	/**
	 * shallow copy of the graph
	 * @param graph = the graph to copy the references to nodes and edges
	 */
	public Graph(Graph graph) {
		nodes = new ArrayList<Node>(graph.nodes.size());
		edges = new ArrayList<Edge>(graph.edges.size());
		buildNodes(graph.getNodes().size());
		for(Edge edge : graph.getEdges())
			addEdge(edge);
	}
	/**
	 * 
	 * @return an ArrayList<Node> containing the references at the nodes of the graph
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	/**
	 * 
	 * @param nodes = set the ArrayList of nodes' references
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}
	/**
	 * automated procedure that builds n nodes starting from 1 to n
	 * @param n = the nodes to add at the graph
	 */
	public void buildNodes(Integer n) {
		for (int i = 1; i <= n.intValue(); i++) 
			this.nodes.add(new Node(i));
	}
	/**
	 * 
	 * @return an ArrayList<Edge> containing the references at the edges of the graph
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	/**
	 * 
	 * @param edges = set the ArrayList of edges' references
	 */
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = new ArrayList<Edge>(edges);
	}
	/**
	 * Adds an edge's reference to graph and update adjacent lists of edge's vertexes
	 * complexity = O(1)
	 * @param e = the reference of the edge to add
	 */
	public void addEdge(Edge e) {
		if(e.getNode1().getID().compareTo(e.getNode2().getID()) > 0) {
			Node tmp = e.getNode1();
			e.setNode1(e.getNode2());
			e.setNode2(tmp);
		}
		this.edges.add(e);
		this.nodes.get(e.getNode1().getID()-1).updateAdjacencyList(e);
		if(!e.getNode1().getID().equals(e.getNode2().getID()))
			this.nodes.get(e.getNode2().getID()-1).updateAdjacencyList(e);
	}
	/**
	 * check if the graph has a cycle
	 * complexity = O(m + n)
	 * @return true if the graph has a cycle, false otherwise
	 */
	public Boolean hasCycle() {
		DepthFirstSearch();
		Boolean ret = false;
		for (Edge edge : this.edges) {
			if(edge.getLabel() == Label.BACK_EDGE) 
				ret = true;
			edge.setLabel(null);
			edge.getNode1().setVisited(false);
			edge.getNode2().setVisited(false);
		}
		return ret;
	}
	/**
	 * returns a node's reference fetching it by ID
	 * @param id = the position inside the ArrayLis<Node> of the reference of node to find 
	 * @return the reference of the node at position id, null if not present
	 */
	public Node getNodeByID(int id){
		if(id <= nodes.size())
			return nodes.get(id - 1);
		return null;
	}
	/**
	 * finds an edge, checking if its reference is present on the edge ArrayList<Edge>
	 * complexity = O(m)
	 * @param node1 = the first node of the edge
	 * @param node2 = the second node of the edge
	 * @return the reference of the edge if present, null otherwise
	 * @deprecated
	 */
	public Edge findEdge(Integer node1, Integer node2){
		for(Edge edge : edges){
			if((edge.getNode1().getID() == node1 && edge.getNode2().getID() == node2)
			|| (edge.getNode1().getID() == node2 && edge.getNode2().getID() == node1)){
				return edge;
			}
		}
		return null;
	}
	/**
	 * computes DFS even in non-connected graphs. 
	 * complexity = O(n)
	 */
	private void DepthFirstSearch() {
		for (Node node : this.nodes) 
			if(!node.isVisited() && !node.getAdjacencyList().isEmpty()){
				this.DepthFirstSearchCore(node);
			}
	}
	/**
	 * computes DFS in a connected component of the graph 
	 * @param start = the reference of the node to start the procedure
	 */
	private void DepthFirstSearchCore(Node start){
		start.setVisited(true);
		for(Edge edge : start.getAdjacencyList()){
			if(edge.getLabel() == null){
				Node opposite = this.nodes.get(edge.getOpposite(start).getID() - 1);
				if(!opposite.isVisited()){
					edge.setLabel(Label.DISCOVERY_EDGE);
					DepthFirstSearchCore(opposite);
				}
				else 
					edge.setLabel(Label.BACK_EDGE);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Graph other = (Graph) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	public int getDimension() {
		return nodes.size();
	}
}
