/**
 * 
 */
package models.beam.dynlintimo;

import kermor.java.dscomp.IInputFunctions;

/**
 * @author CreaByte
 *
 */
public class Inputs implements IInputFunctions {

	/**
	 * @see kermor.java.dscomp.IInputFunctions#getNumFunctions()
	 */
	@Override
	public int getNumFunctions() {
		return 1;
	}

	/**
	 * @see kermor.java.dscomp.IInputFunctions#evaluate(double, int)
	 */
	@Override
	public double[] evaluate(double t, int idx) {
		return new double[]{t};
	}

}
