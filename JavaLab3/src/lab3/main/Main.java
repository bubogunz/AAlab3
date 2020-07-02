package lab3.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lab3.model.Algorithm;
import lab3.model.Graph;


public class Main {

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

	/**
	 * This function compute the minimumcut problem whit the Karger
	 * algorithm and measures its the computational time. It measures
	 * also the computational time of first iteration of Karger
	 * algorithm, calling the sub-funtion full_contaction.
	 * At the end writes all results in the file "mincut.txt" 
	 */
	public static void main (String args[]) throws InterruptedException {
		// fetch files
		try (Stream<Path> walk = Files.walk(Paths.get("mincut_dataset"))) {
			List<String> mincut_dataset = walk.filter(Files::isRegularFile).map(x -> x.toString()).sorted()
					.collect(Collectors.toList());
			
			Map<String, Integer> dataset = new TreeMap<String, Integer>();
			
			mincut_dataset.stream().forEach(file -> {
				if(file.contains("input")) 
					dataset.put(file, null);
				else {
					File myObj = new File(file);
					Scanner myReader;
					try {
						myReader = new Scanner(myObj);
						int value = Integer.valueOf(myReader.nextLine());
						dataset.put(file.replace("output", "input"), value);
					} catch (FileNotFoundException e) {e.printStackTrace();}
				}
			});

			final File outputPath = new File("mincut.txt");
			FileWriter fw = new FileWriter(outputPath, false);
			fw.write("Size\tSolution\tTime(s)\tFull Contraction time(s)\tDiscovery time(s)\tError(%)\n");
			fw.close();

			 dataset.forEach((in, out) -> {
				try{
					// String in = "mincut_dataset/input_random_29_150.txt";
					// int out = 37;
					System.out.println("Input: " + in);
					System.out.println("Expected output:" + out);
	
					File myObj = new File(in);
					Scanner myReader = new Scanner(myObj);
					ArrayList<Integer> nodes = new ArrayList<Integer> ();
					ArrayList<ArrayList<Integer>> adjacentNodes = new ArrayList<ArrayList<Integer>> ();
					
					while(myReader.hasNextLine()){
						String line = myReader.nextLine();
						String[] values = line.split(" ");
						nodes.add(Integer.parseInt(values[0]) - 1);
						ArrayList<Integer> adjacentN = new ArrayList<Integer> ();
						for(int i = 1; i < values.length; i++){
							adjacentN.add(Integer.parseInt(values[i]) - 1);
						}
						adjacentNodes.add(adjacentN);
					}

					myReader.close();

					Graph G = new Graph(nodes.size());
					for(int i = 0; i < nodes.size(); i++){
						G.addAdjacentsNodes(nodes.get(i), adjacentNodes.get(i));
					}

					int k = (int) Math.ceil(Math.pow(nodes.size(), 2)/2*Math.log(nodes.size()));

					long start = System.currentTimeMillis();
					long stop = 0;

					int cost = Algorithm.Karger(G, k);

					if(stop == 0)
						stop = System.currentTimeMillis();
					double time = stop - start;
					time = time  / 1000;

					double time_full_contr = Algorithm.full_contraction_time(G);

					double discovery_time = Algorithm.Karger_discovery_time(G, k, out);

					writeresults(in, cost, time, time_full_contr, discovery_time, out);
					
				}catch (FileNotFoundException e) {}
				catch (IOException e) {
					e.printStackTrace();
				}
			 });
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Write the results of "compute" function in "mincut.txt"
	 * @param fw FileWriter for the mincut.txt file
	 * @param in name of the graph of witch it was calculated mincut
	 * @param cost the mincut calculated
	 * @param time the time to calculate the mincut
	 * @param time_full_contr the time to calculate first iteration of Karger algorithm
	 * @param discovery_time the time to find the correct mincut
	 * @param out the correct mincut value, user to calcurate the relative error
	 * @throws IOException
	 */
	static void writeresults(String in, int cost, double time, double time_full_contr, double discovery_time, int out) throws IOException {
		final File outputPath = new File("mincut.txt");
		FileWriter fw = new FileWriter(outputPath, true);
		String[] words = in.split("_");
		int size = Integer.parseInt(words[4].replace(".txt", ""));
		double error = (cost - out)/out*100;
		error = Math.floor(error * 100) / 100;
		boolean test = true;
		if(cost - out < 0){
			if(size >= 150)
				error = 0;
			else{
				test = false;
				System.out.println("Test FAILED! Cost is " + cost);
				System.out.println();
				fw.close();			
				return;
			}
		}
		
		System.out.println("Test passed! Cost is " + cost);
		if(!test)
			System.out.println("Some tests not passed");
		System.out.println();
		fw.write(size + "\t" + cost + "\t" + time + "\t" + time_full_contr + "\t" + discovery_time + "\t" + error + "\n");
		fw.close();
	}
}
