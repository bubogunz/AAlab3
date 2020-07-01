package lab3.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lab3.algorithm.MinCut;
import lab3.model.Graph;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		// printHeapInfo();
		compute("Karger");
		// test();
	}

	public static void printHeapInfo() {

		long heapSize = Runtime.getRuntime().totalMemory();

		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.//
		// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();

		// Get amount of free memory within the heap in bytes. This size will increase
		// // after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory();

		System.out.println("heapsize " + formatSize(heapSize));
		System.out.println("heapmaxsize " + formatSize(heapMaxSize));
		System.out.println("heapFreesize " + formatSize(heapFreeSize));
		System.out.println("usedHeap " + formatSize(heapMaxSize - heapFreeSize));
	}

	public static String formatSize(long v) {
		if (v < 1024)
			return v + " B";
		int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
		return String.format("%.1f %sB", (double) v / (1L << (z * 10)), " KMGTPE".charAt(z));
	}

	public static void compute(String algorithm) {
		try (Stream<Path> walk = Files.walk(Paths.get("mincut_dataset"))) {
			List<String> mincut_dataset = walk.filter(Files::isRegularFile).map(x -> x.toString()).sorted()
					.collect(Collectors.toList());
			
			HashMap<String, Integer> dataset = new HashMap<>(mincut_dataset.size()/2);
			
			mincut_dataset.stream().forEach(file -> {
				if(file.contains("input")) 
					dataset.put(file, null);
				else {
					File myObj = new File(file);
					Scanner myReader;
					try {
						myReader = new Scanner(myObj);
						dataset.put(file.replace("output", "input"), new Integer(myReader.nextLine()));
					} catch (FileNotFoundException e) { }
				}
			});
			
			dataset.keySet().stream().forEach(input -> {
//				input = "mincut_dataset/input_random_1_6.txt";
//				Integer size = new Integer(input.substring(input.lastIndexOf("_") + 1, input.indexOf(".txt")));
				HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
				File myObj = new File(input);
				try {
					Scanner myReader;
					myReader = new Scanner(myObj);
					
					while(myReader.hasNextLine()) {
						String g[] = myReader.nextLine().split(" ");
						ArrayList<Integer> adjacencyList = new ArrayList<Integer>(g.length-1);
						Integer n = new Integer(g[0]);
						
						for(int i=1; i<g.length; ++i)
							adjacencyList.add(new Integer(g[i]));
						graph.put(n, adjacencyList);
					}
					myReader.close();
					
//					for (Integer key : graph.keySet()) {
//						System.out.println(key + ": " + graph.get(key));						
//					}
					Graph G = new Graph(graph);
					MinCut mincut = new MinCut();
					Integer result = mincut.Karger(G);
					
				} catch (FileNotFoundException e) { }
				
			});
			
			final File outputPath = new File("mincut.txt");
			
			FileWriter fw = new FileWriter(outputPath, false);
			fw.write("Dataset\tSolution\tTime(s)\tError(%)\n");
			

			System.out.println("Executing " + algorithm + " algorithm");
			
			fw.close();
			//garbage collector
			System.gc();
		} catch (IOException e) {
			System.out.println("mincut_dataset/ folder not found");
			e.printStackTrace();
		}
	}

	static void test(){
		//TODO
	}
}
