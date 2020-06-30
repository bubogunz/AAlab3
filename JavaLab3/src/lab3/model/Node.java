package lab3.model;

import java.util.ArrayList;
import java.util.Objects;

public class Node {
    private int ID;
    private ArrayList<Node> adjacentNodes = new ArrayList<Node>();


    public Node(int ID) {
        this.ID = ID;
    }

    public ArrayList<Node> getAdjacentNodes() {
        return this.adjacentNodes;
    }

    public void setAdjacentNodes(ArrayList<Node> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public void addAdjacentNode(Node n){
        adjacentNodes.add(n);
    }

    public boolean isLeaf(){
        return adjacentNodes.size() == 0 ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return ID == node.ID && Objects.equals(adjacentNodes, node.adjacentNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, adjacentNodes);
    }

    @Override
    public String toString() {
        return "{" +
            " ID='" + getID() + "'" +
            ", adjacentNodes='" + getAdjacentNodes() + "'" +
            "}";
    }

}