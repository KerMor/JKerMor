package kermor.dscomp;

/**
 * Interface for any dynamical system core function.
 * 
 * This can be any linear or nonlinear function, see @ref jkermor_dynsys.
 * 
 * @author Daniel Wirtz
 *
 */
public interface ICoreFun {

	/**
	 * Corresponds to the evaluation @f$ f(x(t),t,\mu) @f$
	 * @param t Time
	 * @param x State space vector
	 * @param mu Parameter
	 * @return
	 */
	public abstract double[] evaluate(double t, double[] x, double[] mu);
	
	public boolean timeDependent();

}