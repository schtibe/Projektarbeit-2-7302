package test.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import simulation.builder.IXMLRoadSegmentBuilder;
import simulation.builder.InvalidXMLException;
import simulation.builder.XMLLaneBuilder;
import simulation.builder.XMLLaneBuilder.directionType;

import common.IVector;
import common.Vector;

import environment.ILane;
import environment.ILaneSegment;
import environment.ILaneSegmentLinear;
import environment.ILaneSegmentQuadratic;
import environment.Lane;
import environment.LaneSegmentLinear;
import environment.LaneSegmentQuadratic;
import environment.SignWayPoint;

public class XMLLaneBuilderTest {
	
	private Element elem;
	
	private TestLaneBuilder lane;
	
	/**
	 * A extended version of the lane builder with wrappers for private methods
	 */
	private class TestLaneBuilder extends XMLLaneBuilder {
		public TestLaneBuilder(Element e, IVector roadPosition) {
			super(e, roadPosition);
		}

		public LaneSegmentQuadratic
			getCurveBetweenSegmentsWrapper( 
					ILaneSegmentLinear laneSegment1,
					ILaneSegmentLinear laneSegment2
				) throws InvalidXMLException {
			return this.getCurveBetweenSegments(laneSegment1, laneSegment2);
		}
		
		public ILaneSegmentLinear
			getCompleteLaneSegmentListWrapper(
					List<ILaneSegmentLinear> laneSegments
				) throws InvalidXMLException {
			return this.getCompleteLaneSegmentList(laneSegments);
		}
	}

	@Before
	public void setUp() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		
		String data = 	"<root>" +
						"	<lane id=\"1\" width=\"5\" />" +
						"</root>";
		Document document = builder.build(
				new ByteArrayInputStream(data.getBytes())
		);

		Element root = document.getRootElement();
		this.elem = root.getChild("lane");
		
