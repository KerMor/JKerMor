/**
 * 
 */
package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author Ernst
 *
 */
public interface IInputConv {

	public RealMatrix evaluate(double t, double[] mu);
	
}
