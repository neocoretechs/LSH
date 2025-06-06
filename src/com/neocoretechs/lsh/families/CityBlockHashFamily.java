package com.neocoretechs.lsh.families;

import java.util.Arrays;

public class CityBlockHashFamily implements HashFamily {
	private static final long serialVersionUID = -8926838846356323484L;
	private int dimensions;
	private int w;
	
	public CityBlockHashFamily(int w,int dimensions){
		this.dimensions = dimensions;
		this.w=w;
	}
	
	@Override
	public HashFunction createHashFunction(){
		return new CityBlockHash(dimensions, w);
	}

	@Override
	public Integer combine(int[] hashes) {
		return Arrays.hashCode(hashes);
	}

	@Override
	public DistanceMeasure createDistanceMeasure() {
		return new CityBlockDistance();
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()+" of "+dimensions+" dimensions and "+w;
	}

}
