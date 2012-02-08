/**
 * 
 */
package models.beam.dynlintimo;

import rmcommon.affine.IAffineCoefficients;

/**
 * @author CreaByte
 * 
 */
public class InputConvCoeffs implements IAffineCoefficients {

	/*
	 * (non-Javadoc)
	 * 
	 * @see rmcommon.affine.IAffineCoefficients#getNumCoeffFcns()
	 */
	@Override
	public int getNumCoeffFcns() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rmcommon.affine.IAffineCoefficients#evaluateCoefficients(double,
	 * double[])
	 */
	@Override
	public double[] evaluateCoefficients(double t, double[] mu) {
		return new double[] { 1, mu[2] };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rmcommon.affine.IAffineCoefficients#isTimeDependent()
	 */
	@Override
	public boolean isTimeDependent() {
		return false;
	}

}
