package lab3.model;

import java.util.ArrayList;

public class AdjacentMatrix{
    private ArrayList<ArrayList<Integer>> matrix;

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

    public void set(int n, int m, int v){
    	if(n != m) {
    		if(n > m)
    			setUtil(m, n, v);
    		else
    			setUtil(n, m, v);
    	}
    }

    public void increment(int n, int m){
        Integer v = get(n, m) + 1;
        set(n, m, v);
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

    public static AdjacentMatrix copy(AdjacentMatrix m){
        AdjacentMatrix tmp = new AdjacentMatrix(m.size());
        for(int i = 0; i < m.size(); i++){
            ArrayList<Integer> row = new ArrayList<Integer>(i + 1);
            tmp.matrix.add(row);
            for(int j = 0; j < i + 1; j++)
                row.add(m.get(i + 1, j));
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