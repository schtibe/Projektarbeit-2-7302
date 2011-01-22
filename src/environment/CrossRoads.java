package environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import simulation.builder.InvalidXMLException;
import simulation.builder.LaneBuilder;

import common.IVector;
import common.Vector;

import driver.IDirection;
import driver.LeftDirection;
import driver.RightDirection;
import driver.StraightDirection;

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

	@Override
	public List<IJunctionDecision> getPossibilities(ILane actualLane) {
		return decisions.get(this.decisionsMap.indexOf(actualLane));
	}
	
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
								if (outLane != null) {
									List<ILane> theseLanes = makeJunctionLanes(
											builder, start, outLane);
									decisionCount++;
									IDirection dir = this.getDirectionForDecision(lane, outLane);
									IJunctionDecision decision = new JunctionDecision(theseLanes,decisionCount,this,dir);
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
	 * attributes a Direction object to a certain combination of lanes
	 * @param coming
	 * @param going
	 * @return IDirection instance
	 */
	
	private IDirection getDirectionForDecision (ILane coming,ILane going){
		IVector incomingStart = coming.getLastILaneSegment().getStartPoint();
		IVector incomingEnd = coming.getLastILaneSegment().getEndPoint();
		IVector goingStart = going.getFirstILaneSegment().getStartPoint();
		IVector goingEnd = going.getLastILaneSegment().getEndPoint();
		IVector turnDirection;
		if (goingStart.sub(incomingEnd).norm()<goingEnd.sub(incomingEnd).norm()){
			turnDirection = going.getFirstILaneSegment().getEndPoint().sub(goingStart);
		}else{
			turnDirection = going.getLastILaneSegment().getStartPoint().sub(goingEnd);
		}
		IVector incomingDirection = incomingEnd.sub(incomingStart);
		//System.out.println("in:"+incomingDirection.getAngle()+";out:"+turnDirection.getAngle());
		//float angle = incomingDirection.getAngle()-turnDirection.getAngle();
		float angle = incomingDirection.getAngle()-turnDirection.getAngle();
		if (angle < 0){
			angle += 2*Math.PI;
		}
		//System.out.println("angle:"+(angle/Math.PI)*180);
		if (angle <= Math.PI/8 || angle >= 15*Math.PI/8){
			return new StraightDirection();
		}else if (angle < 15*Math.PI/8 && angle > Math.PI){
			return new RightDirection();
		}else{
			return new LeftDirection();
		}
	}
	
	/**
	 * creates a way point for this junction on the provided lane
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
	 * @return the list of the lanes in the junction
	 * @throws Exception
	 * @throws InvalidXMLException
	 */
	private List<ILane> makeJunctionLanes(LaneBuilder builder,
			LaneSegmentLinear start, ILane outLane) throws Exception,
			InvalidXMLException {
		
		outLane.setJunction(this);
		LaneSegmentLinear end = (LaneSegmentLinear) outLane.getFirstILaneSegment();
		IVector startVector   = (start.getEndPoint().sub(start.getStartPoint())).normalize();
		IVector endVector     = (end.getEndPoint().sub(end.getStartPoint())).normalize();
		List<ILaneSegmentLinear> segments = new ArrayList<ILaneSegmentLinear>();
		
		segments.add(new LaneSegmentLinear(1,start.getEndPoint(),((Vector)start.getEndPoint()).add(startVector)));
		segments.add(new LaneSegmentLinear(1,((Vector) end.getStartPoint()).sub(endVector), end.getStartPoint()));
		
		Lane newLane = null;
		try {
			newLane = new Lane(start.getEndPoint(),builder.getCompleteLaneSegmentList(segments),(float)10.0, false);
		} catch(InvalidXMLException e) {
			segments = new ArrayList<ILaneSegmentLinear>();
			segments.add(new LaneSegmentLinear(1,start.getEndPoint(),end.getStartPoint()));
			newLane = new Lane(start.getEndPoint(),builder.getCompleteLaneSegmentList(segments),(float)10.0, false);
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
	 * true means the startPoint of the Road with index i is to be connected to the cross roads, 
	 * false means the same for the endPoint
	 * @return Boolean[]
	 * 
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
	 * @param length to a neighbouring endPoint,
	 * @param length to a neighbouring startPoint
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
	 * finds the first two points of a crossroad according to the provided roads
	 * @param roadOne
	 * @param roadTwo
	 * @param ouput, contains the values accoridng to spec in findPointsInCrossroad
	 * @TODO beautify: better return an array than fill it in the function
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
	 * returns an empty list, as a cross road only has right lanes
	 */
	@Override
	public List<ILane> getLeftLanes() {
		return new ArrayList<ILane>();
	}

	@Override
	public List<ILane> getRightLanes() {
		return this.junctionLanes;
	}

	@Override
	public IDirection comingFrom(ILane incoming, ILane going) {
		IVector incomingStart = incoming.getFirstILaneSegment().getStartPoint();
		IVector incomingEnd = incoming.getLastILaneSegment().getEndPoint();
		IVector goingStart = going.getFirstILaneSegment().getStartPoint();
		IVector goingEnd = going.getLastILaneSegment().getEndPoint();
		float endEnd = goingEnd.sub(incomingEnd).norm();
		float endStart = goingEnd.sub(incomingStart).norm();
		float startEnd = goingStart.sub(incomingEnd).norm();
		float startStart = goingStart.sub(incomingStart).norm();
		IVector goingDirection;
		IVector incomingDirection;
		//System.out.println("eE:"+endEnd+";eS:"+endStart+";sE:"+startEnd+";sS:"+startStart);
		if (endEnd < endStart){
			if (endEnd < startEnd){
				if (endEnd < startStart){
					goingDirection = going.getLastILaneSegment().getEndPoint().sub(
						going.getLastILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getLastILaneSegment().getStartPoint().sub(
						incoming.getLastILaneSegment().getEndPoint()
					);
					//System.out.println("eE is smallest");
				}else{
					goingDirection = going.getFirstILaneSegment().getEndPoint().sub(
						going.getFirstILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getFirstILaneSegment().getStartPoint().sub(
						incoming.getFirstILaneSegment().getEndPoint()
					);
					//System.out.println("sS is smallest");
				}
			}else{
				if (startEnd < startStart){
					goingDirection = going.getFirstILaneSegment().getEndPoint().sub(
							going.getFirstILaneSegment().getStartPoint()
						);
						incomingDirection = incoming.getLastILaneSegment().getStartPoint().sub(
							incoming.getLastILaneSegment().getEndPoint()
						);
						//System.out.println("sE is smallest"); 
				}else{
					goingDirection = going.getFirstILaneSegment().getEndPoint().sub(
						going.getFirstILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getFirstILaneSegment().getStartPoint().sub(
						incoming.getFirstILaneSegment().getEndPoint()
					);
					//System.out.println("sS is smallest");
				}
			}
		}else{
			if (endStart < startEnd){
				if (endStart < startStart){
					goingDirection = going.getLastILaneSegment().getEndPoint().sub(
						going.getLastILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getFirstILaneSegment().getStartPoint().sub(
						incoming.getFirstILaneSegment().getEndPoint()
					);
					//System.out.println("eS is smallest");
				}else{
					goingDirection = going.getFirstILaneSegment().getEndPoint().sub(
						going.getFirstILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getFirstILaneSegment().getStartPoint().sub(
						incoming.getFirstILaneSegment().getEndPoint()
					);
					//System.out.println("sS is smallest");
				}
			}else{
				if (startEnd < startStart){
					goingDirection = going.getFirstILaneSegment().getEndPoint().sub(
						going.getFirstILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getLastILaneSegment().getStartPoint().sub(
						incoming.getLastILaneSegment().getEndPoint()
					);
					//System.out.println("sE is smallest");
				}else{
					goingDirection = going.getFirstILaneSegment().getEndPoint().sub(
						going.getFirstILaneSegment().getStartPoint()
					);
					incomingDirection = incoming.getFirstILaneSegment().getStartPoint().sub(
						incoming.getFirstILaneSegment().getEndPoint()
					);
					//System.out.println("sS is smallest");
				}
			}
		}
		//System.out.println("in:"+incomingDirection.getAngle()+";out:"+goingDirection.getAngle());
		//float angle = incomingDirection.getAngle()-turnDirection.getAngle();
		float angle = incomingDirection.getAngle()-goingDirection.getAngle();
		if (angle < 0){
			angle += 2*Math.PI;
		}
		//System.out.println("angle:"+(angle/Math.PI)*180);
		if (angle <= Math.PI/8 || angle >= 15*Math.PI/8){
			return new StraightDirection();
		}else if (angle < 15*Math.PI/8 && angle > Math.PI){
			return new RightDirection();
		}else{
			return new LeftDirection();
		}
	}
	

	@Override
	public List<ILane> getRelevantLanes(ILane actualLane) {
		Set<ILane> relevant = new HashSet();
		relevant.addAll(this.junctionLanes);
		for (List<ILane> lanes :incomingLanes){
			if (!lanes.contains(actualLane)){
				relevant.addAll(lanes);
			}
		}
		List<ILane> output = new ArrayList<ILane>(relevant);
		return output;
	}
		
}
