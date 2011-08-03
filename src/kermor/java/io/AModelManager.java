package kermor.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kermor.java.IProgressHandler;
import kermor.java.ModelDescriptor;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/***
 * This class serves as base class for accessing various types of models at different locations.
 * 
 * Implementing classes implement the abstract members in order to reflect necessary adoptions to different input sources like the file system,
 * websites or others like Android-Assets.
 * Implemented in JKerMor are {@link kermor.java.io.WebModelManager} and {@link kermor.java.io.FileModelManager}.
 * 
 * Each manager has a root directory which must be, depending on the type, either provided at instantiation or are given implicitly.
 * The model system is organized in a way that the root directory contains folders which each contain a single model.
 * Within each such folder, a model.xml-file must be present that describes the model.
 * 
 * The basic XML file structure is as follows:
 * {@code
 * <?xml version="1.0" encoding="utf-8"?>
	<model type="sometype" title="sometitle" image="imagefile">
	</model>
 * }
 * 
 *  
 * @author dwirtz
 *
 */
public abstract class AModelManager {

	/**
	 * This Exception gets thrown when an error occurs regarding the
	 * functionality of the ModelManager.
	 * 
	 * @author dwirtz
	 * 
	 */
	public class ModelManagerException extends Exception {

		private static final long serialVersionUID = 7411589173897801550L;

		public ModelManagerException(String msg) {
			super(msg);
		}

		public ModelManagerException(String msg, Exception inner) {
			super(msg, inner);
		}

	}

	/**
	 * The model's info html file name (imported from rbappmit, might change later)
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
			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			bf.setIgnoringElementContentWhitespace(true);
			
			db = bf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Error creating a XML document builder",
					e);
		}
	}

//	public AModelManager(String modeldir) {
//		this();
//		this.mdir = modeldir;
//	}

	/**
	 * 
	 * @return The directory of the current model.
	 */
	public String getModelDir() {
		return mdir;
	}

