/**
 * 
 */
package kermor.java.test;

import kermor.java.kernel.GaussKernel;
import kermor.java.kernel.IKernel;
import kermor.java.visual.Plotter;

import org.junit.Test;

import rmcommon.Util;



/**
 * @author dwirtz
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
//	 * Test method for {@link kermor.java.kernel.GaussKernel#evaluate(org.apache.commons.math.linear.RealMatrix, org.apache.commons.math.linear.RealMatrix)}.
//	 */
//	@Test
//	public void testEvaluateRealMatrixRealMatrix() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link kermor.java.kernel.GaussKernel#evaluate(double[], org.apache.commons.math.linear.RealMatrix)}.
//	 */
//	@Test
//	public void testEvaluateDoubleArrayRealMatrix() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link kermor.java.kernel.GaussKernel#evaluate(double, double[])}.
//	 */
//	@Test
//	public void testEvaluateDoubleDoubleArray() {
//		fail("Not yet implemented");
//	}

}
