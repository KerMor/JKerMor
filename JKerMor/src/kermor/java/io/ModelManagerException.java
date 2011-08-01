/**
 * 
 */
package kermor.java.io;


/**
 * This Exception gets thrown when an error occurs regarding the functionality of the ModelManager.
 * 
 * @author Ernst
 *
 */
public class ModelManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7411589173897801550L;

	public ModelManagerException(String msg) {
		super(msg);
	}
	
	public ModelManagerException(String msg, Exception inner) {
		super(msg, inner);
	}

}
