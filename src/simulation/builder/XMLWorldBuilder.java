package simulation.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import common.GlobalConstants;
import common.IVector;
import common.Vector;

import environment.IJunction;
import environment.ILane;
import environment.IRoad;
import environment.IWayPoint;

/**
 * {@inheritDoc}
 */
public class XMLWorldBuilder implements IXMLWorldBuilder {
	private File file;

	/**
	 * The roads from the XML
	 */
	private HashMap<Integer, XMLRoadBuilder> roads = new HashMap<Integer, XMLRoadBuilder>();

	/**
	 * The junctions from the XML
	 */
	private HashMap<Integer, XMLJunctionBuilder> junctions = new HashMap<Integer, XMLJunctionBuilder>();

	/**
	 * All Way Points
	 */
	protected List<IWayPoint> wayPoints = new ArrayList<IWayPoint>();

	/**
	 * The world boundaries
	 */
	protected float worldXBiggest, worldYBiggest;
	protected float worldXLowest, worldYLowest;
	protected boolean lowestInitialized = false;

	/**
	 * The scale factor of the numbers
	 */
	protected float scale;

	/**
	 * The XML document object
	 */
	private Document document;

	/**
	 * Construct with a file
	 * 
	 * @param filename
	 *            The file to read
	 * @throws Exception
	 */
	public XMLWorldBuilder(String filename) throws JDOMException {
		this.file = new File(filename);

		SAXBuilder builder = new SAXBuilder(true);
		builder.setFeature("http://apache.org/xml/features/validation/schema",
				true);

		try {
			this.document = builder.build(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construct with the direct dom object
	 * 
	 * @param document
	 *            JDom Document object
	 * @throws Exception
	 */
	public XMLWorldBuilder(Document document) {
		this.document = document;
	}

	/**
	 * Construct with the file name of global constants
	 * 
	 * @throws JDOMException
	 */
	public XMLWorldBuilder() throws JDOMException {
		this.file = new File(GlobalConstants.getInstance().getStreetXMLSchema());

		SAXBuilder builder = new SAXBuilder(true);
		builder.setFeature("http://apache.org/xml/features/validation/schema",
				true);

		try {
			this.document = builder.build(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void generate() throws Exception {
		Element root = this.document.getRootElement();

		List<?> roads = root.getChildren("road");
		List<?> junctions = root.getChildren("junction");

		for (int i = 0; i < roads.size(); i++) {
			Element e = (Element) roads.get(i);
			Integer id = Integer.parseInt(e.getAttributeValue("id"));
			this.roads.put(id, new XMLRoadBuilder(e, this));
		}

		for (int i = 0; i < junctions.size(); i++) {
			Element e = (Element) junctions.get(i);
			Integer id = Integer.parseInt(e.getAttributeValue("id"));
			this.junctions.put(id, new XMLJunctionBuilder(e, this));
		}

		this.scale = Float.parseFloat(root.getAttributeValue("scale"));

		this.makeConnections();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IRoad> getRoads() {
		List<IRoad> roads = new ArrayList<IRoad>();
		for (XMLRoadBuilder road : this.roads.values()) {
			roads.add(road.getRoad());
		}

		return roads;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IJunction> getJunctions() {
		List<IJunction> junctions = new ArrayList<IJunction>();
		for (XMLJunctionBuilder junction : this.junctions.values()) {
			junctions.add(junction.getJunction());
		}

		return junctions;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IWayPoint> getAllWayPoints() {
		return this.wayPoints;
	}

	/**
	 * Connect the roads and junctions
	 * 
	 * Go through all junctions and get the roads that are connected to each
	 * function. Then get a list of the references to these roads and save them
	 * in the junction. Also save the junction in the lane.
	 * 
	 * @throws InvalidXMLException
	 */
	protected void makeConnections() throws Exception {
		for (Entry<Integer, XMLJunctionBuilder> jBuilder : this.junctions
				.entrySet()) {
			List<Integer> roads = jBuilder.getValue().getRoads();
			List<IRoad> roadRefs = new ArrayList<IRoad>();
			for (Integer road : roads) {
				try {
					roadRefs.add(this.roads.get(road).getRoad());
				} catch (NullPointerException e) {
					throw new InvalidXMLException("the road " + road
							+ " defined for the junction "
							+ jBuilder.getValue().getJunction() + " "
							+ "doesn't exist");
				}

				for (ILane lane : this.roads.get(road).getRoad()
						.getRightLanes()) {
					lane.setJunction(jBuilder.getValue().getJunction());
				}
			}
			jBuilder.getValue().getJunction().setRoads(roadRefs);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setWayPoint(IWayPoint wayPoint) {
		this.wayPoints.add(wayPoint);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param wayPoints
	 */
	public void setWayPoints(List<IWayPoint> wayPoints) {
		this.wayPoints.addAll(wayPoints);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector[] getWorldBoundaries() {
		return new IVector[] {
				new Vector(new float[] { this.worldXLowest, this.worldYLowest }),
				new Vector(
						new float[] { this.worldXBiggest, this.worldYBiggest }) };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(IVector coordinates) {
		if (coordinates.getComponent(0) > this.worldXBiggest) {
			this.worldXBiggest = coordinates.getComponent(0);
		}

		if (coordinates.getComponent(1) > this.worldYBiggest) {
			this.worldYBiggest = coordinates.getComponent(1);
		}

		if (this.lowestInitialized) {
			if (coordinates.getComponent(0) < this.worldXLowest) {
				this.worldXLowest = coordinates.getComponent(0);
			}

			if (coordinates.getComponent(1) < this.worldYLowest) {
				this.worldYLowest = coordinates.getComponent(1);
			}
		} else {
			this.worldXLowest = coordinates.getComponent(0);
			this.worldYLowest = coordinates.getComponent(1);
			this.lowestInitialized = true;
		}
	}

	public float getScale() {
		return scale;
	}

}
