package com.neocoretechs.lsh.families;

import com.neocoretechs.lsh.F32FloatTensor;

public class CosineDistance implements DistanceMeasure {

	@Override
	public double distance(F32FloatTensor one, F32FloatTensor other) {
		double distance=0;
		double similarity = one.dot(other) / Math.sqrt(one.dot(one) * other.dot(other));
		distance = 1 - similarity;
		return distance;
	}
}
