package simulation.builder;

import java.util.List;

import org.jdom.Document;

import common.IVector;

import environment.IJunction;
import environment.IRoad;
import environment.IWayPoint;

/**
 * World Builder
 * 
 * This unit reads the world from the XML and builds it
 */
public interface IXMLWorldBuilder {

	/**
	 * Generate the world from the global XML
	 * 
	 * @throws Exception
	 */
	public abstract void generate() throws Exception;

	/**
	 * Generate the world from a given file
	 * @param filename
	 * @throws InvalidXMLException
	 * @throws Exception
	 */
	public abstract void generate(String filename) 
		throws InvalidXMLException, Exception;
	
	/**
	 * Generate the world directly from a DOM document
	 * @param document
	 * @throws InvalidXMLException
	 * @throws Exception
	 */
	public abstract void generate(Document document) 
		throws InvalidXMLException, Exception;

	/**
	 * Return the built roads
	 * 
	 * @return
	 */
	public abstract List<IRoad> getRoads();

	/**
	 * Return the build junctions
	 * 
	 * @return
	 */
	public abstract List<IJunction> getJunctions();

	/**
	 * Return all available way points
	 * 
	 * @return
	 */
	public abstract List<IWayPoint> getAllWayPoints();

	/**
	 * Set a way point
	 * 
	 * @param wayPoint
	 */
	public void setWayPoint(IWayPoint wayPoint);

	/**
	 * Set coordinates
	 * 
	 * If one component of this coordinates is bigger than the one saved before,
	 * override it. In the end we know the biggest coordinates in the world
	 */
	public void setCoordinates(IVector coordinates);

	/**
	 * Return the world boundaries
	 * 
	 * @return
	 */
	public IVector[] getWorldBoundaries();

	/**
	 * Return the scale
	 * 
	 * @return
	 */
	public float getScale();
}