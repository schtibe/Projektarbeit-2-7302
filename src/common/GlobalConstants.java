package common;

import org.newdawn.slick.Color;

// TODO probably these things should come from a configuration file
public final class GlobalConstants {

	/**
	 * singleton instance
	 */
	private static GlobalConstants instance;

	/**
	 * private constructor
	 */
	private GlobalConstants() {
	}

	/**
	 * 
	 * method to access the singleton instance
	 * 
	 * @return singleton instance
	 */
	public synchronized static GlobalConstants getInstance() {
		if (instance == null) {
			instance = new GlobalConstants();
		}
		return instance;
	}

	/*
	 * The scale of the world
	 */
	private float scale;

	/**
	 * getter for scale
	 * 
	 * @return wordlscale as float
	 */
	public float getScale() {
		return this.scale;
	}

	/**
	 * setter for world scale
	 * 
	 * @param float scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/*
	 * Time factor
	 */
	private double timeFactor;

	/**
	 * getter for the time factor
	 * 
	 * @return timeFactor
	 */
	public double getTimeFactor() {
		return timeFactor;
	}

	/**
	 * setter for timeFactor
	 * 
	 * @param timeFactor
	 */
	public void setTimeFactor(double timeFactor) {
		this.timeFactor = timeFactor;
	}

	/*
	 * Initial time stamp
	 */
	private long initialTimestamp;

	/**
	 * getter for initial time stamp
	 * 
	 * @return initial time stamp
	 */
	public long getInitialTimestamp() {
		return this.initialTimestamp;
	}

	/**
	 * setter for initial time stamp
	 * 
	 * @param timestamp
	 */
	public void setInitialTimestamp(long timestamp) {
		this.initialTimestamp = timestamp;
	}

	/*
	 * The default street schema xml
	 */
	private String streetXMLSchema = "ressources/data/drivermodel.xml";

	/**
	 * getter for street schema
	 * 
	 * @return streetSchema
	 */
	public String getStreetXMLSchema() {
		return this.streetXMLSchema;
	}

	/*
	 * The world boundaries
	 */
	private IVector[] boundaries;

	/**
	 * setter for the world boundaries
	 * 
	 * @param boundaries
	 */
	public void setWorldBoundaries(IVector[] boundaries) {
		this.boundaries = boundaries;
	}

	/**
	 * getter for the world boundaries
	 * 
	 * @return
	 */
	public IVector[] getWorldBoundaries() {
		return this.boundaries;
	}

	/*
	 * border around the drawn streets
	 */
	public IVector getBorder() {
		return new Vector(new float[] { 30, 30 });
	}

	/**
	 * Return the Colour of the path
	 * @return a colour obviously
	 */
	public Color getPathColor() { 
		return new Color(255, 255, 255);
	}

	/**
	 * 
	 * @return a colour obviously
	 */

	public Color getBackgroundColor() {
		return new Color(100, 100, 100);
	}

	/**
	 * 
	 * @return root path for maps
	 */
	public String getMapRoot() {
		return "ressources/data/";
	}

	/**
	 * setter for street schema
	 * 
	 * @param streetXMLSchema
	 */
	public void setStreetXMLSchema(String streetXMLSchema) {
		this.streetXMLSchema = streetXMLSchema;
	}
}
