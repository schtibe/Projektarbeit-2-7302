package environment;

import java.util.ArrayList;
import java.util.List;

import simulation.builder.InvalidXMLException;
import simulation.builder.LaneBuilder;

import common.Vector;

import driver.IDecision;

public class CrossRoads implements IJunction {

	/**
	 * instance variables
	 */
	
	private List<IRoad> roads;
	private Boolean[] startPointInCrossroad;
	private List<ILane>[] incomingLanes;
	private List<ILane>[] outgoingLanes;
	private List<ILane> junctionLanes;
	private List<ILane> decisionsMap;
	private List<List<IJunctionDecision>> decisions;

	/**
	 * constructor initializes some important collections
	 */
	
	public CrossRoads(){
		this.junctionLanes = new ArrayList<ILane>();
		this.decisionsMap = new ArrayList<ILane>();
		this.decisions = new ArrayList<List<IJunctionDecision>>();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IJunctionDecision> getPossibilities(ILane actualLane) {
		return decisions.get(this.decisionsMap.indexOf(actualLane));
	}
	
	/**
	 * {@inheritedDoc}
	 */
	
	@Override
	public void setRoads(List<IRoad> roads) throws Exception{
		this.roads = roads;
		roadsToLanes();
		LaneBuilder builder = new LaneBuilder();	
		int decisionCount = 0;
		for (int i=0;i<this.incomingLanes.length;i++){
			if(this.incomingLanes[i]!=null){
				for (ILane lane: this.incomingLanes[i]) {
					if (!this.decisionsMap.contains(lane)){
						this.decisionsMap.add(lane);
					}
					int laneIdx = this.decisionsMap.indexOf(lane);
					LaneSegmentLinear start = (LaneSegmentLinear) lane.getLastILaneSegment();
					for (int k=0;k<this.outgoingLanes.length;k++){
						if (this.outgoingLanes[k]!=null && k!= i){
							for (ILane outLane: this.outgoingLanes[k]) {
								if (outLane != null){
									List<ILane> theseLanes = makeJunctionLanes(
											builder, start, outLane);
									decisionCount++;
									IJunctionDecision decision = new JunctionDecision(theseLanes,decisionCount,this);
									try{
										decisions.get(laneIdx);
									}catch(Exception e){
										decisions.add(laneIdx,new ArrayList<IJunctionDecision>());
									}
									//makeJunctionWayPoint(lane);
									decisions.get(laneIdx).add(decision);
								}
							}
						}
					}
					makeJunctionWayPoint(lane);
				}
			}
		}
	}
	
	/**
	 * creates a way point for this junction on th eprovided lane
	 * @param lane
	 * @throws Exception
	 */
	
	private void makeJunctionWayPoint(ILane lane) throws Exception {
		JunctionWayPoint wp = new JunctionWayPoint(lane,this);
		WayPointManager.getInstance().add(wp);
	}

	/**
	 * creates the junction lanes for an incoming lane
	 * @param builder
	 * @param start
	 * @param outLane
	 * @return
	 * @throws Exception
	 * @throws InvalidXMLException
	 */
	
	
	private List<ILane> makeJunctionLanes(LaneBuilder builder,
			LaneSegmentLinear start, ILane outLane) throws Exception,
			InvalidXMLException {
		outLane.setJunction(this);
		LaneSegmentLinear end = (LaneSegmentLinear) outLane.getFirstILaneSegment();
		Vector startVector = ((Vector) start.getEndPoint().sub(start.getStartPoint())).normalize();
		Vector endVector = ((Vector) end.getEndPoint().sub(end.getStartPoint())).normalize();
		List<ILaneSegmentLinear> segments = new ArrayList<ILaneSegmentLinear>();
		segments.add(new LaneSegmentLinear(1,start.getEndPoint(),((Vector)start.getEndPoint()).add(startVector)));
		segments.add(new LaneSegmentLinear(1,((Vector) end.getStartPoint()).sub(endVector), end.getStartPoint()));
		Lane newLane = null;
		try{
			newLane = new Lane(start.getEndPoint(),builder.getCompleteLaneSegmentList(segments),(float)10.0);
		}catch(InvalidXMLException e){
		//System.out.println(e.toString()+", just assumed now they're perfectly parallel and continuous");
			segments = new ArrayList<ILaneSegmentLinear>();
			segments.add(new LaneSegmentLinear(1,start.getEndPoint(),end.getStartPoint()));
			newLane = new Lane(start.getEndPoint(),builder.getCompleteLaneSegmentList(segments),(float)10.0);
		}
		this.junctionLanes.add(newLane);
		List<ILane> theseLanes = new ArrayList<ILane>();
		theseLanes.add(newLane);
		theseLanes.add(outLane);
		return theseLanes;
	}

	/**
	 * separates the incoming from the outgoing lanes
	 * @throws Exception
	 */
	
	private void roadsToLanes() throws Exception {
		Object[] roadsAsArray = this.roads.toArray();
		startPointInCrossroad = findPointsInCrossroad(roadsAsArray);
		this.incomingLanes = new List[startPointInCrossroad.length];
		this.outgoingLanes = new List[startPointInCrossroad.length];
		for (int i=0;i<startPointInCrossroad.length;i++){
			if (startPointInCrossroad[i]){
				this.incomingLanes[i] = ((ITrafficCarrier)roadsAsArray[i]).getLeftLanes();
				this.outgoingLanes[i] = ((ITrafficCarrier)roadsAsArray[i]).getRightLanes();
			}else{
				this.incomingLanes[i] = ((ITrafficCarrier)roadsAsArray[i]).getRightLanes();
				this.outgoingLanes[i] = ((ITrafficCarrier)roadsAsArray[i]).getLeftLanes();
			}
		}
	}

	/**
	 * @return Boolean[] , true means the startPoint of the Road with index i is to be 
	 * connected to the crossroads, false means the same for the endPoint
	 */
	private Boolean[] findPointsInCrossroad(Object[] roadsAsArray) throws Exception{
		if (roadsAsArray.length > 1 || roadsAsArray.length > 4) {
			Boolean[] output = new Boolean[roadsAsArray.length];
			for (int i = 0; i < roadsAsArray.length; i++) {
				if (i == 0) {
					// stay idle

				}else if(i == 1){
					firstTwoPointsInCrossroad((IRoad)roadsAsArray[0], (IRoad)roadsAsArray[1],output);
				}else {
					if (output[i - 1]) {
						float toStart = ((IRoad)roadsAsArray[(i - 1)])
								.getStartPoint().sub(
										((IRoad)roadsAsArray[i]).getStartPoint()).norm();
						float toEnd = ((IRoad)roadsAsArray[(i - 1)])
								.getStartPoint().sub(
										((IRoad)roadsAsArray[i]).getEndPoint()).norm();
						output[i] = endOrStart(toEnd,toStart);
					} else {
						float toEnd = ((IRoad)roadsAsArray[(i - 1)]).getEndPoint()
								.sub(((IRoad)roadsAsArray[i]).getEndPoint()).norm();
						float toStart = ((IRoad)roadsAsArray[(i - 1)]).getEndPoint()
								.sub(((IRoad)roadsAsArray[i]).getStartPoint()).norm();
						output[i] = endOrStart(toEnd,toStart);
					}
				}
			}
			return output;
		}else{
			throw new Exception ("a crossroad needs more than one road and less than five");
		}
	}
	
	/**
	 * @param toEnd: length to a neighbouring endPoint,
	 * toStart: length to a neighbouring startPoint
	 * @return true if toStart is larger than toEnd
	 */
	
	private Boolean endOrStart(float toEnd, float toStart){
		if (toEnd < toStart){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @param Road roadOne, Road roadTwo
	 * @return Boolean[] following the specification given at the method findPointsInCrossroad 
	 */
	
	private void firstTwoPointsInCrossroad(IRoad roadOne, IRoad roadTwo,Boolean[] output) {
		//Boolean[] output = new Boolean[2];
		float endToEnd = roadOne.getEndPoint().sub(roadTwo.getEndPoint()).norm();
		float endToStart = roadOne.getEndPoint().sub(roadTwo.getStartPoint()).norm();
		float startToEnd = roadOne.getStartPoint().sub(roadTwo.getEndPoint()).norm();
		float startToStart = roadOne.getStartPoint().sub(roadTwo.getStartPoint()).norm();
		if (endToEnd < startToStart) {
			if (endToEnd < startToEnd) {
				if (endToEnd < endToStart) {
					output[0] = false;
					output[1] = false;
				} else {
					output[0] = false;
					output[1] = true;
				}
			} else {
				if (startToEnd < endToStart) {
					output[0] = true;
					output[1] = false;
				} else {
					output[0] = false;
					output[1] = true;
				}
			}
		} else {
			if (startToStart < startToEnd) {
				if (startToStart < endToStart) {
					output[0] = true;
					output[1] = true;
				} else {
					output[0] = false;
					output[1] = true;
				}
			} else {
				if (startToEnd < endToStart) {
					output[0] = true;
					output[1] = false;
				} else {
					output[0] = false;
					output[1] = true;
				}
			}
		}
	}

	/**
	 * returns an empty list, as a crossroad only has right lanes
	 */
	
	@Override
	public List<ILane> getLeftLanes() {
		// TODO Auto-generated method stub
		return new ArrayList<ILane>();
	}

	/**
	 * returns the crossroads lanes
	 */
	
	@Override
	public List<ILane> getRightLanes() {
		return this.junctionLanes;
	}

	/**
	 * {@deprecated}
	 */
	
	@Override
	public List<ILane> getImportantLanes(IDecision decision) {
		// TODO Auto-generated method stub
		return null;
	}
}