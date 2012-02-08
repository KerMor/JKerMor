/**
 * 
 */
package kermor.java.dscomp;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author CreaByte
 *
 */
public interface IMassMatrix {
	
	public RealMatrix evaluate(double t, double[] mu);
	
	public boolean isTimeDependent();
}
