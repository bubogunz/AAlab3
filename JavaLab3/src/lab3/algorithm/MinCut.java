package lab3.algorithm;

import java.util.ArrayList;

import javafx.util.Pair;
import lab3.model.Graph;

public class MinCut {
	
	private Integer FullContraction(Graph G) {
		while(G.size() > 2) {
			//e <- Random(E)
			Integer rndNode1 = new Integer((int) (Math.random() * (G.size()) + 1));
			ArrayList<Integer> adjLstNode1 = G.getAdjacencyListFromGivenNode(rndNode1);
			Integer rndNode2 = new Integer((int) (Math.random() * (adjLstNode1.size()) + 1));
//			Pair<Integer, Integer> e = new Pair<Integer, Integer>(rndNode1, rndNode2);
			
			//G' <- (V', E') = G\e
			ArrayList<Integer> adjLstNode2 = G.getAdjacencyListFromGivenNode(rndNode2);
			for(int i=0; i<adjLstNode1.size(); ++i) {
				adjLstNode2.add(adjLstNode1.remove(0));
			}
			G.removeNode(rndNode1);
//			System.out.println();
		}
		
		return null;
	}
	
	private static int computeKValue(Graph G) {
		return (int) Math.ceil((Math.pow(G.size(), 2)/2 * Math.log(G.size())));
	}
	
	public Integer Karger(Graph G) {
		int k = computeKValue(G);
		Integer min = new Integer(Integer.MAX_VALUE);
		
		for(int i=0; i<k; ++i) {
			Graph nG = G.clone();
			Integer t = FullContraction(nG);
			if(t.compareTo(min) < -1) 
				min = t;
		}
		
		return min;
	}
}
