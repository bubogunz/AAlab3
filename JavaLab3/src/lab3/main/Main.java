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

import lab3.model.Graph;


public class Main {
	public static void main(String[] args) throws InterruptedException {
		// dare l'opzione -Xmx8192m per dire alla JVM di riservare 8GB di RAM (serve a
		// HeldKarp)
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

	/**
	 * @param algorithm = the algorithm to compute. This would be the Karger-Stein
	 *                  algorithm to calculate the minimum cut of a multigraph
	 * @throws InterruptedException
	 * 
	 *                              This function executes the algorithm choosen in
	 *                              68 graphs that are builded with mst_dataset
	 *                              folder. The results are stored in a file with
	 *                              the name of algorithm choosen.
	 */
	public static void compute(String algorithm) throws InterruptedException {
		int minutes = 1;
		// fetch files
		try (Stream<Path> walk = Files.walk(Paths.get("mincut_dataset"))) {
			List<String> input_dataset = walk.filter(Files::isRegularFile).map(x -> x.toString())
					.filter(x -> x.contains("input")).sorted().collect(Collectors.toList());
			List<Integer> output_dataset = walk.filter(Files::isRegularFile).map(x -> x.toString())
					.filter(x -> x.contains("output")).map(x -> {
						String file = x.toString();
						File myObj = new File(file);
						Scanner myReader;
						try {
							myReader = new Scanner(myObj);
							Integer out = Integer.parseInt(myReader.nextLine().split(" ")[0]);
							myReader.close();
							return out;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						return -1;
					}).sorted().collect(Collectors.toList());

			HashMap<String, Integer> mincut_dataset = new HashMap<String, Integer>(input_dataset.size());
			for(int i = 0; i < input_dataset.size(); i++)
				mincut_dataset.put(input_dataset.get(i), output_dataset.get(i));

			final File outputPath = new File("mincut.txt");
			FileWriter fw = new FileWriter(outputPath, false);
			fw.write("Size Dataset\tSolution\tTime(s)\tError(%)\n");
			boolean testResult = true;


			System.out.println("Executing " + algorithm + " algorithm");

			mincut_dataset.forEach((in, out) -> {
				System.out.println(in.toString());
				try{
					System.out.println("Input: " + in);
					int cost = 0;
	
					File myObj = new File(in);
					Scanner myReader = new Scanner(myObj);
					ArrayList<Integer> nodes = new ArrayList<Integer> ();
					ArrayList<ArrayList<Integer>> adjacentNodes = new ArrayList<ArrayList<Integer>> ();
					
					while(myReader.hasNextLine()){
						String line = myReader.nextLine();
						String[] values = line.split(" ");
						nodes.add(Integer.parseInt(values[0]));
						ArrayList<Integer> adjacentN = new ArrayList<Integer> ();
						for(int i = 1; i < values.length; i++){
							adjacentN.add(Integer.parseInt(values[i]));
						}
						adjacentNodes.add(adjacentN);
					}

					myReader.close();

					Graph G = new Graph(nodes.size());
					for(int i = 0; i < nodes.size(); i++){
						G.addAdjacentsNodes(nodes.get(i), adjacentNodes.get(i));
					}

					double k = Math.pow(nodes.size(), 2)/2*Math.log(nodes.size());

					long start = System.nanoTime();
					long stop = 0;

					cost = Algorithm.Karger(G, k);

					if(stop == 0)
						stop = System.nanoTime();
					double time = (stop - start) / 1000000000;

					writeresults(fw, in, cost, time, out);
					
				}catch (FileNotFoundException e) {}

			fw.close();
				//				try {
				//					int example = k;
				//					String entryset = tsp_dataset.get(example);
				//					System.out.println("Input: " + entryset);
				//					int cost = 0;
				//	
				//					File myObj = new File(entryset);
				//					Scanner myReader = new Scanner(myObj);
				//					String line = myReader.nextLine();
				//					String name = line.split(" ")[1];
				//	
				//					while(myReader.hasNextLine() && !line.split(" ")[0].equals("DIMENSION:"))
				//						line = myReader.nextLine();
				//					Integer size_graph = Integer.valueOf(line.split(" ")[1]);
				//					
				//					Graph graph = new Graph(size_graph);
				//					
				//					double[][] nodes = new double[size_graph][2];
				//	
				//					line = myReader.nextLine();
				//					String[] modalities = line.split(" ");
				//					String mode = "";
				//					for(int i=0; i<modalities.length; ++i) {
				//						if(modalities[i].equals("EUC_2D"))
				//							mode = "EUC_2D";
				//						else if(modalities[i].equals("GEO"))
				//							mode = "GEO";
				//					}
				//	
				//					while(myReader.hasNextLine() && !line.equals("NODE_COORD_SECTION"))
				//						line = myReader.nextLine();
				//	
				//					for(int i = 0; i < size_graph && myReader.hasNextLine() && !line.equals("EOF"); i++) {
				//						line = myReader.nextLine();
				//						String[] data = line.split(" ");
				//						nodes[i][0] = Double.valueOf(data[1]);
				//						nodes[i][1] = Double.valueOf(data[2]);
				//					}
				//	
				//					myReader.close();
				//	
				//					for(int i = 0; i < size_graph; i++){
				//						for(int j = i + 1; j < size_graph; j++){
				//							switch (mode){
				//							case "EUC_2D":
				//								graph.setAdjacentmatrixWeight(i, j, Distancies.euclidean(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]));
				//								break;
				//							case "GEO":
				//								graph.setAdjacentmatrixWeight(i, j, Distancies.geo(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]));
				//								break;
				//							default:
				//							}
				//						}
				//					}
				//					
				//					long start = System.nanoTime();
				//	
				//					long stop = 0;
				//	
				//					switch (algorithm){
				//						case "HeldKarp":
				//						
				//						ExecutorService executor = Executors.newCachedThreadPool();
				//						Future<Void> future  = executor.submit(new Callable<Void>() {
				//							@Override
				//	
				//							public Void call() throws Exception {
				//								tsp.HeldKarp();
				//								return null;
				//	
				//							}
				//						});
				//						//does not brutally shut down the program, it waits until the last l computation is finished
				//						executor.shutdown();
				//						
				//						if(entryset.contains("14.tsp") || entryset.contains("16.tsp") || entryset.contains("22.tsp"))
				//							for(int i=0; i<minutes*1200 && !future.isDone(); ++i)
				//								Thread.sleep(50);
				//						else 
				//							Thread.sleep(30000);
				//					
				//						//Sets the "interrupted flag" that will break computation of the thread gracefully
				//						future.cancel(true);
				//						stop = System.nanoTime();
				//						long timeElapsed = stop - start;
				//						double time = timeElapsed;
				//						time = time / 1000000000;
				//						System.out.println("Stopped in " + time + "s");
				//						executor.awaitTermination(1, TimeUnit.DAYS);
				//	
				//						cost = tsp.getResult();
				//						break;
				//					case "Heuristic":
				//						cost = tsp.CheapestInsertion();
				//						break;
				//					case "2Approx":
				//						cost = tsp.TriangleTSP();
				//						break;
				//					default:
				//						throw new InvalidParameterException("Wrong choice of algorithm");	
				//					}
				//					
				//					if(stop == 0)
				//						stop = System.nanoTime();
				//	
				//					long timeElapsed = stop - start;
				//					double time = timeElapsed;
				//					time = time / 1000000000;
				//	
				//					fw.write(name + "\t" + cost + "\t" + time + "\t");
				//	
				//					if(!TestTSP.test(algorithm, example, cost, graph.getDimension(), fw))
				//						testResult = false;
				//					
				//					printHeapInfo();
				//					System.out.println("Time elapsed: " + time + "s");
				//					System.out.println("cost: " + cost);
				//					System.out.println();
				//				} catch (FileNotFoundException e) {}
				//				//garbage collector
				//				System.gc();
				//				}
				//				//  });
				//				fw.close();
				//				if(testResult)
				//					System.out.println("All tests passed!");
				//				else
				//					System.out.println("Some tests not passed.");
				//				System.out.println("Finish!");
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		static void writeresults(FileWriter fw, String in, int cost, double time, int out) throws IOException {
			String[] words = in.split("_");
			int size = Integer.parseInt(words[3]);
			double error = (cost - out)/out*100;
			if(cost - out < 0 && size >= 150)
				error = 0;

			fw.write(size + "\t" + cost + "\t" + time + "\t" + error + "\n");
		}
	}
