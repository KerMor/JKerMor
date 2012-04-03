/**
 * 
 */
package kermor;


/**
 * @author CreaByte
 *
 */
public class DefaultOutputToDoFs implements IOutputToDoF {

	@Override
	public double[][] transformOutputToDoFs(double[][] output) {
		return output;
	}

	/* (non-Javadoc)
	 * @see kermor.IOutputToSimulationResult#getSimulationResult(double[][])
	 */
//	@Override
//	public SimulationResult transformOutputToDoFs(double[][] model_output) {
//		SimulationResult res = new SimulationResult();
//		// Flatten out the results to a single solution field with more
//		float[] field = new float[model_output.length * model_output[0].length];
//		for (int frame=0;frame< model_output.length; frame++) {
//			for (int node=0; node < model_output[frame].length; node++) {
//				field[frame*model_output.length + node] = (float)model_output[node][frame];
//			}
//		}
//		SolutionField f = new RealSolutionField(field);
//		res.addField(f);
//		return res;
//	}

}
