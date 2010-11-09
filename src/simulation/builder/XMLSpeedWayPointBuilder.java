package simulation.builder;

import org.jdom.Element;

import common.IVector;
import common.Vector;

import environment.ILane;
import environment.LaneLengthExceededException;
import environment.SignWayPoint;
import environment.SpeedWayPoint;

/**
 * Builder for speed way points
 * 
 * Currently not in use due to missing time
 */
public class XMLSpeedWayPointBuilder extends XMLWayPointBuilder {
	/**
	 * Initialize
	 * 
	 * @param e
	 * @param wBuilder
	 * @throws InvalidXMLException 
	 */
	public XMLSpeedWayPointBuilder(Element e) throws InvalidXMLException {
		super(e);
	}

	/**
	 * Create the way point
	 */
	@Override
	public SignWayPoint createWayPoint(ILane lane) {
		return new SpeedWayPoint(
				lane, 
				this.readSpeed(), 
				this.getWayPointPosition(lane)
		); 
	}
	
	/**
	 * Calculate the position of the way point
	 * @param lane
	 * @return
	 */
	private IVector getWayPointPosition(ILane lane) {
		float pos = this.readPosition();
		
		IVector position = null;
		try {
			position = lane.getVehiclePosition(pos);
		} catch (LaneLengthExceededException e) {
			// forget it, it is already checked by the XSD
		}
		
		return position;
	}
	
	/**
	 * Return the position given by the XML
	 * @return
	 */
	private float readPosition() {
		return Float.parseFloat(this.elem.getAttributeValue("position"));
	}
	
	/**
	 * Return the road speed given by the XML
	 * @return
	 */
	private int readSpeed() {
		return Integer.parseInt(this.elem.getAttributeValue("value"));
	}
}
