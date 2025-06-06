package com.neocoretechs.lsh.families;

import java.util.Arrays;

public class EuclidianHashFamily implements HashFamily {
	private static final long serialVersionUID = 3406464542795652263L;
	private final int dimensions;
	private int w;
		
	public EuclidianHashFamily(int w,int dimensions){
		this.dimensions = dimensions;
		this.w=w;
	}
	
	@Override
	public HashFunction createHashFunction(){
		return new EuclideanHash(dimensions, w);
	}
	
	@Override
	public Integer combine(int[] hashes){
		return Arrays.hashCode(hashes);
	}

	@Override
	public DistanceMeasure createDistanceMeasure() {
		return new EuclideanDistance();
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()+" of "+dimensions+" dimensions and "+w;
	}
}
