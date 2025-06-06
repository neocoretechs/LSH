package com.neocoretechs.lsh.families;

import java.util.concurrent.atomic.DoubleAdder;

import com.neocoretechs.lsh.F32FloatTensor;
import com.neocoretechs.lsh.util.Parallel;

/**
 * Calculates the straight line
 * between two vectors. Sometimes this is also called the L<sub>2</sub>
 * distance.
 * 
 */
public class EuclideanDistance implements DistanceMeasure {
	@Override
	public double distance(F32FloatTensor one, F32FloatTensor other) {
	    DoubleAdder sum = new DoubleAdder();
	    Parallel.parallelFor(0, one.getSize(), d -> {
	        double delta = one.getFloat(d) - other.getFloat(d);
	        sum.add(delta * delta);
	    });
	    return Math.sqrt(sum.sum());
	}
}
