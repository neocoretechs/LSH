package com.neocoretechs.lsh.families;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.neocoretechs.lsh.F32FloatTensor;
import com.neocoretechs.lsh.util.Parallel;

public class EuclideanHash implements HashFunction{
	private static final long serialVersionUID = -3784656820380622717L;
	private F32FloatTensor randomProjection;
	private int offset;
	private int w;
	
	public EuclideanHash(int dimensions, int w){
	    ThreadLocalRandom rand = ThreadLocalRandom.current();
	    this.w = w;
	    this.offset = rand.nextInt(w);
	    
	    randomProjection = new F32FloatTensor(dimensions);
	    if(dimensions > 1000) {
	        Parallel.parallelFor(0, dimensions, d -> {
	            double val = rand.nextGaussian();
	            randomProjection.setFloat(d, (float) val);
	        });
	    } else {
	        for(int d=0; d<dimensions; d++) {
	            double val = rand.nextGaussian();
	            randomProjection.setFloat(d, (float) val);
	        }
	    }
	}
	
	@Override
	public int hash(F32FloatTensor vector){
		double hashValue = (vector.dot(randomProjection)+offset)/Double.valueOf(w);
		return (int) Math.round(hashValue);
	}
	
	@Override
	public String toString(){
		//return String.format("%s offset=%d w=%d\nrandomProjection:%s",this.getClass().getName(), offset, w, randomProjection);
		return String.format("%s offset=%d w=%d randomProjectionSize=%d",this.getClass().getName(), offset, w, randomProjection.getSize());
	}
}
