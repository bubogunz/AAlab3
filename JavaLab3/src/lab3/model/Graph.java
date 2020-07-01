package lab3.model;

import java.util.ArrayList;

public final class Graph {

	private AdjacentMatrix adjacentMatrix;

	public Graph(int n) {
		super();
		adjacentMatrix = new AdjacentMatrix(n);
	}

	public Graph(Graph graph){//O(m)
		adjacentMatrix = AdjacentMatrix.copy(graph.getAdjacentMatrix());
	}

    public AdjacentMatrix getAdjacentMatrix(){
        return adjacentMatrix;
	}

	public int getAdjacentMatrixWeight(int x, int y) {
		return adjacentMatrix.get(x, y);
	}

	public void setAdjacentmatrixWeight(int x, int y, int value) {
		adjacentMatrix.set(x, y, value);
	}

	public void addAdjacentsNodes(Integer node, ArrayList<Integer> adjacensts){
		for(Integer nextNode : adjacensts)
			adjacentMatrix.initializate(node, nextNode);
	}

	//O(n)
	public void contraction(Integer nodeA, Integer nodeB){
		for(int i = 0; i < adjacentMatrix.size(); i++){//O(n)
			if(adjacentMatrix.get(i, nodeB) != 0){
				adjacentMatrix.increment(i, nodeA, adjacentMatrix.get(i, nodeB));
				adjacentMatrix.set(i, nodeB, 0);
			}
		}
		if(adjacentMatrix.getEdges().size() != 1)
			adjacentMatrix.getEdges().removeIf(x -> (x.getNodeA() == nodeB || x.getNodeB() == nodeB));//O(n)
		adjacentMatrix.set(nodeA, nodeB, 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjacentMatrix == null) ? 0 : adjacentMatrix.hashCode());
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
		if (adjacentMatrix == null) {
			if (other.adjacentMatrix != null)
				return false;
		} else if (!adjacentMatrix.equals(other.adjacentMatrix))
			return false;
		return true;
	}

	public int getDimension() {
		return adjacentMatrix.size();
	}

	public int getNumberOfEdges(){
		return adjacentMatrix.sumOfValues();
	}
	
	@Override
	public String toString() {
		return adjacentMatrix.toString();
	}
}
