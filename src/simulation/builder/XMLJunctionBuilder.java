package simulation.builder;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import environment.CrossRoads;
import environment.IJunction;

/**
 * The builder class for junctions
 * 
 * @todo Should create an interface for this class
 */
public class XMLJunctionBuilder extends XMLObjectBuilder {

	/**
	 * Junction type
	 */
	protected String type;

	/**
	 * The roads to connect to
	 */
	protected List<Integer> roads;

	/**
	 * The junction to build
	 */
	protected IJunction junction;

	/**
	 * Initialize needed variables
	 * 
	 * @param e
	 * @param wBuilder
	 */
	public XMLJunctionBuilder(Element e, IXMLWorldBuilder wBuilder) {
		super(e, wBuilder);

		this.roads = this.readRoads();

		this.type = this.elem.getAttributeValue("type");

		this.createJunction();
	}

	/**
	 * Create a junction
	 * 
	 * Not sure if this is done well
	 */
	protected void createJunction() {
		if (type.equals("CrossRoad")) {
			this.junction = new CrossRoads();
		}
	}

	/**
	 * Get an integer list of the roads from the XML
	 * 
	 * @return
	 */
	protected List<Integer> readRoads() {
		List<?> roadElems = this.elem.getChild("roads").getChildren("road");
		List<Integer> roads = new ArrayList<Integer>();

		for (int i = 0; i < roadElems.size(); i++) {
			roads.add(Integer.parseInt(((Element) roadElems.get(i))
					.getAttributeValue("id")));
		}

		return roads;
	}

	/**
	 * Get a list of the road integers
	 * 
	 * @return
	 */
	public List<Integer> getRoads() {
		return this.roads;
	}

	/**
	 * Return the junction
	 * 
	 * @return
	 */
	public IJunction getJunction() {
		return this.junction;
	}
}
