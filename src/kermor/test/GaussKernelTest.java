
package kermor.test;

import jarmos.Util;
import kermor.kernel.GaussKernel;
import kermor.kernel.IKernel;
import kermor.visual.Plotter;

import org.junit.Test;

/**
 * @author Daniel Wirtz @date 2013-08-07
 *
 */
public class GaussKernelTest {
	
	@Test
	public void testGaussKernel() {
		Plotter p = new Plotter("GaussTest");
		
		IKernel g = new GaussKernel(2.2);
		double[] x = Util.range(-4, .05, 4);
		double[] fx = g.evaluate(0, x);
		
		p.plot(x, fx, "GaussTest");
		
		//while(p.isVisible()) {};
	}

//	/**
//	 * Test method for {@link kermor.kernel.GaussKernel#evaluate(org.apache.commons.math.linear.RealMatrix, org.apache.commons.math.linear.RealMatrix)}.
//	 */
//	@Test
//	public void testEvaluateRealMatrixRealMatrix() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link kermor.kernel.GaussKernel#evaluate(double[], org.apache.commons.math.linear.RealMatrix)}.
//	 */
//	@Test
//	public void testEvaluateDoubleArrayRealMatrix() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link kermor.kernel.GaussKernel#evaluate(double, double[])}.
//	 */
//	@Test
//	public void testEvaluateDoubleDoubleArray() {
//		fail("Not yet implemented");
//	}

}
