package kermor.kernel;

import org.apache.commons.math.linear.RealMatrix;

/**
 * 
 * Interface for kernel implementations in JKerMor.
 * 
 * Requires different evaluate methods (matrix, vectorial, scalar) to be implemented by subclasses.
 * 
 * @author Daniel Wirtz @date 07.08.2013
 * 
 */
public interface IKernel {

	public abstract RealMatrix evaluate(RealMatrix x, RealMatrix y);

	public abstract double[] evaluate(double[] x, RealMatrix y);

	public abstract double[] evaluate(double t, double[] ti);

}