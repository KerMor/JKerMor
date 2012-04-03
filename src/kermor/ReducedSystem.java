/**
 * 
 */
package kermor;

import jarmos.Util;
import jarmos.affine.AffParamMatrix;
import jarmos.affine.IAffineCoefficients;
import jarmos.io.AModelManager;
import jarmos.io.MathObjectReader;
import jarmos.io.AModelManager.ModelManagerException;
import jarmos.io.MathObjectReader.MathReaderException;

import java.io.IOException;

import kermor.dscomp.AffParamTimeCoreFun;
import kermor.dscomp.AffineInputConv;
import kermor.dscomp.ConstInitialValue;
import kermor.dscomp.ConstMassMatrix;
import kermor.dscomp.ICoreFun;
import kermor.dscomp.IInitialValue;
import kermor.dscomp.IInputConv;
import kermor.dscomp.IInputFunctions;
import kermor.dscomp.IMassMatrix;
import kermor.dscomp.IOutputConv;
import kermor.dscomp.LinearCoreFun;
import kermor.dscomp.LinearInputConv;
import kermor.dscomp.LinearOutputConv;
import kermor.kernel.GaussKernel;
import kermor.kernel.IKernel;
import kermor.kernel.KernelExpansion;
import kermor.kernel.LinearKernel;

import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;


/**
 * @author Ernst
 * 
 */
public class ReducedSystem implements FirstOrderDifferentialEquations {

	private int dim = -1;

	public IOutputConv C = null;

	public IInitialValue x0 = null;

	public IInputConv B = null;
	
	public IMassMatrix M = null;

	public IInputFunctions u = null;

	public ICoreFun f = null;

	private double[] mu = null;
	private int inidx = -1;

	public void setConfig(double[] mu, int InputIdx) {
		this.mu = mu;
		this.inidx = InputIdx;
	}
	
	public double[] currentMu() {
		return mu;
	}
	
	public int currentInput() {
		return inidx;
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

	/**
	 * 
	 * @param mng
	 * @return
	 * @throws MathReaderException - If errors reading math objects occur
	 * @throws ModelManagerException - If errors loading presumably existent model files occur
	 * @throws IOException - If errors loading files from the model's source do occur
	 */
	public static ReducedSystem load(AModelManager mng)
			throws MathReaderException, ModelManagerException, IOException {
		ReducedSystem res = new ReducedSystem();

		res.dim = Integer.parseInt(mng.getModelXMLTagValue("dim"));

		MathObjectReader r = mng.getMathObjReader();

		loadCoreFun(mng, res);

		loadInputs(mng, res);
		
		loadMassMatrix(mng, res);

		if ("dscomponents.LinearOutputConv".equals(mng
				.getModelXMLTagValue("outputconvtype"))) {
			res.C = new LinearOutputConv(r.readMatrix(mng.getInStream("C.bin")));
		}

		if ("dscomponents.ConstInitialValue".equals(mng
				.getModelXMLTagValue("initialvaluetype"))) {
			res.x0 = new ConstInitialValue(r.readRawDoubleVector(mng
					.getInStream("x0.bin")));
		}

		return res;
	}
	
	

	private static void loadInputs(AModelManager mng, ReducedSystem res)
			throws IOException, ModelManagerException {
		// Inputs
		MathObjectReader r = mng.getMathObjReader();
		String hlp;
		if (mng.xmlTagExists("kermor_model.inputconv")) {
			res.u = (IInputFunctions) mng.loadModelClass("Inputs");
			hlp = mng.getModelXMLAttribute("type","kermor_model.inputconv");
			if ("dscomponents.LinearInputConv".equals(hlp)) {
				res.B = new LinearInputConv(r.readMatrix(mng
						.getInStream("B.bin")));
			} else if ("dscomponents.AffLinInputConv".equals(hlp)) {
				IAffineCoefficients coeff = null;
				coeff = (IAffineCoefficients) mng.loadModelClass(mng.getModelXMLTagValue("inputconv.coeffclass"));
				AffParamMatrix a = new AffParamMatrix(r.readMatrix(mng
						.getInStream("B.bin")), res.dim, coeff);
				res.B = new AffineInputConv(a);
			}
		}
	}

	private static void loadCoreFun(AModelManager mng, ReducedSystem res)
			throws IOException, MathReaderException, ModelManagerException {
		MathObjectReader r = mng.getMathObjReader();
		String type = mng.getModelXMLAttribute("type", "corefun");
		if ("dscomponents.ParamTimeKernelCoreFun".equals(type)) {
			KernelExpansion k = new KernelExpansion();
			k.ma = r.readMatrix(mng.getInStream("Ma.bin"));

			k.StateKernel = loadKernel(mng, "statekernel", "kernel.bin");
			k.xi = r.readMatrix(mng.getInStream("xi.bin"));
			// System.out.println(Util.realMatToString(res.f.xi));

			k.TimeKernel = loadKernel(mng, "timekernel", "timekernel.bin");
			k.ti = r.readVector(mng.getInStream("ti.bin"));
			// System.out.println(Util.realVecToString(res.f.ti));

			k.ParamKernel = loadKernel(mng, "paramkernel", "paramkernel.bin");
			k.mui = r.readMatrix(mng.getInStream("mui.bin"));
			// System.out.println(Util.realMatToString(res.f.mui));

			res.f = k;
		} else if ("dscomponents.LinearCoreFun".equals(type)) {
			res.f = new LinearCoreFun(r.readMatrix(mng.getInStream("A.bin")));
		} else if ("dscomponents.AffLinCoreFun".equals(type)) {
			IAffineCoefficients coeff = null;
			coeff = (IAffineCoefficients) mng.loadModelClass(mng.getModelXMLTagValue("corefun.coeffclass"));
			AffParamMatrix a = new AffParamMatrix(r.readMatrix(mng
					.getInStream("A.bin")), res.dim, coeff);
			res.f = new AffParamTimeCoreFun(a);
		} else {
			throw new RuntimeException("Unknown core function type: " + type);
		}
	}
	
	private static void loadMassMatrix(AModelManager mng, ReducedSystem res)
			throws IOException, ModelManagerException {
		// Inputs
		MathObjectReader r = mng.getMathObjReader();
		String hlp;
		if (mng.xmlTagExists("kermor_model.massmatrix")) {
			hlp = mng.getModelXMLAttribute("type","kermor_model.massmatrix");
			if ("dscomponents.ConstMassMatrix".equals(hlp)) {
				res.M = new ConstMassMatrix(r.readMatrix(mng
						.getInStream("M.bin")));
			} else if ("dscomponents.AffLinMassMatrix".equals(hlp)) {
				IAffineCoefficients coeff = (IAffineCoefficients) mng.loadModelClass(mng.getModelXMLTagValue("massmatrix.coeffclass"));
				throw new RuntimeException("Not yet implemented.");
//				AffParamMatrix a = new AffParamMatrix(r.readMatrix(mng
//						.getInStream("B.bin")), res.dim, coeff);
//				res.B = new AffineInputConv(a);
			}
		}
	}

	private static IKernel loadKernel(AModelManager mng, String typestr,
			String datafile) throws MathReaderException, IOException {
		IKernel res = null;
		MathObjectReader r = new MathObjectReader();
		String type = mng.getModelXMLTagValue(typestr);
		if ("kernels.GaussKernel".equals(type)) {
			double[] g = r.readRawDoubleVector(mng.getInStream(datafile));
			res = new GaussKernel(g[0]);
		} else if ("kernels.LinearKernel".equals(type)) {
			res = new LinearKernel();
		}
		type = null;
		r = null;
		return res;
	}
}
