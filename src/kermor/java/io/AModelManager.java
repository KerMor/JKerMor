package kermor.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kermor.java.IProgressHandler;
import kermor.java.ModelDescriptor;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.MatrixIndexException;
import org.apache.commons.math.linear.RealMatrix;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class AModelManager {

	/**
	 * The model's info html file name
	 */
	public static final String info_filename = "site_info.html";

	private String mdir = "notset";
	private DocumentBuilder db = null;
	private Document modelxml = null;
	private Node modelnode = null;
	
	private List<IProgressHandler> phandlers;

	public AModelManager() {
		super();
		phandlers = new ArrayList<IProgressHandler>();
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Error creating a XML document builder",
					e);
		}
	}

	public AModelManager(String modeldir) {
		this();
		this.mdir = modeldir;
	}

	/**
	 * 
	 * @return The directory of the current model.
	 */
	public String getModelDir() {
		return mdir;
	}

	public void setModelDir(String dir) throws ModelManagerException {
		String olddir = mdir;
		this.mdir = dir;
		//if (!fileExists("model.xml"))
		try {
			modelxml = db.parse(getInStream("model.xml"));
			modelnode = modelxml.getElementsByTagName("kermormodel").item(0);
		} catch (Exception e) {
			throw new ModelManagerException("Could not set the model directory to "+dir, e);
		} finally {
			mdir = olddir;
		}
	}
	
	public void addProgressHandler(IProgressHandler h) {
		phandlers.add(h);
	}
	
	public void removeProgressHandler(IProgressHandler h) {
		phandlers.remove(h);
	}
	
	protected void progress(String msg) {
		for (IProgressHandler h : phandlers) {
			h.progress(msg, 0);
		}
	}

	public String getModelXMLTagValue(String tagname) {
		if (modelnode != null) {
			NodeList nl = modelnode.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node c = nl.item(i);
				if (c.getNodeName().equals(tagname)) {
					return c.getTextContent();
				}
			}
		}
		return null;
	}

	public abstract String[] getModelList() throws IOException;

	public abstract boolean fileExists(String filename);

	public List<ModelDescriptor> getModelDescriptors()
			throws ModelManagerException {
		ArrayList<ModelDescriptor> res = new ArrayList<ModelDescriptor>();
		try {

			for (String model : getModelList()) {

				if (!fileExists("model.xml")) {
					res.add(new ModelDescriptor(model,
							"No model.xml found in '" + model + "'", null));
					continue;
				} else {
					// try {
					Document doc = db.parse(getInStream("model.xml"));
					// doc.getDocumentElement().normalize();

					Node rb = doc.getElementsByTagName("kermormodel").item(0);
					String imgfile = rb.getAttributes().getNamedItem("image")
							.getNodeValue();
					res.add(new ModelDescriptor(model, rb.getAttributes()
							.getNamedItem("title").getNodeValue(),
							getInStream(imgfile)));
					// } catch (SAXException e) {
					// e.printStackTrace();
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
				}
			}
		} catch (Exception e) {
			throw new ModelManagerException(
					"Loading model descriptors failed.", e);
		}
		// } catch (RuntimeException e) {
		// throw new ModelManagerException(
		// "Error initializing a new XML document builder.", e);
		// // Log.e(getClass().getName(),
		// // "Parser configuration exception! " + e2.getMessage(), e2);
		// } catch (ParserConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SAXException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return res;
	}

	protected abstract InputStream getInStream(String filename)
			throws IOException;

	public abstract String getInfoFileURL();

	// public BufferedReader getBufReader(String filename) throws IOException {
	// int buffer_size = 8192;
	//
	// InputStreamReader isr = new InputStreamReader(getInStream(filename));
	// return new BufferedReader(isr, buffer_size);
	// }

	public BinaryReader getBinReader(String filename) throws IOException {
		progress(filename);
		return new BinaryReader(getInStream(filename));
	}

	public RealMatrix readMatrix(String file, int numRows, int numCols)
			throws ModelManagerException {
		try {
			return readMatrix(getBinReader(file), numRows, numCols);
		} catch (IOException io) {
			throw new ModelManagerException("Error reading matrix file '"
					+ file + "' using rows=" + numRows + ", cols=" + numCols,
					io);
		}
	}

	public RealMatrix readMatrix(String file) throws ModelManagerException {
		BinaryReader rd;
		int rows = -1;
		int cols = -1;
		try {
			rd = getBinReader(file);
		} catch (IOException e) {
			throw new ModelManagerException("Error opening matrix file '"
					+ file + "' for rows/columns autoread (first four bytes)",
					e);
		}
		try {
			rows = rd.ReadInt();
			cols = rd.ReadInt();
			return readMatrix(rd, rows, cols);
		} catch (IOException e) {
			throw new ModelManagerException("Error reading matrix file '"
					+ file + "' with rows/columns autoread to " + rows + "/"
					+ cols, e);
		}
	}

	private RealMatrix readMatrix(BinaryReader rd, int rows, int cols)
			throws MatrixIndexException, IOException {
		RealMatrix res = new Array2DRowRealMatrix(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				res.setEntry(i, j, rd.ReadDouble());
			}
		}
		return res;
	}

	protected void copyStream(InputStream fis, OutputStream fos)
			throws IOException {
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
	}

}