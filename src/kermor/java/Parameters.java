/**
 * 
 */
package kermor.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Ernst
 *
 */
public class Parameters {
	
	public class Parameter {
		public String name;
		double min;
		double max;
		
		public Parameter(String name, double minval, double maxval) {
			this.name = name;
			this.max = maxval;
			this.min = minval;
		}
	}
	
	private List<Parameter> params;
	
	public Parameters() {
		params = new ArrayList<Parameter>();
	}
	
	public void addParam(String name, double minval, double maxval) {
		params.add(new Parameter(name, minval, maxval));
	}
	
	public List<Parameter> getParams() {
		return Collections.unmodifiableList(params);
	}
	
	public int getParamNumber() {
		return params.size();
	}
	
	public double[] getRandomParam() {
		double[] res = new double[getParamNumber()];
		Random r = new Random(System.currentTimeMillis());
		for (int i=0;i<res.length;i++) {
			res[i] = r.nextDouble() * (params.get(i).max - params.get(i).min);
		}
		return res;
	}
	
}
