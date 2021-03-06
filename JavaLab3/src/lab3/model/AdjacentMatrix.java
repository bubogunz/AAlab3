package lab3.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Adjacent matrix for the graphs. It contain a list
 * of edge to return a random edge for the full_contraction
 * function.
 * Each value of the matrix is the molteplicity of the edge.
 */
public class AdjacentMatrix{
    private ArrayList<ArrayList<Integer>> matrix;
    private LinkedList<Edge> edges = new LinkedList<Edge>();

    public AdjacentMatrix(int n){
        matrix = new ArrayList<ArrayList<Integer>>();
       
        n--;
        for(int i=0; i<n; ++i) {
        	ArrayList<Integer> row = new ArrayList<Integer>(n - i);
        	for(int j=0; j<n-i; ++j) 
        		row.add(0);
        	
        	matrix.add(row);
        }
    }

    public LinkedList<Edge> getEdges() {
        return this.edges;
    }

    public void set(int n, int m, int v){
    	if(n != m) {
    		if(n > m)
    			setUtil(m, n, v);
    		else
    			setUtil(n, m, v);
    	}
    }

    /**
     * Increment the molteplicity of edge (n, m)
     * by the value v
     * Complexity = O(1)
     * @param n first vertex of edge
     * @param m last vertex of edge
     * @param v value to increment
     */
    public void increment(int n, int m, int v){
        if(get(n, m) == 0 && n != m)
            edges.add(new Edge(n, m));
        v += get(n, m);
        if(v == 0)
            System.out.println("aiuto");
        set(n, m, v);
    }

    /**
     * Used during construction of graph,
     * initialize the value of adjacentMatrix in the
     * edge (n, m)
     * Complexity = O(1)
     * @param n first vertex of edge
     * @param m second vertex of edge
     */
    public void initializate(int n, int m){
        if(get(n,m) == 0)
            edges.add(new Edge(n, m));
        set(n, m, 1);
    }

    /**
     * Complexity = O(1)
     * @return a random edge of the graph
     */
    public Edge getRandomEdge(){
        int rnd = (int) (Math.random() * edges.size());
        return edges.get(rnd);
    }
    
    private void setUtil(int n, int m, int v) {
    	matrix.get(n).set(m - (n + 1), v);
    }

    public Integer get(int n, int m){
    	if(n == m)
    		return 0;
    	return n > m ? getUtil(m, n)
    			: getUtil(n, m);
    }
    
    private Integer getUtil(int n, int m) {
    	return matrix.get(n).get(m - (n+1));
    }

    public Integer size(){
        return matrix.size() + 1;
    }

    public Integer sumOfValues(){
        int count = 0;
        for(ArrayList<Integer> list : matrix){
            for(Integer value : list)
                count += value;
        }
        return count;
    }

    /**
     * Return a shallow copy of the adjacent matric
     * Complexity = O(m)
     * @param m the adjacentMatrix to copy
     * @return the new adjacentMatrix
     */
    public static AdjacentMatrix copy(AdjacentMatrix m){
        AdjacentMatrix tmp = new AdjacentMatrix(m.size());

        for(Edge edge : m.edges){
            int nodeA = edge.getNodeA();
            int nodeB = edge.getNodeB();
            tmp.edges.add(new Edge(nodeA, nodeB));
            tmp.set(nodeA, nodeB, m.get(nodeA, nodeB));
        }
        return tmp;
    }

    @Override
    public String toString(){
        String tmp = "";
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++)
                tmp += get(i, j) + "\t";
            tmp += "\n";
        }
        return tmp;
    }
}