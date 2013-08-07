package kermor.dscomp;


/**
 * Interface for initial values of dynamical systems.
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz @date 2013-08-07
 *
 */
public interface IInitialValue {
	
	public double[] evaluate(double[] mu);

}
