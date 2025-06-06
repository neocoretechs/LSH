package com.neocoretechs.lsh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.neocoretechs.lsh.families.CityBlockHashFamily;
import com.neocoretechs.lsh.families.CosineHashFamily;
import com.neocoretechs.lsh.families.EuclidianHashFamily;
import com.neocoretechs.lsh.families.HashFamily;
import com.neocoretechs.lsh.families.HashFunction;
import com.neocoretechs.lsh.util.Parallel;

/**
 * An {@link Index} contains one or more locality sensitive hash tables. These hash
 * tables contain the mapping between a combination of a number of hashes
 * (encoded using an integer) and a list of possible nearest neighbors.
 * 
 */
class HashTable implements Serializable {
	private static final long serialVersionUID = -5410017645908038641L;
	private static boolean DEBUG = true;
	private static int radius = 500;
	/**
	 * Contains the mapping between a combination of a number of hashes (encoded
	 * using an integer) and a list of possible nearest neighbours
	 */
	private HashMap<Integer,List<F32FloatTensor>> hashTable;
	private HashFunction[] hashFunctions;
	private HashFamily family;
	private int index;
	
	static enum hashFamilyType {
		l1, l2, cos;
	}
	/**
	 * Initialize a new hash table, it needs a hash family and a number of hash
	 * functions that should be used.
	 * 
	 * @param numberOfHashes
	 *            The number of hash functions that should be used.
	 * @param family
	 *            The hash function family knows how to create new hash
	 *            functions, and is used therefore.
	 */
	public HashTable(int index, int numberOfHashes, HashFamily family) {
		this.index = index;
	    this.hashTable = new HashMap<Integer, List<F32FloatTensor>>();
	    this.hashFunctions = new HashFunction[numberOfHashes];
	    if(numberOfHashes > 64)
	    	Parallel.parallelFor(0, numberOfHashes, i -> {
	    		hashFunctions[i] = family.createHashFunction();
	    	});
	    else
	    	for(int i = 0; i < numberOfHashes; i++)
	    		hashFunctions[i] = family.createHashFunction();
	    this.family = family;
	}

	/**
	 * Query the hash table for a vector. It calculates the hash for the vector,
	 * and does a lookup in the hash table. If no candidates are found, an empty
	 * list is returned, otherwise, the list of candidates is returned.
	 * 
	 * @param query
	 *            The query vector.
	 * @return Does a lookup in the table for a query using its hash. If no
	 *         candidates are found, an empty list is returned, otherwise, the
	 *         list of candidates is returned.
	 */
	public List<F32FloatTensor> query(F32FloatTensor query) {
		Integer combinedHash = hash(query);
		if(DEBUG)
			System.out.println("Combined hash for query:"+combinedHash);
		if(hashTable.containsKey(combinedHash))
			return hashTable.get(combinedHash);
		else
			return new ArrayList<F32FloatTensor>();
	}

	/**
	 * Add a vector to the index.
	 * @param vector
	 */
	public void add(F32FloatTensor vector) {
		Integer combinedHash = hash(vector);
		if(! hashTable.containsKey(combinedHash)){
			hashTable.put(combinedHash, new ArrayList<F32FloatTensor>());
		}
		hashTable.get(combinedHash).add(vector);
	}
	
	/**
	 * Calculate the combined hash for a vector.
	 * @param vector The vector to calculate the combined hash for.
	 * @return An integer representing a combined hash.
	 */
	private Integer hash(F32FloatTensor vector){
		int hashes[] = new int[hashFunctions.length];
		for(int i = 0 ; i < hashFunctions.length ; i++){
			hashes[i] = hashFunctions[i].hash(vector);
		}
		Integer combinedHash = family.combine(hashes);
		return combinedHash;
	}

	/**
	 * Return the number of hash functions used in the hash table.
	 * @return The number of hash functions used in the hash table.
	 */
	public int getNumberOfHashes() {
		return hashFunctions.length;
	}
	/**
	 * GEnerate the proper HashFamily depending on type and dataset size
	 * @param type type of HashFamily: l1, l2, cos
	 * @param datasetSize vectors.size
	 * @return the HashFamily implementation, defaults to cos
	 */
	public static HashFamily hashFactory(hashFamilyType type, int datasetSize) {
		int w;
		switch(type) {
		case l1:
			w = (int) (10 * radius);
			w = w == 0 ? 1 : w;
			return new CityBlockHashFamily(w,datasetSize);
		case l2:
			w = (int) (10 * radius);
			w = w == 0 ? 1 : w;
			return new EuclidianHashFamily(w,datasetSize);
		case cos:
		default:
			return new CosineHashFamily(datasetSize);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s index=%d family=%s hashes=%s tableSize=%d",this.getClass().getName(), index, family, Arrays.toString(hashFunctions), hashTable.size());
	}
}
