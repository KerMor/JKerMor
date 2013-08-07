package kermor.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Interface for dynamical system mass matrices @f$ M(t,\mu) @f$
 * 
 * @see @ref jkermor_dynsys
 * 
 * @author Daniel Wirtz
 * 
 */
public interface IMassMatrix {

	public RealMatrix evaluate(double t, double[] mu);

	public boolean isTimeDependent();
}
