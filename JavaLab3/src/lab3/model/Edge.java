package lab3.model;
/**
 * Edge class holds every properties that an edge in a graph has
 */
public final class Edge implements Comparable<Edge>{

	public enum Label{
		DISCOVERY_EDGE,
		BACK_EDGE
	}

	private Node node1 = null;
	private Node node2 = null;
	private Integer weight = null;
	private Label label = null;
	/**
	 * empty constructor
	 */
	public Edge() {
		super();
	}
	/**
	 * constructor with minimum required parameters for an edge
	 * @param v1 = first node of the edge
	 * @param v2 = second node of the edge
	 * @param weight = weight of the node
	 */
	public Edge(Node v1, Node v2, Integer weight) {
		super();
		this.node1 = v1;
		this.node2 = v2;
		this.weight = weight;
	}
	/**
	 * @param start = the reference of the node at the first side
	 * @return the reference of the node at the other side of the edge
	 */
	public Node getOpposite(Node start) {
		return start.getID().equals(this.node1.getID()) 
				? this.node2 
						: this.node1;
	}
	/**
	 * 
	 * @return the reference at the first node of the edge
	 */
	public Node getNode1() {
		return node1;
	}
	/**
	 * 
	 * @param node1 = the reference to change 
	 */
	public void setNode1(Node node1) {
		this.node1 = node1;
	}
	/**
	 * 
	 * @return the reference at the second node of the edge
	 */
	public Node getNode2() {
		return node2;
	}
	/**
	 * 
	 * @param node2 = set a new reference on the second node of the edge
	 */
	public void setNode2(Node node2) {
		this.node2 = node2;

	}
	/**
	 * 
	 * @return the weight of the node, null if not present
	 */
	public Integer getWeight() {
		return weight;
	}
	/**
	 * 
	 * @param weight = the new weight of the node
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * 
	 * @return the label of the node, null if not present
	 */
	public Label getLabel() {
		return label;
	}
	/**
	 * 
	 * @param label = the label to set. Can be DISCOVERY_EDGE or BACK_EDGE
	 */
	public void setLabel(Label label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((node1 == null) ? 0 : node1.hashCode());
		result = prime * result + ((node2 == null) ? 0 : node2.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Edge other = (Edge) obj;
		if (label != other.label)
			return false;
		if (node1 == null) {
			if (other.node1 != null)
				return false;
		} else if (!node1.getID().equals(other.node1.getID()))
			return false;
		if (node2 == null) {
			if (other.node2 != null)
				return false;
		} else if (!node2.getID().equals(other.node2.getID()))

			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "(" + node1.getID() + ", " + node2.getID() + "; " + weight + ")";
	}

	@Override
	public int compareTo(Edge e) {
		if(weight.compareTo(e.weight) > 0)
			return 1;
		else if(weight.compareTo(e.weight) == 0)
			return 0;
		return -1;

	}
}
