/**
 * 
 */
package kermor.java.dscomp;

/**
 * @author Ernst
 *
 */
public class ConstInputFunction implements IInputFunctions {

	private double[] values;
	
	public ConstInputFunction(double[] values) {
		this.values = values;
	}
	
	/* (non-Javadoc)
	 * @see kermor.java.IInputFunctions#evaluate(double, int)
	 */
	@Override
	public double[] evaluate(double t, int idx) {
		return new double[]{values[idx]};
	}

	@Override
	public int getNumFunctions() {
		return values.length;
	}

}
