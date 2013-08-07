package kermor.kernel;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.util.FastMath;

/**
 * Implementation of the Gaussian kernel @f$ K(x,y) = e^{-\frac{\|x-y\|^2}{\gamma^2}} @f$
 * 
 * The field gamma corresponds to the kernel width @f$ \gamma > 0 @f$.
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class GaussKernel implements IKernel {

	public double gamma;

	public GaussKernel(double gamma) {
		this.gamma = gamma;
	}

	/**
	 * @see kermor.kernel.IKernel#evaluate(org.apache.commons.math.linear.RealMatrix,
	 * org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public RealMatrix evaluate(RealMatrix x, RealMatrix y) {
		RealMatrix res = new Array2DRowRealMatrix(x.getColumnDimension(), y.getColumnDimension());
		for (int i = 0; i < x.getColumnDimension(); i++) {
			res.setColumn(i, evaluate(x.getColumn(i), y));
		}
		return res;
	}

	/**
	 * @see kermor.kernel.IKernel#evaluate(double[], org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public double[] evaluate(double[] x, RealMatrix y) {
		double[] res = new double[y.getColumnDimension()];
		for (int i = 0; i < y.getColumnDimension(); i++) {
			res[i] = FastMath.exp(-(distance_sq(x, y.getColumn(i))) / (gamma * gamma));
		}
		return res;
	}

	/**
	 * @see kermor.kernel.IKernel#evaluate(double, double[])
	 */
	@Override
	public double[] evaluate(double t, double[] ti) {
		double[] res = new double[ti.length];
		for (int i = 0; i < ti.length; i++) {
			res[i] = FastMath.exp(-(t - ti[i]) * (t - ti[i]) / (gamma * gamma));
		}
		return res;
	}

	private double distance_sq(double[] p1, double[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			sum += (p1[i] - p2[i]) * (p1[i] - p2[i]);
		}
		return sum;
	}
}
