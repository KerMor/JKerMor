package kermor.dscomp;

/**
 * Interface for dynamical system input functions @f$ u_i(t) @f$
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public interface IInputFunctions {

	public int getNumFunctions();

	public double[] evaluate(double t, int idx);

}
