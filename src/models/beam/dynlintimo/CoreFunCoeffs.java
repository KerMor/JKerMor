package models.beam.dynlintimo;

import jarmos.affine.IAffineCoefficients;

/**
 * Coefficient functions for the KerMor models.beam.DLTLinearCoreFun class used by the models.beam.DynLinTimoshenkoModel
 * model
 * 
 * @author Daniel Wirtz
 * 
 */
public class CoreFunCoeffs implements IAffineCoefficients {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jarmos.affine.IAffineCoefficients#getNumCoeffFcns()
	 */
	@Override
	public int getNumCoeffFcns() {
		return 3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jarmos.affine.IAffineCoefficients#evaluateCoefficient(double,
	 * double[])
	 */
	@Override
	public double[] evaluateCoefficients(double t, double[] mu) {
		return new double[] { 1, mu[0], mu[1] };
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
