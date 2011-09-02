/**
 * 
 */
package kermor.java;

import java.io.IOException;

import kermor.java.dscomp.ConstInitialValue;
import kermor.java.dscomp.IInitialValue;
import kermor.java.dscomp.IInputConv;
import kermor.java.dscomp.IInputFunctions;
import kermor.java.dscomp.IOutputConv;
import kermor.java.dscomp.LinearInputConv;
import kermor.java.dscomp.LinearOutputConv;
import kermor.java.kernel.GaussKernel;
import kermor.java.kernel.IKernel;
import kermor.java.kernel.KernelExpansion;
import kermor.java.kernel.LinearKernel;

import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;

import rmcommon.Util;
import rmcommon.io.AModelManager;
import rmcommon.io.MathObjectReader;
import rmcommon.io.MathObjectReader.MathReaderException;


/**
 * @author Ernst
 * 
 */
public class ReducedSystem implements FirstOrderDifferentialEquations {

	private int dim = -1;

	public IOutputConv C = null;

	public IInitialValue x0 = null;

	public IInputConv B = null;

	public IInputFunctions u = null;

	public KernelExpansion f = null;

	private double[] mu = null;
	private int inidx = -1;

	public void setConfig(double[] mu, int InputIdx) {
		this.mu = mu;
		this.inidx = InputIdx;
	}

	@Override
	public int getDimension() {
		return dim;
	}

	@Override
	public void computeDerivatives(double t, double[] x, double[] xDot)
			throws DerivativeException {
		Util.vecAdd(xDot, f.evaluate(t, x, mu));
		if (B != null) {
			Util.vecAdd(xDot, B.evaluate(t, mu).operate(u.evaluate(t, inidx)));
		}
	}

	public static ReducedSystem load(AModelManager mng) throws MathReaderException, IOException {
		ReducedSystem res = new ReducedSystem();

		res.dim = Integer.parseInt(mng.getModelXMLTagValue("dim"));
		
		MathObjectReader r = new MathObjectReader();
		
		String hlp = mng.getModelXMLTagValue("inputconvtype");
		if ("dscomponents.LinearInputConv".equals(hlp)) {
			res.B = new LinearInputConv(r.readMatrix(mng.getInStream("B.bin")));
		}
		
		hlp = mng.getModelXMLTagValue("outputconvtype");
		if ("dscomponents.LinearOutputConv".equals(hlp)) {
			res.C = new LinearOutputConv(r.readMatrix(mng.getInStream("C.bin")));
		}
		
		hlp = mng.getModelXMLTagValue("initialvaluetype");
		if ("dscomponents.ConstInitialValue".equals(hlp)) {
			res.x0 = new ConstInitialValue(r.readRawDoubleVector(mng.getInStream("x0.bin")));
		}
		
		//res.u = new ConstInputFunction(new double[]{.5});
		hlp = mng.getModelXMLTagValue("corefuntype");
		if ("dscomponents.ParamTimeKernelCoreFun".equals(hlp)) {
			res.f = new KernelExpansion();
			res.f.ma = r.readMatrix(mng.getInStream("Ma.bin"));
			
			res.f.StateKernel = loadKernel(mng, "statekernel", "kernel.bin");
			res.f.xi = r.readMatrix(mng.getInStream("xi.bin"));
			//System.out.println(Util.realMatToString(res.f.xi));
			
			res.f.TimeKernel = loadKernel(mng, "timekernel", "timekernel.bin");
			res.f.ti = r.readVector(mng.getInStream("ti.bin"));
			//System.out.println(Util.realVecToString(res.f.ti));
			
			res.f.ParamKernel = loadKernel(mng, "paramkernel", "paramkernel.bin");
			res.f.mui = r.readMatrix(mng.getInStream("mui.bin"));
			//System.out.println(Util.realMatToString(res.f.mui));
			
		} else {
			throw new RuntimeException("Unknown core function type: "+hlp);
		}
		

		return res;
	}
	
	private static IKernel loadKernel(AModelManager mng, String typestr, String datafile) throws MathReaderException, IOException {
		IKernel res = null;
		MathObjectReader r = new MathObjectReader();
		String type = mng.getModelXMLTagValue(typestr);
		if ("kernels.GaussKernel".equals(type)) {
			double[] g = r.readRawDoubleVector(mng.getInStream(datafile));
			res = new GaussKernel(g[0]);
		} else if ("kernels.LinearKernel".equals(type)) {
			res = new LinearKernel();
		}
		type = null; r = null;
		return res;
	}

}
