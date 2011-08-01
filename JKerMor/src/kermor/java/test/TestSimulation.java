package kermor.java.test;

import static org.junit.Assert.fail;
import kermor.java.KerMorException;
import kermor.java.ReducedModel;

import org.junit.Test;

public class TestSimulation {

	@Test
	public void testSimulate() {
		ReducedModel r = ReducedModel.load(null);
		try {
			r.simulate(null);
		} catch (KerMorException e) {
			e.printStackTrace();
			fail("Exception thrown.");
		}
	}

	@Test
	public void testGetTimes() {
		fail("Not yet implemented");
	}

}
