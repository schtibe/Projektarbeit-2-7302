package test.builder;


import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import simulation.builder.XMLObjectBuilder;

import common.IVector;
import common.Vector;

public class XMLObjectBuilderTest {
	
	private class TestObjectBuilder extends XMLObjectBuilder {

		public TestObjectBuilder(Element e) {
			super(e);
		}
		
		public IVector getPerpVectorWrapper(IVector direction) {
			return this.getPerpVector(direction);
		}
	}
	
	TestObjectBuilder oBuilder;

	@Before
	public void setUp() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		String data = "<root></root>";
		Document document = builder.build(
				new ByteArrayInputStream(data.getBytes())
		);
		
		
		this.oBuilder = new TestObjectBuilder(document.getRootElement());
	}

	@Test
	public void testGetRoadOffset() {
		IVector perp;
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{1, 0})
		);
		assertEquals(0, perp.getComponent(0), 0.001);
		assertEquals(-1, perp.getComponent(1), 0.001);
		
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{0, -1})
		);
		assertEquals(-1, perp.getComponent(0), 0.001);
		assertEquals(0, perp.getComponent(1), 0.001);
		
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{-1, 0})
		);
		assertEquals(0, perp.getComponent(0), 0.001);
		assertEquals(1, perp.getComponent(1), 0.001);
		
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{2, 3})
		);
		assertEquals(3, perp.getComponent(0), 0.001);
		assertEquals(-2, perp.getComponent(1), 0.001);
		
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{3, -2})
		);
		assertEquals(-2, perp.getComponent(0), 0.001);
		assertEquals(-3, perp.getComponent(1), 0.001);
		
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{-2, -3})
		);
		assertEquals(-3, perp.getComponent(0), 0.001);
		assertEquals(2, perp.getComponent(1), 0.001);
		
		perp = this.oBuilder.getPerpVectorWrapper(
				new Vector(new float[]{-3, 2})
		);
		assertEquals(2, perp.getComponent(0), 0.001);
		assertEquals(3, perp.getComponent(1), 0.001);
	}
}