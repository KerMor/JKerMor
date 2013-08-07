package kermor.kernel;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Implements a linear scalar product kernel @f$ K(x,y) = \langle x,y \rangle @f$
 * 
 * @TODO implement!
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class LinearKernel implements IKernel {

	/**
	 * @see kermor.kernel.IKernel#evaluate(org.apache.commons.math.linear.RealMatrix,
	 * org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public RealMatrix evaluate(RealMatrix x, RealMatrix y) {
		throw new RuntimeException("not yet implemented");
	}

	/**
	 * @see kermor.kernel.IKernel#evaluate(double[], org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public double[] evaluate(double[] x, RealMatrix y) {
		throw new RuntimeException("not yet implemented");
	}

	/**
	 * @see kermor.kernel.IKernel#evaluate(double, double[])
	 */
	@Override
	public double[] evaluate(double t, double[] ti) {
		throw new RuntimeException("not yet implemented");
	}

}
