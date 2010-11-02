package environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.IVector;


public class Lane implements ILane {
	/**
	 * The first lane segment to be added
	 * 
	 * This is a reference point. With this, the lane segments can
	 * be treated like a list.
	 */
	private ILaneSegmentLinear startSegment;
	
	/**
	 * if needed the last segment can be found and put in this property variable
	 */
	private LaneSegment endSegment;
	
	/**
	 * Start position relative to the world's x and y axis
	 */
	private IVector startPosition;
	
	/**
	 * The width of the lane
	 */
	private float laneWidth;

	/**
	 * The end position
	 * 
	 * Probably never used
	 */
	//private IVector endPosition;
	
	/**
	 * All the lane segments the lane contains
	 */
	//private LaneSegment<?> laneSegments[];
	
	/**
	 * A helper array for lane segment finding
	 * 
	 * This array helps to find the lane segment a car is
	 * on. A divide and conquer algorithm is used on this.
	 * 
	 * @see #getLaneSegmentAtPosition(float)
	 */
	private Float[] laneSegmentDistances;
	
	/**
	 * The association of start positions to lane segments
	 * 
	 * @see #laneSegmentDistances
	 */
	private HashMap<Float, ILaneSegment<?>> lanePositions = 
		new HashMap<Float, ILaneSegment<?>>();
	
	/**
	 * The length of the whole lane
	 */
	private float length;
	
	
	private IJunction junction;

	private ArrayList<SignWayPoint> wayPoints;
	
	/**
	 * Construct
	 * 
	 * @param startPosition The position relative to the world's x and y axis
	 * @param laneSegments All the laneSegments that this lane contains
	 */
	public Lane(IVector startPosition, ILaneSegmentLinear laneSegmentLinear,
			float laneWidth) 
		throws IllegalArgumentException {
		this.startPosition = startPosition;
		this.startSegment = laneSegmentLinear;
		//this.laneSegments = laneSegments;
		this.initializeLaneSegments();
		this.laneWidth = laneWidth;
		
		this.wayPoints = new ArrayList<SignWayPoint>();
	}
	
	/**
	 * Return the lane width
	 * @return
	 */
	public float getLaneWidth() {
		return laneWidth;
	}

	/**
	 * Set the lane width
	 * @param laneWidth
	 */
	public void setLaneWidth(float laneWidth) {
		this.laneWidth = laneWidth;
	}

	
	/**
	 * {@inheritDoc}
	 */
	public IVector getVehiclePosition(float drivenDistance) throws LaneLengthExceededException {
		if (drivenDistance > this.length) {
		//System.out.println("you have to change lane now!");
			throw new LaneLengthExceededException();
		}
		ILaneSegment<?> segment = this.getLaneSegmentAtPosition(drivenDistance);
		float position = getLengthBeforeSegment(segment);
		float segmentLength = drivenDistance - position;
		IVector actualPosition = segment.getVehiclePosition(segmentLength);
		
		//System.out.println("Lanesegment position... "+ segment.getStartPoint());
		/*
	//System.out.println("Actual position" + actualPosition);
	//System.out.println("Lanesegment length: " + segment.getLength());*/
		return actualPosition;
	}
	
	/**
	 * {@inheritDoc}
	 */
	/*
	public List<IWayPoint> getWayPointsAtPosition(float lanePosition, float viewField) {
		ILaneSegment<?> laneSegment;
		laneSegment = this.getLaneSegmentAtPosition(lanePosition);
		List<IWayPoint> wayPoints = new ArrayList<IWayPoint>();
		
		for (float i = viewField; i > 0;) {
			wayPoints.addAll(laneSegment.getSignWayPoints(viewField));
			
			if (laneSegment.getLength() < viewField) {
				viewField -= laneSegment.getLength();
				laneSegment = (LaneSegment<?>) laneSegment.getNextLaneSegment();
			}
		}
		
		return wayPoints;
	}
	*/
	
	/**
	 * Return the lane segment's distance on the lane
	 * 
	 * @param segment
	 * @return
	 */
	private float getLengthBeforeSegment(ILaneSegment<?> segment) {
		return segment.getDistanceOnLane();
	}
	
