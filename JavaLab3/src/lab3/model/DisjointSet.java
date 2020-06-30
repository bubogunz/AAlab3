
package lab3.model;

import java.util.ArrayList;
/**
 * The DisjointSet structure is a container class that represents a disjoint set data structure.
 */
public class DisjointSet{
    private ArrayList<Integer> parents = new ArrayList<Integer>();
    private ArrayList<Integer> ranks = new ArrayList<Integer>();

    /**
     * constructor that creates n subset wuth one element di ascending order.
     * complexity= O(n)
     * @param n
     */
    public DisjointSet(Integer n){
        parents = new ArrayList<Integer>(n);
        ranks = new ArrayList<Integer>(n);
        for(int i = 0; i < n; i++){
            parents.add(i);
            ranks.add(0);
        }
    }
    /**
     * 
     * @return An ArrayList<Integer> representing the parents of the disjoint set
     */
    public ArrayList<Integer> getParents(){
        return parents;
    }
    /**
     * 
     * @return An ArrayList<Integer> representing the ranks of the disjoint set
     */
    public ArrayList<Integer> getRanks(){
        return ranks;
    }
    /**
     * finds the subset in witch the element n belongs. 
     * complexity = O(log n)
     * @param n = Integer element of the subset to find
     * @return an Integer representing the subset in which the element n belongs
     * 
     */
    public Integer find(Integer n){
        if(parents.get(n) != n)
            parents.set(n, find(parents.get(n)));
        return parents.get(n);
    }
    /**
     * Joins subsets of x-element and y-element. 
     * complexity = O(log n)
     * @param x = Integer representing the first subset in the join procedure
     * @param y = Integer representing the second subset in the join procedure
     */
    public void union(Integer x, Integer y){
        Integer xRoot = find(x);//O(log n)
        Integer yRoot = find(y);//O(log n)

        if(xRoot == yRoot)
            return;

        if(ranks.get(xRoot) < ranks.get(yRoot)){
            Integer tmp = xRoot;
            xRoot = yRoot;
            yRoot = tmp;
        }

        parents.set(yRoot, xRoot);
        if(ranks.get(xRoot) == ranks.get(yRoot))
            ranks.set(xRoot, xRoot + 1);
    }
}