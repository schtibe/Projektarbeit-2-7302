package simulation.builder;

/**
 * Exception if the XML file contains invalid definitions
 * 
 * @author user
 * 
 */
public class InvalidXMLException extends Exception {
	/**
	 * yeah...
	 */
	private static final long serialVersionUID = -5586357638120947819L;

	/**
	 * Construct
	 * 
	 * @param message
	 */
	public InvalidXMLException(String message) {
		super(message);
	}

}
