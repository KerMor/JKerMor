/**
 * 
 */
package kermor.java;

import kermor.java.io.AModelManager;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderIntegrator;
import org.apache.commons.math.ode.nonstiff.EulerIntegrator;
import org.apache.commons.math.ode.sampling.FixedStepHandler;
import org.apache.commons.math.ode.sampling.StepNormalizer;

/**
 * @author Ernst
 *
 */
public class ReducedModel implements FixedStepHandler {
	
	public ReducedSystem system;
	
	public FirstOrderIntegrator integrator;
	
	public String name;
	
	private double[] times;
	
	private int cnt;
	private RealMatrix output;
	private Parameter mu;
	private double dt = .1;
	private double T = 1;
	
//	public ReducedModel() {
//		
//	}
	
	public RealMatrix simulate(Parameter mu) throws KerMorException {
		cnt = 0;
		double[] out = new double[system.getDimension()];
		output = new Array2DRowRealMatrix(system.C.getOutputDimension(), times.length);
		this.mu = mu;
		
		try {
			integrator.integrate(system, 0, system.x0.evaluate(mu), T, out);
		} catch (Exception e) {
			throw new KerMorException("Simulation failed due to an exception.", e);
		}
		
		return output;
	}
	
	@Override
	public void handleStep(double t, double[] x, double[] xDot, boolean isLast)
			throws DerivativeException {
		output.setColumn(cnt++, system.C.evaluate(t, x, mu));
	}
	
	public double getT() {
		return T;
	} 
	
	public void setT(double value) {
		this.T = value;
		this.times = Util.range(0, dt, T); 
	}
	
	public double getdt() {
		return dt;
	}
	
	public void setdt(double value) {
		this.dt = value;
		this.times = Util.range(0, dt, T);
	}
	
	public double[] getTimes() {
		return times;
	}
	
	public static ReducedModel load(AModelManager mng) {
		ReducedModel res = new ReducedModel();
		
		res.setdt(.02);
		res.setT(1);
		res.name = "Testmodel!";
		
		// Load the system
		res.system = ReducedSystem.load(mng);
		
		// The ODE integrator
		res.integrator = new EulerIntegrator(res.dt);
		res.integrator.addStepHandler(new StepNormalizer(res.dt, res));
		
		return res;
	}
	
	public static void main(String[] args) throws KerMorException {
		ReducedModel r = ReducedModel.load(null);
		RealMatrix res = r.simulate(null);
		System.out.print(res);
	}

}
