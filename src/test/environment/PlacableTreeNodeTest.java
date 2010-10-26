package test.environment;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import environment.PlacableTreeNode;

public class PlacableTreeNodeTest extends TestCase {
	
	private PlacableTreeNode root;
	
	@Before
	public void setUp(){
		
		this.root = new PlacableTreeNode(0, 0, 1400, 800, 8);
		
	}

	@Test 
	public void testAddElement() {
		root.add(null);
	}

}
