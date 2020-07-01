package lab3.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

public class Graph{
	private HashMap<Integer, ArrayList<Integer>> graph;
	
	public Graph(HashMap<Integer, ArrayList<Integer>> graph) {
		this.graph = graph;
	}
	
	@Override
	public Graph clone() {
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		for(Integer key: this.graph.keySet())
			graph.put(key, getAdjacencyListFromGivenNode(key));
		return new Graph(graph);
	}
	
	public Integer size() {
		return graph.size();
	}
	
	//O(1)
	public void removeEdgeAtPos(Integer u, int pos) {
		graph.get(u).remove(pos);
	}
	
	//O(degree(graph.get(u)))
	public void removeEdge(Integer u, Integer v) {
		graph.get(u).remove(v);
	}
	
	public void removeNode(Integer node) {
		graph.remove(node);
	}
	
	public ArrayList<Integer> getAdjacencyListFromGivenNode(Integer node){
		return graph.get(node);
	}
	
	/*private ArrayList<ArrayList<Integer>> matrix;

    public Graph(int n){
        matrix = new ArrayList<ArrayList<Integer>>();
       
        --n;
        for(int i=0; i<n; ++i) {
        	ArrayList<Integer> row = new ArrayList<Integer>(n - i);
        	for(int j=0; j<n-i; ++j) 
        		row.add(0);
        	
        	matrix.add(row);
        }
    }

    public void set(int n, int m, int v){
    	if(n != m) {
    		if(n > m)
    			setUtil(m, n, v);
    		else
    			setUtil(n, m, v);
    	}
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
    
    @Override
    public Graph clone() {
    	Graph G = new Graph(this.size());
    	for(int i = 0; i < size(); ++i) 
    		for(int j = 0; j < size(); ++j)
    			G.set(i, j, get(i,j));
    	
    	return G;
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
    }*/
}