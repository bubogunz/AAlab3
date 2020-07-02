package lab3.model;

public class Algorithm {
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

    public static double Karger_discovery_time(Graph G, int k, int out){
        long start = System.currentTimeMillis();
        long stop = 0;

        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < k; i++){//O(n^2 log n)
            Graph newGraph = new Graph(G);//O(m)
            int t = full_contraction(newGraph);//O(n^2)
            if(t < min)
                min = t;
            if(min == out){
                if(stop == 0)
                    stop = System.currentTimeMillis();
                double time = stop - start;
                return time / 1000;
            }
        }

        if(stop == 0)
            stop = System.currentTimeMillis();
        double time = stop - start;
        return time / 1000;
    }

    /**
     * Measure the compute time of full_contraction function.
     * Complexity = O(n^2)
     * @param G the graph you want to calculate the mincut 
     * @return the time to compute full_contraction
     */
    public static double full_contraction_time(Graph G){
        long start = System.currentTimeMillis();
        long stop = 0;

        full_contraction(G);//O(n)

        if(stop == 0)
            stop = System.currentTimeMillis();
        double time = stop - start;
        return time / 1000;
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