	/**
	 * Do some initialization with the lane segments
	 * 
	 * - Determine the first lane segment
	 * - Count the length of all lane segments
	 * - Add the lane positions to the helper array (see {@link #laneSegmentDistances})
	 * - Add the lane positions to the association hash map (see {@link #lanePositions})
	 */
	private void initializeLaneSegments() {
		List<Float> segments = new ArrayList<Float>();
		
		ILaneSegment<?> curSeg = this.startSegment;
		List<Float> laneSegmentDistances = new ArrayList<Float>();
		while (curSeg != null) {
			float laneLength = curSeg.getLength();
			
			laneSegmentDistances.add(this.length);
			
			this.lanePositions.put(this.length, curSeg);
			curSeg.setDistanceOnLane(this.length);
			curSeg = (ILaneSegment<?>) curSeg.getNextLaneSegment();
			
			this.length += laneLength;
		}
		
		int pos = 0;
		this.laneSegmentDistances = new Float[laneSegmentDistances.size()];
		for (Float elem: laneSegmentDistances) {
			this.laneSegmentDistances[pos] = elem;
			pos++;
		}
		
		//segments.toArray(this.laneSegmentDistances);
	}
	
	/**
	 * Wrapper method for seeking the lane segment
	 * 
	 * @param lanePosition
	 * @see #getLanePositionKey(int, int, float)
	 * @return lane segment the car is on
	 */
	private ILaneSegment<?> getLaneSegmentAtPosition(float lanePosition) {
		float key = this.getLanePositionKey(0, this.laneSegmentDistances.length - 1, lanePosition);
		return this.lanePositions.get(key);
	}

	/**
	 * Recursive function to seek the lane segment position
	 * 
	 * @see #lanePositions
	 * @see #laneSegmentDistances
	 * @see #getLaneSegmentAtPosition(float)
	 * @param start The start of the array to look for 
	 * @param end The end of the array to look for
	 * @param seek The value we're looking for
	 * @return position
	 */
	private float getLanePositionKey(int start, int end, double seek) {
		int middle = (int)Math.ceil((start + end) / 2.0);
		
		if (((double)this.laneSegmentDistances[middle]) == seek) {
			return this.laneSegmentDistances[middle];
			
		} else if (end - start == 1) {
			if (seek < this.laneSegmentDistances[end]) {
				return this.laneSegmentDistances[start];
			} else {
				return this.laneSegmentDistances[end];
			}
			
		} else if (start == middle){
			return this.laneSegmentDistances[start];
		} else if ((double)this.laneSegmentDistances[middle] > seek) {
			return this.getLanePositionKey(start, middle, seek);
			
		} else if (start == middle){
			return this.laneSegmentDistances[start];
		}else {
			return this.getLanePositionKey(middle, end, seek);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IVector getStartPosition() { 
		return this.startPosition.clone();
	}

	/**
	 * returns the start segment
	 * @return ILaneSegment<?>
	 */
	@Override
	public ILaneSegment<?> getFirstILaneSegment() {
		return this.startSegment;
	}
	
	/**
	 * get the last segment
	 * @return ILaneSegment<?>
	 */
	public ILaneSegment<?> getLastILaneSegment(){
		if (this.endSegment == null){
			Boolean hasNext = true;
			LaneSegment nextSeg = (LaneSegment) this.startSegment;
			while(hasNext){
				if (nextSeg.getNextLaneSegment() != null){
					nextSeg = (LaneSegment) nextSeg.getNextLaneSegment();
				}else{
					this.endSegment = nextSeg;
					hasNext = false;
				}
			}
		}
		return this.endSegment;
	}

	/**
	 * setter for this lanes junction
	 */
	@Override
	public void setJunction(IJunction junction) {
		this.junction = junction;
	}

	/**
	 * getter for this lanes junction
	 * @return IJunction 
	 */	@Override
	public IJunction getJunction() {
		return this.junction;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<IVector[]> getLanePath (){
		ArrayList<IVector[]> output = new ArrayList<IVector[]> ();
		LaneSegment<?> actualSegment = (LaneSegment<?>) startSegment;
		while (actualSegment != null){
			output.add(actualSegment.getBezierPoints());
			actualSegment = (LaneSegment<?>) actualSegment.getNextLaneSegment();
		}
		return output;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLength() {
		return this.length;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWayPoint(SignWayPoint wp) {
		this.wayPoints.add(wp);
		try {
			WayPointManager.getInstance().add(wp);
		} catch (Exception e) {
			e.printStackTrace(); // not sure what this exception is about
		}
	}
}
