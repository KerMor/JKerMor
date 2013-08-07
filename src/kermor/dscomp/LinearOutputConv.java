package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Constant linear output conversion @f$ C @f$
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class LinearOutputConv implements IOutputConv {

	private RealMatrix C;

	public LinearOutputConv(RealMatrix C) {
		this.C = C;
	}

	/* (non-Javadoc)
	 * @see kermor.IOutputConv#evaluate(double, double[], double[])
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
