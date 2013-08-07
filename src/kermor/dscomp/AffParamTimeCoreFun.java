package kermor.dscomp;

import jarmos.affine.AffParamMatrix;

/**
 * This class implements a time/parameter-affine linear core function @f$ A(t,\mu) = \sum\limits_{i=1}^n \theta_i(t,\mu)A_i @f$.
 * 
 * @author Daniel Wirtz
 * 
 */
public class AffParamTimeCoreFun implements ICoreFun {

	private AffParamMatrix a;
	
	public AffParamMatrix getAffParamMatrix() {
		return a;
	}

	public AffParamTimeCoreFun(AffParamMatrix a) {
		this.a = a;
	}

	/**
	 * 
	 * @see kermor.dscomp.ICoreFun#evaluate(double, double[], double[])
	 */
	@Override
	public double[] evaluate(double t, double[] x, double[] mu) {
		return a.compose(t, mu).operate(x);
	}

	@Override
	public boolean timeDependent() {
		return a.isTimeDependent();
	}
}
