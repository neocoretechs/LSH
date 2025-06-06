package com.neocoretechs.lsh.families;

import java.io.Serializable;

import com.neocoretechs.lsh.F32FloatTensor;

/**
 * A hash function can hash a vector of arbitrary dimensions to an integer
 * representation. The hash function needs to be locality sensitive to work in
 * the locality sensitive hash scheme. Meaning that vectors that are 'close'
 * according to some metric have a high probability to end up with the same
 * hash.<p>
 * In the context of Locality-Sensitive Hashing (LSH), w represents the bucket width or window size.<p>
 * When we compute the hash value for a vector using a random projection. Here's what each component does:<p>
 * vector.dot(randomProjection): Computes the dot product of the input vector and a random projection vector. <br>
 * This projects the input vector onto a random direction.<br>
 * offset: Adds a random offset to the projected value. <br>
 * This helps to shift the projected values and create a more uniform distribution.<p>
 * w: The bucket width or window size. This value determines the granularity of the hash function.<br>
 * By dividing the projected value (plus offset) by w, you're essentially:<br>
 * Quantizing the projected values into discrete buckets.<br>
 * Assigning each bucket a unique hash value. <br>
 * The choice of w affects the trade-off between:<br>
 * Precision: Smaller w values result in more precise hashing, but may lead to more collisions.
 * Larger w values result in fewer collisions, but may reduce precision.<p>
 * In general, w is a hyperparameter that needs to be tuned for specific applications and datasets. 
 * A good choice of w can significantly impact the performance of the LSH algorithm.
 */
public interface HashFunction extends Serializable {
	/**
	 * Hashes a vector of arbitrary dimensions to an integer. The hash function
	 * needs to be locality sensitive to work in the locality sensitive hash (LSH)
	 * scheme. Meaning that vectors that are 'close' according to some metric
	 * have a high probability to end up with the same hash.
	 * 
	 * @param vector
	 *            The vector to hash. Can have any number of dimensions.
	 * @return A locality sensitive hash (LSH). Vectors that are 'close'
	 *         according to some metric have a high probability to end up with
	 *         the same hash.
	 */
	int hash(F32FloatTensor vector);
}
