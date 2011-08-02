/**
 * 
 */
package kermor.java.kernel;

import kermor.java.Parameter;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author Ernst
 *
 */
public class KernelExpansion {
	
	public RealMatrix ma;
	
	public GaussKernel StateKernel;
	public GaussKernel TimeKernel;
	public GaussKernel ParamKernel;
	
	public RealMatrix xi, mui;
	public double[] ti;
	
	public double[] evaluate(double t, double[] x, Parameter mu) {
		if (TimeKernel != null) {
			return ma.operate(dotTimes(
					dotTimes(StateKernel.evaluate(x, xi), TimeKernel.evaluate(t, ti)),
					ParamKernel.evaluate(mu.getValue(), mui)));
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

}
