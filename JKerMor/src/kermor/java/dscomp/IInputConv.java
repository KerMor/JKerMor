/**
 * 
 */
package kermor.java.dscomp;

import kermor.java.Parameter;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author Ernst
 *
 */
public interface IInputConv {

	public RealMatrix evaluate(double t, Parameter mu);
	
}
