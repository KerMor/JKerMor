/**
 * 
 */
package kermor.java;

import java.io.InputStream;

/**
 * Represents a grid view item.
 * 
 * @author dwirtz
 * 
 */
public class ModelDescriptor {

	public String modeldir;
	public String title;
	public InputStream image;

	public ModelDescriptor(String mdir, String t, InputStream i) {
		modeldir = mdir;
		title = t;
		image = i;
	}
}
