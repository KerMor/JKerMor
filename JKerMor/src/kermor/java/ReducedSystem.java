/**
 * 
 */
package kermor.java;

import kermor.java.dscomp.ConstInitialValue;
import kermor.java.dscomp.ConstInputFunction;
import kermor.java.dscomp.IInitialValue;
import kermor.java.dscomp.IInputConv;
import kermor.java.dscomp.IInputFunctions;
import kermor.java.dscomp.IOutputConv;
import kermor.java.dscomp.LinearInputConv;
import kermor.java.dscomp.LinearOutputConv;
import kermor.java.io.AModelManager;
import kermor.java.kernel.GaussKernel;
import kermor.java.kernel.KernelExpansion;

import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;

/**
 * @author Ernst
 * 
 */
public class ReducedSystem implements FirstOrderDifferentialEquations {

	private int dim;

	public IOutputConv C;

	public IInitialValue x0;

	public IInputConv B;

	public IInputFunctions u;

	public KernelExpansion f;

	private Parameter mu;
	private int inidx;

	public void setConfig(Parameter mu, int InputIdx) {
		this.mu = mu;
		this.inidx = InputIdx;
	}

	@Override
	public int getDimension() {
		return dim;
	}

	@Override
	public void computeDerivatives(double t, double[] x, double[] xDot)
			throws DerivativeException {
		Util.vecAdd(xDot, f.evaluate(t, x, mu));
		if (B != null) {
			Util.vecAdd(xDot, B.evaluate(t, mu).operate(u.evaluate(t, inidx)));
		}
	}

	public static ReducedSystem load(AModelManager mng) {
		ReducedSystem res = new ReducedSystem();

		int dim = 10;
		int numxi = 15;
		
		RealVector v = new ArrayRealVector(dim);
		v.set(1);
		res.dim = dim;
		res.C = new LinearOutputConv(
				MatrixUtils.createRealIdentityMatrix(res.dim));
		res.B = new LinearInputConv(
				MatrixUtils.createColumnRealMatrix(v.getData()));
		res.x0 = new ConstInitialValue(v.getData());
		res.u = new ConstInputFunction(new double[]{.5});
		
		res.f = new KernelExpansion();
		res.f.StateKernel = new GaussKernel(2);
		res.f.xi = Util.randMatrix(dim, numxi);
		res.f.ma = Util.randMatrix(dim, numxi);

		return res;
	}

}
