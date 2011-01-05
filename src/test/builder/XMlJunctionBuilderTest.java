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

import simulation.builder.IXMLWorldBuilder;
import simulation.builder.XMLJunctionBuilder;

import common.IVector;

import environment.CrossRoads;
import environment.IJunction;
import environment.IRoad;
import environment.IWayPoint;

public class XMlJunctionBuilderTest {
	private class TestJunctionBuilder extends XMLJunctionBuilder {
		
		public TestJunctionBuilder(Element e, 
				IXMLWorldBuilder worldBuilder) {
			super(e, worldBuilder);
		}

		public void createJunctionWrapper() {
			this.createJunction();
		}
		
		public List<Integer> readRoadsWrapper() {
			return this.readRoads();
		}
	}
	
	TestJunctionBuilder jBuilder;

	@Before
	public void setUp() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		
		String data = 
			"<root>" +
				"<junction type=\"CrossRoad\" id=\"1\">" +
					"<roads>" +
						"<road id=\"1\" />" +
						"<road id=\"7\" />" +
						"<road id=\"18\" />" +
					"</roads>" + 
				"</junction>" +
			"</root>";
		
		Document document = builder.build(
				new ByteArrayInputStream(data.getBytes())
		);
		
		IXMLWorldBuilder worldBuilder = new IXMLWorldBuilder() {
			
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
		
		this.jBuilder = new TestJunctionBuilder(document.getRootElement().
				getChild("junction"), worldBuilder);
	}

	@Test
	public void testCreateJunction() {
		this.jBuilder.createJunctionWrapper();
		assertTrue(this.jBuilder.getJunction() instanceof CrossRoads);
	}

	@Test
	public void testReadRoads() {
		List<Integer> roads = this.jBuilder.readRoadsWrapper();
		
		assertEquals(3, roads.size());
		assertTrue(roads.get(0).equals(1));
		assertTrue(roads.get(1).equals(7));
		assertTrue(roads.get(2).equals(18));
	}

	@Test
	public void testGetJunction() {
		assertTrue(this.jBuilder.getJunction() instanceof IJunction);
	}
}
