/**
 * 
 */
package kermor.java;

import kermor.java.dscomp.AffParamTimeCoreFun;

import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.DecompositionSolver;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math.ode.AbstractIntegrator;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math.ode.IntegratorException;
import org.apache.commons.math.ode.sampling.AbstractStepInterpolator;
import org.apache.commons.math.ode.sampling.DummyStepInterpolator;
import org.apache.commons.math.ode.sampling.StepHandler;

/**
 * @author CreaByte
 * 
 */
public class ImplicitLinearEulerIntegrator extends AbstractIntegrator {

	private double dt;
	ReducedModel model;

	public ImplicitLinearEulerIntegrator(ReducedModel model, double dt) {
		this.dt = dt;
		this.model = model;
		if (!(model.system.f instanceof AffParamTimeCoreFun)) {
			throw new RuntimeException("Not yet implemented.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.math.ode.FirstOrderIntegrator#integrate(org.apache
	 * .commons.math.ode.FirstOrderDifferentialEquations, double, double[],
	 * double, double[])
	 */
	@Override
	public double integrate(FirstOrderDifferentialEquations equations,
			double t0, double[] y0, double T, double[] yarr)
			throws DerivativeException, IntegratorException {

		//sanityChecks(equations, t0, y0, T, y);
		// setEquations(equations);
		// resetEvaluations();
		ReducedSystem sys = model.system;

		if (yarr != y0) {
			System.arraycopy(y0, 0, yarr, 0, y0.length);
		}
		RealVector y = new ArrayRealVector(yarr);

		// Mass matrix
		RealMatrix M = null;
		// Time-independent mass matrix
		if (!sys.M.isTimeDependent()) {
			M = sys.M.evaluate(0, sys.currentMu());
		}
		RealMatrix A = null;
		// time-independent A matrix
		if (!sys.f.timeDependent()) {
			A = ((AffParamTimeCoreFun) sys.f).getAffParamMatrix().compose(0,
					sys.currentMu());
		}

		DecompositionSolver solver = new LUDecompositionImpl(M.add(A
				.scalarMultiply(dt))).getSolver();
		double t = t0;
		RealVector rhs;
		do {
			t += dt;
			rhs = M.operate(y);
			if (sys.currentInput() > -1) {
				double[] u = sys.u.evaluate(t, sys.currentInput());
				RealMatrix B = sys.B.evaluate(t, sys.currentMu()).scalarMultiply(dt);
				double[] tmp = B.operate(u);
				rhs = rhs.add(tmp);
			}
			
			y = solver.solve(rhs);
			
			model.handleStep(t, y.toArray(), null, t == T);
		} while (t < T);

		// for (int ts=0;ts < )

		return T;
	}

}
