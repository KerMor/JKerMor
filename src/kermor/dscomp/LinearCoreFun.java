
package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Constant linear dynamical system core function @f$ A @f$
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz
 *
 */
public class LinearCoreFun implements ICoreFun {
	
	private RealMatrix A;
	
	/**
	 * Creates a new linear core function
	 * @param A
	 */
	public LinearCoreFun(RealMatrix A) {
		this.A = A;
	}

	/**
	 * @see kermor.dscomp.ICoreFun#evaluate(double, double[], double[])
	 */
	@Override
	public double[] evaluate(double t, double[] x, double[] mu) {
		return A.operate(x);
	}

	@Override
	public boolean timeDependent() {
		return false;
	}

}
