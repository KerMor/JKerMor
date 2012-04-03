/**
 * 
 */
package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author Ernst
 *
 */
public class LinearInputConv implements IInputConv {

	private RealMatrix B;
	
	public LinearInputConv(RealMatrix B) {
		this.B = B;
	}
	
	/* (non-Javadoc)
	 * @see kermor.IInputConv#evaluate(double, kermor.Parameter)
	 */
	@Override
	public RealMatrix evaluate(double t, double[] mu) {
		return B;
	}

}
