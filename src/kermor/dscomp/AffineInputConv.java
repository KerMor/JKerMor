package kermor.dscomp;

import jarmos.affine.AffParamMatrix;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Class for time/parameter-affine dynamical system inputs @f$ B(t,\mu) = \sum\limits_{i=1}^n \theta_i(t,\mu)B_i @f$
 * 
 * This class corresponds in part to the KerMor system input @f$ B(t,\mu)u(t) @f$.
 * 
 * @author Daniel Wirtz
 * 
 */
public class AffineInputConv implements IInputConv {

	private AffParamMatrix a;

	public AffineInputConv(AffParamMatrix a) {
		this.a = a;
	}

	/**
	 * @see kermor.dscomp.IInputConv#evaluate(double, double[])
	 */
	@Override
	public RealMatrix evaluate(double t, double[] mu) {
		return a.compose(t, mu);
	}

}
