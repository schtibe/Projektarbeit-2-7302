package test.environment;

import common.IVector;
import common.Vector;

public class getAngleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector test = new Vector(new float[]{0,-1});
		System.out.println(getAngle(test)/Math.PI);
	}
	
	private static float getAngle(IVector vector) {
		float angle = 0;
		float x = vector.getComponent(0) / vector.norm();
		float y = vector.getComponent(1) / vector.norm();
		if (x >= 0) {
			if (y >= 0) {
				angle = (float) Math.asin(y);
			} else {
				angle = (float) (2 * Math.PI - Math.asin(Math.abs(y)));
			}
		} else {
			if (y >= 0) {
				angle = (float) (Math.PI - Math.asin(y));
			} else {
				angle = (float) (Math.PI + Math.asin(Math.abs(y)));
			}
		}
		return angle;
	}

}
