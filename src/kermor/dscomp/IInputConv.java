package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Interface for input conversion matrices @f$ B(t,\mu) @f$
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public interface IInputConv {

	public RealMatrix evaluate(double t, double[] mu);

}
