package kermor.dscomp;


/**
 * Interface for dynamical system output conversion matrices @f$ C(t,\mu) @f$
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz @date 2013-08-07
 *
 */
public interface IOutputConv {
	
	public int getOutputDimension();
	
	public double[] evaluate(double t, double[] x, double[] mu);

}
