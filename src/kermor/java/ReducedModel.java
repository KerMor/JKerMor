/**
 * 
 */
package kermor.java;

import java.io.IOException;

import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderIntegrator;
import org.apache.commons.math.ode.nonstiff.EulerIntegrator;
import org.apache.commons.math.ode.sampling.FixedStepHandler;
import org.apache.commons.math.ode.sampling.StepNormalizer;

import rmcommon.ModelType;
import rmcommon.Parameters;
import rmcommon.Util;
import rmcommon.geometry.GeometryData;
import rmcommon.io.AModelManager;
import rmcommon.io.MathObjectReader.MathReaderException;

/**
 * @author Daniel Wirtz
 * 
 */
public class ReducedModel implements FixedStepHandler {// , StepHandler {

	public ReducedSystem system;

	public FirstOrderIntegrator integrator;

	public String name;

	public Parameters params;

	public GeometryData geo;

	private double[] times;

	private int cnt;
	private float[][] output;
	private double[] mu;
	private double dt = .1;
	private double T = 1;

	public void simulate(double[] mu, int input) throws KerMorException {
		if (mu == null && params != null) {
			throw new KerMorException(
					"Simulation without a parameter when parameters are configured are not allowed.");
		}
		cnt = 0;
		double[] out = new double[system.getDimension()];
		// output = new Array2DRowRealMatrix(system.C.getOutputDimension(),
		// times.length);
		output = new float[system.C.getOutputDimension()][];
		this.mu = mu;
		this.system.setConfig(mu, input);

		try {
			integrator.integrate(system, 0, system.x0.evaluate(mu), T, out);
		} catch (Exception e) {
			throw new KerMorException("Simulation failed due to an exception.",
					e);
		}
	}

	public float[][] getOutput() {
		return output;
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

	public static ReducedModel load(AModelManager mng)
			throws MathReaderException, IOException {
		ReducedModel res = new ReducedModel();

		if (mng.getModelType() == ModelType.JKerMor) {

			String hlp = mng.getModelXMLTagValue("kermor_model.dt");
			res.setdt(Double.parseDouble(hlp));
			hlp = mng.getModelXMLTagValue("T");
			res.setT(Double.parseDouble(hlp));
			res.name = mng.getModelXMLAttribute("title");

			res.params = mng.getParameters();

			// Load the system
			res.system = ReducedSystem.load(mng);

			// The ODE integrator
			hlp = mng.getModelXMLTagValue("kermor_model.solvertype");
			if ("implicit".equals(hlp)) {
				res.integrator = new ImplicitLinearEulerIntegrator(res, res.dt);
				// res.integrator = new AdamsMoultonIntegrator(3, 1e-10*res.dt,
				// res.dt, 1e-4, 1e-3);
			} else {
				res.integrator = new EulerIntegrator(res.dt);
			}
			res.integrator.addStepHandler(new StepNormalizer(res.dt, res));

			if (mng.xmlTagExists("model.geometry")) {
				res.geo = new GeometryData();
				res.geo.loadModelGeometry(mng);
			}
		} else {
			throw new RuntimeException("Model type '" + mng.getModelType()
					+ "' not applicable for JKerMor ReducedModels.");
		}

		return res;
	}

	// //////// FixedStepHandler methods
	@Override
	public void handleStep(double t, double[] x, double[] xDot, boolean isLast)
			throws DerivativeException {
		double[] tmp = system.C.evaluate(t, x, mu);
		output[cnt++] = new float[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			output[cnt][i] = (float)tmp[i];
		}
		// output.setColumn(cnt++, system.C.evaluate(t, x, mu));
	}

	// ////////// StepHandler methods
	// @Override
	// public boolean requiresDenseOutput() {
	// return false;
	// }
	//
	// @Override
	// public void reset() {
	// cnt = 0;
	// }
	//
	// @Override
	// public void handleStep(StepInterpolator interpolator, boolean isLast)
	// throws DerivativeException {
	// double t = interpolator.getInterpolatedTime();
	// double[] x = interpolator.getInterpolatedState();
	// output.setColumn(cnt++, system.C.evaluate(t, x, mu));
	// }

}
