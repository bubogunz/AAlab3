package lab3.model;

public class Algorithm {
    public static int Karger(Graph G, int k){
        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < k; i++){
            Graph newGraph = new Graph(G);//O(m)
            int t = full_contraction(newGraph);//O(n^2)
            if(t < min)
                min = t;
        }
        return min;
    }

    public static double full_contraction_time(Graph G){
//        Graph newGraph = new Graph(G);
        long start = System.currentTimeMillis();
        long stop = 0;

//        full_contraction(newGraph);
        full_contraction(G);

        stop = System.currentTimeMillis();
        return (stop - start) / 1000;
    }

    private static int full_contraction(Graph G){
        for(int i = 0; i < G.getDimension() - 2; i++){
            Edge randomEdge = G.getAdjacentMatrix().getRandomEdge();//O(n)
            Integer nodeA = randomEdge.getNodeA();
            Integer nodeB = randomEdge.getNodeB();

            G.contraction(nodeA, nodeB);//O(n)
        }
        return G.getNumberOfEdges();//O((n^2)/2)
    }
}