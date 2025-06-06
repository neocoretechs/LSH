package com.neocoretechs.lsh;

import java.util.ArrayList;
import java.util.List;

import com.neocoretechs.lsh.util.FileUtils;

/**
 *A class implementing a command line interface for the LSH program.
 */
public class CommandLineInterface {
	private static final int VECTOR_DIMENSION = 50;
	//GLOVE_FILE = "glove.6B.50d.txt";
	public static HashTable.hashFamilyType hashFamilyType = HashTable.hashFamilyType.cos; // l1, l2, cos
	private Index index;
	public static int numberOfHashTables = 8;
	public static int numberOfHashes = 8;
	public static int numberOfNeighbors = -1;//4;
	
	/**
	 * Show the nearest neighbors stored in the pre-built index.
	 * @param queries The list of tensors to query, the query method is called for each element.
	 */
	public void showNeighbors(List<F32FloatTensor> queries){
		if(queries != null){
			List<F32FloatTensor> neighbors = null;
			for(F32FloatTensor query:queries){
				neighbors = query(query, numberOfNeighbors);
				System.out.println("Target:"+query.getKey());
				System.out.println("Results:");
				for(F32FloatTensor neighbor:neighbors){
					System.out.print(neighbor.getKey() + ";");
				}
				System.out.print("\n");
				System.out.println("There were "+neighbors.size()+" neighbors for "+query.getKey());
			}	
		}		
	}
	/**
	 * Build an index by creating a new one and adding each vector.
	 * {@link com.neocoretechs.lsh.families.HashFunction}
	 * 
	 * @param dataset list of tensors to build or null to deserialize current index
	 * @param numberOfHashes
	 *            The number of hashes to use in each hash table.
	 * @param numberOfHashTables
	 *            The number of hash tables to use.
	 */
	public void buildIndex(List<F32FloatTensor> dataset, int numberOfHashes, int numberOfHashTables) {
		index = Index.deserialize(HashTable.hashFactory(hashFamilyType, VECTOR_DIMENSION), numberOfHashes, numberOfHashTables);
		if(dataset != null){
			for(F32FloatTensor vector : dataset){
				index.index(vector);
			}
			Index.serialize(index);
		}
	}
	
	/**
	 * Find the nearest neighbors for a query in the index.
	 * 
	 * @param query
	 *            The query vector.
	 * @param neighborsSize
	 *            The size of the neighborhood. The returned list length
	 *            contains the maximum number of elements, or less. Zero
	 *            elements are possible.
	 * @return A list of nearest neighbors, according to the index. The returned
	 *         list length contains the maximum number of elements, or less.
	 *         Zero elements are possible.
	 */
	public List<F32FloatTensor> query(final F32FloatTensor query,int neighborsSize){
		return index.query(query,neighborsSize);
	}
	/**
	 * Args either build or query, supply either source or list of queries
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ArrayList<F32FloatTensor> vectors = new ArrayList<F32FloatTensor>();
		CommandLineInterface cmdl = new CommandLineInterface();
		if(args.length < 2) {
			System.out.println("Usage: java [build | query] vectorfile");
		}
		System.out.println(args[0]+" "+args[1]+" ...");
		// read either the source vecors of the query vecors
		List<String[]> data = FileUtils.readCSVFile(args[1], " ", -1);
		for(String[] parts: data) {
			String word = parts[0];
			//double[] vector = new double[VECTOR_DIMENSION];
			F32FloatTensor vector = new F32FloatTensor(VECTOR_DIMENSION);
			vector.setKey(word);
			for (int i = 0; i < VECTOR_DIMENSION; i++) {
				//vector[i] = Double.parseDouble(parts[i + 1]);
				vector.setFloat(i, Float.parseFloat(parts[i + 1]));	
			}
			vectors.add(vector);
		}
		if(args[0].equals("build")) {
			cmdl.buildIndex(vectors, numberOfHashes, numberOfHashTables);
		} else {
			if(args[0].equals("query")) {
				cmdl.index = Index.deserialize(HashTable.hashFactory(hashFamilyType, VECTOR_DIMENSION), numberOfHashes, numberOfHashTables);
				cmdl.showNeighbors(vectors);
			}
		}
	}
}