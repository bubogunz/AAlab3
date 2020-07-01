package lab3.model;

public class Edge{
    private Integer nodeA;
    private Integer nodeB;

    public Edge(Integer nodeA, Integer nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public Integer getNodeA() {
        return this.nodeA;
    }

    public void setNodeA(Integer nodeA) {
        this.nodeA = nodeA;
    }

    public Integer getNodeB() {
        return this.nodeB;
    }

    public void setNodeB(Integer nodeB) {
        this.nodeB = nodeB;
    }

    @Override
    public String toString(){
        return "(" + getNodeA() + "," + getNodeB() + ")";
    }
}
