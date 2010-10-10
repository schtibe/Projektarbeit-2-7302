package simulation.builder;

import org.jdom.Element;

/**
 * Way point factory
 */
public class XMLWayPointBuilderFactory {
	/**
	 * The possible directions for way points
	 */
	public enum direction {
		right, left, both
	}

	/**
	 * Create the accordant way point
	 * 
	 * @param elem
	 * @param wBuilder
	 * @return
	 */
	public static IXMLWayPointBuilder getWayPointBuilder(Element elem,
			IXMLWorldBuilder wBuilder) {
		String name = elem.getName();
		IXMLWayPointBuilder wayPoint = null;
		if (name.equals("SpeedWaypoint")) {
			wayPoint = new XMLSpeedWayPointBuilder(elem, wBuilder);
		}

		return wayPoint;
	}
}
