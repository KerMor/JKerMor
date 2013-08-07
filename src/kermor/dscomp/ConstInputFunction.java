package kermor.dscomp;

/**
 * Implements a set of constant input functions @f$ u_i(t) = u_i^0 \forall t\in[0,T]@f$
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class ConstInputFunction implements IInputFunctions {

	private double[] values;

	public ConstInputFunction(double[] values) {
		this.values = values;
	}

	/**
	 * @see kermor.IInputFunctions#evaluate(double, int)
	 */
	@Override
	public double[] evaluate(double t, int idx) {
		return new double[] { values[idx] };
	}

	@Override
	public int getNumFunctions() {
		return values.length;
	}

}
