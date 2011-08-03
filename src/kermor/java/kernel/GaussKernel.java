/**
 * 
 */
package kermor.java.kernel;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.util.FastMath;

/**
 * @author Ernst
 * 
 */
public class GaussKernel implements IKernel {

	public double gamma;
	
	public GaussKernel(double gamma) {
		this.gamma = gamma;
	}

	/* (non-Javadoc)
	 * @see kermor.java.kernel.IKernel#evaluate(org.apache.commons.math.linear.RealMatrix, org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public RealMatrix evaluate(RealMatrix x, RealMatrix y) {
		RealMatrix res = new Array2DRowRealMatrix(x.getColumnDimension(),
				y.getColumnDimension());
		for (int i = 0; i < x.getColumnDimension(); i++) {
			res.setColumn(i, evaluate(x.getColumn(i), y));
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see kermor.java.kernel.IKernel#evaluate(double[], org.apache.commons.math.linear.RealMatrix)
	 */
	@Override
	public double[] evaluate(double[] x, RealMatrix y) {
		double[] res = new double[y.getColumnDimension()];
		for (int i = 0; i < y.getColumnDimension(); i++) {
			res[i] = FastMath.exp(-(distance_sq(x, y.getColumn(i)))
					/ (gamma * gamma));
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see kermor.java.kernel.IKernel#evaluate(double, double[])
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
			final double dp = p1[i] - p2[i];
			sum += dp * dp;
		}
		return sum;
	}

	// function K = evaluate(this, x, y)
	// n1sq = sum(x.^2,1);
	// n1 = size(x,2);
	//
	// if nargin == 2;
	// n2sq = n1sq;
	// n2 = n1;
	// y = x;
	// else
	// n2sq = sum(y.^2,1);
	// n2 = size(y,2);
	// end;
	// % try
	// K = (ones(n2,1)*n1sq)' + ones(n1,1)*n2sq - 2*x'*y;
	// % catch ME
	// % keyboard;
	// % end
	// K(K<0) = 0;
	// K = exp(-K/this.Gamma^2);
	// end

}
