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

import common.IVector;

import simulation.builder.IXMLWorldBuilder;
import simulation.builder.XMLJunctionBuilder;
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setCoordinates(IVector coordinates) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public IVector[] getWorldBoundaries() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public float getScale() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<IRoad> getRoads() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<IJunction> getJunctions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<IWayPoint> getAllWayPoints() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void generate() throws Exception {
				// TODO Auto-generated method stub
				
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