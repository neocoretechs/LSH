package com.neocoretechs.lsh.families;

import java.util.Comparator;

import com.neocoretechs.lsh.F32FloatTensor;

/**
 * This comparator can be used to sort candidate neighbors according to their
 * distance to a query vector. Either for linear search or to sort the LSH
 * candidates found in colliding hash bins.
 * 
 */
public class DistanceComparator implements Comparator<F32FloatTensor>{
	
	private final F32FloatTensor query;
	private final DistanceMeasure distanceMeasure;
	
	/**
	 * 
	 * @param query
	 * @param distanceMeasure
	 */
	public DistanceComparator(F32FloatTensor query,DistanceMeasure distanceMeasure){
		this.query = query;
		this.distanceMeasure = distanceMeasure;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(F32FloatTensor one, F32FloatTensor other) {
		Double oneDistance = distanceMeasure.distance(query,one);
		Double otherDistance = distanceMeasure.distance(query,other);
		return oneDistance.compareTo(otherDistance);
	}
}
