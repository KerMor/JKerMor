/**
 * 
 */
package kermor.java.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author Ernst
 *
 */
public class LinearOutputConv implements IOutputConv {
	
	private RealMatrix C;
	
	public LinearOutputConv(RealMatrix C) {
		this.C = C;
	}

	/* (non-Javadoc)
	 * @see kermor.java.IOutputConv#evaluate(double, double[], double[])
	 */
	@Override
	public double[] evaluate(double t, double[] x, double[] mu) {
		return C.operate(x);
	}

	@Override
	public int getOutputDimension() {
		return C.getRowDimension();
	}

}
