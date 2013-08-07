
package kermor;


/**
 * Interface for post-simulation output to DoF conversion.
 * 
 * This has been added in the context of the timoshenko-beam models as the output contains possibly more values
 * than needed.
 * 
 * @todo This should be somehow put into an output conversion matrix @f$ C(t,\mu) @f$ / component kermor.dscomp.IOutputConv
 * 
 * @author Daniel Wirtz
 *
 */
public interface IOutputToDoF {
	
	public double[][] transformOutputToDoFs(double[][] output);

}
