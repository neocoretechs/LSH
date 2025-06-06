package com.neocoretechs.lsh.families;

public class CosineHashFamily implements HashFamily {
	private static final long serialVersionUID = 7678152513757669089L;
	private final int dimensions;
	
	public CosineHashFamily(int dimensions) {
		this.dimensions = dimensions;
	}

	@Override
	public HashFunction createHashFunction() {
		return new CosineHash(dimensions);
	}

	@Override
	public Integer combine(int[] hashes) {
		//Treat the hashes as a series of bits.
		//They are either zero or one, the index 
		//represents the value.
		int result = 0;
		//factor holds the power of two.
		int factor = 1;
		for(int i = 0 ; i < hashes.length ; i++){
			result += hashes[i] == 0 ? 0 : factor;
			factor *= 2;
		}
		return result;
	}

	@Override
	public DistanceMeasure createDistanceMeasure() {
		return new CosineDistance();
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()+" of "+dimensions+" dimensions";
	}
}
