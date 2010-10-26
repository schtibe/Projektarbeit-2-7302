package test.common;

import junit.framework.TestCase;

import org.junit.Test;

import common.Vector;

public class VectorTest extends TestCase {
	
	@Test
	public void testNormSimple (){
		Vector vec = new Vector(new float[]{0,4});
		assertEquals(4.0,vec.norm(),0.0000001);
	}
	
	@Test
	public void testNorm(){
		float[] comp1 = new float[]{0,1};
		float[] comp2 = new float[]{1,0};
		Vector vec1 = new Vector(comp1);
		Vector vec2 = new Vector(comp2);
		assertEquals(vec2.norm(),vec1.norm(),0);
	}
	@Test
	public void testCreation(){
		Vector vec1 = new Vector(null);
		Vector vec2 = new Vector(new float[]{0,0});
		assertEquals(vec1.norm(),vec2.norm(),0);
	}
	@Test 
	public void testAddSimple(){
		Vector vec1 = new Vector(new float[]{1,1});
		Vector vec2 = new Vector(new float[]{2,2});
		Vector vec3 = new Vector(new float[]{3,3});
		Vector vec4 = vec1.add(vec2);
		assertEquals(vec3.norm(),vec4.norm(),0.000001);
	}
	@Test 
	public void testAddComplex(){
		Vector vec1 = new Vector(new float[]{-1,-1});
		Vector vec2 = new Vector(new float[]{2,2});
		Vector vec3 = new Vector(new float[]{1,1});
		Vector vec4 = vec1.add(vec2);
		assertEquals (vec3.norm(),vec4.norm(),0.000001);
	}
	@Test
	public void testSubSimple(){
		Vector vec1 = new Vector(new float[]{1,1});
		Vector vec2 = new Vector(new float[]{2,2});
		Vector vec3 = new Vector(new float[]{3,3});
		Vector vec4 = vec3.sub(vec1);
		assertEquals(vec2.norm(),vec4.norm(),0.000001);
	}
	@Test 
	public void testSubComplex(){
		Vector vec1 = new Vector(new float[]{-1,-1});
		Vector vec2 = new Vector(new float[]{2,2});
		Vector vec3 = new Vector(new float[]{1,1});
		Vector vec4 = vec3.sub(vec1);
		assertEquals (vec2.norm(),vec4.norm(),0.000001);
	}
	@Test 
	public void testCross(){
		Vector vec1 = new Vector(new float[]{1,1});
		Vector vec2 = new Vector(new float[]{2,2});
		Vector vec3 = vec2.cross(vec1);
		Vector vec4 = new Vector(new float[]{});
		assertEquals (vec3.norm(),vec4.norm(),0.000001);
	}
	@Test 
	public void testDot(){
		Vector vec1 = new Vector(new float[]{1,1});
		Vector vec2 = new Vector(new float[]{2,2});
		float scalar = vec2.dot(vec1);
		assertEquals (scalar,4,0.000001);
	}
	@Test
	public void testComponents (){
		Vector vec1 = new Vector(new float[]{1,2});
		float toTest = vec1.getComponent(0);
		toTest += vec1.getComponent(1);
		assertEquals (toTest,3,0.000001);
	}
	public void testNormalize (){
		Vector vec1 = new Vector(new float[]{2,2});
		Vector vec2 = vec1.normalize();
		assertEquals (1.0,vec2.norm(),0.00001);
	}
	public void testMultiply (){
		Vector vec1 = new Vector(new float[]{1,1});
		Vector vec2 = vec1.multiply((float)3.4);
		assertEquals(vec2.getComponent(0),3.4,0.00001);
		assertEquals(vec2.getComponent(1),3.4,0.00001);
	}
}
