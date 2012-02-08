/**
 * 
 */
package kermor.java.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author CreaByte
 *
 */
public class ConstMassMatrix implements IMassMatrix {
	
	RealMatrix M;
	
	public ConstMassMatrix(RealMatrix M) {
		this.M = M;
	}

	/* (non-Javadoc)
	 * @see kermor.java.dscomp.IMassMatrix#evaluate(double, double[])
	 */
	@Override
	public RealMatrix evaluate(double t, double[] mu) {
		return M;
	}

	/* (non-Javadoc)
	 * @see kermor.java.dscomp.IMassMatrix#isTimeDependent()
	 */
	@Override
	public boolean isTimeDependent() {
		return false;
	}
}
