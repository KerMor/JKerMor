/**
 * 
 */
package kermor.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Ernst
 *
 */
public class WebModelManager extends AModelManager {

	private String rooturl;
	
	public WebModelManager(String rooturl) {
		super();
		this.rooturl = rooturl;
	}

	/* (non-Javadoc)
	 * @see kermor.java.io.IModelManager#getInStream(java.lang.String)
	 */
	@Override
	public InputStream getInStream(String filename) throws IOException {
		URL u = new URL(rooturl + "/" + getModelDir() + "/" + filename);
		return u.openStream();
	}

	@Override
	public String[] getModelList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfoFileURL() {
		return rooturl + "/" + getModelDir() + "/" + info_filename;
	}

	@Override
	public boolean fileExists(String filename) {
		try {
			getInStream(filename).close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
