/**
 * 
 */
package kermor.java;

import java.io.IOException;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderIntegrator;
import org.apache.commons.math.ode.nonstiff.EulerIntegrator;
import org.apache.commons.math.ode.sampling.FixedStepHandler;
import org.apache.commons.math.ode.sampling.StepNormalizer;

import rmcommon.Parameters;
import rmcommon.Util;
import rmcommon.io.AModelManager;
import rmcommon.io.MathObjectReader;
import rmcommon.io.MathObjectReader.MathReaderException;


/**
 * @author Ernst
 *
 */
public class ReducedModel implements FixedStepHandler {
	
	public ReducedSystem system;
	
	public FirstOrderIntegrator integrator;
	
	public String name;
	
	public Parameters params;
	
	private double[] times;
	
	private int cnt;
	private RealMatrix output;
	private double[] mu;
	private double dt = .1;
	private double T = 1;
	
	public RealMatrix simulate(double[] mu) throws KerMorException {
		if (mu == null && params != null) {
			throw new KerMorException("Simulation without a parameter when parameters are configured are not allowed.");
		}
		cnt = 0;
		double[] out = new double[system.getDimension()];
		output = new Array2DRowRealMatrix(system.C.getOutputDimension(), times.length);
		this.mu = mu;
		this.system.setConfig(mu, 1);
		
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
	
	public static ReducedModel load(AModelManager mng) throws MathReaderException, IOException {
		ReducedModel res = new ReducedModel();
		
		String type = mng.getModelXMLAttribute("type");
		if ("kermor".equals(type)) {
			MathObjectReader r = new MathObjectReader();
			
			String hlp = mng.getModelXMLTagValue("dt");
			res.setdt(Double.parseDouble(hlp));
			hlp = mng.getModelXMLTagValue("T");
			res.setT(Double.parseDouble(hlp));
			res.name = mng.getModelXMLAttribute("title");
			
			// Load the parameters
			hlp = mng.getModelXMLTagValue("parameters");
			double[][] pvals = r.readRawDoubleMatrix(mng.getInStream("paramvalues.bin"));
			if (hlp != null) {
				res.params = new Parameters();
				int p = 1;
				while (mng.xmlTagExists("param"+p)) {
					res.params.addParam(mng.getModelXMLAttribute("name", "param"+p), pvals[p-1][0], pvals[p-1][1]);
					p++;
				}
			}
			
			// Load the system
			res.system = ReducedSystem.load(mng);
			
			// The ODE integrator
			res.integrator = new EulerIntegrator(res.dt);
			res.integrator.addStepHandler(new StepNormalizer(res.dt, res));
		} else {
			throw new RuntimeException("Model type '"+type+"' not applicable for JKerMor ReducedModels.");
		}
		
		return res;
	}
}
