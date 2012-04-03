/**
 * 
 */
package kermor.dscomp;

import jarmos.affine.AffParamMatrix;

import org.apache.commons.math.linear.RealMatrix;


/**
 * @author CreaByte
 *
 */
public class AffineInputConv implements IInputConv {
	
	private AffParamMatrix a;
	
	public AffineInputConv(AffParamMatrix a) {
		this.a = a;
	}

	/* (non-Javadoc)
	 * @see kermor.dscomp.IInputConv#evaluate(double, double[])
	 */
	@Override
	public RealMatrix evaluate(double t, double[] mu) {
		return a.compose(t, mu);
	}

}
