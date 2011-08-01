/**
 * 
 */
package kermor.java.dscomp;

import kermor.java.Parameter;

/**
 * @author Ernst
 *
 */
public interface IOutputConv {
	
	public int getOutputDimension();
	
	public double[] evaluate(double t, double[] x, Parameter mu);

}
