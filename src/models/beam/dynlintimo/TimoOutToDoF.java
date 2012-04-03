/**
 * 
 */
package models.beam.dynlintimo;

import kermor.IOutputToDoF;

/**
 * @author CreaByte
 *
 */
public class TimoOutToDoF implements IOutputToDoF {

	/* (non-Javadoc)
	 * @see kermor.IOutputToSimulationResult#getSimulationResult(double[][])
	 */
	@Override
	public double[][] transformOutputToDoFs(double[][] out) {
		double[][] res = new double[6][(out.length/6) * out[0].length];
		int cnt = 0;
		for (int step = 0; step < out[0].length; step++) {
			for (int i = 0; i < out.length; i += 6) {
				res[0][cnt] = out[i][step];
				res[1][cnt] = out[i+1][step];
				res[2][cnt] = out[i+2][step];
				res[3][cnt] = out[i+3][step];
				res[4][cnt] = out[i+4][step];
				res[5][cnt] = out[i+5][step];
				cnt++;
			}
		}
		return res;
	}

}
