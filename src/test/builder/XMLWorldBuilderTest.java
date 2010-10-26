package test.builder;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

import simulation.builder.IXMLWorldBuilder;
import simulation.builder.XMLWorldBuilder;
import environment.IRoad;

public class XMLWorldBuilderTest {
	
	IXMLWorldBuilder wBuilder;

	@Before
	public void setUp() throws Exception {
		Document document = new Document(); 
		Element world = new Element("world"); 
		
		Element road = new Element("road");
		road.setAttribute("id", "7");
		road.setAttribute("startX", "10");
		road.setAttribute("startY", "10");
		world.addContent(road);
		
		Element leftLanes = new Element("leftlanes"); 
		Element lane1 = new Element("lane");
		lane1.setAttribute("id", "1");
		lane1.setAttribute("width", "5");
		leftLanes.addContent(lane1);
		Element lane2 = new Element("lane");
		lane2.setAttribute("id", "2");
		lane2.setAttribute("width", "4");
		leftLanes.addContent(lane2);
		
		road.addContent(leftLanes);
		Element rightLanes = new Element("rightlanes");
		Element lane3 = new Element("lane");
		lane3.setAttribute("id", "5");
		lane3.setAttribute("width", "6");
		rightLanes.addContent(lane3);
		
		road.addContent(rightLanes);
		
		Element roadSegments = new Element("roadsegments");
		road.addContent(roadSegments);
		
		Element seg1 = new Element("roadsegment");
		seg1.setAttribute("order", "1");
		seg1.setAttribute("startX", "10");
		seg1.setAttribute("startY", "10");
		seg1.setAttribute("endX", "20");
		seg1.setAttribute("endY", "30");
		seg1.addContent(new Element("waypoint").
				setAttribute("id", "1"));
		
		Element seg2 = new Element("roadsegment");
		seg2.setAttribute("order", "2");
		seg2.setAttribute("startX", "25");
		seg2.setAttribute("startY", "35");
		seg2.setAttribute("endX", "50");
		seg2.setAttribute("endY", "35");
		
		roadSegments.addContent(seg1);
		roadSegments.addContent(seg2);
		
		Element junction = new Element("junction");
		junction.setAttribute("type", "CrossRoad");
		junction.setAttribute("id", "1");
		
		Element junctionRoads = new Element("roads");
		Element junctionRoad1 = new Element("road").setAttribute("id", "7");
		
		junctionRoads.addContent(junctionRoad1);
		junction.addContent(junctionRoads);
		
		world.addContent(junction);
		
		document.addContent(world);
		
		XMLOutputter x = new XMLOutputter();
	//System.out.println(x.outputString(document));

		this.wBuilder = new XMLWorldBuilder(document);
		this.wBuilder.generate();
	}

	@Test
	public void testGetRoads() {
		List<IRoad> roads = this.wBuilder.getRoads();
		assertEquals(1, roads.size());
	}

}
