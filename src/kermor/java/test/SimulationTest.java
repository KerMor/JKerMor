package kermor.java.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import kermor.java.ReducedModel;
import kermor.java.visual.Plotter;

import org.apache.commons.math.linear.RealMatrix;
import org.junit.Test;

import rmcommon.io.FileModelManager;


public class SimulationTest {

	@Test
	public void testSimulate() {
		try {
			FileModelManager m = new FileModelManager("./test");
			m.setModelDir("kerneltest");
			ReducedModel r = ReducedModel.load(m);
			
			r.setT(7);
			double[] mu = r.params.getRandomParam();
			//mu[0] = 0; mu[1] = 0;
			RealMatrix res = r.simulate(mu);
			
			Plotter p = new Plotter(r.name);
			p.plotResult(r.getTimes(), res, r);
			
			while(p.isVisible()) {}
			
//			System.out.print(Util.realMatToString(res));
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

//	@Test
//	public void testGetTimes() {
//		fail("Not yet implemented");
//	}

}
