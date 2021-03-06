package models.beam.dynlintimo;

import jarmos.affine.IAffineCoefficients;

/**
 * Affine input mapping coefficient function implementation
 *  
 * @author Daniel Wirtz
 * 
 */
public class InputConvCoeffs implements IAffineCoefficients {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jarmos.affine.IAffineCoefficients#getNumCoeffFcns()
	 */
	@Override
	public int getNumCoeffFcns() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jarmos.affine.IAffineCoefficients#evaluateCoefficients(double,
	 * double[])
	 */
	@Override
	public double[] evaluateCoefficients(double t, double[] mu) {
		return new double[] { 1, mu[2] };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jarmos.affine.IAffineCoefficients#isTimeDependent()
	 */
	@Override
	public boolean isTimeDependent() {
		return false;
	}

}
