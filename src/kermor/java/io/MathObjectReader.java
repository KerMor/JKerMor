/**
 * 
 */
package kermor.java.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import kermor.java.MathFactory;

import org.apache.commons.math.linear.MatrixIndexException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

/**
 * 
 * Reading matrices and vectors with a bunch of convenient overloads for
 * different sources and output formats.
 * 
 * All methods operate on binary files, which have been written using the BIG ENDIAN machine format,
 * as Java natively supports this format in the DataInputStream class.
 * 
 * When matrices or vectors are read by methods NOT taking the dimensions explicitly, the MathObjectReader will
 * read the first (two) 32-bit integer value(s) as the dimensions of the vector (matrix).
 * 
 * @author dwirtz
 * 
 */
public class MathObjectReader {

	public class MathReaderException extends Exception {

		private static final long serialVersionUID = -3505742802789851382L;

		public MathReaderException(String msg, Exception inner) {
			super(msg, inner);
		}
	}

	/**
	 * Reads a matrix from an InputStream, pointing to a binary file.
	 * @param in
	 * @return
	 * @throws MathReaderException
	 */
	public RealMatrix readMatrix(InputStream in) throws MathReaderException {
		return MathFactory.createRealMatrix(readMatrixData(in));
	}

	/**
	 * Reads a matrix from a given binary file in the file system (accessible via java.io, i.e. FileStream)
	 * including its dimensions.
	 * 
	 * @param file Path to a binary file
	 * @return
	 * @throws MathReaderException
	 * @throws FileNotFoundException
	 */
	public RealMatrix readMatrix(String file) throws MathReaderException,
			FileNotFoundException {
		return readMatrix(new FileInputStream(file));
	}

	/**
	 * Reads a real vector including dimension from a given input stream which points to a binary file. 
	 * @param in InputStream of a binary file
	 * @return
	 * @throws MathReaderException
	 */
	public RealVector readVector(InputStream in) throws MathReaderException {
		return MathFactory.createRealVector(readVectorData(in));
	}

	/**
	 * Reads a real vector including dimension from a given binary file. 
	 * @param filename Name of a binary file
	 * @return
	 * @throws MathReaderException
	 */
	public RealVector readVector(String filename) throws FileNotFoundException,
			MathReaderException {
		return readVector(new FileInputStream(filename));
	}

	/**
	 * Reads a real matrix as double[][] array from a given binary input stream, including dimension detection.
	 * @param in
	 * @return
	 * @throws MathReaderException
	 */
	public double[][] readMatrixData(InputStream in) throws MathReaderException {
		int rows = -1;
		int cols = -1;
		DataInputStream rd = new DataInputStream(in);
		try {
			rows = rd.readInt();
			cols = rd.readInt();
			return readMatrixData(rd, rows, cols);
		} catch (IOException e) {
			throw new MathReaderException(
					"Error reading matrix from data stream with rows/columns autoread to "
							+ rows + "/" + cols, e);
		}
	}

	/**
	 * Reads a real vector from a binary input stream, including dimension detection.
	 * @param in
	 * @return
	 * @throws MathReaderException
	 */
	public double[] readVectorData(InputStream in) throws MathReaderException {
		int size = 0;
		DataInputStream ds = new DataInputStream(in);
		try {
			size = ds.readInt();
			return readVectorData(ds, size);
		} catch (IOException e) {
			throw new MathReaderException(
					"Error reading vector data stream with size autoread to "
							+ size, e);
		}
	}

	private double[][] readMatrixData(DataInputStream rd, int rows, int cols)
			throws MatrixIndexException, IOException {
		double[][] res = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			res[i] = readVectorData(rd, cols);
		}
		return res;
	}

	private double[] readVectorData(DataInputStream rd, int size)
			throws MatrixIndexException, IOException {
		double[] res = new double[size];
		for (int i = 0; i < size; i++) {
			res[i] = rd.readDouble();
		}
		return res;
	}

}
