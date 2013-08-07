package kermor.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import jarmos.io.FileModelManager;
import jarmos.visual.ColorGenerator;
import jarmos.visual.VisualizationData;
import kermor.ReducedModel;
import kermor.visual.Plotter;

import org.apache.commons.math.linear.RealMatrix;
import org.junit.Test;

public class SimulationTest {

	@Test
	public void testSimulate() {
		try {
			FileModelManager m = new FileModelManager();
			m.useModel("beam");
			ReducedModel r = new ReducedModel();
			r.loadOfflineData(m);

			r.setT(7);
			double[] mu = r.params.getRandomParam();

			// mu[0] = 0; mu[1] = 0;
			RealMatrix res = null;
			r.simulate(mu, 1);

			VisualizationData v = new VisualizationData(r.getGeometry());
			v.useResult(r.getSimulationResult());
			v.computeVisualFeatures(new ColorGenerator());

			Plotter p = new Plotter(r.name);
			p.plotResult(r.getTimes(), res, r);

			while (p.isVisible()) {
			}

			// System.out.print(Util.realMatToString(res));

			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	// @Test
	public void testSimulateAfflinModel() {
		try {
			FileModelManager m = new FileModelManager("./test");
			m.useModel("afflinmodel");
			ReducedModel r = new ReducedModel();
			r.loadOfflineData(m);

			r.setT(7);
			// double[] mu = r.params.getRandomParam();
			double[] mu = new double[] { 0.3, 0.01, 9.81 };
			// mu[0] = 0; mu[1] = 0;
			r.simulate(mu, 1);
			double[][] res = r.getOutput();
			System.out.println(res.length + " x " + res[0].length + ", times: " + r.getTimes().length);

			Plotter p = new Plotter(r.name);
			p.plotResult(r.getTimes(), res, r);

			while (p.isVisible()) {
			}

			// System.out.print(Util.realMatToString(res));

			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
