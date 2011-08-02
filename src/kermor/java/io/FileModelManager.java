/**
 * 
 */
package kermor.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ernst
 *
 */
public class FileModelManager extends AModelManager {

	private String root;
	
	public FileModelManager(String root) {
		super();
		this.root = root;
	}

	/* (non-Javadoc)
	 * @see kermor.java.io.IModelManager#getInStream(java.lang.String)
	 */
	@Override
	public InputStream getInStream(String filename) throws FileNotFoundException {
		String file = root + File.separator + getModelDir() + File.separator + filename;
		return new FileInputStream(file);
	}

	@Override
	public String[] getModelList() throws IOException {
		return new File(root + File.separator + getModelDir()).list();
	}

	@Override
	public String getInfoFileURL() {
		return "file://" + root + "/" + getModelDir() + "/" + info_filename;
	}

	@Override
	public boolean fileExists(String filename) {
		return new File(root + File.separator + getModelDir() + File.separator + filename).exists();
	}

}
