package lab3.algorithm;

import lab3.model.Edge;
import lab3.model.Graph;

public class MinCut {
    /**
     * Compute the mincut of graph G with
     * hight probability.
     * Complexity = O(n^4 log n)
     * @param G the graph you want to calculate the mincut
     * @param k number of iteration to have hight probability
     * @return mincut cost
     */
    public static int Karger(Graph G, int k){
        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < k; i++){//O(n^2 log n)
            Graph newGraph = new Graph(G);//O(m)
            int t = full_contraction(newGraph);//O(n^2)
            if(t < min)
                min = t;
        }
        return min;
    }

    /**
     * Measure computational time to find the mincut
     * @param G the graph you want to calculate the mincut
     * @param k number of iteration to have hight probability
     * @param out the mincut cost
     * @return the time to find the mincut
     */
    public static double Karger_discovery_time(Graph G, int k, int out){
        long start = System.nanoTime();
        long stop = System.nanoTime();

        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < k; i++){//O(n^2 log n)
            Graph newGraph = new Graph(G);//O(m)
            int t = full_contraction(newGraph);//O(n^2)
            if(t < min)
                min = t;
            if(min == out){
                stop = System.nanoTime();
                break;
            }
        }
        long timeElapsed = stop - start;
		double time = timeElapsed;
		time = time / 1000000000;
		return time;
    }

    /**
     * Measure the compute time of full_contraction function.
     * Complexity = O(n^2)
     * @param G the graph you want to calculate the mincut 
     * @return the time to compute full_contraction
     */
    public static double full_contraction_time(Graph G){
        long start = System.nanoTime();
        long stop = 0;

        full_contraction(G);//O(n)

        if(stop == 0)
            stop = System.nanoTime();
        long timeElapsed = stop - start;
		double time = timeElapsed;
		time = time / 1000000000;
		return time;
    }

    /**
     * Make the full contraction for Karger algorithm.
     * Complexity = O(n^2)
     * @param G the graph you want to calculate the mincut
     * @return The number of edge of new graph contracted
     */
    private static int full_contraction(Graph G){
        while(G.getAdjacentMatrix().getEdges().size() > 1){//O(n)
            Edge randomEdge = G.getAdjacentMatrix().getRandomEdge();//O(n)
            Integer nodeA = randomEdge.getNodeA();
            Integer nodeB = randomEdge.getNodeB();
            G.contraction(nodeA, nodeB);//O(n)
        }
        return G.getNumberOfEdges();//O((n^2)/2)
    }
}