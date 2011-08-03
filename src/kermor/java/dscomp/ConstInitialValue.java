/**
 * 
 */
package kermor.java.dscomp;


/**
 * @author Ernst
 *
 */
public class ConstInitialValue implements IInitialValue {

	private double[] x0;
	
	public ConstInitialValue(double[] x0) {
		this.x0 = x0;
	}
	/* (non-Javadoc)
	 * @see kermor.java.IInitialValue#evaluate(kermor.java.Parameter)
	 */
	@Override
	public double[] evaluate(double[] mu) {
		return x0;
	}

}
