/**
 * 
 */
package kermor.dscomp;

import jarmos.affine.AffParamMatrix;

/**
 * @author CreaByte
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

	/*
	 * (non-Javadoc)
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
