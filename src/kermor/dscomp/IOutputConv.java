/**
 * 
 */
package kermor.dscomp;


/**
 * @author Ernst
 *
 */
public interface IOutputConv {
	
	public int getOutputDimension();
	
	public double[] evaluate(double t, double[] x, double[] mu);

}
