/**
 * 
 */
package kermor.java.dscomp;

import org.apache.commons.math.linear.RealMatrix;

import rmcommon.affine.AffParamMatrix;

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
	 * @see kermor.java.dscomp.IInputConv#evaluate(double, double[])
	 */
	@Override
	public RealMatrix evaluate(double t, double[] mu) {
		return a.compose(t, mu);
	}

}
