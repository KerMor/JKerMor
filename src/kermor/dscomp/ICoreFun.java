package kermor.dscomp;

public interface ICoreFun {

	public abstract double[] evaluate(double t, double[] x, double[] mu);
	
	public boolean timeDependent();

}