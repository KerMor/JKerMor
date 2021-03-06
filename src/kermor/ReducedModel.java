package kermor;

import jarmos.DefaultSolutionField;
import jarmos.FieldDescriptor;
import jarmos.ModelBase;
import jarmos.Parameters;
import jarmos.SimulationResult;
import jarmos.Util;
import jarmos.geometry.DefaultTransform;
import jarmos.geometry.DisplacementField;
import jarmos.io.AModelManager;
import jarmos.io.AModelManager.ModelManagerException;
import jarmos.io.MathObjectReader.MathReaderException;

import java.io.IOException;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderIntegrator;
import org.apache.commons.math.ode.nonstiff.EulerIntegrator;
import org.apache.commons.math.ode.sampling.FixedStepHandler;
import org.apache.commons.math.ode.sampling.StepNormalizer;

/**
 * Main reduced model class
 * 
 * Contains the ReducedSystem, Parameters, the ODE solver and methods to load the offline data.
 * 
 * @author Daniel Wirtz
 * 
 */
public class ReducedModel extends ModelBase implements FixedStepHandler {

	public ReducedSystem system;

	public FirstOrderIntegrator integrator;

	public String name;

	public Parameters params;

	private IOutputToDoF converter;

	private double[] times;

	private int cnt;
	private RealMatrix output;
	private double[] mu;
	private double dt = .1;
	private double T = 1;

	public void simulate(double[] mu, int input) throws KerMorException {
		if (mu == null && params != null) {
			throw new KerMorException("Simulation without a parameter when parameters are configured are not allowed.");
		}
		cnt = 0;
		double[] out = new double[system.getDimension()];
		output = new Array2DRowRealMatrix(system.C.getOutputDimension(), times.length);
		// output = new double[times.length][];
		this.mu = mu;
		this.system.setConfig(mu, input);

		try {
			integrator.integrate(system, 0, system.x0.evaluate(mu), T, out);
		} catch (Exception e) {
			throw new KerMorException("Simulation failed due to an exception:" + e.getMessage(), e);
		}
	}

	public double[][] getOutput() {
		return output.getData();
	}

	public SimulationResult getSimulationResult() {
		double[][] dof_fields = converter.transformOutputToDoFs(output.getData());

		SimulationResult res = new SimulationResult(times.length);
		// Add default transforms for each timestep (TODO improve; use only
		// some transforms and an transform-index)
		for (int i = 0; i < times.length; i++) {
			res.addTransform(new DefaultTransform());
		}

		int fnumcnt = 0;
		for (FieldDescriptor sftype : logicalFieldTypes) {
			if (fnumcnt + sftype.Type.requiredDoFFields > getNumDoFFields()) {
				throw new RuntimeException("Too many output fields used by current "
						+ "SolutionFieldTypes set in RBSystem. Check your model.xml.");
			}
			int numDoF = dof_fields[fnumcnt].length;
			switch (sftype.Type) {
			case Displacement3D: {
				DisplacementField d = new DisplacementField(sftype, numDoF);
				for (int nodenr = 0; nodenr < numDoF; nodenr++) {
					d.setDisplacement(nodenr, (float) dof_fields[fnumcnt][nodenr],
							(float) dof_fields[fnumcnt + 1][nodenr], (float) dof_fields[fnumcnt + 2][nodenr]);
				}
				res.addField(d);
				break;
			}
			case RealValue: {
				DefaultSolutionField d = new DefaultSolutionField(sftype, numDoF);
				for (int nodenr = 0; nodenr < numDoF; nodenr++) {
					d.setValue(nodenr, (float) dof_fields[fnumcnt][nodenr]);
				}
				res.addField(d);
				break;
			}
			default: {
				throw new RuntimeException("Invalid/unimplemented solution field type '" + sftype + "'");
			}
			}
			/*
			 * Increase field counter by the amount the current solution field
			 * used
			 */
			fnumcnt += sftype.Type.requiredDoFFields;
		}
		return res;
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

	@Override
	public void loadOfflineData(AModelManager mng) throws MathReaderException, ModelManagerException, IOException {
		super.loadOfflineData(mng);

		String hlp = mng.getModelXMLTagValue("kermor_model.dt");
		setdt(Double.parseDouble(hlp));
		hlp = mng.getModelXMLTagValue("T");
		setT(Double.parseDouble(hlp));
		name = mng.getModelXMLAttribute("title");

		params = mng.getParameters();

		// Load the system
		system = ReducedSystem.load(mng);

		// The ODE integrator
		hlp = mng.getModelXMLTagValue("kermor_model.solvertype");
		if ("implicit".equals(hlp)) {
			integrator = new ImplicitLinearEulerIntegrator(this, dt);
			// res.integrator = new AdamsMoultonIntegrator(3, 1e-10*res.dt,
			// res.dt, 1e-4, 1e-3);
		} else {
			integrator = new EulerIntegrator(dt);
		}
		integrator.addStepHandler(new StepNormalizer(dt, this));

		// Check if a custom conversion of output data to simulation result
		// has been implemented & attached
		if (mng.xmlTagExists("kermor_model.outputtodof")) {
			converter = (IOutputToDoF) mng.loadModelClass(mng.getModelXMLTagValue("kermor_model.outputtodof"));
		} else {
			converter = new DefaultOutputToDoFs();
		}
	}

	// //////// FixedStepHandler methods
	@Override
	public void handleStep(double t, double[] x, double[] xDot, boolean isLast) throws DerivativeException {
		// output[cnt++] = system.C.evaluate(t, x, mu);
		output.setColumn(cnt++, system.C.evaluate(t, x, mu));
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
