package simulation.builder;

import org.jdom.Element;

import common.IVector;
import common.Vector;

/**
 * The top class for all builder classes
 * 
 * @author user
 * 
 */
public abstract class XMLObjectBuilder implements IXMLObjectBuilder {
	/**
	 * The XML element
	 */
	protected Element elem;

	/**
	 * Save the XML element and the world builder reference
	 * 
	 * @param e
	 */
	public XMLObjectBuilder(Element e) {
		this.elem = e;
	}

	/**
	 * Return the unit vector perpendicular to a vector
	 * 
	 * Turns the vector 90 ° to the right.
	 * 
	 * @param int count The number of the lane
	 * @param The
	 *            direction of the lane
	 * @return
	 */
	protected IVector getPerpVector(IVector direction) {
		IVector newVector = new Vector(new float[] {
				0 - direction.getComponent(1), direction.getComponent(0) });
		return newVector;
	}
}
