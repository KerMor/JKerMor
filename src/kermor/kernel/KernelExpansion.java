/**
 * 
 */
package kermor.kernel;

import kermor.dscomp.ICoreFun;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

/**
 * @author D. Wirtz
 *
 */
public class KernelExpansion implements ICoreFun {
	
	public RealMatrix ma;
	
	public IKernel StateKernel;
	public IKernel TimeKernel;
	public IKernel ParamKernel;
	
	public RealMatrix xi, mui;
	public RealVector ti;
	
	/**
	 * @see kermor.kernel.ICoreFun#evaluate(double, double[], double[])
	 */
	@Override
	public double[] evaluate(double t, double[] x, double[] mu) {
		if (TimeKernel != null) {
			return ma.operate(dotTimes(
					dotTimes(StateKernel.evaluate(x, xi), TimeKernel.evaluate(t, ti.getData())),
					ParamKernel.evaluate(mu, mui)));
		} else {
			return ma.operate(StateKernel.evaluate(x, xi));
		}
	}
	
	private double[] dotTimes(double[] a, double[] b) {
		double[] res = new double[a.length];
		for (int i=0;i<a.length;i++) {
			res[i] = a[i] * b[i];
		}
		return res;
	}

	@Override
	public boolean timeDependent() {
		return TimeKernel != null;
	}

}