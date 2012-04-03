package kermor.kernel;

import org.apache.commons.math.linear.RealMatrix;

public interface IKernel {

	public abstract RealMatrix evaluate(RealMatrix x, RealMatrix y);

	public abstract double[] evaluate(double[] x, RealMatrix y);

	public abstract double[] evaluate(double t, double[] ti);

}