	/**
	 * Attempts to set the current directory as model directory.
	 * If the specified directory does not contain an model.xml file or the file is not valid,
	 * an exception is thrown.
	 * 
	 * @param dir The directory to change to
	 * @throws ModelManagerException No file "model.xml" present in directory or model.xml file invalid.
	 */
	public void setModelDir(String dir) throws ModelManagerException {
		String olddir = mdir;
		this.mdir = dir;
		if (!modelFileExists("model.xml")) {
			throw new ModelManagerException(
					"No valid model found in the directory " + dir);
		}
		try {
			modelxml = db.parse(getInStream("model.xml"));
			modelxml.normalize();
			modelnode = modelxml.getElementsByTagName("model").item(0);
		} catch (Exception e) {
			mdir = olddir;
			throw new ModelManagerException(
					"Could not set the model directory to " + dir, e);
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

	/**
	 * Returns the text content of a tag inside the model.xml file.
	 * 
	 * @param tagname The tag whos value should be returned.
	 * @return The tag text content or null if none found.
	 */
	public String getModelXMLTagValue(String tagname) {
		if (modelxml != null) {
			
//			System.out.println("\nDocument body contents are:");
//		    listNodes(modelxml.getDocumentElement(),"");  

			NodeList nl = modelxml.getDocumentElement().getElementsByTagName(tagname);
			if (nl != null && nl.getLength() > 0) {
				return nl.item(0).getTextContent();
			}
		}
//		if (modelnode != null) {
//			NodeList nl = modelnode.getChildNodes();
//			if (nl != null) {
//				for (int i = 0; i < nl.getLength(); i++) {
//					Node c = nl.item(i);
//					if (tagname.equals(c.getNodeName())) {
//						return c.getTextContent();
//					}
//				}
//			}
//		}
		return null;
	}
	
//	private void listNodes(Node node, String indent) {
//	    String nodeName = node.getNodeName();
//	    System.out.println(indent+" Node: " + nodeName);
//	    short type = node.getNodeType();
//	    System.out.println(indent+" Node Type: " + nodeType(type));
//	    if(type == TEXT_NODE){
//	      System.out.println(indent+" Content is: "+((Text)node).getWholeText());
//	    }
//	    
//	    NodeList list = node.getChildNodes();       
//	    if(list.getLength() > 0) {                  
//	      System.out.println(indent+" Child Nodes of "+nodeName+" are:");
//	      for(int i = 0 ; i<list.getLength() ; i++) {
//	        listNodes(list.item(i),indent+"  ");     
//	      }
//	    }         
//	  }
//	
//	private String nodeType(short type) {
//	    switch(type) {
//	      case ELEMENT_NODE:                return "Element";
//	      case DOCUMENT_TYPE_NODE:          return "Document type";
//	      case ENTITY_NODE:                 return "Entity";
//	      case ENTITY_REFERENCE_NODE:       return "Entity reference";
//	      case NOTATION_NODE:               return "Notation";
//	      case TEXT_NODE:                   return "Text";
//	      case COMMENT_NODE:                return "Comment";
//	      case CDATA_SECTION_NODE:          return "CDATA Section";
//	      case ATTRIBUTE_NODE:              return "Attribute";
//	      case PROCESSING_INSTRUCTION_NODE: return "Attribute";
//	    }
//	    return "Unidentified";
//	  }

	/**
	 * Returns the attribute value of any attributes of the "model" tag in
	 * the model.xml file. Returns null if no model directory has been set or
	 * the attribute does not exist.
	 * 
	 * @param attrib_name The attribute's name
	 * @return The attribute value or null if the attribute does not exist
	 */
	public String getModelXMLAttribute(String attrib_name) {
		assert attrib_name != null;
		
		if (modelnode != null) {
			return getNodeAttributeValue(modelnode, attrib_name);
		}
		return null;
	}
	
	/**
	 * Returns the attribute value of any attributes of the tag given by tagname in
	 * the model.xml file. Returns null if no model directory has been set or
	 * the attribute does not exist.
	 * 
	 * @param attrib_name the attribute's name
	 * @param tagname The xml tag whos attributes are to be searched.
	 * @return The attribute value or null if the attribute does not exist
	 */
	public String getModelXMLAttribute(String attrib_name, String tagname) {
		assert attrib_name != null;
		assert tagname != null;
		
		NodeList nl = modelxml.getDocumentElement().getElementsByTagName(tagname);
		if (nl.getLength() > 0) {
			return getNodeAttributeValue(nl.item(0), attrib_name); 
		}
		return null;
	}
	
	private String getNodeAttributeValue(Node n, String attrib_name) {
		assert n != null;
		assert attrib_name != null;
		
		Node a = n.getAttributes().getNamedItem(attrib_name);
		if (a != null) {
			return a.getNodeValue();
		} else return null;
	}
	
	/**
	 * Checks if a specified tag exists inside the current models model.xml file.
	 * 
	 * @param tagname The tag to check
	 * @return True if the tag exists or false otherwise
	 */
	public boolean xmlTagExists(String tagname) {
		return modelxml.getDocumentElement().getElementsByTagName(tagname).getLength() > 0;
	}

	/**
	 * 
	 * Returns the list of all models directories available at the ModelManagers source
	 * location.
	 * At this stage, no validity checks have to be performed regarding if a returned folder actually 
	 * contains a valid model.
	 * 
	 * @return
	 * @throws IOException
	 */
	protected abstract String[] getFolderList() throws IOException;

	/**
	 * Returns whether the specified file exists in the current model folder.
	 * 
	 * @param filename
	 * @return
	 */
	public abstract boolean modelFileExists(String filename);

	/**
	 * Returns an InputStream instance streaming the contents of the file given
	 * by filename.
	 * 
	 * @param filename
	 *            The model file to return a stream for
	 * @return An InputStream pointing to the resource
	 * @throws IOException
	 */
	public abstract InputStream getInStream(String filename)
			throws IOException;

	/**
	 * [Old] The URL for a model's info html page.
	 * 
	 * @return
	 */
	public abstract String getInfoFileURL();

	/**
	 * Scans all directories given by getFolderList() for valid models and returns a list of model descriptors for each valid model.
	 *  
	 * @return
	 * @throws ModelManagerException
	 */
	public List<ModelDescriptor> getModelDescriptors()
			throws ModelManagerException {
		ArrayList<ModelDescriptor> res = new ArrayList<ModelDescriptor>();
		try {
			for (String model : getFolderList()) {
				try {
					setModelDir(model);
				} catch (ModelManagerException me) {
					continue;
				}

				InputStream img = null;
				String imgfile = getModelXMLAttribute("image");
				if (imgfile != null) {
					try {
						img = getInStream(imgfile);
					} catch (IOException e) {
						// Ignore when the image could not be loaded.
					}
				}
				res.add(new ModelDescriptor(model,
						getModelXMLAttribute("title"),
						getModelXMLAttribute("type"),
						img));
			}
		} catch (IOException e) {
			throw new ModelManagerException("Loading model list failed.", e);
		}
		return res;
	}
}