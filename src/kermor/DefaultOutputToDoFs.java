package kermor;

/**
 * 
 * Default implementation for output to DoF conversion.
 * 
 * Simply returns the same reference (leaves data untouched)
 * 
 * @author Daniel Wirtz
 * 
 */
public class DefaultOutputToDoFs implements IOutputToDoF {

	@Override
	public double[][] transformOutputToDoFs(double[][] output) {
		return output;
	}

}
