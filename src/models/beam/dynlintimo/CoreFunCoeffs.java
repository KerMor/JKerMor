/**
 * 
 */
package models.beam.dynlintimo;

import rmcommon.affine.IAffineCoefficients;

/**
 * Coefficient functions for the models.beam.DLTLinearCoreFun class used by the
 * models.beam.DynLinTimoshenkoModel model
 * 
 * @author CreaByte
 * 
 */
public class CoreFunCoeffs implements IAffineCoefficients {

	/*
	 * (non-Javadoc)
	 * 
	 * @see rmcommon.affine.IAffineCoefficients#getNumCoeffFcns()
	 */
	@Override
	public int getNumCoeffFcns() {
		return 3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rmcommon.affine.IAffineCoefficients#evaluateCoefficient(double,
	 * double[])
	 */
	@Override
	public double[] evaluateCoefficients(double t, double[] mu) {
		return new double[] { 1, mu[0], mu[1] };
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
