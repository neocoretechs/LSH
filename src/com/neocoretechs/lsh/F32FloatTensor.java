package com.neocoretechs.lsh;

import java.io.Serializable;
import java.util.Arrays;

/**
 * An Vector contains a vector of 'dimension' values. It serves as the main data
 * structure that is stored and retrieved. It also has an identifier (key).
 * 
 */
public class F32FloatTensor implements Serializable {
	
	private static final long serialVersionUID = 5169504339456492327L;


	/**
	 * Values are stored here.
	 */
	private double[] values;
	
	
	/**
	 * An optional key, identifier for the vector.
	 */
	private String key;

	/**
	 * Creates a new vector with the requested number of dimensions.
	 * @param dimensions The number of dimensions.
	 */
	public F32FloatTensor(int dimensions) {
		this(null,new double[dimensions]);
	}
	
	
	/**
	 * Copy constructor.
	 * @param other The other vector.
	 */
	public F32FloatTensor(F32FloatTensor other){
		//copy the values
		this(other.getKey(),Arrays.copyOf(other.values, other.values.length));
	}
	
	/**
	 * Creates a vector with the values and a key
	 * @param key The key of the vector.
	 * @param values The values of the vector.
	 */
	public F32FloatTensor(String key,double[] values){
		this.values = values;
		this.key = key;
	}
	

	/**
	 * Set a value at a certain dimension d.
	 * @param dimension The dimension, index for the value.
	 * @param value The value to set.
	 */
	public void setFloat(int dimension, double value) {
		values[dimension] = value;
	}

	/**
	 * Returns the value at the requested dimension.
	 * @param dimension The dimension, index for the value.
	 * @return Returns the value at the requested dimension.
	 */
	public double getFloat(int dimension) {
		return values[dimension];
	}
	
	/**
	 * @return The number of dimensions this vector has.
	 */
	public int getSize(){
		return values.length;
	}

	/**
	 * Calculates the dot product, or scalar product, of this vector with the
	 * other vector.
	 * 
	 * @param other
	 *            The other vector, should have the same number of dimensions.
	 * @return The dot product of this vector with the other vector.
	 * @exception ArrayIndexOutOfBoundsException
	 *                when the two vectors do not have the same dimensions.
	 */
	public double dot(F32FloatTensor other) {
		double sum = 0.0;
		for(int i=0; i < getSize(); i++) {
			sum += values[i] * other.values[i];
		}
		return sum;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public String toString(){
		StringBuilder sb= new StringBuilder();
		sb.append("values:[");
		for(int d=0; d < getSize() - 1; d++) {
			sb.append(values[d]).append(",");
		}
		sb.append(values[getSize()-1]).append("]");
		return sb.toString();
	}
}
