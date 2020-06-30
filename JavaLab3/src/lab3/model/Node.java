
package lab3.model;

import java.util.ArrayList;
/**
 * Edge class holds every properties that an edge in a graph has
 */
public final class Node implements Comparable<Node>{
	private Integer ID = null;
	private Integer weight = null;
	private Node father = null;
	//list of references to adjacent edges 
	private ArrayList<Edge> adjacencyList = new ArrayList<Edge>();
	private Boolean visited = false;
	/**
	 * Constructor with only ID parameter
	 * @param id = the ID of the node
	 */
	public Node(Integer id){
		ID = id;
	}
	/**
	 * Copy constructor of the node with shallow copy of the adjacent list (reference copy)
	 * @param n = the node to copy
	 */
	public Node(Node n) {
		this.ID = n.ID;
		this.weight = n.weight;
		this.father = n.father;
		this.adjacencyList = new ArrayList<Edge>();
		this.visited = n.visited;
	}
	/**
	 * 
	 * @return true if the node has a father, false otherwise
	 */
	public Boolean hasFather() {
		if(father.getID().equals(null))
			return false;
		return true;
	}
	/**
	 * 
	 * @return the reference of the father of this node
	 */
	public Node getFather() {
		return father;
	}
	/**
	 * 
	 * @param father = the reference of the father of this node  
	 */
	public void setFather(Node father) {
		this.father = father;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public ArrayList<Edge> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(ArrayList<Edge> adjacentList) {
		this.adjacencyList = adjacentList;
	}

	public void updateAdjacencyList(Edge edge) {
		this.adjacencyList.add(edge);
	}

	public void setVisited(Boolean vis) {
		this.visited = vis;
	}
	/**
	 * 
	 * @return true if the node results visited, false otherwise
	 */
	public Boolean isVisited() {
		return this.visited;
	}
	/**
	 * clears the node from any label or any father set beforehand
	 */
	public void clear() {
		this.father = null;
		this.visited = false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((father == null) ? 0 : father.hashCode());
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((adjacencyList == null) ? 0 : adjacencyList.hashCode());
		result = prime * result + ((visited == null) ? 0 : visited.hashCode());
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
		Node other = (Node) obj;
		if (father == null) {
			if (other.father != null)
				return false;
		} else if (other.father != null 
				&& !father.getID().equals(other.father.getID()))
			return false;

		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (adjacencyList == null) {
			if (other.adjacencyList != null)
				return false;
		} else {
			if(adjacencyList.size() != other.adjacencyList.size())
				return false;
			int i = 0;
			Boolean guard = false;
			while(i < adjacencyList.size() && !guard) {
				if(!adjacencyList.get(i)
						.equals(other.getAdjacencyList().get(i)))
					guard = true;
				++i;
			}
			if(guard == true)
				return false;	
		}
		if (visited == null) {
			if (other.visited != null)
				return false;
		} else if (!visited.equals(other.visited))

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
		return "NodeID: " + ID + ", weight: " + weight;
	}

	@Override
	public int compareTo(Node n) {
		if(weight.compareTo(n.weight) > 0)
			return 1;
		else if(weight.compareTo(n.weight) == 0)
			return 0;
		return -1;
	}
}
