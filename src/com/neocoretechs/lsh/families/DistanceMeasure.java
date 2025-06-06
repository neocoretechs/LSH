package com.neocoretechs.lsh.families;

import com.neocoretechs.lsh.F32FloatTensor;

/**
 * A distance measure defines how distance is calculated, measured as it were, between two vectors.
 * Each hash family has a corresponding distance measure which is abstracted using this interface.
 */
public interface DistanceMeasure {
	/**
	 * Calculate the distance between two vectors. From one to two.
	 * @param one The first vector.
	 * @param other The other vector
	 * @return A value representing the distance between two vectors.
	 */
	double distance(F32FloatTensor one, F32FloatTensor other);
}
