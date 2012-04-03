/**
 * 
 */
package kermor.kernel;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author dwirtz
 *
 */
public class LinearKernel implements IKernel {

	/* (non-Javadoc)
	 * @see kermor.kernel.IKernel#evaluate(org.apache.commons.math.linear.RealMatrix, org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public RealMatrix evaluate(RealMatrix x, RealMatrix y) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see kermor.kernel.IKernel#evaluate(double[], org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public double[] evaluate(double[] x, RealMatrix y) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see kermor.kernel.IKernel#evaluate(double, double[])
	 */
	@Override
	public double[] evaluate(double t, double[] ti) {
		// TODO Auto-generated method stub
		return null;
	}

}