		this.lane = new TestLaneBuilder(
				elem, new Vector(new float[]{2.0F, 3.0F})) {

		};
	}

	@Test
	public void testSetLaneWidth() {
		this.lane.setLaneWidth(10);
		assertEquals(10, this.lane.getLaneWidth(), 0.1);
	}

	@Test
	public void testGetLaneWidth() {
		assertEquals(5, this.lane.getLaneWidth(), 0.1); 
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateLaneException() 
	throws IllegalArgumentException, InvalidXMLException {
		List<IXMLRoadSegmentBuilder> roadSegments = 
			new ArrayList<IXMLRoadSegmentBuilder>();
		
		this.lane.createLane(roadSegments, 1);
	}
	
	@Test
	public void testCreateLane() {
		List<IXMLRoadSegmentBuilder> roadSegments = 
			new ArrayList<IXMLRoadSegmentBuilder>();
		roadSegments.add(new IXMLRoadSegmentBuilder() {
			@Override
			public void setStartPoint(IVector startPoint) {
				
			}
			
			@Override
			public void setEndPoint(IVector endPoint) {

				
			}
			/*
			@Override
			public List<SignWayPoint> getWayPoints(directionType direction) {
				return null;
			}
			// obviously doesn't exist anymore
			*/
			
			@Override
			public IVector getStartPoint() {
				return null;
			}
			
			@Override
			public IVector getEndPoint() {
				return null;
			}
			
			@Override
			public ILaneSegmentLinear createLaneSegment(float offset, 
					directionType direction) 
				throws Exception {
				return new ILaneSegmentLinear() {
					
					@Override
					public void setDistanceOnLane(float distanceOnLane) {
						
					}
					
					@Override
					public IVector getVehiclePosition(float segmentLength) {
						return null;
					}
					
					@Override
					public IVector getStartPoint() {
						return new Vector(new float[]{10, 20});
					}
					
					/* doesn't exist anymore apparently
					@Override
					public List<SignWayPoint> getSignWayPoints(float distance) {
						return null;
					}
					
					*/
					
					@Override
					public LaneSegmentQuadratic getNextLaneSegment() {
						return null;
					}
					
					@Override
					public float getLength() {
						return 0;
					}
					
					@Override
					public IVector getEndPoint() {
						return null;
					}
					
					@Override
					public float getDistanceOnLane() {
						return 0;
					}
					
					/*
					 apparently doesn't exist anymore
					@Override
					public void addSignWayPoints(
							List<SignWayPoint> signWayPoints) {
						
					}
					*/

					@Override
					public void setNextLaneSegment(
							ILaneSegmentQuadratic LaneSegment) {
						
					}

					@Override
					public IVector[] getBezierPoints() {
						return null;
					}

					@Override
					public int getId() {
						return 0;
					}

					@Override
					public void setId(int id) {
						
					}

					@Override
					public List<SignWayPoint> getAllWayPoints() {
						return null;
					}

					@Override
					public IVector getPointOnCurve(float middle) {
						// TODO Auto-generated method stub
						return null;
					}
				};
			}

			@Override
			public int getOrder() {
				return 0;
			}

			@Override
			public int compareTo(IXMLRoadSegmentBuilder o) {
				return 0;
			}	
		});
		
		ILane lane = null;
		try {
			lane = this.lane.createLane(roadSegments, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(lane instanceof Lane);

		List<LaneSegmentLinear> laneSegments = 
			new ArrayList<LaneSegmentLinear>();
		
		assertEquals(10, lane.getStartPosition().getComponent(0), 0.001);
		assertEquals(20, lane.getStartPosition().getComponent(1), 0.001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCompleteLaneSegmentListException() 
			throws InvalidXMLException {
		this.lane.getCompleteLaneSegmentListWrapper(null);
	}
	
	@Test
	public void testCompleteLaneSegmentList() throws Exception {
		List<ILaneSegmentLinear> laneSegments = 
			new ArrayList<ILaneSegmentLinear>();
		
		laneSegments.add(
			new LaneSegmentLinear(3, 
				new Vector(new float[] {10.0f, 30.2f}), 
				new Vector(new float[] {20.0f, 50f}) 
			)
		);
		
		laneSegments.add(
				new LaneSegmentLinear(3, 
					new Vector(new float[] {50f, 40f}), 
					new Vector(new float[] {80f, 10f})
				)
			);
		
		ILaneSegmentLinear firstSegment = null;
		try {
			firstSegment = this.lane.getCompleteLaneSegmentListWrapper(
					laneSegments);
		} catch (InvalidXMLException e) {
			e.printStackTrace();
		}
		
		assertTrue(firstSegment instanceof LaneSegmentLinear);
		ILaneSegment<?> curSeg = firstSegment;
		int counter = 0;
		while (curSeg != null) {
			if (counter % 2 == 0) {
				assertTrue(curSeg instanceof LaneSegmentLinear);
			} else {
				assertTrue(curSeg instanceof LaneSegmentQuadratic);
			}
			
			counter++;
			curSeg = (ILaneSegment<?>) curSeg.getNextLaneSegment();
		}
		
		assertEquals(3, counter);
	}

	@Test
	public void testGetCurveBetweenSegments() {
		LaneSegmentQuadratic segmentBetween = null;
		try {
			segmentBetween = 
				this.lane.getCurveBetweenSegmentsWrapper(
						new ILaneSegmentLinear() {

							@Override
							public float getDistanceOnLane() {
								return 0;
							}

							@Override
							public IVector getEndPoint() {
								return new Vector(new float[]{
										10f,
										10f,
								});
							}

							@Override
							public float getLength() {
								return 0;
							}

							@Override
							public LaneSegmentQuadratic getNextLaneSegment() {
								return null;
							}

							/*
							@SuppressWarnings("unchecked")
							@Override
							public List getSignWayPoints(float distance) {
								return null;
							}
							*/

							@Override
							public IVector getStartPoint() {
								return new Vector(new float[]{
										0f,
										0f
								});
							}

							@Override
							public IVector getVehiclePosition(
									float segmentLength) {
								return null;
							}

							@Override
							public void setDistanceOnLane(
									float distanceOnLane) {
								
							}

							/*
							@Override
							public void addSignWayPoints(
									List<SignWayPoint> signWayPoints) {
								
							}
							*/

							@Override
							public void setNextLaneSegment(
									ILaneSegmentQuadratic LaneSegment) {
							}

							@Override
							public IVector[] getBezierPoints() {
								return null;
							}

							@Override
							public int getId() {
								return 0;
							}

							@Override
							public void setId(int id) {
								
							}

							@Override
							public List<SignWayPoint> getAllWayPoints() {
								return null;
							}

							@Override
							public IVector getPointOnCurve(float middle) {
								// TODO Auto-generated method stub
								return null;
							}

						}, 
						new ILaneSegmentLinear() {

							/*
							@Override
							public void addSignWayPoints(
									List<SignWayPoint> signWayPoints) {
								
							}
							*/

							@Override
							public float getDistanceOnLane() {
								return 0;
							}

							@Override
							public IVector getEndPoint() {
								return new Vector(new float[] {
										40f,
										15f
								});
							}

							@Override
							public float getLength() {
								return 0;
							}

							@Override
							public LaneSegmentQuadratic getNextLaneSegment() {
								return null;
							}

							/*
							@Override
							public List<SignWayPoint> getSignWayPoints(
									float distance) {
								return null;
							}
							*/

							@Override
							public IVector getStartPoint() {
								return new Vector(new float[]{
										30f,
										15f,
								});
							}

							@Override
							public IVector getVehiclePosition(
									float segmentLength) {
								return null;
							}

							@Override
							public void setDistanceOnLane(float distanceOnLane) {
								
							}

							@Override
							public void setNextLaneSegment(
									ILaneSegmentQuadratic LaneSegment) {	
							}

							@Override
							public IVector[] getBezierPoints() {
								return null;
							}

							@Override
							public int getId() {
								return 0;
							}

							@Override
							public void setId(int id) {
								
							}

							@Override
							public List<SignWayPoint> getAllWayPoints() {
								return null;
							}

							@Override
							public IVector getPointOnCurve(float middle) {
								// TODO Auto-generated method stub
								return null;
							}
						}
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(10.0, segmentBetween.getStartPoint().getComponent(0), 0.1);
		assertEquals(10.0, segmentBetween.getStartPoint().getComponent(1), 0.1);
		assertEquals(30.0, segmentBetween.getEndPoint().getComponent(0), 0.1);
		assertEquals(15.0, segmentBetween.getEndPoint().getComponent(1), 0.1);
		assertEquals(15.0, segmentBetween.getBendPoint().getComponent(0), 0.1);
		assertEquals(15.0, segmentBetween.getBendPoint().getComponent(1), 0.1);
	}

	@Test(expected=InvalidXMLException.class)
	public void testGetCurveBetweenCrossingSegments() throws InvalidXMLException {
//System.out.println("normal test");

		LaneSegmentQuadratic segmentBetween = 
			this.lane.getCurveBetweenSegmentsWrapper(
					new ILaneSegmentLinear() {

						@Override
						public float getDistanceOnLane() {
							return 0;
						}

						@Override
						public IVector getEndPoint() {
							return new Vector(new float[]{
									1400f,
									1000f,
							});
						}

						@Override
						public float getLength() {
							return 0;
						}

						@Override
						public LaneSegmentQuadratic getNextLaneSegment() {
							return null;
						}

						/*
						@SuppressWarnings("unchecked")
						@Override
						public List getSignWayPoints(float distance) {
							return null;
						}
						*/

						@Override
						public IVector getStartPoint() {
							return new Vector(new float[]{
									1400f,
									400f
							});
						}

						@Override
						public IVector getVehiclePosition(
								float segmentLength) {
							return null;
						}

						@Override
						public void setDistanceOnLane(
								float distanceOnLane) {
							
						}

						/*
						@Override
						public void addSignWayPoints(
								List<SignWayPoint> signWayPoints) {
							
						}
						*/

						@Override
						public void setNextLaneSegment(
								ILaneSegmentQuadratic LaneSegment) {
						}

						@Override
						public IVector[] getBezierPoints() {
							return null;
						}

						@Override
						public int getId() {
							return 0;
						}

						@Override
						public void setId(int id) {
							
						}

						@Override
						public List<SignWayPoint> getAllWayPoints() {
							return null;
						}

						@Override
						public IVector getPointOnCurve(float middle) {
							// TODO Auto-generated method stub
							return null;
						}

					}, 
					new ILaneSegmentLinear() {

						/*
						@Override
						public void addSignWayPoints(
								List<SignWayPoint> signWayPoints) {
							
						}
						*/

						@Override
						public float getDistanceOnLane() {
							return 0;
						}

						@Override
						public IVector getEndPoint() {
							return new Vector(new float[] {
									1750f,
									2100f
							});
						}

						@Override
						public float getLength() {
							return 0;
						}

						@Override
						public LaneSegmentQuadratic getNextLaneSegment() {
							return null;
						}

						/*
						@Override
						public List<SignWayPoint> getSignWayPoints(
								float distance) {
							return null;
						}
						*/

						@Override
						public IVector getStartPoint() {
							return new Vector(new float[]{
									1502f,
									1100f,
							});
						}

						@Override
						public IVector getVehiclePosition(
								float segmentLength) {
							return null;
						}

						@Override
						public void setDistanceOnLane(float distanceOnLane) {
							
						}

						@Override
						public void setNextLaneSegment(
								ILaneSegmentQuadratic LaneSegment) {	
						}

						@Override
						public IVector[] getBezierPoints() {
							return null;
						}

						@Override
						public int getId() {
							return 0;
						}

						@Override
						public void setId(int id) {
							
						}

						@Override
						public List<SignWayPoint> getAllWayPoints() {
							return null;
						}

						@Override
						public IVector getPointOnCurve(float middle) {
							// TODO Auto-generated method stub
							return null;
						}
					}
			);
	}

	@Test(expected=InvalidXMLException.class)
	public void testGetCurveBetweenCrossingSegmentsReversed() throws InvalidXMLException {
	//System.out.println("reversed test");

		LaneSegmentQuadratic segmentBetween = 
			this.lane.getCurveBetweenSegmentsWrapper(
					new ILaneSegmentLinear() {

						@Override
						public float getDistanceOnLane() {
							return 0;
						}

						@Override
						public IVector getEndPoint() {
							return new Vector(new float[]{
									1750,
									2100f,
							});
						}

						@Override
						public float getLength() {
							return 0;
						}

						@Override
						public LaneSegmentQuadratic getNextLaneSegment() {
							return null;
						}

						/*
						@SuppressWarnings("unchecked")
						@Override
						public List getSignWayPoints(float distance) {
							return null;
						}
						*/

						@Override
						public IVector getStartPoint() {
							return new Vector(new float[]{
									1502,
									1100f
							});
						}

						@Override
						public IVector getVehiclePosition(
								float segmentLength) {
							return null;
						}

						@Override
						public void setDistanceOnLane(
								float distanceOnLane) {
							
						}

						/*
						@Override
						public void addSignWayPoints(
								List<SignWayPoint> signWayPoints) {
							
						}
						*/

						@Override
						public void setNextLaneSegment(
								ILaneSegmentQuadratic LaneSegment) {
						}

						@Override
						public IVector[] getBezierPoints() {
							return null;
						}

						@Override
						public int getId() {
							return 0;
						}

						@Override
						public void setId(int id) {
							
						}

						@Override
						public List<SignWayPoint> getAllWayPoints() {
							return null;
						}

						@Override
						public IVector getPointOnCurve(float middle) {
							// TODO Auto-generated method stub
							return null;
						}

					}, 
					new ILaneSegmentLinear() {

						/*
						@Override
						public void addSignWayPoints(
								List<SignWayPoint> signWayPoints) {
							
						}
						*/

						@Override
						public float getDistanceOnLane() {
							return 0;
						}

						@Override
						public IVector getEndPoint() {
							return new Vector(new float[] {
									1400f,
									1000f
							});
						}

						@Override
						public float getLength() {
							return 0;
						}

						@Override
						public LaneSegmentQuadratic getNextLaneSegment() {
							return null;
						}

						/*
						@Override
						public List<SignWayPoint> getSignWayPoints(
								float distance) {
							return null;
						}
						*/

						@Override
						public IVector getStartPoint() {
							return new Vector(new float[]{
									1400f,
									400f,
							});
						}

						@Override
						public IVector getVehiclePosition(
								float segmentLength) {
							return null;
						}

						@Override
						public void setDistanceOnLane(float distanceOnLane) {
							
						}

						@Override
						public void setNextLaneSegment(
								ILaneSegmentQuadratic LaneSegment) {	
						}

						@Override
						public IVector[] getBezierPoints() {
							return null;
						}

						@Override
						public int getId() {
							return 0;
						}

						@Override
						public void setId(int id) {
							
						}

						@Override
						public List<SignWayPoint> getAllWayPoints() {
							return null;
						}

						@Override
						public IVector getPointOnCurve(float middle) {
							// TODO Auto-generated method stub
							return null;
						}
					}
			);
	}
	
}
