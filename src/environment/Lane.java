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
	 * The last lane segment
	 * 
	 * If needed the last segment can be found and put in 
	 * this property variable so it doesn't have to be looked for twice.
	 */
	private LaneSegment<?> endSegment;
	
	/**
	 * Start position relative to the world's x and y axis
	 */
	private IVector startPosition;
	
	/**
	 * The width of the lane
	 */
	private float laneWidth;
	
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
	
	/**
	 * Assumingly the next junction from the lane
	 */
	private IJunction junction;

	/**
	 * The way points on the lane
	 */
	private ArrayList<SignWayPoint> wayPoints;
	
	/**
	 * Whether a vehicle is placable or not
	 */
	protected boolean vehiclePlacable = true;
	
	/**
	 * Construct
	 * 
	 * @param startPosition      The position relative to the world's x and y axis
	 * @param laneSegmentLinear  The first lane segment on this lane
	 * @param laneWidth          The width of the lane
	 * @param vehiclePlacable    Whether vehicles can be placed on this lane
	 */
	public Lane(
			IVector startPosition, 
			ILaneSegmentLinear laneSegmentLinear,
			float laneWidth,
			boolean vehiclePlacable
		) throws IllegalArgumentException {
		this.startPosition = startPosition;
		this.startSegment = laneSegmentLinear;
		this.initializeLaneSegments();
		this.laneWidth = laneWidth;
		this.vehiclePlacable = vehiclePlacable;
		
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

	@Override
	public IVector getPositionOnLane(float drivenDistance) throws LaneLengthExceededException {
		if (drivenDistance > this.length) {
			throw new LaneLengthExceededException();
		}
		ILaneSegment<?> segment = this.getLaneSegmentAtPosition(drivenDistance);
		float position = getLengthBeforeSegment(segment);
		float segmentLength = drivenDistance - position;
		IVector actualPosition = segment.getVehiclePosition(segmentLength);
		
		return actualPosition;
	}


	@Override
	public IVector getPositionOnLaneByPercentage(float position)
			throws LaneLengthExceededException {
		float absolutePos = this.length / 100 * position;
		
		return this.getPositionOnLane(absolutePos);
	}
	
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
	 * Do some initialisation with the lane segments
	 * 
	 * - Determine the first lane segment
	 * - Count the length of all lane segments
	 * - Add the lane positions to the helper array (see {@link #laneSegmentDistances})
	 * - Add the lane positions to the association hash map (see {@link #lanePositions})
	 */
	private void initializeLaneSegments() {
		
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

	@Override
	public IVector getStartPosition() { 
		return this.startPosition.clone();
	}

	@Override
	public ILaneSegment<?> getFirstILaneSegment() {
		return this.startSegment;
	}
	
	@Override
	public ILaneSegment<?> getLastILaneSegment(){
		if (this.endSegment == null){
			Boolean hasNext = true;
			LaneSegment<?> nextSeg = (LaneSegment<?>) this.startSegment;
			while(hasNext){
				if (nextSeg.getNextLaneSegment() != null){
					nextSeg = (LaneSegment<?>) nextSeg.getNextLaneSegment();
				}else{
					this.endSegment = nextSeg;
					hasNext = false;
				}
			}
		}
		return this.endSegment;
	}

	@Override
	public void setJunction(IJunction junction) {
		this.junction = junction;
	}

	@Override
	public IJunction getJunction() {
		return this.junction;
	}
	
	@Override
	public List<IVector[]> getLanePath (){
		ArrayList<IVector[]> output = new ArrayList<IVector[]> ();
		LaneSegment<?> actualSegment = (LaneSegment<?>) startSegment;
		while (actualSegment != null){
			output.add(actualSegment.getBezierPoints());
			actualSegment = (LaneSegment<?>) actualSegment.getNextLaneSegment();
		}
		return output;
	}

	@Override
	public float getLength() {
		return this.length;
	}

	@Override
	public void addWayPoint(SignWayPoint wp) {
		this.wayPoints.add(wp);
		try {
			WayPointManager.getInstance().add(wp);
		} catch (Exception e) {
			e.printStackTrace(); // not sure what this exception is about
		}
	}

	@Override
	public boolean vehiclePlacable() {
		return this.vehiclePlacable;
	}

}
