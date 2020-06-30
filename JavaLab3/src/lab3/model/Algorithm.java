package lab3.model;

import java.util.Random;

public class Algorithm {
    public static int Karger(Graph G, int k){
        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < k; i++){
            int t = full_contraction(G);
            if(t < min)
                min = t;
        }
        return min;
    }

    private static int full_contraction(Graph G){
        Graph newGraph = new Graph(G);
        for(int i = 0; i < newGraph.getDimension(); i++){
            Random rnd = new Random(42);
            int nodeA = rnd.nextInt(newGraph.getDimension());
            int nodeB = rnd.nextInt(newGraph.getDimension());
            while(newGraph.getAdjacentMatrixWeight(nodeA, nodeB) == 0){
                nodeA = rnd.nextInt(newGraph.getDimension());
                nodeB = rnd.nextInt(newGraph.getDimension());
            }
            newGraph.contraction(nodeA, nodeB);
        }
        return newGraph.getNumberOfEdges();
    }
}