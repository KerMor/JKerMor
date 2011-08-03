/**
 * 
 */
package kermor.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A model manager reading models from a remote web location.
 * 
 * The remote root url must be given upon instantiation.
 * 
 * The getFolderList() method tries to access the file DIRLIST_FILE in that
 * location in order to retrieve the possible model folders located at the
 * remote root url.
 * 
 * @author dwirtz
 * 
 */
public class WebModelManager extends AModelManager {

	/**
	 * The file containing the model folders to consider per line.
	 */
	public static final String DIRLIST_FILE = "models.txt";

	private String rooturl;

	public WebModelManager(String rooturl) {
		super();
		this.rooturl = rooturl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kermor.java.io.IModelManager#getInStream(java.lang.String)
	 */
	@Override
	public InputStream getInStream(String filename) throws IOException {
		URL u = new URL(rooturl + "/" + getModelDir() + "/" + filename);
		return u.openStream();
	}

	@Override
	public String[] getFolderList() throws IOException {
		URL u = new URL(rooturl + "/" + DIRLIST_FILE);
		Scanner s = new Scanner(u.openStream());
		List<String> folders = new ArrayList<String>();
		while (s.hasNextLine()) {
			folders.add(s.nextLine());
		}
		if (folders.size() > 0) {
			return folders.toArray(new String[0]);
		} else {
			return new String[0];
		}
	}

	@Override
	public String getInfoFileURL() {
		return rooturl + "/" + getModelDir() + "/" + info_filename;
	}

	@Override
	public boolean modelFileExists(String filename) {
		try {
			getInStream(filename).close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
