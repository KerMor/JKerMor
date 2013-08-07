package kermor.dscomp;

/**
 * Represents a constant initial value for dynamical systems.
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class ConstInitialValue implements IInitialValue {

	private double[] x0;

	public ConstInitialValue(double[] x0) {
		this.x0 = x0;
	}

	/**
	 * @see kermor.IInitialValue#evaluate(kermor.Parameter)
	 */
	@Override
	public double[] evaluate(double[] mu) {
		return x0;
	}

}
