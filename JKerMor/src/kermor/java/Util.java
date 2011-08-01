/**
 * 
 */
package kermor.java;

import java.util.Random;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

/**
 * @author Ernst
 * 
 */
public class Util {
	public static double[] linspace(double a, double b, int num) {
		double[] res = new double[num];
		double step = (b - a) / (num - 2);
		for (int i = 0; i < num - 1; i++) {
			res[i] = a + i * step;
		}
		res[num - 1] = b;
		return res;
	}

	public static double[] range(double a, double step, double b) {
		int num = (int) Math.floor((b - a) / step);
		double[] res = new double[num];
		for (int i = 0; i < num - 1; i++) {
			res[i] = a + i * step;
		}
		res[num - 1] = b;
		return res;
	}
	
	public static void vecAdd(double[] to, double[] what) {
		for (int i = 0; i < to.length; i++) {
			to[i] += what[i];
		}
	}

	public static RealVector randVector(int dim) {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		RealVector res = new ArrayRealVector(dim);
		for (int i = 0; i < dim; i++) {
			res.setEntry(i, r.nextDouble());
		}
		return res;
	}

	public static RealMatrix randMatrix(int rows, int cols) {
		RealMatrix res = new Array2DRowRealMatrix(rows, cols);
		for (int i = 0; i < rows; i++) {
			res.setRow(i, randVector(cols).getData());
		}
		return res;
	}
}
