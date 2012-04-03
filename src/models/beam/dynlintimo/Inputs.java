/**
 * 
 */
package models.beam.dynlintimo;

import kermor.dscomp.IInputFunctions;

/**
 * @author CreaByte
 * 
 */
public class Inputs implements IInputFunctions {

	/**
	 * @see kermor.dscomp.IInputFunctions#getNumFunctions()
	 */
	@Override
	public int getNumFunctions() {
		return 1;
	}

	/**
	 * @see kermor.dscomp.IInputFunctions#evaluate(double, int)
	 */
	@Override
	public double[] evaluate(double t, int idx) {
		double w = Math.PI * 2 * t / 10;
		switch (idx) {
		case 0:
			return new double[] { 1, 0, 0, -1 };
		case 1:
			return new double[] { 1, -1, 0, 0 };
		case 2:
			return new double[] { 1, 0, -1, 0 };
		case 3:
			return new double[] { 1, Math.sin(t), 0, 0 };
		case 4:
			return new double[] { 1, Math.sin(w), Math.cos(w), 0 };
		case 5:
			return new double[] { 1, 0, Math.sin(w), Math.cos(w) };
		default:
			return new double[] { 1, 0, 0, 0 };
		}
	}
}
