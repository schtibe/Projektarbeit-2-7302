package test.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import simulation.builder.IXMLRoadSegmentBuilder;
import simulation.builder.IXMLWorldBuilder;
import simulation.builder.InvalidXMLException;
import simulation.builder.XMLLeftLaneBuilder;
import simulation.builder.XMLRightLaneBuilder;
import simulation.builder.XMLRoadBuilder;

import common.IVector;

import environment.IJunction;
import environment.IRoad;
import environment.IWayPoint;

/**
 * readRoadSegments() is deliberately not tested.
 * 
 */
public class XMLRoadBuilderTest {
	
	private class TestRoadBuilder extends XMLRoadBuilder { 
	
		public TestRoadBuilder(Element e, IXMLWorldBuilder worldBuilder)
				throws InvalidXMLException {
			super(e, worldBuilder);
		}
		
		public IVector getRoadPositionWrapper() {
			return this.getRoadPosition();
		}
		public List<IXMLRoadSegmentBuilder> getRoadSegmentsWrapper() {
			return this.getRoadSegments();
		}
		
		public List<IXMLRoadSegmentBuilder> getRoadSegmentsReversedWrapper() {
			return this.getRoadSegmentsReversed();
		}
		
		public List<XMLRightLaneBuilder> getRightLanesWrapper() {
			return this.getRightLanes();
		}
		
		public List<XMLLeftLaneBuilder> getLeftLanesWrapper() {
			return this.getLeftLanes();
		}
	}
	
	TestRoadBuilder rBuilder;

	@Before
	public void setUp() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		
		String data = 	
			"<root>" +
				"<road id=\"7\" startX=\"10\" startY=\"8\">" + 
					"<leftlanes>" + 
						"<lane id=\"1\" width=\"5\">" + 
						"</lane>" +
						"<lane id=\"2\" width=\"5\"/> " +
					"</leftlanes>" +
					"<rightlanes>" +
						"<lane id=\"3\" width=\"5\" />" +
					"</rightlanes>" +
					"<roadsegments>" +
						"<roadsegment order=\"1\" " +
						"startX=\"10\" startY=\"10\" endX=\"20\" endY=\"30\">" +
							"<waypoint id=\"1\" />" +
						"</roadsegment>" +
						"<roadsegment order=\"2\" " +
							"startX=\"25\" startY=\"35\" " +
							"endX=\"50\" endY=\"35\" /> " +
					"</roadsegments>" +
				"</road>" +
			"</root>";
		Document document = builder.build(
				new ByteArrayInputStream(data.getBytes())
		);

		Element root = document.getRootElement();
		Element elem = root.getChild("road");
		
		IXMLWorldBuilder wb = new IXMLWorldBuilder() {
			
			@Override
			public void setWayPoint(IWayPoint wayPoint) {
				
				
			}
			
			@Override
			public void setCoordinates(IVector coordinates) {
				
				
			}
			
			@Override
			public IVector[] getWorldBoundaries() {
				
				return null;
			}
			
			@Override
			public float getScale() {
				
				return 0;
			}
			
			@Override
			public List<IRoad> getRoads() {
				
				return null;
			}
			
			@Override
			public List<IJunction> getJunctions() {
				
				return null;
			}
			
			@Override
			public List<IWayPoint> getAllWayPoints() {
				
				return null;
			}
			
			@Override
			public void generate() throws Exception {
				
				
			}
		}; 
		
		this.rBuilder = new TestRoadBuilder(elem, wb);
	}

	@Test
	public void testReadRoadPosition() {
		IVector roadPosition = this.rBuilder.getRoadPositionWrapper();
		
		assertEquals(10, roadPosition.getComponent(0), 0.00001);
		assertEquals(8, roadPosition.getComponent(1), 0.00001);
	}
	

	@Test
	public void testGetRoad() {
		IRoad road = this.rBuilder.getRoad();
		
		assertTrue(road instanceof IRoad);
		
		assertEquals(2, road.getLeftLanes().size());
		assertEquals(1, road.getRightLanes().size());
		
		assertEquals(10, road.getStartPoint().getComponent(0), 0.001);
		assertEquals(8, road.getStartPoint().getComponent(1), 0.001);
	}

	@Test
	public void testGetRoadSegments() {
		//System.out.println("rightlanes");
		List<IXMLRoadSegmentBuilder> segments = 
			this.rBuilder.getRoadSegmentsWrapper();
		
		assertEquals(2, segments.size());
		assertEquals(1, segments.get(0).getOrder());
		assertEquals(2, segments.get(1).getOrder());
		
		assertEquals(20, segments.get(0).getStartPoint().getComponent(0), 0.001);
		assertEquals(18, segments.get(0).getStartPoint().getComponent(1), 0.001);
		assertEquals(30, segments.get(0).getEndPoint().getComponent(0), 0.001);
		assertEquals(38, segments.get(0).getEndPoint().getComponent(1), 0.001);
		
		assertEquals(35, segments.get(1).getStartPoint().getComponent(0), 0.001);
		assertEquals(43, segments.get(1).getStartPoint().getComponent(1), 0.001);
		assertEquals(60, segments.get(1).getEndPoint().getComponent(0), 0.001);
		assertEquals(43, segments.get(1).getEndPoint().getComponent(1), 0.001);
		
		/*
	//System.out.println(segments.get(0).getStartPoint() + " " + 
				segments.get(0).getEndPoint());
	//System.out.println(segments.get(1).getStartPoint() + " " + 
				segments.get(1).getEndPoint());
				*/
	}

	@Test
	public void testGetRoadSegmentsReversed() {
		//System.out.println("leftlanes");
		List<IXMLRoadSegmentBuilder> segments = 
			this.rBuilder.getRoadSegmentsReversedWrapper();
		
		assertEquals(2, segments.size());
		assertEquals(2, segments.get(0).getOrder());
		assertEquals(1, segments.get(1).getOrder());
		
		assertEquals(60, segments.get(0).getStartPoint().getComponent(0), 0.001);
		assertEquals(43, segments.get(0).getStartPoint().getComponent(1), 0.001);
		assertEquals(35, segments.get(0).getEndPoint().getComponent(0), 0.001);
		assertEquals(43, segments.get(0).getEndPoint().getComponent(1), 0.001);
		
		assertEquals(30, segments.get(1).getStartPoint().getComponent(0), 0.001);
		assertEquals(38, segments.get(1).getStartPoint().getComponent(1), 0.001);
		assertEquals(20, segments.get(1).getEndPoint().getComponent(0), 0.001);
		assertEquals(18, segments.get(1).getEndPoint().getComponent(1), 0.001);
		
		/*
	//System.out.println(segments.get(0).getStartPoint() + " " + 
				segments.get(0).getEndPoint());
	//System.out.println(segments.get(1).getStartPoint() + " " + 
				segments.get(1).getEndPoint());
				*/
	}

	@Test
	public void testGetRightLanes() {
		List<XMLRightLaneBuilder> rightLanes = this.rBuilder.getRightLanesWrapper();
		
		assertEquals(1, rightLanes.size());
	}

	@Test
	public void testGetLeftLanes() {
		List<XMLLeftLaneBuilder> leftLanes = this.rBuilder.getLeftLanesWrapper();
		
		assertEquals(2, leftLanes.size());
	}

}
