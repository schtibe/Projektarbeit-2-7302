package test.builder;


import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Test;

import simulation.builder.IXMLWorldBuilder;
import simulation.builder.XMLWorldBuilder;

import common.GlobalConstants;

/**
 * This is not a real test, it only looks if the schema is implemented correctly

 */
public class XMLSchemaTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testSchema() throws JDOMException {
		IXMLWorldBuilder wb = new XMLWorldBuilder(
				GlobalConstants.getInstance().getStreetXMLSchema());
	}

}
