package test.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import simulation.builder.XMLRoadSegmentBuilder;

import common.IVector;
import common.Vector;

import environment.LaneSegmentLinear;

public class XMLRoadSegmentBuilderTest {
	
	
	
	XMLRoadSegmentBuilder rsBuilder;

	@Before
	public void setUp() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		
		String data = 	
			"<root>" +
				"<roadsegment order=\"12\" " +
						"startX=\"10\" startY=\"12\" " +
						"endX=\"5\" endY=\"32\">" +
					"<waypoint id=\"1\" />" +
				"</roadsegment>" +
			"</root>";
		Document document = builder.build(
				new ByteArrayInputStream(data.getBytes())
		);

		Element root = document.getRootElement();
		Element elem = root.getChild("roadsegment");
		
		IVector roadPosition = new Vector(new float[]{
				10, 20
		});
		
		this.rsBuilder = new XMLRoadSegmentBuilder(elem, roadPosition);
	}
	
	@Test
	public void testGetStartPoint() {	
		IVector startPoint = this.rsBuilder.getStartPoint();
		assertEquals(20, startPoint.getComponent(0), 0.1);
		assertEquals(32, startPoint.getComponent(1), 0.1);
	}
	
	@Test
	public void testGetEndPoint() {
	
		IVector endPoint = this.rsBuilder.getEndPoint();
		assertEquals(15, endPoint.getComponent(0), 0.1);
		assertEquals(52, endPoint.getComponent(1), 0.1);
	}

	@Test
	public void testGetWaypoints() {
		// TODO implement later
	}

	@Test
	public void testCreateLaneSegment() throws Exception {
		LaneSegmentLinear lane = this.rsBuilder.createLaneSegment(2);
		
		assertTrue(lane instanceof LaneSegmentLinear);
		
		IVector startPoint = lane.getStartPoint();
		IVector endPoint = lane.getEndPoint();
		
		assertEquals(21.94028, startPoint.getComponent(0), 0.0001);
		assertEquals(32.48507, startPoint.getComponent(1), 0.0001);
		
		assertEquals(16.9402, endPoint.getComponent(0), 0.0001);
		assertEquals(52.4850, endPoint.getComponent(1), 0.0001);
	}

	@Test
	public void testGetOrder() {
		assertEquals(12, this.rsBuilder.getOrder());
	}

}
