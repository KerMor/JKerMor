package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Implements a constant mass matrix @f$ M @f$ of the dynamical system.
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz
 * 
 */
public class ConstMassMatrix implements IMassMatrix {

	RealMatrix M;

	public ConstMassMatrix(RealMatrix M) {
		this.M = M;
	}

	/* (non-Javadoc)
	 * @see kermor.dscomp.IMassMatrix#evaluate(double, double[])
	 */
	@Override
	public RealMatrix evaluate(double t, double[] mu) {
		return M;
	}

	/* (non-Javadoc)
	 * @see kermor.dscomp.IMassMatrix#isTimeDependent()
	 */
	@Override
	public boolean isTimeDependent() {
		return false;
	}
}
