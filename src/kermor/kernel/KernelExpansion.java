package kermor.kernel;

import kermor.dscomp.ICoreFun;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

/**
 * 
 * Implements a kernel expansion @f$ f(x,t,\mu) = \sum\limits_{i=0}^m c_i K(x,x_i)K_t(t,t_i)K_\mu(\mu,\mu_i) @f$ with
 * optional time kernel @f$ K_t @f$ and parameter kernel @f$ K_\mu @f$.
 * 
 * Stores the expansion coefficients @f$ c_i @f$ as well as all centers @f$ x_i [, t_i, \mu_i] @f$.
 * 
 * @author Daniel Wirtz @date 07.08.2013
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
			return ma.operate(dotTimes(dotTimes(StateKernel.evaluate(x, xi), TimeKernel.evaluate(t, ti.getData())),
					ParamKernel.evaluate(mu, mui)));
		} else {
			return ma.operate(StateKernel.evaluate(x, xi));
		}
	}

	private double[] dotTimes(double[] a, double[] b) {
		double[] res = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			res[i] = a[i] * b[i];
		}
		return res;
	}

	@Override
	public boolean timeDependent() {
		return TimeKernel != null;
	}

}
