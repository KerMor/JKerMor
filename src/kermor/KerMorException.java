package kermor;

/**
 * Custom exception for JKerMor related errors
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class KerMorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3311639214049185983L;

	public KerMorException(String msg, Exception inner) {
		super(msg, inner);
	}

	public KerMorException(String msg) {
		super(msg);
	}

}
