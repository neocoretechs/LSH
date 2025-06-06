package com.neocoretechs.lsh.families;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.neocoretechs.lsh.F32FloatTensor;
import com.neocoretechs.lsh.util.Parallel;

public class CityBlockHash implements HashFunction {
	private static final long serialVersionUID = -635398900309516287L;
	private int w;
	private F32FloatTensor randomPartition;
	
	public CityBlockHash(int dimensions, int w){
	    ThreadLocalRandom rand = ThreadLocalRandom.current();
	    this.w = w;
	    
	    randomPartition = new F32FloatTensor(dimensions);
	    if(dimensions > 1000) {
	        Parallel.parallelFor(0, dimensions, d -> {
	            int val = rand.nextInt(w);
	            randomPartition.setFloat(d, val);
	        });
	    } else {
	        for(int d=0; d<dimensions; d++) {
	            int val = rand.nextInt(w);
	            randomPartition.setFloat(d, val);
	        }
	    }
	}
	
	public int hash(F32FloatTensor vector){
		int hash[] = new int[randomPartition.getSize()];
		for(int d=0; d<randomPartition.getSize(); d++) {
			hash[d] =  (int) (vector.getFloat(d)-randomPartition.getFloat(d) / Double.valueOf(w));
		}
		return Arrays.hashCode(hash);
	}
	
	@Override
	public String toString(){
		//return String.format("%s w=w:%d\nrandomPartition:%s",this.getClass().getName(),w,randomPartition); 
		return String.format("%s w=%d randomPartitionSize=%d",this.getClass().getName(),w,randomPartition.getSize()); 
	}
}
