package com.neocoretechs.lsh.families;

import java.util.concurrent.atomic.DoubleAdder;

import com.neocoretechs.lsh.F32FloatTensor;
import com.neocoretechs.lsh.util.Parallel;

/**
 * <p>
 * Calculates the intersection of one vector with an other vector. Intersection
 * is normally between 0 and 1. 1 meaning 100% overlap. To make it a distance
 * measure, 1 - intersection is done, so 1 becomes no intersection, 0 total
 * overlap.
 * </p>
 * <p>
 * This distance measure has no related hash family.
 * </p>
 */
public class IntersectionDistance implements DistanceMeasure {

	@Override
	public double distance(F32FloatTensor one, F32FloatTensor other) {
	    DoubleAdder intersectionSum = new DoubleAdder();
	    DoubleAdder thisArea = new DoubleAdder();
	    DoubleAdder otherArea = new DoubleAdder();
	    
	    Parallel.parallelFor(0, one.getSize(), d -> {
	        intersectionSum.add(Math.min(one.getFloat(d), other.getFloat(d)));
	        thisArea.add(one.getFloat(d));
	        otherArea.add(other.getFloat(d));
	    });
	    
	    double area = Math.max(thisArea.sum(), otherArea.sum());
	    return area == 0 ? 1 : 1 - intersectionSum.sum() / area;
	}
}
